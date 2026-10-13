package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
    Menu mainMenu;
    DcMotor motor;
    EasyPID pid = new EasyPID(this);

    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(this, "fL", "fR",
                "bL", "bR", false);
        mainMenu = new Menu(this);
        menuDriveSpeed = mainMenu.new MenuItem("Drive Speed", driveSpeed, 5, 0, 100);
        light = new PWMLight("light", hardwareMap);
        testBoolean = mainMenu.new MenuItem("Test Boolean", testBool);
        testInteger = mainMenu.new MenuItem("Test Integer", testInt, 5, 0, 100);
        testFloat = mainMenu.new MenuItem("Test Float", testFlt, 5, 0, 100);
        motor = SafeHardware.get(DcMotor.class, "motor", this);
    }

    @Override
    public void loop()
    {
        mainMenu.create();
        if (!Menu.inMenu) {
            light.setColor(PWMLight.Color.GREEN);
            mecDrive.drive(driveSpeed);
            telemetry.addData("left", gamepad1.left_bumper);
            telemetry.addData("right", gamepad1.right_bumper);
        }
        else {
            light.blink(500, PWMLight.Color.YELLOW);
            driveSpeed = menuDriveSpeed.getDoubleValue();
            testBool = testBoolean.getBooleanValue();
            testInt = testInteger.getIntValue();
            testFlt = testFloat.getFloatValue();
        }
    }
}