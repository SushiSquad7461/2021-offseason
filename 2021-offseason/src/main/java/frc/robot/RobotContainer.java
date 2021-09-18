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

  private final XboxController driveController = new XboxController(Constants.kOI.DRIVE_CONTROLLER);
  private XboxController operatorController = new XboxController(Constants.kOI.OPERATOR_CONTROLLER);

  private final Drivetrain s_drivetrain;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    s_drivetrain = new Drivetrain();
    // Configure the button bindings
    s_drivetrain.setDefaultCommand(new RunCommand(
      () -> s_drivetrain.curveDrive(OI.getTriggers(driveController), 
        OI.getLeftStick(driveController),
        driveController.getXButton()),
      s_drivetrain)); 
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  //Assign operator controller buttons A and Y to startIntake and startReverse respectively
  private void configureButtonBindings() {

    // drive Y --> invert direction
    new JoystickButton(driveController, XboxController.Button.kY.value)
      .whenPressed(new InstantCommand(s_drivetrain::invertDirection, s_drivetrain));

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
    new JoystickButton(driveController, XboxController.Button.kBumperRight.value)
      .whenPressed(new InstantCommand(s_intake::actuateIntake, s_intake));

    // drive A --> run hopper + kicker (shoot)
    new JoystickButton(driveController, XboxController.Button.kA.value)
      .whenPressed(new ParallelCommandGroup(
        //new InstantCommand(s_intake::startIntake, s_intake),
        new InstantCommand(s_hopper::shootForward, s_hopper)
      ))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(s_intake::stopIntake, s_intake),
        new InstantCommand(s_hopper::stopHopper, s_hopper)
      ));

    // operator A --> rev flywheel (change to trig maybe)
    new JoystickButton(operatorController, XboxController.Button.kA.value)
      .whenPressed(new RunCommand(() -> s_flywheel.setGoal(Constants.kFlywheel.GOAL), s_flywheel))
      .whenReleased(new RunCommand(() -> s_flywheel.setGoal(0), s_flywheel));
    
    // operator B -> reverse intake
    new JoystickButton(operatorController, XboxController.Button.kB.value)
      .whenPressed(new InstantCommand(s_intake::startReverse, s_intake))
      .whenReleased(new InstantCommand(s_intake::stopIntake, s_intake)); 

    // operator X --> reverse hopper
    new JoystickButton(operatorController, XboxController.Button.kX.value)
      .whenPressed(new InstantCommand(s_hopper::moveBackward, s_hopper))
      .whenReleased(new InstantCommand(s_hopper::stopHopper, s_hopper));
    
    // operator Y --> reverse kicker
    new JoystickButton(operatorController, XboxController.Button.kY.value)
      .whenPressed(new InstantCommand(s_hopper::shootBackward, s_hopper))
      .whenReleased(new InstantCommand(s_hopper::stopHopper, s_hopper));
  
    // operator left bumper --> retract intake
    /*new JoystickButton(operatorController, XboxController.Button.kBumperLeft.value)
      .whenPressed(new InstantCommand(() -> s_intake.retractIntake(), s_intake));*/
    
    // operator pad up --> increment hood angle up
    new JoystickButton(operatorController, XboxController.Button.kBack.value)
      .whenPressed(new InstantCommand(s_hood::incrementUp, s_hood));

    // operator pad down --> increment hood angle down
    new JoystickButton(operatorController, XboxController.Button.kStart.value)
      .whenPressed(new InstantCommand(s_hood::incrementDown, s_hood));
    
    new JoystickButton(operatorController, XboxController.Button.kBumperRight.value)
      .whenPressed(new RunCommand(s_climb::start, s_climb))
      .whenReleased(new RunCommand(s_climb  ::stop, s_climb));
    
    new JoystickButton(operatorController, XboxController.Button.kBumperLeft.value)
      .whenPressed(new RunCommand(s_climb::reverse, s_climb))
      .whenReleased(new RunCommand(s_climb::stop, s_climb));

          //new JoystickButton(driveController, XboxController.Button.kY.value)
    //  .whenPressed(new RunCommand(m_hood::setZero, m_hood));
    // new JoystickButton(operatorController, XboxController.Button.kBumperLeft.value)
    //   .whenPressed(new InstantCommand(s_hood::incrementUp, s_hood));
    // //  .whenReleased(new RunCommand(m_hood::stopHood,m_hood));
    // new JoystickButton(operatorController, XboxController.Button.kBumperRight.value)
    //   .whenPressed(new InstantCommand(s_hood::incrementDown, s_hood));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
 