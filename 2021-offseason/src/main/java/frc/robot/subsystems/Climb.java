// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;

public class Climb extends SubsystemBase {
  private final CANSparkMax winch_right, winch_left;

  public Climb() {
    controller = new ProfiledPIDController(
      Constants.Flywheel.kP,
      Constants.Flywheel.kI,
      Constants.Flywheel.kD,
      new Constraints(
          Constants.Flywheel.MAX_ACCELERATION,
          Constants.Flywheel.MAX_JERK
    ));

    winch_right = new CANSparkMax(Constants.kCLimb.WINCH_RIGHT, Constants.kCLimb.MOTOR_TYPE);
    winch_left = new CANSparkMax(Constants.kCLimb.WINCH_LEFT, Constants.kCLimb.MOTOR_TYPE);

    winch_left.follow(winch_right);

    winch_left.setInverted(Constants.kCLimb.WINCH_INVERTED);
    winch_right.setInverted(!Constants.kCLimb.WINCH_INVERTED);

    winch_left.setSmartCurrentLimit(Constants.kCLimb.CURRENT_LIMIT);
    winch_right.setSmartCurrentLimit(Constants.kCLimb.CURRENT_LIMIT);
  }

  public void start() {
    winch_left.set(Constants.kCLimb.MAX_SPEED);
  }

  public void stop() {
    winch_left.set(0);
  }

  public void reverse() {
    winch_left.set(-Constants.kCLimb.MAX_SPEED);
  }

  @Override
  public void periodic() { }

  @Override
  public void simulationPeriodic() { }
}
