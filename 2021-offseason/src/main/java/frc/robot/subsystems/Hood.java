package frc.robot.subsystems;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.team254.lib.util.InterpolatingDouble;
import com.team254.lib.util.InterpolatingTreeMap;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;

public class Hood extends SubsystemBase {
  private final CANSparkMax hoodMain;
  private final CANEncoder hoodEncoder;
  private final CANPIDController hoodController;
  private InterpolatingTreeMap closeTreeMap;
  private InterpolatingTreeMap farTreeMap;
  private PhotonCamera camera;
  private double currentDegree = 0;
  //private final DigitalInput limitswitch = new DigitalInput(Constants.kHood.LIMIT_PORT);
  private boolean isTaring = false;
  private double initialSetpoint;
  
  public Hood(PhotonCamera camera) {
    this.camera = camera;
    //SmartDashboard.putNumber("kP", Constants.kHood.kP);
    //SmartDashboard.putNumber("kD", Constants.kHood.kD);
    this.hoodMain = new CANSparkMax(Constants.kHood.MOTOR_ID, Constants.kHood.MOTOR_TYPE);
    hoodMain.setInverted(true);
    hoodMain.setIdleMode(CANSparkMax.IdleMode.kCoast);
    this.hoodEncoder = this.hoodMain.getEncoder();
    this.hoodController = this.hoodMain.getPIDController();
    this.hoodController.setOutputRange(-Constants.kHood.MAX_SPEED, Constants.kHood.MAX_SPEED);
    this.hoodController.setP(Constants.kHood.kP);
    this.hoodController.setI(Constants.kHood.kI);
    this.hoodController.setD(Constants.kHood.kD);
    this.camera = new PhotonCamera("photonvision");
    //initialSetpoint = hoodEncoder.getPosition();
    initialSetpoint = Constants.kHood.INIT_LINE_ANGLE;
    set(initialSetpoint);
    
    //setZero();
  }
  @Override
  public void periodic() {
    boolean targets = camera.getLatestResult().hasTargets();
    if (targets) {
      SmartDashboard.putNumber("Yaw", camera.getLatestResult().getBestTarget().getYaw());
      SmartDashboard.putNumber("Pitch", camera.getLatestResult().getBestTarget().getPitch());
    } else {
      SmartDashboard.putNumber("Yaw", -69);
      SmartDashboard.putNumber("Pitch", -69);
    }
    SmartDashboard.putBoolean("targets", targets);
    
    //SmartDashboard.putNumber("hood applied output", hoodMain.getAppliedOutput());
    //SmartDashboard.putNumber("hood current", hoodMain.getOutputCurrent());
    SmartDashboard.putNumber("hood angle", hoodEncoder.getPosition());
    SmartDashboard.putBoolean("at angle", atAngle());
    //if (isTaring) tareHoodPeriodic();
  }

  

  public void setZero() {
    // isTaring = true;
    currentDegree = 0;
    initialSetpoint = hoodEncoder.getPosition();
  }
  /*
  private void tareHoodPeriodic() {
    if (limitswitch.get()) {
      hoodMain.set(0);
      currentDegree = 0;
      isTaring = false;
      initialSetpoint = hoodEncoder.getPosition();
    }
    else hoodMain.set(Constants.kHood.MAX_SPEED);
  } */

  public void set(double setpoint) {
    this.hoodController.setReference(setpoint + Constants.kHood.kFF, ControlType.kPosition);
    //SmartDashboard.putNumber("hood setpoint", setpoint);
  }
  public void incrementUp() {
    increaseSetpoint(Constants.kHood.HOOD_INCREMENT);
  }
  public void incrementDown() {
    increaseSetpoint(-Constants.kHood.HOOD_INCREMENT);
  }
  public void increaseSetpoint(double amount) {
    currentDegree+=amount;
    currentDegree=Math.max(0, currentDegree);
    set(currentDegree+initialSetpoint);
  }
  public void setSetpoint(double setpoint) {
    increaseSetpoint(setpoint-currentDegree);
  } 

  public void initLineSetpoint() {
    set(Constants.kHood.INIT_LINE_ANGLE);
  }

  public void runHood() {
    hoodMain.set(0.2);
    //SmartDashboard.putString("hood", "runninghood");
  }

  public void stopHood() {
    hoodMain.set(0);
    //SmartDashboard.putString("hood", "notrunninhood");
  }

  public boolean atAngle() {
    double hoodPosition = this.hoodEncoder.getPosition();
    double lowerBound = Constants.kHood.INIT_LINE_ANGLE * Constants.kHood.ANGLE_TOLERANCE;
    double upperBound = Constants.kHood.INIT_LINE_ANGLE * (2 - Constants.kHood.ANGLE_TOLERANCE);

    return hoodPosition >= lowerBound && hoodPosition <= upperBound;
  }
}
