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

    public void suck(double speed)
    {
        speed /= 100;

        intakeMotor.setPower(speed);
    }

    public void blow(double speed)
    {
        speed /= -100;

        intakeMotor.setPower(speed);
    }
}
