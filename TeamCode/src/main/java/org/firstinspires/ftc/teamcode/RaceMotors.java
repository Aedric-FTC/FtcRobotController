package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RaceMotors
{
    public DcMotor sixR;
    public DcMotor sixL;
    public DcMotor slow;

    public void init(HardwareMap hwMap)
    {
        sixR = hwMap.get(DcMotor.class, "sixR");
        sixR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sixR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        sixL = hwMap.get(DcMotor.class, "sixL");
        sixL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sixL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        sixL.setDirection(DcMotorSimple.Direction.REVERSE);

        slow = hwMap.get(DcMotor.class, "slow");
        slow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void coast()
    {
        sixL.setPower(0);
        sixR.setPower(0);
        slow.setPower(0);
    }

    public void brake()
    {
        sixR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sixL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void floatyTime()
    {
        sixR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        sixL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        slow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}
