// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class AutoShoot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Flywheel flywheel;
  private final Hopper hopper;
  private final Intake intake;
  private final Hood hood;

  public AutoShoot(Flywheel flywheel, Hopper hopper, Intake intake, Hood hood) {
    this.flywheel = flywheel;
    this.hopper = hopper;
    this.intake = intake;
    this.hood = hood;
    addRequirements(flywheel, hopper, intake, hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hood.set(Constants.kHood.INIT_LINE_ANGLE);
    flywheel.setGoal(Constants.kFlywheel.GOAL);
    intake.actuateIntake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (flywheel.atSpeed()) {
      intake.startIntake();
      hopper.shootForward();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    flywheel.setGoal(0);
    intake.stopIntake();
    hopper.stopHopper();
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
