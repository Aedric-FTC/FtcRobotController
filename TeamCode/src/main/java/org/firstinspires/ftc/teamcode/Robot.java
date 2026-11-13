package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.DemoMotor;
import org.firstinspires.ftc.teamcode.aedricLib.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.aedricLib.Menu;

public class Robot {
    public final OpMode opMode;
    public final HardwareMap hardwareMap;
    public final Telemetry telemetry;
    public final MecanumDriveTrain mecanumDriveTrain;
    public final DemoMotor demoMotor;
    /*
        Add all SubSystems into here, formatted just like the DemoMotor example
     */

    public Robot(OpMode opMode)
    {
        this.opMode = opMode;
        this.hardwareMap = this.opMode.hardwareMap;
        this.telemetry = this.opMode.telemetry;
        this.mecanumDriveTrain = new MecanumDriveTrain(this,
                "flMotor", "frMotor",
                "blMotor", "brMotor",
                false);
        this.demoMotor = new DemoMotor(this);
        /*
            Initialize all SubSystems into here, formatted just like the DemoMotor example
        */
    }
}
