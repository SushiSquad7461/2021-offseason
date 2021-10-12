// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
    private NetworkTableInstance nInstance;

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
        nInstance = NetworkTableInstance.getDefault();
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
        NetworkTable photonTable = nInstance.getTable("photonvision").getSubTable("photonvision");
        result = camera.getLatestResult();
        double yaw = photonTable.getEntry("targetYaw").getDouble(200);
        SmartDashboard.putBoolean("in execute", true);
        SmartDashboard.putNumber("target count", result.getTargets().size());
        if (yaw < 100) {
            System.out.println("this is what the yaw is " + yaw);
            double angularVelocity = pid.calculate(-yaw);
            drivetrain.curveDrive(0, angularVelocity, true);
            SmartDashboard.putNumber("YAWE", result.getBestTarget().getYaw());
            SmartDashboard.putNumber("Piss",result.getBestTarget().getPitch()); 
            SmartDashboard.putNumber("yaw from nt", yaw);
        } else {
            SmartDashboard.putNumber("YAWE", -69.0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("in execute", false);
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        /*
        result = camera.getLatestResult();
        if (!result.hasTargets()) { return false; }
        SmartDashboard.putBoolean("Target aligned", true);
        double yaw = result.getBestTarget().getYaw();
        return Math.abs(yaw) < Constants.kCamera.DRIVE_ALIGNMENT_TOLERANCE;
        */
        NetworkTable photonTable = nInstance.getTable("photonvision").getSubTable("photonvision");
        result = camera.getLatestResult();
        double yaw = photonTable.getEntry("targetYaw").getDouble(200);
        return Math.abs(yaw)<Constants.kCamera.DRIVE_ALIGNMENT_TOLERANCE;
    }
}
