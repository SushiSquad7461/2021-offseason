// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private VictorSPX intakeTalon;
  private DoubleSolenoid solenoid;

  // sparks for testing
  private CANSparkMax test_spark_one;
  private CANSparkMax test_spark_two;

  public Intake() {
    intakeTalon = new VictorSPX(Constants.kIntake.INTAKE_PORT);
    intakeTalon.setInverted(Constants.kIntake.INVERTED);
    solenoid = new DoubleSolenoid(Constants.kIntake.PCM_ID, Constants.kIntake.SOLENOID_FRONT, Constants.kIntake.SOLENOID_BACK);

    test_spark_one = new CANSparkMax(Constants.kIntake.TEST_SPARK_PORT, MotorType.kBrushless);
    test_spark_two = new CANSparkMax(Constants.kIntake.TEST_SPARK_PORT_SECOND, MotorType.kBrushless);
    test_spark_one.setInverted(false);
    test_spark_two.setInverted(false);

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
    moveIntake(Constants.kIntake.INTAKE_SPEED);
  }

  public void stopIntake() {
    moveIntake(0);
  }

  public void startReverse() {
    moveIntake(-Constants.kIntake.INTAKE_SPEED);
  }

  public void moveIntake(double velocity) {
    intakeTalon.set(ControlMode.PercentOutput, velocity);
  }

  public void actuateIntake() {
    solenoid.set(Value.kForward);
  }

  public void retractIntake() {
    solenoid.set(Value.kReverse);
  }
}
