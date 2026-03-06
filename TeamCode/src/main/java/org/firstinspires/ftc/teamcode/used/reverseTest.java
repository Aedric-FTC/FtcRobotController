package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class reverseTest extends OpMode
{
    boolean lastA = false;
    boolean intakeOn = false;

    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {
        if (gamepad1.a && !lastA)
        {
            intakeOn = !intakeOn;
        }

        lastA = gamepad1.a;

        if (intakeOn)
        {
            telemetry.addData("On?", "yuh");
        } else {
            telemetry.addData("On?", "nuh");
        }
    }
}
