package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Deprecated @Disabled
public class Robot {
    OpMode opMode;
    int itemCount = 0;
    public Map<Integer, Object> devices = new HashMap<>();
    public Robot(OpMode opMode) {
        this.opMode = opMode;
    }
    public void init() {
        for (int i = 0; i <= this.itemCount; i++)
        {
            Object thisObject = this.devices.get(this.itemCount);
        }
    }
    public void addObject(@NonNull Object object, String name) {
        List<HardwareDevice> allDevices = opMode.hardwareMap.getAll(HardwareDevice.class);
        this.itemCount ++;
        this.devices.put(itemCount, object);
    }
}
