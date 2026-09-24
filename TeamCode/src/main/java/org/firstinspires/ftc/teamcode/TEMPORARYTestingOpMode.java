package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TEMPORARYTestingOpMode extends OpMode {
    ButtonOperation testButton;
    MecanumDriveTrain mecDrive;
    Menu menu;
    DcMotor fL, fR, bL, bR;
    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(gamepad1, fL, fR, bL, bR, false);
        menu = new Menu(this);
        testButton = new ButtonOperation(GamepadButton.A,
                () -> telemetry.addLine("gooned"));
    }

    double drivePower = 100;
    @Override
    public void loop()
    {
        mecDrive.drive(drivePower);
        testButton.run();
    }
}
