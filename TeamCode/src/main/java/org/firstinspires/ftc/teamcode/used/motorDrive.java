package org.firstinspires.ftc.teamcode.used;


import com.qualcomm.robotcore.hardware.DcMotor;
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
        frMotor = hwMap.get(DcMotor.class, "frMotor");
        frMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blMotor = hwMap.get(DcMotor.class, "blMotor");
        blMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brMotor = hwMap.get(DcMotor.class, "brMotor");
        brMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void drive(double forward, double strafe, double rotate, boolean isReversed)
    {
        double flPower = forward + strafe + rotate;
        double frPower = forward - strafe - rotate;
        double blPower = forward - strafe + rotate;
        double brPower = forward + strafe - rotate;

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
        frMotor.setPower(maxSpeed * (flPower / maxPower));
        blMotor.setPower(maxSpeed * (blPower / maxPower));
        brMotor.setPower(maxSpeed * (brPower / maxPower));
    }




}
