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

        driveSpeed = 50;
    }

    @Override
    public void start()
    {
        driveSpeed /= 100;
    }

    private boolean buttonToggle(boolean input, boolean lastInput, boolean outputToggle, boolean output)
    {
        if (input && !lastInput)
        {
            outputToggle = !outputToggle;
        }

        lastInput = input;

        if (outputToggle)
        {
            output = true;
        } else {
            output = false;
        }
        return output;
    }

   public boolean wasRight;
    public boolean wasLeft;
    public int addRight;
    public int addLeft;
    @Override
    public void init_loop()
    {
        telemetry.addData("Drive Speed", driveSpeed);

        if (gamepad1.dpad_right && !wasRight)
        {
            addRight = 1;
        }
        else
        {
            addRight = 0;
        }
        wasRight = gamepad1.dpad_right;
        driveSpeed += addRight;

        if (gamepad1.dpad_left && !wasLeft)
        {
            addLeft = 1;
        }
        else
        {
            addLeft = 0;
        }
        wasLeft = gamepad1.dpad_left;
        driveSpeed -= addLeft;
    }

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
