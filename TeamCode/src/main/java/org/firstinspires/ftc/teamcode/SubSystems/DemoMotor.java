package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

public class DemoMotor {
    public DcMotor demoMotor;

    // Always pass a Robot class through your SubSystem constructors
    public DemoMotor(Robot robot)
    {
        demoMotor = robot.hardwareMap.get(DcMotor.class, "demoMotor");
        demoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        demoMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void spin(boolean gamepadButton)
    {
        if (gamepadButton) demoMotor.setPower(1);
        else demoMotor.setPower(0);
    }
}
