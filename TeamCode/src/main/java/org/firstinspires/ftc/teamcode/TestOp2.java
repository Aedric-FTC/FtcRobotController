package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestOp2 extends OpMode {
    MecanumDriveTrain mecDrive;
    Menu.MenuItem menuDriveSpeed;
    Menu.MenuItem item2;
    double driveSpeed = RobotJson.load("driveSpeed").getAsDouble();
    double item2Value = RobotJson.load("item2Value").getAsDouble();
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
        item2 = new Menu.MenuItem("test 2", 50, 5, 0, 100);
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
            item2Value = item2.getDoubleValue();
        }
        Menu.onMenuExit(() -> {
            RobotJson.save("driveSpeed", driveSpeed);
            RobotJson.save("item2Value", item2Value);
        });
    }
}
