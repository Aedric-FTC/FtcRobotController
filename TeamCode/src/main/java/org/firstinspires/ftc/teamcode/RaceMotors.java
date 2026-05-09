package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RaceMotors
{
    public DcMotor sixR;
    public DcMotor sixL;
    public DcMotor sixM;
    public DcMotor turnMotor;

    public void init(HardwareMap hwMap)
    {
        sixR = hwMap.get(DcMotor.class, "sixR");
        sixR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sixR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        sixL = hwMap.get(DcMotor.class, "sixL");
        sixL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sixL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        sixL.setDirection(DcMotor.Direction.REVERSE);

        sixM = hwMap.get(DcMotor.class, "sixM");
        sixM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sixM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        turnMotor = hwMap.get(DcMotor.class, "turnMotor");
        turnMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turnMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void coast()
    {
        sixL.setPower(0);
        sixR.setPower(0);
        sixM.setPower(0);
    }

    public void brake()
    {
        sixM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void floatyTime()
    {
        sixR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        sixL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        sixM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}
