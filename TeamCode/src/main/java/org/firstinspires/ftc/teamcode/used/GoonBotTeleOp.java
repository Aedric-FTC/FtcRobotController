package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GoonBotTeleOp extends OpMode
{
    double driveSpeed;
    motorDrive motors = new motorDrive();
    light light = new light();

    @Override
    public void init()
    {
        motors.init(hardwareMap);
        light.init(hardwareMap);

        // Set drive speed in percentage
        driveSpeed = 100;
    }

    boolean dpadRightPressedLastCycle;
    boolean dpadLeftPressedLastCycle;
    @Override
    public void init_loop()
    {
        if (gamepad1.dpad_right && driveSpeed <= 95 && !dpadRightPressedLastCycle)
        {
            driveSpeed += 5;
        }

        if (gamepad1.dpad_left && driveSpeed >= 5 && !dpadLeftPressedLastCycle)
        {
            driveSpeed -= 5;
        }

        dpadRightPressedLastCycle = gamepad1.dpad_right;
        dpadLeftPressedLastCycle = gamepad1.dpad_left;
    }

    @Override
    public void start()
    {
        driveSpeed /= 100;
    }

    boolean lastA = false;
    boolean intakeOn = false;

    @Override
    public void loop()
    {
        if (motors.isReversed)
        {
            telemetry.addData("Direction", "Reversed");
            light.lightYellow();
        }
        else
        {
            telemetry.addData("Direction", "Forward");
            light.lightGreen();
        }

        telemetry.addData("Drive Speed", driveSpeed);

        motors.drive(gamepad1.left_stick_y, gamepad1.left_stick_x,
                     gamepad1.right_stick_x, gamepad1.dpad_down, driveSpeed);

    }
}
