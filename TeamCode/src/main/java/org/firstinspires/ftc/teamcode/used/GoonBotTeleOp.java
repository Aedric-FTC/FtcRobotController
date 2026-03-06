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



    public boolean lastDPADRIGHT;
    public boolean rightWasPressed;
    public boolean lastDPADLEFT;
    public boolean leftWasPressed;
    boolean a;
    boolean b;
    boolean willIncrease;
    @Override
    public void init_loop()
    {
        telemetry.addData("Drive Speed", driveSpeed);


        boolean output = buttonToggle(gamepad1.dpad_right, a, b, willIncrease);
        telemetry.addData("output", output);


        if (output && driveSpeed <=95)
        {
            driveSpeed +=5;
        }
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
