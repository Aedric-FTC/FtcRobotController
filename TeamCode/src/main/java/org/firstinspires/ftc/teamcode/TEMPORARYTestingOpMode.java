package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

@TeleOp
public class TEMPORARYTestingOpMode extends OpMode {
    ButtonOperation testButton;
    ButtonComboOperation comboTest;
    MecanumDriveTrain mecDrive;
    Menu menu;
    DcMotor fL = hardwareMap.get(DcMotor.class, "fL"),
            fR = hardwareMap.get(DcMotor.class, "fR"),
            bL = hardwareMap.get(DcMotor.class, "bL"),
            bR = hardwareMap.get(DcMotor.class, "bR"),
            leftOdom = hardwareMap.get(DcMotor.class, "leftOdom"),
            rightOdom = hardwareMap.get(DcMotor.class, "rightOdom"),
            strafeOdom = hardwareMap.get(DcMotor.class, "strafeOdom"),
            intakeMotor = hardwareMap.get(DcMotor.class, "intakeM");
    IMU imu;
    PWMLight light = new PWMLight("light", hardwareMap);
    EasyOdom.threePodOdom odom;

    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(this, fL, fR, bL, bR, false);
        menu = new Menu(this);
        testButton = new ButtonOperation(this, GamepadButton.X, 1, () -> telemetry.addLine(" singleworked"));
        odom = new EasyOdom.threePodOdom(imu, leftOdom, rightOdom, strafeOdom,
                2000, 1.37795354, -2);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        comboTest = new ButtonComboOperation(() -> gamepad1.a && gamepad1.right_bumper, () -> telemetry.addLine("combo worked"));
    }
    double drivePower = DataSaver.loadThis("Drive Power").getAsDouble();
    int something;
    Menu.MenuItem item = new Menu.MenuItem("Drive Power", drivePower, 5, 0, 100);
    Menu.MenuItem item2 = new Menu.MenuItem("testSubMenu", "sum bullshit", something, 1, 0, 5);
    Menu.SubMenu testSubMenu = new Menu.SubMenu("testSubMenu", 3);
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
            mecDrive.reverse(GamepadButton.DPAD_DOWN);
            if (gamepad1.left_bumper)
            {
                intakeMotor.setPower(1);
            }
            else {
                intakeMotor.setPower(0);
            }
            testButton.run();
            comboTest.run();
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
