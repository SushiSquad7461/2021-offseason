// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hopper extends SubsystemBase {
  private final VictorSPX kicker;
  private final TalonSRX left, right, center;
  
  public Hopper() {
    left = new TalonSRX(Constants.kHopper.LEFT_ID);
    right = new TalonSRX(Constants.kHopper.RIGHT_ID);
    center = new TalonSRX(Constants.kHopper.CENTER_ID);
    kicker = new VictorSPX(Constants.kHopper.KICKER_ID);

    left.configFactoryDefault();
    right.configFactoryDefault();
    center.configFactoryDefault();
    kicker.configFactoryDefault();

    left.setNeutralMode(NeutralMode.Coast);
    right.setNeutralMode(NeutralMode.Coast);

    left.configPeakCurrentLimit(Constants.kHopper.CURRENT_LIMIT);
    right.configPeakCurrentLimit(Constants.kHopper.CURRENT_LIMIT);
    center.configPeakCurrentLimit(Constants.kHopper.CURRENT_LIMIT);
    //kicker.configPeakCurrentLimit(Constants.kHopper.CURRENT_LIMIT);

    left.setInverted(Constants.kHopper.INVERTED);
    right.setInverted(Constants.kHopper.INVERTED);
    center.setInverted(Constants.kHopper.INVERTED);
    kicker.setInverted(Constants.kHopper.INVERTED);
  }

  public void shootForward() {
    moveForward();
    moveWallsForward();
    kicker.set(ControlMode.PercentOutput, Constants.kHopper.KICKER_FORWARD);
  }

  public void shootBackward() {
    moveBackward();
    kicker.set(ControlMode.PercentOutput, Constants.kHopper.KICKER_REVERSE);
  }

  private void moveWallsForward() {
    left.set(ControlMode.PercentOutput, Constants.kHopper.LEFT_SPEED);
    right.set(ControlMode.PercentOutput, Constants.kHopper.RIGHT_SPEED);
  }
  public void moveForward() {
    center.set(ControlMode.PercentOutput, Constants.kHopper.FLOOR_SPEED);
  }

  public void moveBackward() {
    left.set(ControlMode.PercentOutput, Constants.kHopper.REVERSE_SPEED);
    right.set(ControlMode.PercentOutput, Constants.kHopper.REVERSE_SPEED);
    center.set(ControlMode.PercentOutput, Constants.kHopper.REVERSE_SPEED);
  }

  public void stopHopper() {
    left.set(ControlMode.PercentOutput, 0);
    right.set(ControlMode.PercentOutput, 0);
    center.set(ControlMode.PercentOutput, 0);
    kicker.set(ControlMode.PercentOutput, 0);
  }
}
