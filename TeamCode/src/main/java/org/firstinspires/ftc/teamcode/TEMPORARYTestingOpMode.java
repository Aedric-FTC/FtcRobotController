package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class TEMPORARYTestingOpMode extends OpMode {
    ButtonOperation testButton;
    MecanumDriveTrain mecDrive;
    Menu menu;
    DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
    DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
    DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
    DcMotor bR = hardwareMap.get(DcMotor.class, "bR");
    DcMotor leftOdom = hardwareMap.get(DcMotor.class, "leftOdom");
    DcMotor rightOdom = hardwareMap.get(DcMotor.class, "rightOdom");
    DcMotor strafeOdom = hardwareMap.get(DcMotor.class, "strafeOdom");
    IMU imu;
    PWMLight light = new PWMLight("light", hardwareMap);
    EasyOdom.threePodOdom odom;

    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(gamepad1, fL, fR, bL, bR, false);
        menu = new Menu(this);
        testButton = new ButtonOperation(GamepadButton.X, () -> telemetry.addLine("worked"));
        odom = new EasyOdom.threePodOdom(imu, leftOdom, rightOdom, strafeOdom,
                2000, 1.37795354, -2);
    }
    double drivePower = DataSaver.loadThis("Drive Power").getAsDouble();
    int something;
    Menu.MenuItem item = new Menu.MenuItem("Drive Power", drivePower, 5, 0, 100);
    Menu.MenuItem item2 = new Menu.MenuItem("testSubMenu", "sum bullshit", something, 1, 0, 5);
    Menu.SubMenu testSubMenu = new Menu.SubMenu("testSubMenu", 2);
    @Override
    public void loop()
    {
        odom.update();
        if (Menu.menuMode)
        {
            item.createMenu();
            drivePower = item.getDoubleValue();
            testSubMenu.addSubMenu();
            something = item2.getIntValue();
            light.setColor(PWMLight.Color.YELLOW);
        }
        else {
            mecDrive.drive(drivePower);
            testButton.run();
            light.setColor(PWMLight.Color.GREEN);
            telemetry.addData("Robot X", odom.getRobotX());
            telemetry.addData("Robot Y", odom.getRobotY());
            telemetry.addData("Robot Heading", odom.getRobotHeading());
        }
    }

    @Override
    public void stop()
    {
        DataSaver.saveThis("Drive Power", drivePower);
        DataSaver.saveThis("sum bullshit", something);
    }
}
