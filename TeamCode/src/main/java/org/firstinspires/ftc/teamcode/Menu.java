package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
/// NOTE: If your robot's code utilizes the d-pad, consider wrapping the setMenuCounter and setMenuItem methods in an if statement for 'if (menuMode)', then put your TeleOp code in an else statement so that it only runs when not in the menu<br>
/// ```java
/// setMenuMode();
/// ```
/// Place this at the start of your loop function. It will activate the menu when the start button is pressed<br>
///
/// ```java
/// setMenuCounter(int numberOfItems);
/// ```
/// Place this after your setMenuMode(). It will save the number of menu items and create the "cursor"<br>
///
/// ```java
/// setMenuItem(int menuNumber, String itemName, double input, double increment, double min, double max);
/// ```
/// This will add an item to your menu and return a Number<br>
///
public class Menu
{
    /// When creating an instance of this class, place (this) within the parentheses:
    /// ```java
    /// Menu menu = new Menu(this);
    /// ```
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
    /// Turns menu on when START is pressed (place at start of loop() method)
    public void setMenuMode()
    {
        boolean output;
        if (opMode.gamepad1.start && !lastInput)
        {
            outputToggle = !outputToggle;
        }

        lastInput = opMode.gamepad1.start;

        output = outputToggle;

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
    /// Sets the number of items in your menu while simultaneously handling menu navigation
    /// @param itemCount The number of items in your menu
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
    /// Creates and adds an item to your menu, returning a number
    /// @param menuNumber The index that tells where in the menu your item will be, so if it is 1, your item will be the first on the menu
    /// @param itemName The displayed name of your item
    /// @param input The variable that you want to modify
    /// @param increment The amount by which your variable's value will change when using the left/right d-pad buttons
    /// @param min The minimum value your variable will be allowed to reach
    /// @param max The maximum value your variable will be allowed to reach
    /// @return Number
    /// <p></p>
    /// <br>To use this method, set your original variable equal to the method and use the .doubleValue() (or whatever number variable you need), for example:
    /// ```java
    /// myDouble = setMenuItem(1, "My Double", myDouble, 5, 0, 100).doubleValue();
    /// ```
    public Number setMenuItem(int menuNumber, String itemName, double input, double increment, double min, double max)
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
