// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel;
import com.team254.lib.util.InterpolatingDouble;
import com.team254.lib.util.InterpolatingTreeMap;

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
    public static final class Hood {
        public static final int MOTOR_ID = 12;
        public static final CANSparkMaxLowLevel.MotorType MOTOR_TYPE = CANSparkMaxLowLevel.MotorType.kBrushless;
        public static final double INITIAL_SETPOINT = 80.0;
        public static final double MAX_SPEED = 0.3;
        public static final double kP = 0.1;
        public static final double kI = 0;
        public static final double kD = 0;
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
    }
}
