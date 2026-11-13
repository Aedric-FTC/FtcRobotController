package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.aedricLib.EasyPID;
import org.firstinspires.ftc.teamcode.aedricLib.GamepadButton;
import org.firstinspires.ftc.teamcode.aedricLib.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.aedricLib.Menu;
import org.firstinspires.ftc.teamcode.aedricLib.MenuItem;
import org.firstinspires.ftc.teamcode.aedricLib.PWMLight;
import org.firstinspires.ftc.teamcode.aedricLib.RobotJson;
import org.firstinspires.ftc.teamcode.aedricLib.SafeHardware;
import org.firstinspires.ftc.teamcode.aedricLib.SubMenu;

@TeleOp
public class TestOp2 extends OpMode {
    MecanumDriveTrain mecDrive;
    MenuItem testInteger;
    MenuItem testFloat;
    MenuItem menuDriveSpeed;
    MenuItem testBoolean;
    double driveSpeed = RobotJson.load("Drive Speed").getAsDouble();
    boolean testBool = RobotJson.load("Test Boolean").getAsBoolean();
    int testInt = RobotJson.load("Test Integer").getAsInt();
    float testFlt = RobotJson.load("Test Float").getAsFloat();
    PWMLight light;
    Menu mainMenu;
    DcMotor motor;
    EasyPID pid;
    SubMenu subMenu;
    MenuItem subItemTest;

    @Override
    public void init()
    {
        mainMenu = new Menu(this, GamepadButton.START, "Main");
        menuDriveSpeed = new MenuItem(mainMenu, "Drive Speed", driveSpeed, 5, 0, 100);
        testBoolean = new MenuItem(mainMenu, "Test Boolean", testBool);
        testInteger = new MenuItem(mainMenu, "Test Integer", testInt, 5, 0, 100);
        testFloat = new MenuItem(mainMenu, "Test Float", testFlt, 5, 0, 100);

        testMenu2 = new Menu(this, GamepadButton.X, "Test 2");
        testItem2 = new MenuItem(testMenu2, "test item 2", test2val, 5, 0, 100);
        testItem3 = new MenuItem(testMenu2, "test item 3", test3val, 5, 0, 100);

        subMenu = new SubMenu(mainMenu, "Test SubMenu");
        subItemTest = new MenuItem(subMenu.subMenu, "SubMenu Double", subTestVal, 5, 0, 100);


        mecDrive = new MecanumDriveTrain(new Robot(this), "fL", "fR",
                "bL", "bR", false);
        light = new PWMLight("light", hardwareMap);
        motor = SafeHardware.get(DcMotor.class, "motor", this);
        pid = new EasyPID(this);
    }
    Menu testMenu2;
    MenuItem testItem2;
    MenuItem testItem3;
    double test2val;
    double test3val;
    double subTestVal;
    @Override
    public void loop()
    {
        mainMenu.create();
        testMenu2.create();
        subMenu.create();
        pid.update(10, 0, true);
        if (Menu.inAMenu)
            light.blink(500, PWMLight.Color.YELLOW);
        else {
            light.setColor(PWMLight.Color.GREEN);
            mecDrive.drive(driveSpeed);
        }
        driveSpeed = menuDriveSpeed.getDoubleValue();
        testBool = testBoolean.getBooleanValue();
        testInt = testInteger.getIntValue();
        testFlt = testFloat.getFloatValue();
        test2val = testItem2.getDoubleValue();
        test3val = testItem3.getDoubleValue();
        subTestVal = subItemTest.getDoubleValue();
        telemetry.addData("sub menu place", subMenu.subItem.itemNumber);
    }
}