package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

@TeleOp
public class TestOp2 extends OpMode {
    MecanumDriveTrain mecDrive;
    Menu.MenuItem menuDriveSpeed;
    Menu.MenuItem menuItem2;
    Menu.MenuItem menuItem3;
    double driveSpeed = RobotJson.load("Drive Speed").getAsDouble();
    double item2 = RobotJson.load("test 2").getAsDouble();
    int item3 = RobotJson.load("test 3").getAsInt();
    PWMLight light;
    Menu menu;

    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(this, "fL", "fR",
                "bL", "bR", false);
        menu = new Menu(this);
        menuDriveSpeed = new Menu.MenuItem("Drive Speed", driveSpeed, 5, 0, 100);
        light = new PWMLight("light", hardwareMap);
        menuItem2 = new Menu.MenuItem("test 2", item2, 5, 0, 100);
        menuItem3 = new Menu.MenuItem("test 3", item3, 5, 0, 100);
    }

    @Override
    public void loop()
    {
        Menu.createMenu();
        if (!Menu.inMenu) {
            light.setColor(PWMLight.Color.GREEN);
            mecDrive.drive(driveSpeed);
        }
        else {
            light.blink(500, PWMLight.Color.YELLOW);
            driveSpeed = menuDriveSpeed.getDoubleValue();
            item2 = menuItem2.getDoubleValue();
            item3 = menuItem3.getIntValue();
        }
        /*Menu.onMenuExit(() -> {
            RobotJson.save("driveSpeed", driveSpeed);
            RobotJson.save("item2Value", item2);
            RobotJson.save("item3Value", item3);
        });*/
    }
}