package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class controllerDeclarations extends OpMode
{
    double spinValue = gamepad1.right_stick_x;
    double strafeValue = gamepad1.left_stick_x;
    double speedValue = -gamepad1.left_stick_y;
    double rightTrigger = gamepad1.right_trigger;
    double leftTrigger = gamepad1.left_trigger;
    boolean aButton = gamepad1.a;
    boolean bButton = gamepad1.b;
    boolean xButton = gamepad1.x;
    boolean yButton = gamepad1.y;
    boolean rBumper = gamepad1.right_bumper;
    boolean lBumper = gamepad1.left_bumper;
    boolean dPadRight = gamepad1.dpad_right;
    boolean dPadLeft = gamepad1.dpad_left;
    boolean dPadUp = gamepad1.dpad_up;
    boolean dPadDown = gamepad1.dpad_down;
    boolean startButton = gamepad1.start;
    boolean selectButton = gamepad1.back;
    public controllerDeclarations() {
        double spinValue = gamepad1.right_stick_x;
        double strafeValue = gamepad1.left_stick_x;
        double speedValue = -gamepad1.left_stick_y;
        double rightTrigger = gamepad1.right_trigger;
        double leftTrigger = gamepad1.left_trigger;
        boolean aButton = gamepad1.a;
        boolean bButton = gamepad1.b;
        boolean xButton = gamepad1.x;
        boolean yButton = gamepad1.y;
        boolean rBumper = gamepad1.right_bumper;
        boolean lBumper = gamepad1.left_bumper;
        boolean dPadRight = gamepad1.dpad_right;
        boolean dPadLeft = gamepad1.dpad_left;
        boolean dPadUp = gamepad1.dpad_up;
        boolean dPadDown = gamepad1.dpad_down;
        boolean startButton = gamepad1.start;
        boolean selectButton = gamepad1.back;
    }





    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {

    }



}
