package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.SubSystems.DemoMotor;
import org.firstinspires.ftc.teamcode.aedricLib.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.aedricLib.PWMLight;

public class Robot {
    public final OpMode opMode;
    public final HardwareMap hardwareMap;
    //public final Telemetry telemetry;
    public final MecanumDriveTrain mecanumDriveTrain;
    public final DemoMotor demoMotor;
    public final PWMLight demoLight;
    // Instantiate all SubSystems in here

    public Robot(OpMode opMode)
    {
        this.opMode = opMode;
        this.hardwareMap = this.opMode.hardwareMap;
        this.mecanumDriveTrain = new MecanumDriveTrain(this,
                "flMotor", "frMotor",
                "blMotor", "brMotor",
                false);

        this.demoMotor = new DemoMotor(this);
        this.demoLight = new PWMLight(this, "demoLight");
        // Initialize all other SubSystems in here
    }
}
