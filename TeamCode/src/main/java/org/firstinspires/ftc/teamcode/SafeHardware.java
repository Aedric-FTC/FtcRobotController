package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import java.util.List;

public class SafeHardware {
    public static <T> T get(Class<? extends T> motorType, String name, OpMode opMode)
    {
        List<HardwareDevice> allDevices = opMode.hardwareMap.getAll(HardwareDevice.class);
        if (opMode.hardwareMap.getAll(HardwareDevice.class).contains(name))
        return opMode.hardwareMap.get(motorType, name);
        else {
            opMode.telemetry.addData("Device type '%d' not found with name '%s'; defaulted to null",
                    motorType.getSimpleName(), name);
            return null;
        }
    }
}
