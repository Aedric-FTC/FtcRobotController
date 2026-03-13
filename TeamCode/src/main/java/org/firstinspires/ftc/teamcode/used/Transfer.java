package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer
{
    private DcMotor transferMotor;

    public void init(HardwareMap hwMap)
    {
        transferMotor = hwMap.get(DcMotor.class, "transferMotor");
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void spinTransfer(double speed)
    {
        speed /= 100;

            transferMotor.setPower(speed);
    }

    public void reverseTransfer(double speed)
    {
        speed /= -100;

            transferMotor.setPower(speed);
    }
}
