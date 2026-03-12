package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Launcher
{
    private DcMotor launcherL;
    private DcMotor launcherR;

    public void init(HardwareMap hwMap)
    {
        launcherL = hwMap.get(DcMotor.class, "launcherL");
        launcherL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launcherL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        launcherR = hwMap.get(DcMotor.class, "launcherR");
        launcherR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launcherR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcherR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void Launch(double inputTrigger, double speed)
    {
        speed /= 100;

        launcherL.setPower(inputTrigger * speed);
        launcherR.setPower(inputTrigger * speed);
    }

    public void reverseLaunch(double inputTrigger, double speed)
    {
        speed /= 100;
        launcherL.setPower(-inputTrigger * speed);
        launcherR.setPower(-inputTrigger * speed);
    }
}

