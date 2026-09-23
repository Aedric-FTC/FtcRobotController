package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDriveTrain {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private final Gamepad gamepad;
    public MecanumDriveTrain(Gamepad gamepad, DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor)
    {
        this.gamepad = gamepad;
        this.frontLeft = frontLeftMotor;
        this.frontRight = frontRightMotor;
        this.backLeft = backLeftMotor;
        this.backRight = backRightMotor;
    }
    public void createMotor(HardwareMap hwMap, String frontLeftName, String frontRightName, String backLeftName, String backRightName, DriveMotor motorLocation, DcMotor.RunMode runMode, DcMotor.ZeroPowerBehavior zeroPowerBehavior)
    {
        if (motorLocation == DriveMotor.FRONT_LEFT)
        {
            this.frontLeft = hwMap.get(DcMotor.class, frontLeftName);
            this.frontLeft.setMode(runMode);
            this.frontLeft.setZeroPowerBehavior(zeroPowerBehavior);
        }

        if (motorLocation == DriveMotor.FRONT_RIGHT)
        {
            this.frontRight = hwMap.get(DcMotor.class, frontRightName);
            this.frontRight.setMode(runMode);
            this.frontRight.setZeroPowerBehavior(zeroPowerBehavior);
        }

        if (motorLocation == DriveMotor.BACK_LEFT)
        {
            this.backLeft = hwMap.get(DcMotor.class, backLeftName);
            this.backLeft.setMode(runMode);
            this.backLeft.setZeroPowerBehavior(zeroPowerBehavior);
        }

        if (motorLocation == DriveMotor.BACK_RIGHT)
        {
            this.backRight = hwMap.get(DcMotor.class, backRightName);
            this.backRight.setMode(runMode);
            this.backRight.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }

    double flPower;
    double frPower;
    double blPower;
    double brPower;
    double forward;
    double strafe;
    double rotate;
    double reverseModifier = 1;
    double maxPower = 1;
    public void drive(double powerPercentage)
    {
        this.forward = -gamepad.left_stick_y;
        this.strafe = gamepad.left_stick_x;
        this.rotate = gamepad.right_stick_x;

        this.flPower = this.forward - this.strafe - this.rotate;
        this.frPower = this.forward + this.strafe + this.rotate;
        this.blPower = this.forward + this.strafe - this.rotate;
        this.brPower = this.forward - this.strafe + this.rotate;

        this.maxPower = Math.max(maxPower, Math.abs(this.flPower));
        this.maxPower = Math.max(maxPower, Math.abs(this.frPower));
        this.maxPower = Math.max(maxPower, Math.abs(this.blPower));
        this.maxPower = Math.max(maxPower, Math.abs(this.brPower));

        this.flPower *= (powerPercentage/this.maxPower);
        this.frPower *= (powerPercentage/this.maxPower);
        this.blPower *= (powerPercentage/this.maxPower);
        this.brPower *= (powerPercentage/this.maxPower);

        this.frontLeft.setPower(flPower);
        this.frontRight.setPower(frPower);
        this.backLeft.setPower(blPower);
        this.backRight.setPower(brPower);
    }
    boolean isReversed = false;
    boolean willReverse = false;
    boolean didReverse = false;
    public void reverse(boolean reverseButton)
    {
        if (reverseButton && !didReverse) {
            isReversed = !isReversed;
        }

        didReverse = reverseButton;
        reverseModifier = isReversed ? -1 : 1;
    }
}