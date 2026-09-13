package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class light
{
    private final Servo light;

    public static final double OFF = 0.000;
    public static final double RED = 0.277;
    public static final double YELLOW = 0.388;
    public static final double GREEN = 0.500;
    public static final double BLUE = 0.611;
    public static final double PURPLE = 0.722;
    public static final double WHITE = 1.000;

    public light(HardwareMap hwMap)
    {
        light = hwMap.get(Servo.class, "light");
    }
    public void lightOff()
    {
        light.setPosition(OFF);
    }
    public void lightRed()
    {
        light.setPosition(RED);
    }
    public void lightYellow()
    {
        light.setPosition(YELLOW);
    }
    public void lightGreen()
    {
        light.setPosition(GREEN);
    }
    public void lightBlue()
    {
        light.setPosition(BLUE);
    }
    public void lightPurple()
    {
        light.setPosition(PURPLE);
    }
    public void lightWhite()
    {
        light.setPosition(WHITE);
    }
}
