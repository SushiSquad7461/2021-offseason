// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;

public class Drivetrain extends SubsystemBase {

  private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
  private final DifferentialDrive diffDrive;
  private int angleInvert;
  private boolean slow;
  private final PhotonCamera camera;

  public Drivetrain() {
    frontLeft = new CANSparkMax(Constants.kDrivetrain.FRONT_LEFT_ID, Constants.kDrivetrain.MOTOR_TYPE);
    frontRight = new CANSparkMax(Constants.kDrivetrain.FRONT_RIGHT_ID, Constants.kDrivetrain.MOTOR_TYPE);
    backLeft = new CANSparkMax(Constants.kDrivetrain.BACK_LEFT_ID, Constants.kDrivetrain.MOTOR_TYPE);
    backRight = new CANSparkMax(Constants.kDrivetrain.BACK_RIGHT_ID, Constants.kDrivetrain.MOTOR_TYPE);

    frontLeft.restoreFactoryDefaults();
    frontRight.restoreFactoryDefaults();
    backLeft.restoreFactoryDefaults();
    backRight.restoreFactoryDefaults();

    diffDrive = new DifferentialDrive(frontLeft, frontRight);
    // front motors are controlled, others follow corresponding
    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    angleInvert = 1;
    slow = false;

    frontLeft.setInverted(Constants.kDrivetrain.DRIVE_INVERTED);
    frontRight.setInverted(Constants.kDrivetrain.DRIVE_INVERTED);
    backLeft.setInverted(Constants.kDrivetrain.DRIVE_INVERTED);
    backRight.setInverted(Constants.kDrivetrain.DRIVE_INVERTED);

    frontLeft.setOpenLoopRampRate(Constants.kDrivetrain.OPEN_LOOP_RAMP);
    frontRight.setOpenLoopRampRate(Constants.kDrivetrain.OPEN_LOOP_RAMP);
    backLeft.setOpenLoopRampRate(Constants.kDrivetrain.OPEN_LOOP_RAMP);
    backRight.setOpenLoopRampRate(Constants.kDrivetrain.OPEN_LOOP_RAMP);

    frontLeft.setSmartCurrentLimit(Constants.kDrivetrain.CURRENT_LIMIT);
    frontRight.setSmartCurrentLimit(Constants.kDrivetrain.CURRENT_LIMIT);
    backLeft.setSmartCurrentLimit(Constants.kDrivetrain.CURRENT_LIMIT);
    backRight.setSmartCurrentLimit(Constants.kDrivetrain.CURRENT_LIMIT);
    this.camera = new PhotonCamera("myCamera");
  }

  public void curveDrive(double linearVelocity, double angularVelocity, boolean isQuickturn) {
    if (isQuickturn) {
      angularVelocity /= 2;
    }

    if (slow) {
      diffDrive.curvatureDrive(linearVelocity * Constants.kDrivetrain.SLOW_SPEED, angularVelocity * angleInvert,
          isQuickturn);
    } else {
      diffDrive.curvatureDrive(linearVelocity, angularVelocity * angleInvert, isQuickturn);
    }
  }

  public void invertDirection() {
    frontLeft.setInverted(!frontLeft.getInverted());
    frontRight.setInverted(!frontRight.getInverted());
    backLeft.setInverted(!backLeft.getInverted());
    backRight.setInverted(!backRight.getInverted());
    angleInvert = angleInvert * -1;
  }

  public void startSlow() {
    slow = true;
  }

  public void endSlow() {
    slow = false;
  }

  public void alignToTarget() {
    var result = camera.getLatestResult();
    if (result.hasTargets()) {
      double yaw = result.getBestTarget().getYaw();
      double angularVelocity = Constants.kDrivetrain.TURN_SPEED * (yaw > 0 ? 1 : -1);
      this.curveDrive(0, angularVelocity, true);
    }
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
