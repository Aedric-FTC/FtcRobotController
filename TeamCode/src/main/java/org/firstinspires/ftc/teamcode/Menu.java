package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.Map;

/// NOTE: If your robot's code utilizes the d-pad, consider wrapping the menu methods in an if statement for 'if (Menu.menuMode)', then put your TeleOp code in an else statement so that it only runs when not in the menu<br>
///
/// Create a Menu object first, then, in your init method, give the object a value:
/// ```java
/// Menu menu;
/// public void init()
/// {
///     menu = new Menu(this);
/// }
/// ```
///
/// Then, create some menu items:
/// ```java
/// Menu.MenuItem item = new MenuItem(int itemNumber, String displayName, Number inputValue, Number increment, Number min, Number max)
/// ```
///
/// Next, place these methods:
///
/// ```java
/// item.createMenu();
/// ```
/// Place this at the start of your loop function. It will toggle menuMode when the start button is pressed<br>
///
/// ```java
/// item.getDoubleValue();
/// ```
/// Handles the value changing code and returns a new double value. Can be replaced with item.getIntValue();
public class Menu
{
    /// When creating an instance of this class, place (this) within the parentheses:
    /// ```java
    /// Menu menu = new Menu(this);
    /// ```
    public Menu(OpMode OpMode)
    {
        opMode = OpMode;
        menuCounter = 1;
        currentSubMenu = "MAIN";
    }
    public static OpMode opMode;

    public static boolean inMenu;
    public static int menuCounter;
    static boolean menuWasIncremented;
    static boolean menuWasDecremented;
    public static boolean lastInput;
    public static boolean outputToggle;
    public int globalMenuNumber;
    static String currentSubMenu;
    String itemSubMenu;
    public int numberOfMenuItems;
    public final Map<Integer, MenuItem> itemRegistry = new HashMap<>();

    public interface menuChange { void run(); }
    private static menuChange menuEnter = () -> {};
    private static menuChange menuExit = () -> {};
    public static void onMenuEnter(menuChange menuChange)  { menuEnter = menuChange; }
    public static void onMenuExit(menuChange menuChange)  { menuExit = menuChange; }
    public static void runOnMenuEnter() { menuEnter.run(); }
    public static void runOnMenuExit() { menuExit.run(); }

    /// Turns menu on when START is pressed (place at start of loop() method)
    public void create()
    {
        boolean output;
        if (opMode.gamepad1.start && !lastInput)
        {
            outputToggle = !outputToggle;
            if (outputToggle)
            {
                runOnMenuEnter();
            }
            else {
                runOnMenuExit();
                menuExit();
            }
        }

        lastInput = opMode.gamepad1.start;

        output = outputToggle;

        if (inMenu)
        {
            setMenuCounter(numberOfMenuItems);
            opMode.telemetry.addLine("Press START to exit the menu");
            opMode.telemetry.addLine();
            opMode.telemetry.addLine("D-Pad up/down to scroll");
            opMode.telemetry.addLine("D-Pad right/left to change values");
            opMode.telemetry.addLine();
        }
        else {
            opMode.telemetry.addLine("Press START to open menu");
        }

        inMenu = output;
        //menuMode = output;
    }
    public void create(GamepadButton menuButton, int gamepadNumber)
    {
        Gamepad gp;
        if (gamepadNumber == 1) gp = gamepad1; else gp = gamepad2;
        boolean buttonIsDown = menuButton.isPressed(gp);
        boolean output;
        if (buttonIsDown && !lastInput)
        {
            outputToggle = !outputToggle;
            if (outputToggle)
            {
                runOnMenuEnter();
            }
            else {
                runOnMenuExit();
                menuExit();
            }
        }

        lastInput = opMode.gamepad1.start;

        output = outputToggle;

        if (inMenu)
        {
            setMenuCounter(numberOfMenuItems);
            opMode.telemetry.addLine("Press START to exit the menu");
            opMode.telemetry.addLine();
            opMode.telemetry.addLine("D-Pad up/down to scroll");
            opMode.telemetry.addLine("D-Pad right/left to change values");
            opMode.telemetry.addLine();
        }
        else {
            opMode.telemetry.addLine("Press START to open menu");
        }

        inMenu = output;
        //menuMode = output;
    }
    static int counterIncrement;
    static int counterDecrement;
    private void setMenuCounter(int itemCount)
    {
        if (inMenu)
        {
            counterIncrement = (opMode.gamepad1.dpad_down && !menuWasIncremented) ? 1 : 0;
            menuWasIncremented = opMode.gamepad1.dpad_down;
            menuCounter = (menuCounter + counterIncrement <= itemCount) ? (menuCounter + counterIncrement) : 1;
            counterDecrement = (opMode.gamepad1.dpad_up && !menuWasDecremented) ? 1 : 0;
            menuWasDecremented = opMode.gamepad1.dpad_up;
            menuCounter = (menuCounter - counterDecrement > 0) ? (menuCounter - counterDecrement) : itemCount;
        }
    }
    private void menuExit()
    {
        for (int i = 1; i <= numberOfMenuItems; i++)
        {
            if (!thisItem(i).isBoolean) {
                RobotJson.save(thisItem(i).itemName, thisItem(i).itemValue);
            } else {
                RobotJson.save(thisItem(i).itemName, thisItem(i).booleanInputValue);
            }
        }
    }
    public MenuItem thisItem(int itemID)
    {
        return itemRegistry.get(itemID);
    }

    public class MenuItem {
        boolean wasIncremented;
        boolean wasDecremented;
        public String itemName;
        public Number itemValue;
        public Number increment;
        public Number min;
        public Number max;
        public int itemNumber;
        public boolean booleanInputValue;
        public boolean isBoolean;
        public String itemSubMenu;
        /// Creates a menu item
        /// @param displayName The displayed name of your item
        /// @param inputValue The variable that you want to modify
        /// @param increment The amount by which your variable's value will change when using the left/right d-pad buttons
        /// @param min The minimum value your variable will be allowed to reach
        /// @param max The maximum value your variable will be allowed to reach
        public MenuItem(String displayName, Number inputValue, Number increment, Number min, Number max)
        {
            numberOfMenuItems ++;
            this.itemNumber = numberOfMenuItems;
            this.itemName = displayName;
            this.itemValue = inputValue;
            this.increment = increment;
            this.min = min;
            this.max = max;
            this.itemSubMenu = "MAIN";
            itemRegistry.put(this.itemNumber, this);
        }
        /// Creates a menu item
        /// @param displayName The displayed name of your item
        /// @param inputValue The variable that you want to modify
        /// @param increment The amount by which your variable's value will change when using the left/right d-pad buttons
        /// @param min The minimum value your variable will be allowed to reach
        /// @param max The maximum value your variable will be allowed to reach
        public MenuItem(String subMenu, String displayName, Number inputValue, Number increment, Number min, Number max)
        {
            numberOfMenuItems ++;
            this.itemNumber = numberOfMenuItems;
            this.itemName = displayName;
            this.itemValue = inputValue;
            this.increment = increment;
            this.min = min;
            this.max = max;
            this.itemSubMenu = subMenu;
            itemRegistry.put(this.itemNumber, this);
        }
        public MenuItem(String displayName, boolean inputValue)
        {
            numberOfMenuItems ++;
            this.itemNumber = numberOfMenuItems;
            this.itemName = displayName;
            this.booleanInputValue = inputValue;
            this.isBoolean = true;
            this.itemSubMenu = "MAIN";
            itemRegistry.put(this.itemNumber, this);
        }
        /// @return The new double value of your menu item
        /// IMPORTANT: This method can only be used once in your loop
        public double getDoubleValue()
        {
            if (inMenu && currentSubMenu.equals(this.itemSubMenu))
            {
                if (this.itemNumber == menuCounter)
                {
                    opMode.telemetry.addData(">  " + this.itemName, this.itemValue);
                    if (opMode.gamepad1.dpad_right && this.itemValue.doubleValue() + this.increment.doubleValue() <= this.max.doubleValue() && !wasIncremented)
                    {
                        this.itemValue = this.itemValue.doubleValue() + this.increment.doubleValue();
                    }

                    this.wasIncremented = opMode.gamepad1.dpad_right;

                    if (opMode.gamepad1.dpad_left && this.itemValue.doubleValue() - this.increment.doubleValue() >= this.min.doubleValue() - 1 && !wasDecremented)
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
            return this.itemValue.doubleValue();
        }

        /// @return The new integer value of your menu item
        /// IMPORTANT: This method can only be used once in your loop
        public int getIntValue()
        {
            if (inMenu && currentSubMenu.equals(this.itemSubMenu))
            {
                if (this.itemNumber == menuCounter)
                {
                    opMode.telemetry.addData(">  " + this.itemName, this.itemValue);
                    if (opMode.gamepad1.dpad_right && this.itemValue.doubleValue() + this.increment.doubleValue() <= this.max.doubleValue() && !wasIncremented)
                    {
                        this.itemValue = this.itemValue.doubleValue() + this.increment.doubleValue();
                    }

                    this.wasIncremented = opMode.gamepad1.dpad_right;

                    if (opMode.gamepad1.dpad_left && this.itemValue.doubleValue() - this.increment.doubleValue() >= this.min.doubleValue() - 1 && !wasDecremented)
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
            return this.itemValue.intValue();
        }
        /// @return The new double value of your menu item
        /// IMPORTANT: This method can only be used once in your loop
        public float getFloatValue()
        {
            if (inMenu && currentSubMenu.equals(this.itemSubMenu))
            {
                if (this.itemNumber == menuCounter)
                {
                    opMode.telemetry.addData(">  " + this.itemName, this.itemValue);
                    if (opMode.gamepad1.dpad_right && this.itemValue.doubleValue() + this.increment.doubleValue() <= this.max.doubleValue() && !wasIncremented)
                    {
                        this.itemValue = this.itemValue.doubleValue() + this.increment.doubleValue();
                    }

                    this.wasIncremented = opMode.gamepad1.dpad_right;

                    if (opMode.gamepad1.dpad_left && this.itemValue.doubleValue() - this.increment.doubleValue() >= this.min.doubleValue() - 1 && !wasDecremented)
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
            return this.itemValue.floatValue();
        }
        public boolean getBooleanValue()
        {
            if (inMenu)// && currentSubMenu.equals(this.itemSubMenu))
            {
                if (this.itemNumber == menuCounter && this.itemSubMenu.equals(currentSubMenu))
                {
                    opMode.telemetry.addData(">  " + this.itemName, this.booleanInputValue);
                    if ((opMode.gamepad1.dpad_right || opMode.gamepad1.dpad_left) && (!this.wasIncremented && !this.wasDecremented))
                    {
                        this.booleanInputValue = !this.booleanInputValue;
                    }
                    this.wasIncremented = opMode.gamepad1.dpad_right;
                    this.wasDecremented = opMode.gamepad1.dpad_left;
                }
                else if (this.itemSubMenu.equals(currentSubMenu))
                {
                    opMode.telemetry.addData(this.itemName, this.booleanInputValue);
                }
            }
            return this.booleanInputValue;
        }
    }
}
