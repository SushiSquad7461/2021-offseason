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

  private TalonSRX intakeTalon;
  private DoubleSolenoid solenoid;

  public Intake() {
    intakeTalon = new TalonSRX(Constants.kIntake.INTAKE_PORT);
    intakeTalon.setInverted(Constants.kIntake.INVERTED);
    solenoid = new DoubleSolenoid(Constants.kIntake.SOLENOID_FRONT, Constants.kIntake.SOLENOID_BACK);

    solenoid.set(DoubleSolenoid.Value.kOff);
    solenoid.set(DoubleSolenoid.Value.kForward);
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
    MoveIntake(Constants.kIntake.INTAKE_SPEED);
  }

  public void stopIntake() {
    MoveIntake(0);
  }

  public void startReverse() {
    MoveIntake(-Constants.kIntake.INTAKE_SPEED);
  }

  public void MoveIntake(double velocity) {
    intakeTalon.set(ControlMode.PercentOutput, velocity);
  }
}
