package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

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
    public Menu(OpMode OpMode)
    {
        opMode = OpMode;
    }
    public static OpMode opMode;

    public boolean menuMode;
    public int menuCounter = 1;
    boolean menuWasIncremented;
    boolean menuWasDecremented;
    public boolean lastInput;
    public boolean outputToggle;
    public int globalMenuNumber;
    /// Turns menu on when START is pressed (place at start of loop() method)
    public void createMenu()
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
        setMenuCounter(globalMenuNumber);
    }
    int counterIncrement;
    int counterDecrement;
    private void setMenuCounter(int itemCount)
    {
        if (menuMode)
        {
            if (opMode.gamepad1.dpad_down && !menuWasIncremented)
            {
                counterIncrement = 1;
            } else
            {
                counterIncrement = 0;
            }
            menuWasIncremented = opMode.gamepad1.dpad_down;
            if (menuCounter + counterIncrement <= itemCount)
            {
                menuCounter += counterIncrement;
            }
            else
            {
                menuCounter = 1;
            }

            if (opMode.gamepad1.dpad_up && !menuWasDecremented)
            {
                counterDecrement = 1;
            } else
            {
                counterDecrement = 0;
            }
            menuWasDecremented = opMode.gamepad1.dpad_up;
            if (menuCounter - counterDecrement > 0)
            {
                menuCounter -= counterDecrement;
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
            if (menuNumber == menuCounter)
            {
                opMode.telemetry.addData(">  " + itemName, input);
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
        globalMenuNumber = Math.max(menuNumber, globalMenuNumber);
        return input;
    }
    public static class MenuItem extends Menu{
        public static int numberOfMenuItems;
        public String itemName;
        public Number itemValue;
        public Number increment;
        public Number min;
        public Number max;
        public int itemNumber;
        public MenuItem(int itemNumber, Number inputValue, Number increment, Number min, Number max)
        {
            super(opMode);
            numberOfMenuItems ++;
            this.itemName = this.getClass().getSimpleName();
            this.itemNumber = itemNumber;
            this.itemValue = inputValue;
            this.increment = increment;
            this.min = min;
            this.max = max;
        }
        public Number getItemValue()
        {
            if (menuMode)
            {
                if (this.itemNumber == menuCounter)
                {
                    opMode.telemetry.addData(">  " + this.itemName, this.itemValue);
                    if (opMode.gamepad1.dpad_right && this.itemValue.doubleValue() + this.increment.doubleValue() <= this.max.doubleValue() && !wasIncremented)
                    {
                        this.itemValue = this.itemValue.doubleValue() + this.increment.doubleValue();
                    }

                    this.wasIncremented = opMode.gamepad1.dpad_right;

                    if (opMode.gamepad1.dpad_left && this.itemValue.doubleValue() - this.increment.doubleValue() != this.min.doubleValue() - 1 && !wasDecremented)
                    {
                        this.itemValue = this.itemValue.doubleValue() - this.increment.doubleValue();
                    }

                    wasDecremented = opMode.gamepad1.dpad_left;
                }
                else
                {
                    opMode.telemetry.addData(this.itemName, this.itemValue);
                }
            }
            globalMenuNumber = Math.max(this.itemNumber, globalMenuNumber);
            return this.itemValue;
        }
    }
}
