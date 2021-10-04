// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShiftControl extends SubsystemBase {
  private boolean shiftActive;
    /** Creates a new ExampleSubsystem. */
  public ShiftControl() {
      shiftActive = false;
  }

  public void enableShift(){
    shiftActive = true;
  }
  public void disableShift(){
    shiftActive = false;
  }

  public boolean getShiftState(){
    return shiftActive;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
