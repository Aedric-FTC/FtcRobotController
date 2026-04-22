package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.used.Menu;
@TeleOp
public class RacerOp extends OpMode
{
    RaceMotors motors = new RaceMotors();
    Menu menu = new Menu(this);

    int currentPosSlow;
    int lastPosSlow;
    double elapsedTimeSlow;
    double lastTimeSlow;
    double RPMSlow;

    int currentPosFast;
    int lastPosFast;
    double elapsedTimeFast;
    double lastTimeFast;
    double RPMFast;

    public void getSlowMotorSpeed(double speed)
    {
        currentPosSlow = motors.slow.getCurrentPosition();
        elapsedTimeSlow = getRuntime();

        int posChange = currentPosSlow - lastPosSlow;
        double timeChange = elapsedTimeSlow - lastTimeSlow;

        double motorTPS = posChange / timeChange;

        lastPosSlow = currentPosSlow;
        lastTimeSlow = elapsedTimeSlow;

        RPMSlow = (motorTPS * 60) / 400;

        double wantedRPM = 6000 / speed;
        int deadZone = 250;
    }

    public void getFastMotorSpeed()
    {
        currentPosFast = (motors.sixR.getCurrentPosition() + motors.sixL.getCurrentPosition()) / 2;
        elapsedTimeFast = getRuntime();

        int posChange = currentPosFast - lastPosFast;
        double timeChange = elapsedTimeFast - lastTimeFast;

        double motorTPS = posChange / timeChange;

        lastPosFast = currentPosFast;
        lastTimeFast = elapsedTimeFast;

        RPMFast = (motorTPS * 60) / 400;
    }

    public double avgMotorSpeed()
    {
        return (RPMSlow + RPMFast) /2;
    }

    @Override
    public void init()
    {
        motors.init(hardwareMap);
    }

    public double driveSpeed;

    @Override
    public void loop()
    {
        menu.menuMode = true;
        menu.setMenuCounter(1);

        getSlowMotorSpeed(driveSpeed);
        getFastMotorSpeed();

        driveSpeed = menu.setMenuItem(1, "Drive Speed", driveSpeed, 5, 0, 100);
        telemetry.addLine("Drive Speed = " + driveSpeed);
        if (RPMSlow >= 400 / driveSpeed)
        {
            motors.slow.setPower(0);
            motors.sixR.setPower(gamepad1.right_trigger * (driveSpeed / 100));
            motors.sixL.setPower(gamepad1.right_trigger * (driveSpeed / 100));
        }
        else
        {
            motors.sixR.setPower(0);
            motors.sixL.setPower(0);
            motors.slow.setPower(gamepad1.right_trigger * (driveSpeed / 100));
        }

    }
}
