package org.firstinspires.ftc.teamcode.aedricLib;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

public class MenuItem {
    //region Variables
    Menu enclosingMenu;
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
    public int itemSubMenu;
    //endregion
    //region Constructors
    /// Creates a menu item
    /// @param displayName The displayed name of your item
    /// @param inputValue The variable that you want to modify
    /// @param increment The amount by which your variable's value will change when using the left/right d-pad buttons
    /// @param min The minimum value your variable will be allowed to reach
    /// @param max The maximum value your variable will be allowed to reach
    public MenuItem(Menu enclosingMenu, String displayName, Number inputValue, Number increment, Number min, Number max)
    {
        this.enclosingMenu = enclosingMenu;
        this.enclosingMenu.numberOfMenuItems ++;
        this.itemNumber = enclosingMenu.numberOfMenuItems;
        this.itemName = displayName;
        this.itemValue = inputValue;
        this.increment = increment;
        this.min = min;
        this.max = max;
        this.itemSubMenu = this.enclosingMenu.menuIndex;
        this.enclosingMenu.itemRegistry.put(this.itemNumber, this);
        this.enclosingMenu.itemNameRegistry.put(this.itemName, this);
    }
    public MenuItem(Menu enclosingMenu, String displayName, boolean inputValue)
    {
        this.enclosingMenu = enclosingMenu;
        this.enclosingMenu.numberOfMenuItems ++;
        this.itemNumber = this.enclosingMenu.numberOfMenuItems;
        this.itemName = displayName;
        this.booleanInputValue = inputValue;
        this.isBoolean = true;
        this.itemSubMenu = this.enclosingMenu.menuIndex;
        this.enclosingMenu.itemRegistry.put(this.itemNumber, this);
        this.enclosingMenu.itemNameRegistry.put(this.itemName, this);
    }
    public MenuItem(Menu enclosingMenu, String displayName)
    {
        this.enclosingMenu = enclosingMenu;
        this.enclosingMenu.numberOfMenuItems ++;
        this.itemNumber = this.enclosingMenu.numberOfMenuItems;
        this.itemName = displayName;
        this.itemSubMenu = this.enclosingMenu.menuIndex;
        this.enclosingMenu.itemRegistry.put(this.itemNumber, this);
        this.enclosingMenu.itemNameRegistry.put(this.itemName, this);
    }
    //endregion
    /// @return The new double value of your menu item
    public double getDoubleValue()
    {
        if (this.enclosingMenu.inMenu)
        {
            if (this.itemNumber == this.enclosingMenu.menuCounter)
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
    public int getIntValue()
    {
        if (this.enclosingMenu.inMenu)
        {
            if (this.itemNumber == this.enclosingMenu.menuCounter)
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
    public float getFloatValue()
    {
        if (this.enclosingMenu.inMenu)
        {
            if (this.itemNumber == this.enclosingMenu.menuCounter)
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
    ///@return The new boolean value of your menu item
    public boolean getBooleanValue()
    {
        if (this.enclosingMenu.inMenu)
        {
            if (this.itemNumber == this.enclosingMenu.menuCounter)
            {
                opMode.telemetry.addData(">  " + this.itemName, this.booleanInputValue);
                if ((opMode.gamepad1.dpad_right || opMode.gamepad1.dpad_left) && (!this.wasIncremented && !this.wasDecremented))
                {
                    this.booleanInputValue = !this.booleanInputValue;
                }
                this.wasIncremented = opMode.gamepad1.dpad_right;
                this.wasDecremented = opMode.gamepad1.dpad_left;
            }
            else
            {
                opMode.telemetry.addData(this.itemName, this.booleanInputValue);
            }
        }
        return this.booleanInputValue;
    }
    public void displayLine()
    { if (this.enclosingMenu.inMenu) opMode.telemetry.addLine(this.itemName); }
}
