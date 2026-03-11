package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.hardware.DcMotor;
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

        if (inputButton)
        {
            intakeMotor.setPower(speed);
        }
    }

    public void reverseSuck(boolean inputButton, double speed)
    {
        speed /= 100;

        if (inputButton)
        {
            intakeMotor.setPower(-speed);
        }
    }
}
