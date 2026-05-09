package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.used.Menu;

import java.text.DecimalFormat;

@TeleOp
public class RacerOp extends OpMode
{
    RaceMotors motors = new RaceMotors();
    Menu menu = new Menu(this);
    camControl camera = new camControl();
    FtcDashboard ftcDashboard = FtcDashboard.getInstance();
    Telemetry dashTelemetry = ftcDashboard.getTelemetry();
    DecimalFormat df = new DecimalFormat("#.##");

    int currentPos;
    int lastPos;
    double elapsedTime;
    double lastTime;
    double RPM;

    public void getFastMotorSpeed()
    {
        currentPos = (motors.sixR.getCurrentPosition() + motors.sixL.getCurrentPosition() + motors.sixM.getCurrentPosition()) / 3;
        elapsedTime = getRuntime();

        int posChange = currentPos - lastPos;
        double timeChange = elapsedTime - lastTime;

        double motorTPS = posChange / timeChange;

        lastPos = currentPos;
        lastTime = elapsedTime;

        RPM = (motorTPS * 60) / 400;
    }

    @Override
    public void init()
    {
        motors.init(hardwareMap);
        camera.initCamera(hardwareMap);
    }
    public double driveSpeed = 100;
    double trueSpeed = 0;
    double truerSpeed = 0;
    double truestSpeed = 0;
    double truesterSpeed = 0;
    String preMph;
    double mph;
    double mps;

    @Override
    public void loop()
    {
        menu.menuMode = true;
        menu.setMenuMode();
        menu.setMenuCounter(1);

        getFastMotorSpeed();

        if (menu.menuMode)
        {
            if (gamepad1.x)
            {
                motors.sixR.setPower(1 * (driveSpeed / 100));
                motors.sixL.setPower(1 * (driveSpeed / 100));
            } else
            {
                motors.sixR.setPower(0);
                motors.sixL.setPower(0);
            }
            if (gamepad1.b)
            {
                motors.sixM.setPower(1 * (driveSpeed / 100));
            } else
            {
                motors.sixM.setPower(0);
            }
        }
        else
        {
            if (gamepad1.right_trigger * (driveSpeed / 100) > trueSpeed)
            {
                trueSpeed = gamepad1.right_trigger * (driveSpeed / 100);
            }

            if (gamepad1.right_trigger > 0 && truerSpeed + (0.02 * trueSpeed) <= gamepad1.right_trigger * (driveSpeed / 100))
            {
                truerSpeed += 0.05 * trueSpeed;
                truestSpeed = truerSpeed;
                truesterSpeed = truestSpeed;
            }
            else if (gamepad1.right_trigger == 0 && truerSpeed - (0.02 * trueSpeed) >= 0)
            {
                truerSpeed -= 0.05 * trueSpeed;
                truestSpeed = truerSpeed;
                truesterSpeed = truestSpeed;
            }

            if (gamepad1.left_trigger > 0)
            {
                motors.brake();
            }
            else
            {
                motors.floatyTime();
            }

            if (gamepad1.right_trigger >= 0.15)
            {
                motors.sixM.setPower(truestSpeed);
                motors.sixR.setPower(truestSpeed);
                motors.sixL.setPower(truestSpeed);
            }
            else
            {
                motors.coast();
            }

            double turnTicks = 383.6;

            double turnAngle = ((45.0/360.0) * turnTicks);

            //motors.turnMotor.setTargetPosition((int)(turnAngle * (-gamepad1.left_stick_x)));
            if (gamepad1.left_stick_x > 0)
            {
                motors.turnMotor.setTargetPosition(-(int)turnAngle);
            }
            else if (gamepad1.left_stick_x < 0)
            {
                motors.turnMotor.setTargetPosition((int)turnAngle);
            }
            else
            {
                motors.turnMotor.setTargetPosition(0);
            }
            motors.turnMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors.turnMotor.setPower(1);
        }

        driveSpeed = menu.setMenuItem(1, "Drive Speed", driveSpeed, 5, 0, 100);
        telemetry.addLine("Drive Speed = " + driveSpeed);
        mph = Double.parseDouble(df.format((0.3 * RPM)/26.8224));
        mps = Double.parseDouble(df.format((0.3 * RPM/26.8224)/2.237));
        dashTelemetry.addData("Speed in Miles per Hour", mph);
        dashTelemetry.addData("Speed in Meters per Second", mps);
        dashTelemetry.update();
    }
}
