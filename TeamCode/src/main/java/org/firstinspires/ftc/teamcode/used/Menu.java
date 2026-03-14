package org.firstinspires.ftc.teamcode.used;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Menu
{
    //
    public Menu(OpMode opMode)
    {
        this.opMode = opMode;
    }
    OpMode opMode;

    public boolean menuMode;
    public int menuCounter = 1;
    boolean menuWasIncremented;
    boolean menuWasDecremented;
    public boolean lastInput;
    public boolean outputToggle;
    public void setMenuMode()
    {
        boolean output;
        if (opMode.gamepad1.start && !lastInput)
        {
            outputToggle = !outputToggle;
        }

        lastInput = opMode.gamepad1.start;

        if (outputToggle)
        {
            output = true;
        } else {
            output = false;
        }

        if (menuMode)
        {
            opMode.telemetry.addLine("Press START to exit the menu");
            opMode.telemetry.addLine();
            opMode.telemetry.addLine("D-Pad up/down to scroll");
            opMode.telemetry.addLine("D-Pad right/left to change values");
            opMode.telemetry.addLine();
        }

        menuMode = output;
    }
    int increment;
    int decrement;
    public void setMenuCounter(int itemCount)
    {

        if (menuMode)
        {
            if (opMode.gamepad1.dpad_down && !menuWasIncremented)
            {
                increment = 1;
            } else
            {
                increment = 0;
            }
            menuWasIncremented = opMode.gamepad1.dpad_down;
            if (menuCounter + increment <= itemCount)
            {
                menuCounter += increment;
            }
            else
            {
                menuCounter = 1;
            }

            if (opMode.gamepad1.dpad_up && !menuWasDecremented)
            {
                decrement = 1;
            } else
            {
                decrement = 0;
            }
            menuWasDecremented = opMode.gamepad1.dpad_up;
            if (menuCounter - decrement > 0)
            {
                menuCounter -= decrement;
            }
            else
            {
                menuCounter = itemCount;
            }
        }
    }

    boolean wasIncremented;
    boolean wasDecremented;
    public double setMenuItem(int menuNumber, String itemName, double input, double increment, double min, double max)
    {
        if (menuMode)
        {
            String itemSelected = ">  " + itemName;

            if (menuNumber == menuCounter)
            {
                opMode.telemetry.addData(itemSelected, input);
                if (opMode.gamepad1.dpad_right && input + increment <= max && !wasIncremented)
                {
                    input += increment;
                }

                wasIncremented = opMode.gamepad1.dpad_right;

                if (opMode.gamepad1.dpad_left && input - increment != min - 1 && !wasDecremented)
                {
                    input -= increment;
                }

                wasDecremented = opMode.gamepad1.dpad_left;
            }
            else
            {
                opMode.telemetry.addData(itemName, input);
            }
        }
        return input;
    }
}
