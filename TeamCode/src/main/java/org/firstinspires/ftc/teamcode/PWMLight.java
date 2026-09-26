package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PWMLight {
    Servo light;
    final double RED_VALUE = 0.277;
    final double ORANGE_VALUE = 0.333;
    final double YELLOW_VALUE = 0.388;
    final double GREEN_VALUE = 0.500;
    final double BLUE_VALUE = 0.611;
    final double PURPLE_VALUE = 0.722;
    final double WHITE_VALUE = 1.000;
    public PWMLight(String lightName, HardwareMap hardwareMap)
    {
        if (hardwareMap.get(Servo.class, lightName) != null)
        {
            this.light = hardwareMap.get(Servo.class, lightName);
        }
    }
    double colorValue = 0;
    public void setColor(Color color)
    {
        switch (color)
        {
            case RED: this.colorValue = RED_VALUE; break;
            case ORANGE: this.colorValue = ORANGE_VALUE; break;
            case YELLOW: this.colorValue = YELLOW_VALUE; break;
            case GREEN: this.colorValue = GREEN_VALUE; break;
            case BLUE: this.colorValue = BLUE_VALUE; break;
            case PURPLE: this.colorValue = PURPLE_VALUE; break;
            case WHITE: this.colorValue = WHITE_VALUE; break;
            case OFF: this.colorValue = 0; break;
        }
        this.light.setPosition(this.colorValue);
    }
    /// @param pwmColorValue Any double value 0-1
    public void setColor(double pwmColorValue)
    {
        this.light.setPosition(pwmColorValue);
    }
    public enum Color
    {
        RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, WHITE, OFF
    }
}
