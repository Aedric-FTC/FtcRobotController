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
    DcMotor intakeMotor() {
        if (hardwareMap.dcMotor.contains("intakeM")) {
            return hardwareMap.get(DcMotor.class, "intakeM");
        }
        else {
            return null;
        }
    }
    DcMotor leftOdom(){
        if (hardwareMap.dcMotor.contains("leftOdom"))
        {
            return hardwareMap.get(DcMotor.class, "leftOdom");
        }
        else {
            return null;
        }
    }
    DcMotor rightOdom(){
        if (hardwareMap.dcMotor.contains("rightOdom"))
        {
            return hardwareMap.get(DcMotor.class, "rightOdom");
        }
        else {
            return null;
        }
    }
    DcMotor strafeOdom(){
        if (hardwareMap.dcMotor.contains("strafeOdom"))
        {
            return hardwareMap.get(DcMotor.class, "strafeOdom");
        }
        else {
            return null;
        }
    }
    IMU imu;
    //PWMLight light = new PWMLight("light", hardwareMap);
    EasyOdom.threePodOdom odom;

    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(this, "fL", "fR", "bL", "bR", false);
        menu = new Menu(this);
        testButton = new ButtonOperation(this, GamepadButton.X, 1, () -> telemetry.addLine(" singleworked"));
        //odom = new EasyOdom.threePodOdom(imu, leftOdom(), rightOdom(), strafeOdom(),
         //       2000, 1.37795354, -2);
        //intakeMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        comboTest = new ButtonComboOperation(() -> gamepad1.a && gamepad1.right_bumper, () -> telemetry.addLine("combo worked"));
    }
    double drivePower = 100;//DataSaver.loadThis("Drive Power").getAsDouble();
    int something;
    Menu.MenuItem item = new Menu.MenuItem("Drive Power", drivePower, 5, 0, 100);
    Menu.MenuItem item2 = new Menu.MenuItem("testSubMenu", "sum bullshit", something, 1, 0, 5);
    Menu.SubMenu testSubMenu = new Menu.SubMenu("testSubMenu", 3);
    @Override
    public void loop()
    {
        Menu.createMenu();
        //odom.update();
        if (Menu.inMenu)
        {
            drivePower = item.getDoubleValue();
            //testSubMenu.addSubMenu();
            something = item2.getIntValue();
         //   light.setColor(PWMLight.Color.YELLOW);
        }
        else {
            mecDrive.drive(drivePower);
            mecDrive.reverse(GamepadButton.DPAD_DOWN);
            if (gamepad1.left_bumper && intakeMotor() != null)
            {
                intakeMotor().setPower(1);
            }
            else if (intakeMotor() != null){
                intakeMotor().setPower(0);
            }
            testButton.run();
            comboTest.run();
          //  light.setColor(PWMLight.Color.GREEN);
            /*telemetry.addData("Robot X", odom.getRobotX());
            telemetry.addData("Robot Y", odom.getRobotY());
            telemetry.addData("Robot Heading", odom.getRobotHeading());*/
        }
    }

    @Override
    public void stop()
    {
        RobotJson.save("Drive Power", drivePower);
        RobotJson.save("sum bullshit", something);
    }
}
