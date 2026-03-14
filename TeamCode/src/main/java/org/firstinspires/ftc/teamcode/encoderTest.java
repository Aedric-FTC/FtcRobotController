package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled
@TeleOp
public class encoderTest extends OpMode
{
    double ticksPerRev;
    private DcMotor motor;
    public void motorInit(HardwareMap hwMap)
    {
        motor = hwMap.get(DcMotor.class, "frMotor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ticksPerRev = motor.getMotorType().getTicksPerRev();
    }

    public void spin()
    {
        if (gamepad1.left_bumper)
        {
            motor.setPower(0.5);
        }
        else if (gamepad1.right_bumper)
        {
            motor.setPower(-0.5);
        }
        else
        {
            motor.setPower(0);
        }
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
        telemetry.addData("Motor Revs", motor.getCurrentPosition() / ticksPerRev);
    }
}
