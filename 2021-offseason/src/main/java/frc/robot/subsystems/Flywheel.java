// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Constants;

public class Flywheel extends SubsystemBase {
  
  //fields
  private final CANSparkMax flywheelMain;
  private final CANSparkMax flywheelFollower;
  private final CANEncoder flywheelEncoder;
  private final ProfiledPIDController pidController;
  private final SimpleMotorFeedforward feedForward;

  public Flywheel() {

    flywheelMain = new CANSparkMax(Constants.kFlywheel.MAIN_ID, Constants.kFlywheel.MOTOR_TYPE);
    flywheelFollower = new CANSparkMax(Constants.kFlywheel.FOLLOWER_ID, Constants.kFlywheel.MOTOR_TYPE);
    flywheelEncoder = flywheelMain.getEncoder();
    flywheelMain.setOpenLoopRampRate(Constants.kFlywheel.OPEN_LOOP_RAMP);
    flywheelFollower.setOpenLoopRampRate(Constants.kFlywheel.OPEN_LOOP_RAMP);

    pidController = new ProfiledPIDController(
    Constants.kFlywheel.kP,
    Constants.kFlywheel.kI,
    Constants.kFlywheel.kD,
    new TrapezoidProfile.Constraints(
        Constants.kFlywheel.MAX_VELOCITY,
        Constants.kFlywheel.MAX_ACCELERATION
      )
    );

    feedForward = new SimpleMotorFeedforward(
      Constants.kFlywheel.kS,
      Constants.kFlywheel.kV,
      Constants.kFlywheel.kA
    );
    
    //basic configuring
    flywheelMain.restoreFactoryDefaults();
    flywheelFollower.restoreFactoryDefaults();
    
    flywheelMain.setSmartCurrentLimit(Constants.kFlywheel.CURRENT_LIMIT);
    flywheelFollower.setSmartCurrentLimit(Constants.kFlywheel.CURRENT_LIMIT);

    flywheelMain.setInverted(!Constants.kFlywheel.MAIN_INVERTED);
    flywheelFollower.setInverted(!Constants.kFlywheel.FOLLOWER_INVERTED);

    flywheelMain.setOpenLoopRampRate(Constants.kFlywheel.OPEN_LOOP_RAMP);
    flywheelFollower.setOpenLoopRampRate(Constants.kFlywheel.OPEN_LOOP_RAMP);

    //flywheelFollower.follow(flywheelMain);

    flywheelFollower.burnFlash();
    flywheelMain.burnFlash();

    flywheelMain.setIdleMode(IdleMode.kCoast);
    flywheelFollower.setIdleMode(IdleMode.kCoast);
    
    //SmartDashboard.putNumber(new Boolean(flywheelFollower.getInverted()).toString(), 69);


    pidController.setTolerance(0,Constants.kFlywheel.ERR_TOLERANCE);

  }

  @Override
  public void periodic() {
    double output = pidController.calculate(flywheelEncoder.getVelocity());
    useOutput(output, pidController.getSetpoint());

    SmartDashboard.putNumber("flywheel rpm", flywheelEncoder.getVelocity());
    SmartDashboard.putBoolean("at speed", atSpeed());
    // This method will be called once per scheduler run
    //SmartDashboard.putBoolean("Main motor inverted", flywheelMain.getInverted());
    //SmartDashboard.putBoolean("Second motor inverted", flywheelFollower.getInverted());
    //SmartDashboard.putNumber("main motor output", flywheelMain.getAppliedOutput());
    //SmartDashboard.putNumber("second motor output", flywheelFollower.getAppliedOutput());
    //SmartDashboard.putNumber("Main motor current", flywheelMain.getOutputCurrent());
    //SmartDashboard.putNumber("Second motor current", flywheelFollower.getOutputCurrent());
    //SmartDashboard.putNumber("rpm", flywheelEncoder.getVelocity());
    // //SmartDashboard.putNumber("main sticky faults", flywheelMain.getStickyFaults());
    // //SmartDashboard.putNumber("secondary sticky faults", flywheelFollower.getStickyFaults());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  //will automatically be called periodically
  protected void useOutput(double output, TrapezoidProfile.State setpoint) {
    //SmartDashboard.putNumber("velocity ff setpoint", setpoint.position);
    //SmartDashboard.putNumber("acceleration ff setpoint", setpoint.velocity);
    double fForward = feedForward.calculate(setpoint.position, setpoint.velocity)/(12*8);
    //SmartDashboard.putNumber("feedforward", fForward);
    if (pidController.getGoal().position == 0) {
      flywheelMain.set(0);
      flywheelFollower.set(0);
    } else {
      flywheelMain.set(output+fForward);
      flywheelFollower.set(output+fForward);
    }
  }

  protected double getMeasurement() {
    return flywheelMain.getEncoder().getVelocity();
  }

  public boolean atSpeed() {
    return flywheelEncoder.getVelocity() >= Constants.kFlywheel.GOAL * Constants.kFlywheel.GOAL_TOLERANCE;
  }

  public double goalPercentage() {
    return flywheelEncoder.getVelocity() / Constants.kFlywheel.GOAL;
  }

  public void setGoal(double goal) {
    //SmartDashboard.putNumber("Goal", goal);
    pidController.setGoal(goal);
  }

  public void setToGoal() {
    SmartDashboard.putBoolean("Revving flywheel", true);
    pidController.setGoal(Constants.kFlywheel.GOAL);
  }
  
  public void runShooter() {
    double speed = 1;
    flywheelMain.set(speed);
    flywheelFollower.set(speed);
  }
  
  public void stopShooter() {
    SmartDashboard.putBoolean("Revving flywheel", false);
    flywheelMain.set(0);
    flywheelFollower.set(0);
  }
}
