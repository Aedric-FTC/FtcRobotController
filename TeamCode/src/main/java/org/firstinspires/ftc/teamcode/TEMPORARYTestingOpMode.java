package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TEMPORARYTestingOpMode extends OpMode {
    ButtonOperation testButton;
    MecanumDriveTrain mecDrive;
    Menu menu;
    DcMotor fL = hardwareMap.get(DcMotor.class, "fL");
    DcMotor fR = hardwareMap.get(DcMotor.class, "fR");
    DcMotor bL = hardwareMap.get(DcMotor.class, "bL");
    DcMotor bR = hardwareMap.get(DcMotor.class, "bR");

    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(gamepad1, fL, fR, bL, bR, false);
        menu = new Menu(this);
        testButton = new ButtonOperation(GamepadButton.A, () -> telemetry.addLine("worked"));
    }
    double drivePower = DataSaver.loadThis("Drive Power").getAsDouble();
    int something;
    Menu.MenuItem item = new Menu.MenuItem(1, "Drive Power", drivePower, 5, 0, 100);
    Menu.MenuItem item2 = new Menu.MenuItem(2, "sum bullshit", something, 1, 0, 5);
    @Override
    public void loop()
    {
        if (Menu.menuMode)
        {
            item.createMenu();
            drivePower = item.getDoubleValue();
            something = item2.getIntValue();
        }
        else {
            mecDrive.drive(drivePower);
            testButton.run();
        }
    }

    @Override
    public void stop()
    {
        DataSaver.saveThis("Drive Power", drivePower);
        DataSaver.saveThis("sum bullshit", something);
    }
}
