package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class encoderTest extends OpMode
{
    private DcMotor motor;
    public void motorInit(HardwareMap hwMap)
    {
        motor = hwMap.get(DcMotor.class, "frMotor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void init()
    {
        motorInit(hardwareMap);
    }

    int currentPos;
    int lastPos;
    double elapsedTime;
    double lastTime;
    double RPM;

    @Override
    public void loop()
    {
        motor.setPower(gamepad1.right_trigger);

        currentPos = motor.getCurrentPosition();
        elapsedTime = getRuntime();

        int posChange = currentPos - lastPos;
        double timeChange = elapsedTime - lastTime;

        double motorTPS = posChange / timeChange;

        lastPos = currentPos;
        lastTime = elapsedTime;

        telemetry.addData("TPS", motorTPS);

        RPM = (motorTPS * 60) / 400;
        telemetry.addData("RPM", RPM);

        int wantedRPM = 312;
        int deadZone = 50;

        if (Math.abs(RPM) >= wantedRPM - deadZone)
        {
            telemetry.addLine("Within Range");
        }
        else
        {
            telemetry.addLine("Below Range");
        }
    }
}
