package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Launcher extends OpMode
{
    light light = new light();
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

    int currentPos;
    int lastPos;
    double elapsedTime;
    double lastTime;
    double RPM;

    public void getMotorSpeed(double speed)
    {
        currentPos = launcherR.getCurrentPosition();
        elapsedTime = getRuntime();

        int posChange = currentPos - lastPos;
        double timeChange = elapsedTime - lastTime;

        double motorTPS = posChange / timeChange;

        lastPos = currentPos;
        lastTime = elapsedTime;

        RPM = (motorTPS * 60) / 400;

        double wantedRPM = 6000 / speed;
        int deadZone = 250;

        if (Math.abs(RPM) >= wantedRPM - deadZone)
        {
            light.lightBlue();
        }
        else
        {
            light.lightRed();
        }
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

        if (launchTrigger > 0.15)
        {
            getMotorSpeed(speed);
        }
    }


    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {

    }
}

