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
        testButton = new ButtonOperation(GamepadButton.A, () -> telemetry.addLine("gooned"));
    }
    double drivePower = 100;
    Menu.MenuItem item = new Menu.MenuItem(1, "Drive Power", drivePower, 5, 0, 100);
    @Override
    public void loop()
    {
        mecDrive.drive(drivePower);
        testButton.run();

        if (Menu.menuMode)
        {
            item.createMenu();
            drivePower = item.getDoubleValue();
        }
    }
}
