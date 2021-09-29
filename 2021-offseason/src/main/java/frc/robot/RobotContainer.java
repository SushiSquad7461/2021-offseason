// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.WaitCommand;
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
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Flywheel s_flywheel = new Flywheel();
  private final Hopper s_hopper = new Hopper();
  private final Hood s_hood = new Hood();
  private final Intake s_intake = new Intake();
  private final Climb s_climb = new Climb();
  private final Drivetrain s_drivetrain;

  private final XboxController driveController = new XboxController(Constants.kOI.DRIVE_CONTROLLER);
  private final XboxController operatorController = new XboxController(Constants.kOI.OPERATOR_CONTROLLER);

  private final AutoShoot c_autoShoot, c_autoShoot2, c_autoShoot3;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    s_drivetrain = new Drivetrain();
    // s_drivetrain.setDefaultCommand(new RunCommand(
    //   () -> s_drivetrain.curveDrive(OI.getTriggers(driveController), 
    //     OI.getLeftStick(driveController),
    //     driveController.getXButton()),
    //   s_drivetrain));

    // IF THIS IS PROBLEMATIC JUST COMMENT IT OUT AND USE BUMPERS
    s_climb.setDefaultCommand(new RunCommand(
      () -> s_climb.climbAnalog(OI.getRightStick(driveController)), s_climb)
    );

    c_autoShoot = new AutoShoot(s_flywheel, s_hopper, s_intake, s_hood);
    c_autoShoot2 = new AutoShoot(s_flywheel, s_hopper, s_intake, s_hood);
    c_autoShoot3 = new AutoShoot(s_flywheel, s_hopper, s_intake, s_hood);

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    // drive Y --> invert direction
    new JoystickButton(driveController, XboxController.Button.kY.value)
      .whenPressed(new InstantCommand(s_drivetrain::invertDirection, s_drivetrain));

    // drive B --> slow mode
    new JoystickButton(driveController, XboxController.Button.kB.value)
      .whenPressed(new InstantCommand(s_drivetrain::startSlow, s_drivetrain))
      .whenReleased(new InstantCommand(s_drivetrain::endSlow, s_drivetrain));

    // drive left bumper --> run intake + hopper (intake)
    new JoystickButton(driveController, XboxController.Button.kBumperLeft.value)
      .whenPressed(new ParallelCommandGroup(
        new InstantCommand(s_intake::startIntake, s_intake),
        new InstantCommand(s_hopper::moveForward, s_hopper)
      ))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(s_intake::stopIntake, s_intake),
        new InstantCommand(s_hopper::stopHopper, s_hopper)
      ));

    // drive right bumper --> extend intake
    // new JoystickButton(driveController, XboxController.Button.kBumperRight.value)
    //   .whenPressed(new InstantCommand(s_intake::actuateIntake, s_intake));

    // drive A --> run hopper + kicker (shoot)
    new JoystickButton(driveController, XboxController.Button.kA.value)
      .whenPressed(new ParallelCommandGroup(
        new InstantCommand(s_intake::startIntake, s_intake),
        new InstantCommand(s_hopper::shootForward, s_hopper)
      ))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(s_intake::stopIntake, s_intake),
        new InstantCommand(s_hopper::stopHopper, s_hopper)
      ));

    // drive back button --> slow climb reverse
    new JoystickButton(driveController, XboxController.Button.kBack.value)
      .whenPressed(new InstantCommand(s_climb::slowClimbReverse, s_climb))
      .whenReleased(new InstantCommand(s_climb::stop, s_climb));

    // drive start button --> slow climb forward
    new JoystickButton(driveController, XboxController.Button.kStart.value)
      .whenPressed(new InstantCommand(s_climb::slowClimbForward, s_climb))
      .whenReleased(new InstantCommand(s_climb::stop, s_climb));

    // operator A --> rev flywheel
    //new JoystickButton(operatorController, XboxController.Button.kA.value)
    new JoystickButton(driveController, XboxController.Button.kBumperRight.value)
      .whenPressed(new RunCommand(() -> s_flywheel.setGoal(Constants.kFlywheel.GOAL), s_flywheel))
      .whenReleased(new RunCommand(() -> s_flywheel.setGoal(0), s_flywheel));
    
    // operator B -> reverse intake
    new JoystickButton(operatorController, XboxController.Button.kB.value)
      .whenPressed(new InstantCommand(s_intake::startReverse, s_intake))
      .whenReleased(new InstantCommand(s_intake::stopIntake, s_intake)); 

    // operator Y --> reverse hopper
    new JoystickButton(operatorController, XboxController.Button.kY.value)
      .whenPressed(new InstantCommand(s_hopper::moveBackward, s_hopper))
      .whenReleased(new InstantCommand(s_hopper::stopHopper, s_hopper));
    
    // operator X --> reverse kicker
    new JoystickButton(operatorController, XboxController.Button.kX.value)
      .whenPressed(new InstantCommand(s_hopper::shootBackward, s_hopper))
      .whenReleased(new InstantCommand(s_hopper::stopHopper, s_hopper));

    // operator click right joystick --> retract intake
    new JoystickButton(driveController, XboxController.Button.kStickRight.value)
      .whenPressed(new InstantCommand(s_intake::retractIntake, s_intake));
    
    // operator start button --> increment hood angle up
    new JoystickButton(operatorController, XboxController.Button.kStart.value)
      .whenPressed(new InstantCommand(s_hood::incrementUp, s_hood));

    // operator back button --> increment hood angle down
    new JoystickButton(operatorController, XboxController.Button.kBack.value)
      .whenPressed(new InstantCommand(s_hood::incrementDown, s_hood));
    
    // operator right bumper --> forward climb
    new JoystickButton(operatorController, XboxController.Button.kBumperRight.value)
      .whenPressed(new InstantCommand(s_climb::fastClimbForward, s_climb))
      .whenReleased(new InstantCommand(s_climb::stop, s_climb));
    
    // operator left bumper --> backward climb
    new JoystickButton(operatorController, XboxController.Button.kBumperLeft.value)
      .whenPressed(new InstantCommand(s_climb::fastClimbReverse, s_climb))
      .whenReleased(new InstantCommand(s_climb::stop, s_climb));

    // operator click left joystick --> rezero and reset hood
    // press will drop hood, release will set zero and then reset setpoint
    new JoystickButton(operatorController, XboxController.Button.kStickLeft.value)
      .whenPressed(new InstantCommand(s_hood::stopHood, s_hood))
      .whenReleased(new SequentialCommandGroup(
        new InstantCommand(s_hood::setZero, s_hood),
        new InstantCommand(s_hood::initLineSetpoint, s_hood)
      ));
  }

  // rumbles operator controller to flywheel speed lol
  public void setRumbles() {
    driveController.setRumble(GenericHID.RumbleType.kRightRumble, s_flywheel.goalPercentage());
    operatorController.setRumble(GenericHID.RumbleType.kRightRumble, s_flywheel.goalPercentage());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  // shoot and move off init
  public SequentialCommandGroup getAutonomousCommand() {
    return new SequentialCommandGroup(
      new InstantCommand(() -> s_hopper.shootBackward()).withTimeout(0.5),
      new InstantCommand(() -> s_hopper.stopHopper()),
      c_autoShoot.withTimeout(8.00007461),
      new RunCommand(() -> s_drivetrain.curveDrive(-0.3, 0, false), s_drivetrain).withTimeout(2)
    );
  }

  // move off init
  public SequentialCommandGroup getSecondAutonomousCommand() {
    return new SequentialCommandGroup(
      new InstantCommand(s_intake::actuateIntake, s_intake),
      new RunCommand(() -> s_drivetrain.curveDrive(-0.3, 0, false), s_drivetrain).withTimeout(2)
    );
  }

  // shoot without moving
  public SequentialCommandGroup getThirdAutonomousCommand() {
    return new SequentialCommandGroup(c_autoShoot2.withTimeout(6));
  }

  // nothing
  public SequentialCommandGroup getFourthAutonomousCommand() {
    return new SequentialCommandGroup(
      new InstantCommand(s_intake::actuateIntake, s_intake)
    );
  }

  // delayed auto
  public SequentialCommandGroup getFifthAutonomousCommand() {
    return new SequentialCommandGroup(
      new RunCommand(() -> s_drivetrain.curveDrive(0, 0, false), s_drivetrain).withTimeout(4),  
      new InstantCommand(s_flywheel::setToGoal, s_flywheel),
      new RunCommand(() -> s_drivetrain.curveDrive(0, 0, false), s_drivetrain).withTimeout(4),  
      //new WaitCommand(10),
      c_autoShoot3.withTimeout(4),
      new RunCommand(() -> s_drivetrain.curveDrive(-0.3, 0, false), s_drivetrain).withTimeout(1)
    );
  }
}
 