package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDriveTrain {
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;
    private final Gamepad gamepad;
    public MecanumDriveTrain(Gamepad gamepad, DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, boolean hasEncoder)
    {
        this.gamepad = gamepad;

        this.frontLeft = frontLeftMotor;
        this.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.frontRight = frontRightMotor;
        this.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.backLeft = backLeftMotor;
        this.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.backRight = backRightMotor;
        this.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (hasEncoder)
        {
            this.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        if (powerPercentage < -100 || powerPercentage > 100)
        {
            throw new IllegalArgumentException("Power percentage must be between -100 and 100!");
        }

        powerPercentage /= 100;

        this.forward = -gamepad.left_stick_y;
        this.strafe = gamepad.left_stick_x;
        this.rotate = gamepad.right_stick_x;

        this.flPower = this.forward - this.strafe - this.rotate;
        this.frPower = this.forward + this.strafe + this.rotate;
        this.blPower = this.forward + this.strafe - this.rotate;
        this.brPower = this.forward - this.strafe + this.rotate;

        this.maxPower = Math.max(this.maxPower, Math.abs(this.flPower));
        this.maxPower = Math.max(this.maxPower, Math.abs(this.frPower));
        this.maxPower = Math.max(this.maxPower, Math.abs(this.blPower));
        this.maxPower = Math.max(this.maxPower, Math.abs(this.brPower));

        this.flPower *= (powerPercentage/this.maxPower);
        this.frPower *= (powerPercentage/this.maxPower);
        this.blPower *= (powerPercentage/this.maxPower);
        this.brPower *= (powerPercentage/this.maxPower);

        this.frontLeft.setPower(this.flPower);
        this.frontRight.setPower(this.frPower);
        this.backLeft.setPower(this.blPower);
        this.backRight.setPower(this.brPower);
    }
    boolean isReversed = false;
    boolean didReverse = false;
    public void reverse(boolean reverseButton)
    {
        if (reverseButton && !this.didReverse) {
            this.isReversed = !this.isReversed;
        }

        this.didReverse = reverseButton;
        this.reverseModifier = this.isReversed ? -1 : 1;
    }
}