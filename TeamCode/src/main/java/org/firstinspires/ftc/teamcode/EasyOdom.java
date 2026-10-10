package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Deprecated
public class EasyOdom {
    public static class threePodOdom
    {
        private DcMotor leftEncoder;
        private DcMotor rightEncoder;
        private DcMotor strafeEncoder;
        private IMU imu;

        private final double TICKS_PER_REV;
        private final double WHEEL_DIAMETER_INCHES;
        private final double INCHES_PER_TICK;
        private final double STRAFE_ODOM_OFFSET;
        public threePodOdom(IMU IMU, DcMotor leftSideEncoder, DcMotor rightSideEncoder, DcMotor strafeRearEncoder,
                            double ticksPerRevolution, double diameterInInches, double rearOdomOffset)
        {
            STRAFE_ODOM_OFFSET = rearOdomOffset;

            TICKS_PER_REV = ticksPerRevolution;
            WHEEL_DIAMETER_INCHES = diameterInInches;
            INCHES_PER_TICK = (Math.PI * WHEEL_DIAMETER_INCHES) / TICKS_PER_REV;

            leftEncoder = leftSideEncoder;
            rightEncoder = rightSideEncoder;
            strafeEncoder = strafeRearEncoder;

            leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            strafeEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            strafeEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            imu = IMU;
            imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )));
            imu.resetYaw();
        }
        private double robotX = 0.0;
        private double robotY = 0.0;
        private double robotHeading = 0.0;
        private int prevLeftTicks = 0;
        private int prevRightTicks = 0;
        private int prevBackTicks = 0;
        private double prevHeading = 0.0;
        public void update() {
            int currentLeft = 0;
            int currentRight = 0;
            int currentBack = 0;
            if (leftEncoder != null && rightEncoder != null && strafeEncoder != null) {
                currentLeft = leftEncoder.getCurrentPosition();
                currentRight = rightEncoder.getCurrentPosition();
                currentBack = strafeEncoder.getCurrentPosition();
            }

            YawPitchRollAngles robotAngles = imu.getRobotYawPitchRollAngles();
            double currentHeading = robotAngles.getYaw(AngleUnit.RADIANS);

            int deltaLeftTicks = currentLeft - prevLeftTicks;
            int deltaRightTicks = currentRight - prevRightTicks;
            int deltaBackTicks = currentBack - prevBackTicks;

            double deltaHeading = currentHeading - prevHeading;
            if (deltaHeading > Math.PI)  deltaHeading -= 2 * Math.PI;
            if (deltaHeading < -Math.PI) deltaHeading += 2 * Math.PI;

            double deltaLeftInches = deltaLeftTicks * INCHES_PER_TICK;
            double deltaRightInches = deltaRightTicks * INCHES_PER_TICK;
            double deltaBackInches = deltaBackTicks * INCHES_PER_TICK;

            double localDeltaY = (deltaLeftInches + deltaRightInches) / 2.0;

            double localDeltaX = deltaBackInches - (deltaHeading * STRAFE_ODOM_OFFSET);

            double midHeading = prevHeading + (deltaHeading / 2.0);

            robotX += (localDeltaX * Math.cos(midHeading)) - (localDeltaY * Math.sin(midHeading));
            robotY += (localDeltaX * Math.sin(midHeading)) + (localDeltaY * Math.cos(midHeading));
            robotHeading = currentHeading;

            prevLeftTicks = currentLeft;
            prevRightTicks = currentRight;
            prevBackTicks = currentBack;
            prevHeading = currentHeading;
        }
        public double getRobotX()
        {
            return robotX;
        }
        public double getRobotY()
        {
            return robotY;
        }
        public double getRobotHeading()
        {
            return Math.toDegrees(robotHeading);
        }
    }
}