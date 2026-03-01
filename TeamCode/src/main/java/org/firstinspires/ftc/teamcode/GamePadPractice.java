package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class GamePadPractice extends OpMode
{

    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {
        double speedForward = -gamepad1.left_stick_y / 2.0;

        telemetry.addData("Left Stick x", gamepad1.left_stick_x);
        telemetry.addData("Left Stick y", speedForward);
        telemetry.addData("Right Stick x", gamepad1.right_stick_x);
        telemetry.addData("Right Stick y", gamepad1.right_stick_y);
        telemetry.addData("A Button", gamepad1.a);
        telemetry.addData("B button", gamepad1.b);
        telemetry.addData("L-R x", gamepad1.left_stick_x - gamepad1.right_stick_x);;
    }




}
