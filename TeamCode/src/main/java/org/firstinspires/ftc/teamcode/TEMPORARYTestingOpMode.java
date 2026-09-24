package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TEMPORARYTestingOpMode extends OpMode {

    MecanumDriveTrain mecDrive;
    Menu menu;
    DcMotor fL, fR, bL, bR;
    @Override
    public void init()
    {
        mecDrive = new MecanumDriveTrain(gamepad1, fL, fR, bL, bR, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        menu = new Menu(this);
    }

    double drivePower = 100;
    @Override
    public void loop()
    {
        mecDrive.drive(drivePower);
        ButtonOperation.onClick("test with the A button", gamepad1.a, () -> drivePower--);
    }
}
