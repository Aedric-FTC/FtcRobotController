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

    int counterYellow;
    int counterOff;
    @Override
    public void init_loop()
    {
        telemetry.addData("Drive Speed", driveSpeed);

        counterOff = 0;
        counterYellow = 0;

        light.lightYellow();
        while (counterYellow <= 1000)
        {
            counterYellow += 1;
        }
        //light.lightOff();
        while (counterOff <= 1000)
        {
            counterOff += 1;
        }
        light.lightOff();

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
