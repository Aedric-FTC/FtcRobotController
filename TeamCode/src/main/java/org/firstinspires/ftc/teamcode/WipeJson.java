package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class WipeJson extends OpMode {
    public void init()
    {
        RobotJson.wipe();
        Menu.MenuItem.numberOfMenuItems = 0;
        requestOpModeStop();
    }
    public void loop(){}
}
