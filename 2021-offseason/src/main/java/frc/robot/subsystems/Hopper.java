// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hopper extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final VictorSPX left, right, kicker;
  
  public Hopper() {
    left = new VictorSPX(Constants.kHopper.LEFT_ID);
    right = new VictorSPX(Constants.kHopper.RIGHT_ID);
    kicker = new VictorSPX(Constants.kHopper.KICKER_ID);

    right.follow(left);
    kicker.follow(left);

    left.setInverted(Constants.kHopper.INVERTED);
    right.setInverted(Constants.kHopper.INVERTED);
    kicker.setInverted(Constants.kHopper.INVERTED);
  }

  public void moveForward(){
    left.set(ControlMode.PercentOutput, Constants.kHopper.FORWARD_SPEED);
  }

  public void moveBackward(){
    left.set(ControlMode.PercentOutput, Constants.kHopper.REVERSE_SPEED);
  }

  public void stop(){
    left.set(ControlMode.PercentOutput, 0);
  }
}
