// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
    public static final class kDrivetrain {
        public static final int FRONT_LEFT_ID = 1;
        public static final int FRONT_RIGHT_ID = 14;
        public static final int BACK_LEFT_ID = 42;
        public static final int BACK_RIGHT_ID = 15;

        public static final CANSparkMaxLowLevel.MotorType MOTOR_TYPE = CANSparkMaxLowLevel.MotorType.kBrushless;

        public static final int CURRENT_LIMIT = 35;
        public static final int OPEN_LOOP_RAMP = 0;
        public static final double SLOW_SPEED = 0.1;

        public static final boolean DRIVE_INVERTED = false;
    }
  
    public static final class kIntake { 
        public static final int INTAKE_PORT = 7;
        public static final int PCM_PORT = 17;
        public static final double INTAKE_SPEED = 1.0;
        public static final int SOLENOID_FRONT = 0;
        public static final int SOLENOID_BACK = 1;
        public static final boolean INVERTED = false;
    }

    public static final class kHopper {
        public static final int LEFT_ID = 6;
        public static final int RIGHT_ID = 11;
        public static final int CENTER_ID = 8;
        public static final int KICKER_ID = 5;
        public static final double FORWARD_SPEED = 0.2;
        public static final double REVERSE_SPEED = FORWARD_SPEED * -1;
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
        public static final double kP = 0.0007;
        public static final double kI = 0;
        public static final double kD = 0.001;  
        public static final double MAX_VELOCITY = 0;
        public static final double MAX_ACCELERATION = 0;
        public static final int ERR_TOLERANCE = 0;

        public static final int GOAL = 2000;

        // feedforward constants
        public static final double kS = 0.71212;
        public static final double kV = 0.02364;
        public static final double kA = 0.016388;
        
    }


}
