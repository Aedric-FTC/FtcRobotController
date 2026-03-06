package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class light
{
    private Servo light = null;

    double Off = 0.000;
    double Red = 0.277;
    double Yellow = 0.388;
    double Green = 0.500;
    double Blue = 0.611;
    double Purple = 0.722;
    double White = 1.000;

    public void init(HardwareMap hwMap)
    {
        light = hwMap.get(Servo.class, "light");
    }
    public void lightOff()
    {
        light.setPosition(Off);
    }
    public void lightRed()
    {
        light.setPosition(Red);
    }
    public void lightYellow()
    {
        light.setPosition(Yellow);
    }
    public void lightGreen()
    {
        light.setPosition(Green);
    }
    public void lightBlue()
    {
        light.setPosition(Blue);
    }
    public void lightPurple()
    {
        light.setPosition(Purple);
    }
    public void lightWhite()
    {
        light.setPosition(White);
    }
}
