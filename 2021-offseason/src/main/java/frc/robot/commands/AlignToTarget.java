// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class AlignToTarget extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Drivetrain drivetrain;
    private final PhotonCamera camera;
    private PhotonPipelineResult result;
    private final PIDController pid;

    /**
     * Creates a new AlignToTarget.
     *
     * @param drivetrain The drive used by this command.
     * @param camera     The photoncamera.
     */
    public AlignToTarget(Drivetrain drivetrain, PhotonCamera camera) {
        this.drivetrain = drivetrain;
        this.camera = camera;
        this.pid = new PIDController(Constants.kCamera.P, Constants.kCamera.I, Constants.kCamera.D);
        this.pid.setSetpoint(0); // yaw target should always be 0
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Target aligned", false);
        result = camera.getLatestResult();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        result = camera.getLatestResult();
        if (result.hasTargets()) {
            double yaw = result.getBestTarget().getYaw();
            double angularVelocity = pid.calculate(yaw);
            drivetrain.curveDrive(0, angularVelocity, true);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Target aligned", true);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        double yaw = result.getBestTarget().getYaw();
        return Math.abs(yaw) < Constants.kCamera.DRIVE_ALIGNMENT_TOLERANCE;
    }
}
