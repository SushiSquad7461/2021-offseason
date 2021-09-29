// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Flywheel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Flywheel flywheel = new Flywheel();
  private final Hopper hopper = new Hopper();
  private final Hood hood = new Hood();
  private final Intake intake = new Intake();
  private final Climb climb = new Climb();
  private final Drivetrain drivetrain;

  private final XboxController driveController = new XboxController(Constants.kOI.DRIVE_CONTROLLER);
  private final XboxController operatorController = new XboxController(Constants.kOI.OPERATOR_CONTROLLER);

  private final AutoShoot c_autoShoot;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drivetrain = new Drivetrain();
    // drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.curveDrive(OI.getTriggers(driveController),
    //     OI.getLeftStick(driveController), driveController.getXButton()), drivetrain));

    // IF THIS IS PROBLEMATIC JUST COMMENT IT OUT AND USE BUMPERS
    climb.setDefaultCommand(
        new RunCommand(() -> climb.climbAnalog(Math.pow(OI.getTriggers(operatorController), 3)), climb));

    c_autoShoot = new AutoShoot(flywheel, hopper, intake, hood);

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // drive Y --> invert direction
    new JoystickButton(driveController, XboxController.Button.kY.value)
        .whenPressed(new InstantCommand(drivetrain::invertDirection, drivetrain));

    // drive B --> slow mode
    new JoystickButton(driveController, XboxController.Button.kB.value)
        .whenPressed(new InstantCommand(drivetrain::startSlow, drivetrain))
        .whenReleased(new InstantCommand(drivetrain::endSlow, drivetrain));

    // drive left bumper --> run intake + hopper (intake)
    new JoystickButton(driveController, XboxController.Button.kBumperLeft.value)
        .whenPressed(new ParallelCommandGroup(new InstantCommand(intake::startIntake, intake),
            new InstantCommand(hopper::moveForward, hopper)))
        .whenReleased(new ParallelCommandGroup(new InstantCommand(intake::stopIntake, intake),
            new InstantCommand(hopper::stopHopper, hopper)));

    // drive right bumper --> extend intake
    // new JoystickButton(driveController, XboxController.Button.kBumperRight.value)
    //     .whenPressed(new InstantCommand(intake::actuateIntake, intake));

    // drive A --> run hopper + kicker (shoot)
    new JoystickButton(driveController, XboxController.Button.kA.value)
        .whenPressed(new ParallelCommandGroup(new InstantCommand(intake::startIntake, intake),
            new InstantCommand(hopper::shootForward, hopper)))
        .whenReleased(new ParallelCommandGroup(new InstantCommand(intake::stopIntake, intake),
            new InstantCommand(hopper::stopHopper, hopper)));

    // drive back button --> slow climb reverse
    new JoystickButton(driveController, XboxController.Button.kBack.value)
        .whenPressed(new InstantCommand(climb::slowClimbReverse, climb))
        .whenReleased(new InstantCommand(climb::stop, climb));

    // drive start button --> slow climb forward
    new JoystickButton(driveController, XboxController.Button.kStart.value)
        .whenPressed(new InstantCommand(climb::fastClimbForward, climb))
        .whenReleased(new InstantCommand(climb::stop, climb));

    // operator A --> rev flywheel
    // new JoystickButton(operatorController, XboxController.Button.kA.value)
    // drive right bumper --> rev flywheel
    new JoystickButton(driveController, XboxController.Button.kBumperRight.value)
        .whenPressed(new RunCommand(() -> flywheel.setGoal(Constants.kFlywheel.GOAL), flywheel))
        .whenReleased(new RunCommand(() -> flywheel.setGoal(0), flywheel));

    // operator B -> reverse intake
    new JoystickButton(operatorController, XboxController.Button.kB.value)
        .whenPressed(new InstantCommand(intake::startReverse, intake))
        .whenReleased(new InstantCommand(intake::stopIntake, intake));

    // operator X --> reverse hopper
    new JoystickButton(operatorController, XboxController.Button.kX.value)
        .whenPressed(new InstantCommand(hopper::moveBackward, hopper))
        .whenReleased(new InstantCommand(hopper::stopHopper, hopper));

    // operator Y --> reverse kicker
    new JoystickButton(operatorController, XboxController.Button.kY.value)
        .whenPressed(new InstantCommand(hopper::shootBackward, hopper))
        .whenReleased(new InstantCommand(hopper::stopHopper, hopper));

    // operator click right joystick --> retract intake
    new JoystickButton(driveController, XboxController.Button.kStickRight.value)
        .whenPressed(new InstantCommand(intake::retractIntake, intake));

    // operator start button --> increment hood angle up
    new JoystickButton(operatorController, XboxController.Button.kStart.value)
        .whenPressed(new InstantCommand(hood::incrementUp, hood));

    // operator back button --> increment hood angle down
    new JoystickButton(operatorController, XboxController.Button.kBack.value)
        .whenPressed(new InstantCommand(hood::incrementDown, hood));

    // operator right bumper --> forward climb
    new JoystickButton(operatorController, XboxController.Button.kBumperRight.value)
        .whenPressed(new InstantCommand(climb::fastClimbForward, climb))
        .whenReleased(new InstantCommand(climb::stop, climb));

    // operator left bumper --> backward climb
    new JoystickButton(operatorController, XboxController.Button.kBumperLeft.value)
        .whenPressed(new InstantCommand(climb::fastClimbReverse, climb))
        .whenReleased(new InstantCommand(climb::stop, climb));

    // operator click left joystick --> rezero and reset hood
    // press will drop hood, release will set zero and then reset setpoint
    new JoystickButton(operatorController, XboxController.Button.kStickLeft.value)
        .whenPressed(new InstantCommand(hood::stopHood, hood)).whenReleased(new SequentialCommandGroup(
            new InstantCommand(hood::setZero, hood), new InstantCommand(hood::initLineSetpoint, hood)));
  }

  // rumbles operator controller to flywheel speed lol
  public void setOperatorRumble() {
    operatorController.setRumble(GenericHID.RumbleType.kRightRumble, flywheel.goalPercentage());
  }

  // rumbles drive controller when flywheel is at speed
  public void setDriveRumble() {
    if (flywheel.atSpeed()) {
      driveController.setRumble(GenericHID.RumbleType.kRightRumble, 1);
    } else {
      driveController.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  // shoot and move off init
  public SequentialCommandGroup getAutonomousCommand() {
    return new SequentialCommandGroup(c_autoShoot,
        new RunCommand(() -> drivetrain.curveDrive(0.3, 0, false), drivetrain).withTimeout(2));
  }

  // move off init
  public SequentialCommandGroup getSecondAutonomousCommand() {
    return new SequentialCommandGroup(new InstantCommand(intake::actuateIntake, intake),
        new RunCommand(() -> drivetrain.curveDrive(0.3, 0, false), drivetrain).withTimeout(2));
  }

  // shoot without moving
  public SequentialCommandGroup getThirdAutonomousCommand() {
    return new SequentialCommandGroup(c_autoShoot);
  }

  // nothing
  public SequentialCommandGroup getFourthAutonomousCommand() {
    return new SequentialCommandGroup(new InstantCommand(intake::actuateIntake, intake));
  }
}
