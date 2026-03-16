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

    public void Launch(double speed, double launchTrigger, double pullTrigger)
    {
        speed /= 100;

        if (launchTrigger > 0)
        {
            launcherL.setPower(speed);
            launcherR.setPower(speed);
        }

        if (pullTrigger > 0)
        {
            launcherL.setPower(-speed);
            launcherR.setPower(-speed);
        }
    }
}

