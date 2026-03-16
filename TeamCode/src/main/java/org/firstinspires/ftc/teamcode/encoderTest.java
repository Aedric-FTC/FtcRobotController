package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    public void spin()
    {
        motor.setPower(gamepad1.right_trigger);
    }

    @Override
    public void init()
    {
        motorInit(hardwareMap);
    }

    @Override
    public void loop()
    {
        spin();
    }
}
