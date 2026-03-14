package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake
{
    private DcMotor intakeMotor;

    public void init(HardwareMap hwMap)
    {
        intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void spin(double speed, boolean suckKey, boolean blowKey)
    {
        speed /= 100;

        if (suckKey)
        {
            intakeMotor.setPower(speed);
        }
        else if (blowKey)
        {
            intakeMotor.setPower(-speed);
        }
        else
        {
            intakeMotor.setPower(0);
        }
    }
}
