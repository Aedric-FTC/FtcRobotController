package org.firstinspires.ftc.teamcode.used;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class motorDrive
{
    private DcMotor flMotor;
    private DcMotor frMotor;
    private DcMotor blMotor;
    private DcMotor brMotor;

    public void init(HardwareMap hwMap)
    {
        flMotor = hwMap.get(DcMotor.class, "flMotor");
        flMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frMotor = hwMap.get(DcMotor.class, "frMotor");
        frMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        blMotor = hwMap.get(DcMotor.class, "blMotor");
        blMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        brMotor = hwMap.get(DcMotor.class, "brMotor");
        brMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    double flPower;
    double frPower;
    double blPower;
    double brPower;

    public boolean isReversed = false;

    public void stop()
    {
        flMotor.setPower(0);
        frMotor.setPower(0);
        blMotor.setPower(0);
        brMotor.setPower(0);
    }

    public void drive(double forward, double strafe, double rotate, boolean reverse)
    {
        if (reverse && !isReversed)
        {
            isReversed = true;
        }
        else if (reverse && isReversed)
        {
            isReversed = false;
        }

        if (isReversed)
        {
            rotate *= -1;
        }

        flPower = forward - strafe - rotate;
        frPower = forward + strafe + rotate;
        blPower = forward + strafe - rotate;
        brPower = forward - strafe + rotate;

        if (isReversed)
        {
            flPower *= -1;
            frPower *= -1;
            blPower *= -1;
            brPower *= -1;
        }

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(flPower));
        maxPower = Math.max(maxPower, Math.abs(frPower));
        maxPower = Math.max(maxPower, Math.abs(blPower));
        maxPower = Math.max(maxPower, Math.abs(brPower));

        flMotor.setPower(maxSpeed * (flPower / maxPower));
        frMotor.setPower(maxSpeed * (frPower / maxPower));
        blMotor.setPower(maxSpeed * (blPower / maxPower));
        brMotor.setPower(maxSpeed * (brPower / maxPower));
    }




}
