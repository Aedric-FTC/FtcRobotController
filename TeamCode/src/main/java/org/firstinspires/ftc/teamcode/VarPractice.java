package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class VarPractice extends OpMode {

    @Override
    public void init()
    {
        int teamNumber = 18619;
        boolean idk = true;
        String name = "jim";
        int motorAngle = 120;

        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Angle",motorAngle);
    }

    public void loop()
    {

    }


}
