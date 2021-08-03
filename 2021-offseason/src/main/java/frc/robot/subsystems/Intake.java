// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private TalonSRX intake;
  private DoubleSolenoid pnuematic;

  public Intake() {
    intake = new TalonSRX(0);
    intake.setInverted(false);
    pnuematic = new DoubleSolenoid(Constants.kIntake.SOLENOID_FRONT, Constants.kIntake.SOLENOID_BACK);

    pnuematic.set(DoubleSolenoid.Value.kOff);
    pnuematic.set(DoubleSolenoid.Value.kForward);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }  

  public void startIntake() {
    intake.set(ControlMode.PercentOutput, Constants.kIntake.INTAKE_SPEED);
  }
  public void stopIntake() {
    intake.set(ControlMode.PercentOutput, 0);
  }
  public void startInverted() {
    intake.set(ControlMode.PercentOutput, -Constants.kIntake.INTAKE_SPEED);
  }

}
