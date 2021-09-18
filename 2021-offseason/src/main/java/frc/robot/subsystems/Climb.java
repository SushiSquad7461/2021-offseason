// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  private final CANSparkMax winch_right, winch_left;

  public Climb() {
    winch_right = new CANSparkMax(Constants.kClimb.WINCH_RIGHT, Constants.kClimb.MOTOR_TYPE);
    winch_left = new CANSparkMax(Constants.kClimb.WINCH_LEFT, Constants.kClimb.MOTOR_TYPE);
    
    winch_left.restoreFactoryDefaults();
    winch_right.restoreFactoryDefaults();

    winch_left.follow(winch_right);

    winch_left.setInverted(Constants.kClimb.WINCH_INVERTED);
    winch_right.setInverted(!Constants.kClimb.WINCH_INVERTED);

    winch_left.setOpenLoopRampRate(Constants.kClimb.OPEN_LOOP_RAMP);
    winch_right.setOpenLoopRampRate(Constants.kClimb.OPEN_LOOP_RAMP);

    winch_left.setSmartCurrentLimit(Constants.kClimb.CURRENT_LIMIT);
    winch_right.setSmartCurrentLimit(Constants.kClimb.CURRENT_LIMIT);

  }

  public void start() {
    SmartDashboard.putBoolean("running climb", true);
    winch_left.set(Constants.kClimb.MAX_SPEED);
  }

  public void stop() {
    SmartDashboard.putBoolean("running climb", false);
    winch_left.set(0);
  }

  public void reverse() {
    winch_left.set(-Constants.kClimb.MAX_SPEED);
  }

  @Override
  public void periodic() { }

  @Override
  public void simulationPeriodic() { }
}
