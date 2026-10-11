package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

@TeleOp
public class TestOp2 extends OpMode {
    MecanumDriveTrain mecDrive;
    Menu.MenuItem testInteger;
    Menu.MenuItem testFloat;
    Menu.MenuItem menuDriveSpeed;
    Menu.MenuItem testBoolean;
    double driveSpeed = RobotJson.load("Drive Speed").getAsDouble();
    boolean testBool = RobotJson.load("Test Boolean").getAsBoolean();
    int testInt = RobotJson.load("Test Integer").getAsInt();
    float testFlt = RobotJson.load("Test Float").getAsFloat();
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
        testBoolean = new Menu.MenuItem("Test Boolean", testBool);
        testInteger = new Menu.MenuItem("Test Integer", testInt, 5, 0, 100);
        testFloat = new Menu.MenuItem("Test Float", testFlt, 5, 0, 100);
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
            testBool = testBoolean.getBooleanValue();
            testInt = testInteger.getIntValue();
            testFlt = testFloat.getFloatValue();
            telemetry.addData("Sub Menu", Menu.currentSubMenu);
        }
        /*Menu.onMenuExit(() -> {
            RobotJson.save("driveSpeed", driveSpeed);
            RobotJson.save("item2Value", item2);
            RobotJson.save("item3Value", item3);
        });*/
    }
}