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
    EasyPID pid;
    Menu.SubMenu subMenu;
    Menu.MenuItem subItemTest;

    @Override
    public void init()
    {
        mainMenu = new Menu(this, GamepadButton.START, "Main");
        menuDriveSpeed = mainMenu.new MenuItem("Drive Speed", driveSpeed, 5, 0, 100);
        testBoolean = mainMenu.new MenuItem("Test Boolean", testBool);
        testInteger = mainMenu.new MenuItem("Test Integer", testInt, 5, 0, 100);
        testFloat = mainMenu.new MenuItem("Test Float", testFlt, 5, 0, 100);

        testMenu2 = new Menu(this, GamepadButton.X, "Test 2");
        testItem2 = testMenu2.new MenuItem("test item 2", test2val, 5, 0, 100);
        testItem3 = testMenu2.new MenuItem("test item 3", test3val, 5, 0, 100);

        subMenu = mainMenu.new SubMenu("Test SubMenu");
        subItemTest = subMenu.subMenu.new MenuItem("SubMenu Double", subTestVal, 5, 0, 100);


        mecDrive = new MecanumDriveTrain(this, "fL", "fR",
                "bL", "bR", false);
        light = new PWMLight("light", hardwareMap);
        motor = SafeHardware.get(DcMotor.class, "motor", this);
        pid = new EasyPID(this);
    }
    Menu testMenu2;
    Menu.MenuItem testItem2;
    Menu.MenuItem testItem3;
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
        //telemetry.addData("sub menu place", subMenu.subItem.itemNumber);
    }
}