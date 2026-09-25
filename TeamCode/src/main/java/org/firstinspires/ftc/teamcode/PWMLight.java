package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PWMLight {
    Servo light;
    public final double RED = 0.277;
    public final double ORANGE = 0.333;
    public final double YELLOW = 0.388;
    public final double GREEN = 0.500;
    public final double BLUE = 0.611;
    public final double PURPLE = 0.722;
    public final double WHITE = 1.000;
    public PWMLight(String lightName, HardwareMap hardwareMap)
    {
        if (hardwareMap.get(Servo.class, lightName) != null)
        {
            this.light = hardwareMap.get(Servo.class, lightName);
        }
    }
    public void setColor(double colorValue)
    {
        this.light.setPosition(colorValue);
    }
}
