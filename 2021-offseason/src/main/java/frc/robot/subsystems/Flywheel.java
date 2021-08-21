// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.kFlywheel;

public class Flywheel extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public CANSparkMax flywheelLeft;
  public CANSparkMax flywheelRight;
  public final boolean inverted = false;
  public Flywheel() {
    flywheelLeft = new CANSparkMax(kFlywheel.LEFT_ID, kFlywheel.MOTOR_TYPE);
    flywheelLeft = new CANSparkMax(kFlywheel.RIGHT_ID, kFlywheel.MOTOR_TYPE);

    flywheelLeft.restoreFactoryDefaults();
    flywheelLeft.setSmartCurrentLimit(kFlywheel.CURRENT_LIMIT);
    flywheelRight.restoreFactoryDefaults();
    flywheelRight.setSmartCurrentLimit(kFlywheel.CURRENT_LIMIT);

    flywheelRight.follow(flywheelLeft, inverted);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  public void startFlywheel() {
    flywheelLeft.set(kFlywheel.SPEED);
  }
  public void stopFlywheel() {
    flywheelLeft.set(0);
  }
}
