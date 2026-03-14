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

    public void spin(double speed, boolean suckKey, boolean blowKey)
    {
        speed /= 100;

        if (suckKey)
        {
            transferMotor.setPower(speed);
        }
        else if (blowKey)
        {
            transferMotor.setPower(-speed);
        }
        else
        {
            transferMotor.setPower(0);
        }
    }
}
