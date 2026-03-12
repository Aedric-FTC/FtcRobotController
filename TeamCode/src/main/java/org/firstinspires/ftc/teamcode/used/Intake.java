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
    }

    public void suck(boolean inputButton, double speed)
    {
        speed /= 100;
        speed *= -1;

        if (inputButton)
        {
            intakeMotor.setPower(speed);
        }
        else
        {
            intakeMotor.setPower(0);
        }
    }

    public void blow(boolean inputButton, double speed)
    {
        speed /= 100;

        if (inputButton)
        {
            intakeMotor.setPower(speed);
        }
        else
        {
            intakeMotor.setPower(0);
        }
    }
}
