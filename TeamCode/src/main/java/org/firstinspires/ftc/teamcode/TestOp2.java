package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestOp2 extends OpMode {
    MecanumDriveTrain mecDrive;
    Menu.MenuItem menuDriveSpeed;
    double driveSpeed = 100;
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
        telemetry.addData("testy time", DataSaver.loadThis("driveSpeed").getAsDouble());
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
        }
        Menu.onMenuExit(() -> DataSaver.saveThis("driveSpeed", driveSpeed));
    }
}
