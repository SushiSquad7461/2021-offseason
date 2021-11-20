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
    
    initializeMotor(winch_left, true);
    initializeMotor(winch_right, false);
  }
  public void initializeMotor(CANSparkMax motor, boolean inverted) {
    motor.restoreFactoryDefaults();
    motor.setInverted(inverted);
    motor.setOpenLoopRampRate(Constants.kClimb.OPEN_LOOP_RAMP);
    motor.setSmartCurrentLimit(Constants.kClimb.CURRENT_LIMIT);
    motor.burnFlash();
  }
  public void fastClimbForward() {
    //SmartDashboard.putBoolean("running climb", true);
    winch_left.set(-Constants.kClimb.MAX_SPEED);
    winch_right.set(-Constants.kClimb.MAX_SPEED);
  }

  public void fastClimbReverse() {
    winch_left.set(Constants.kClimb.MAX_SPEED);
    winch_right.set(Constants.kClimb.MAX_SPEED);
  }

  public void slowClimbForward() {
    winch_left.set(-Constants.kClimb.SLOW_SPEED);
    winch_right.set(-Constants.kClimb.SLOW_SPEED);
  }

  public void slowClimbReverse() {
    winch_left.set(Constants.kClimb.SLOW_SPEED);
    winch_right.set(Constants.kClimb.SLOW_SPEED);
  }

  public void stop() {
    //SmartDashboard.putBoolean("running climb", false);
    winch_left.set(0);
    winch_right.set(0);
  }

  public void climbAnalog(double power) {
    winch_left.set(-power);
    winch_right.set(-power);
  }

  @Override
  public void periodic() { }

  @Override
  public void simulationPeriodic() { }
}
