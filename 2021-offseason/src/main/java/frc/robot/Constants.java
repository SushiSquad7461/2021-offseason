// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import com.revrobotics.CANSparkMaxLowLevel;
import com.team254.lib.util.InterpolatingDouble;
import com.team254.lib.util.InterpolatingTreeMap;

import com.revrobotics.CANSparkMaxLowLevel;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class kHood {
        public static final int MOTOR_ID = 4;
        public static final CANSparkMaxLowLevel.MotorType MOTOR_TYPE = CANSparkMaxLowLevel.MotorType.kBrushless;
        public static final double MAX_SPEED = 0.3;
        public static final double HOOD_INCREMENT = 0.5;
        public static final double kP = 0.1;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kFF = 0.0;
        public static final double SETPOINT = (45.0/360.0) * (332.0/14.0) * 15;
        public static final InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> hoodAngleTreeMap = new InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble>();
        public static final double[] ZONE_SETPOINTS = {37.0, 56.0, 80.0, 80.0};
        public static final int LIMIT_PORT = -1;

        static {
            hoodAngleTreeMap.put(new InterpolatingDouble(50.0), new InterpolatingDouble(39.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(60.0), new InterpolatingDouble(41.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(70.0), new InterpolatingDouble(45.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(80.0), new InterpolatingDouble(49.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(90.0), new InterpolatingDouble(52.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(100.0), new InterpolatingDouble(54.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(110.0), new InterpolatingDouble(56.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(120.0), new InterpolatingDouble(57.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(130.0), new InterpolatingDouble(58.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(140.0), new InterpolatingDouble(59.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(150.0), new InterpolatingDouble(62.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(160.0), new InterpolatingDouble(64.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(170.0), new InterpolatingDouble(65.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(180.0), new InterpolatingDouble(65.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(190.0), new InterpolatingDouble(65.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(200.0), new InterpolatingDouble(66.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(210.0), new InterpolatingDouble(65.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(220.0), new InterpolatingDouble(66.0));
            hoodAngleTreeMap.put(new InterpolatingDouble(230.0), new InterpolatingDouble(67.0));
        }

        public static final double INIT_LINE_ANGLE = 7.1;
        public static final double ANGLE_TOLERANCE = 0.03; // percentage / 100
    }

    public static final class kDrivetrain {
        public static final int FRONT_LEFT_ID = 1;
        public static final int FRONT_RIGHT_ID = 15;
        public static final int BACK_LEFT_ID = 42;
        public static final int BACK_RIGHT_ID = 14;

        public static final CANSparkMaxLowLevel.MotorType MOTOR_TYPE = CANSparkMaxLowLevel.MotorType.kBrushless;

        public static final int CURRENT_LIMIT = 35;
        public static final int OPEN_LOOP_RAMP = 0;
        public static final double SLOW_SPEED = 0.2;

        public static final boolean DRIVE_INVERTED = false;
    }
  
    public static final class kIntake { 
        public static final int INTAKE_PORT = 7;
        public static final int PCM_PORT = 17;
        public static final double INTAKE_SPEED = 0.60007461;
        public static final int SOLENOID_FRONT = 1;
        public static final int SOLENOID_BACK = 0;
        public static final boolean INVERTED = true;
    }

    public static final class kHopper {
        public static final int LEFT_ID = 6;
        public static final int RIGHT_ID = 11;
        public static final int CENTER_ID = 8;
        public static final int KICKER_ID = 5;
        public static final double LEFT_SPEED = 0.5;
        public static final double RIGHT_SPEED = 0.7;
        public static final double FLOOR_SPEED = 1.0;
        public static final double KICKER_FORWARD = 1;
        public static final double KICKER_REVERSE = -1;
        public static final int CURRENT_LIMIT = 40;
        public static final double REVERSE_SPEED = -1;
        public static final boolean INVERTED = false;
    }

    public static final class kOI {
        public static final int DRIVE_CONTROLLER = 0;
        public static final int OPERATOR_CONTROLLER = 1;
    }

    public static final class kFlywheel {
       
        // motors
        public static final int MAIN_ID = 3;
        public static final int FOLLOWER_ID = 13;
        public static final CANSparkMaxLowLevel.MotorType MOTOR_TYPE = CANSparkMaxLowLevel.MotorType.kBrushless;
        public static final boolean MAIN_INVERTED = true;
        public static final boolean FOLLOWER_INVERTED = false;  
        public static final int CURRENT_LIMIT = 80;
        public static final double OPEN_LOOP_RAMP = 1;

        // pid constants
        public static final double kP = 0.001;
        public static final double kI = 0;
        public static final double kD = 0.0005;  
        public static final double MAX_VELOCITY = 0;
        public static final double MAX_ACCELERATION = 0;
        public static final int ERR_TOLERANCE = 0;

        public static final int GOAL = 3000;
        public static final double GOAL_TOLERANCE = 0.95; // percentage of goal that counts

        // feedforward constants
        public static final double kS = 0.71212;
        public static final double kV = 0.02364;
        public static final double kA = 0.016388;
        
    }
    public static final class kClimb {
        public static final
                CANSparkMaxLowLevel.MotorType MOTOR_TYPE =
                CANSparkMaxLowLevel.MotorType.kBrushless;

        public static final int WINCH_LEFT = 12;
        public static final int WINCH_RIGHT = 2;

        public static final boolean WINCH_INVERTED = true;

        public static final int CURRENT_LIMIT = 40;
        public static final double OPEN_LOOP_RAMP = 1;

        public static final double MAX_SPEED = 1; // maybe max out ?
        public static final double SLOW_SPEED = 0.1; // should tune, test in pit


}}
