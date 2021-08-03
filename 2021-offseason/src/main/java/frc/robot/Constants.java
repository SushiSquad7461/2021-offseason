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

    public static final class kFlywheel {
       
        // motors
        public static final int MAIN_ID = 0;
        public static final int FOLLOWER_ID = 0;
        public static final CANSparkMaxLowLevel.MotorType MOTOR_TYPE = CANSparkMaxLowLevel.MotorType.kBrushless;
        public static final boolean MAIN_INVERTED = false;
        public static final boolean FOLLOWER_INVERTED = false;  
        public static final int CURRENT_LIMIT = 0;

        // pid constants
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;  
        public static final double MAX_VELOCITY = 0;
        public static final double MAX_ACCELERATION = 0;
        public static final int ERR_TOLERANCE = 0;

        // feedforward constants
        public static final double kS = 0;
        public static final double kV = 0;
        public static final double kA = 0;
        
    }

}
