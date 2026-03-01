package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GoonBotTeleOp extends OpMode
{
    motorDrive motors = new motorDrive();
    @Override
    public void init()
    {
        motors.init(hardwareMap);
    }

    @Override
    public void loop()
    {

        if (gamepad1.left_stick_y < -0.15 && gamepad1.left_stick_y > 0.15 &&
            gamepad1.left_stick_x < -0.15 && gamepad1.left_stick_x > 0.15)
        {
            motors.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.dpad_down);
        }





    }
}
