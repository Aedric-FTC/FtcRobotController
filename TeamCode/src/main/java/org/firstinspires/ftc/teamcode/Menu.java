package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    static Map<Integer, Menu> menuRegistry = new HashMap<>();
    private final List<Menu> children = new ArrayList<>();
    /// When creating an instance of this class, place (this) within the parentheses:
    /// ```java
    /// Menu menu = new Menu(this);
    /// ```
    public Menu(OpMode OpMode, GamepadButton menuButton, String menuName)
    {
        opMode = OpMode;
        this.menuCounter = 1;
        globalMenuNumber ++;
        this.menuIndex = globalMenuNumber;
        menuRegistry.put(this.menuIndex, this);
        toggleMenu = new ButtonOperation(opMode.gamepad1, menuButton, () -> {
            for (Menu child : children)
            {
                if (child.outputToggle)
                {
                    child.outputToggle = false;
                    child.inMenu = false;
                    child.runOnMenuExit();
                    child.menuExit();
                    return;
                }
            }
            outputToggle = !outputToggle;
            if (outputToggle)
            {
                runOnMenuEnter();
                menuEnter();
            }
            else {
                runOnMenuExit();
                menuExit();
            }
        });
        closeMenu = new ButtonOperation(opMode.gamepad1, GamepadButton.B, () -> {
            outputToggle = false;
            inMenu = false;
            runOnMenuExit();
            menuExit();
        });
        this.menuButton = menuButton.name();
        this.menuName = menuName;
        numberOfMenuItems = 0;
        runClose = true;
    }
    private Menu(OpMode OpMode, String menuName)
    {
        opMode = OpMode;
        this.menuCounter = 1;
        globalMenuNumber ++;
        this.menuIndex = globalMenuNumber;
        menuRegistry.put(this.menuIndex, this);
        this.menuName = menuName;
        numberOfMenuItems = 0;
        runClose = false;
    }
    public OpMode opMode;
    public String menuButton;
    public boolean inMenu;
    public int menuCounter;
    boolean menuWasIncremented;
    boolean menuWasDecremented;
    public boolean outputToggle;
    public static int globalMenuNumber;
    public int numberOfMenuItems;
    public int menuIndex;
    public String menuName;
    public static boolean inAMenu;
    public String subMenuName;
    public boolean runClose;
    public final Map<Integer, MenuItem> itemRegistry = new HashMap<>();

    public interface menuChange { void run(); }
    private menuChange menuEnter = () -> {};
    private menuChange menuExit = () -> {};
    public void onMenuEnter(menuChange menuChange)  { menuEnter = menuChange; }
    public void onMenuExit(menuChange menuChange)  { menuExit = menuChange; }
    public void runOnMenuEnter() { menuEnter.run(); }
    public void runOnMenuExit() { menuExit.run(); }
    ButtonOperation toggleMenu;
    ButtonOperation closeMenu;

    public void create()
    {
        boolean output;
        if (this.subMenuName == null) toggleMenu.run();

        output = outputToggle;
        inMenu = output;


        if (inMenu)
        {
            setMenuCounter(numberOfMenuItems);
            if (runClose && closeMenu != null) closeMenu.run();
            opMode.telemetry.addLine("Press " + this.menuButton + " to exit the menu");
            opMode.telemetry.addLine();
            opMode.telemetry.addLine("D-Pad up/down to scroll");
            opMode.telemetry.addLine("D-Pad right/left to change values");
            opMode.telemetry.addLine();
        }
        else if (!inAMenu && this.subMenuName == null) {
            opMode.telemetry.addLine("Press " + this.menuButton + " to enter the " + this.menuName + " menu");
        }

        inAMenu = false;
        for (Menu menu : menuRegistry.values())
        {
            if (menu.inMenu) { inAMenu = true; break; }
        }
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
    private void menuEnter()
    {
        for (Menu other : menuRegistry.values())
        {
            if (other != this && other.outputToggle)
            {
                other.outputToggle = false;
                other.inMenu = false;
                other.runOnMenuExit();
                other.menuExit();
            }
        }
    }
    private void menuExit()
    {
        for (int i = 1; i <= numberOfMenuItems; i++)
        {
            MenuItem thisItem = thisItem(i);
            if (!thisItem.isBoolean) {
                RobotJson.save(thisItem.itemName, thisItem.itemValue);
            } else {
                RobotJson.save(thisItem.itemName, thisItem.booleanInputValue);
            }
        }
    }
    public MenuItem thisItem(int itemID)
    {
        return itemRegistry.get(itemID);
    }
    public Menu thisMenu()
    {
        return this;
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
        public int itemSubMenu;
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
            this.itemSubMenu = menuIndex;
            itemRegistry.put(this.itemNumber, this);
        }
        public MenuItem(String displayName, boolean inputValue)
        {
            numberOfMenuItems ++;
            this.itemNumber = numberOfMenuItems;
            this.itemName = displayName;
            this.booleanInputValue = inputValue;
            this.isBoolean = true;
            this.itemSubMenu = menuIndex;
            itemRegistry.put(this.itemNumber, this);
        }
        public MenuItem(String displayName)
        {
            numberOfMenuItems ++;
            this.itemNumber = numberOfMenuItems;
            this.itemName = displayName;
            this.itemSubMenu = menuIndex;
            itemRegistry.put(this.itemNumber, this);
        }
        /// @return The new double value of your menu item
        /// IMPORTANT: This method can only be used once in your loop
        public double getDoubleValue()
        {
            if (inMenu)
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
            if (inMenu)
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
            if (inMenu)
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
            if (inMenu)
            {
                if (this.itemNumber == menuCounter)
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
    }
    public class SubMenu {
        Menu subMenu;
        Menu superMenu;
        Menu.MenuItem subItem;
        ButtonOperation openSubMenu;
        ButtonOperation closeSubMenu;
        public SubMenu(String subMenuName)
        {
            superMenu = thisMenu();
            subMenu = new Menu(opMode, subMenuName);
            subMenu.subMenuName = subMenuName;
            superMenu.children.add(subMenu);
            subMenu.runClose = false;
            subItem = superMenu.new MenuItem(subMenu.subMenuName);
            openSubMenu = new ButtonOperation(opMode.gamepad1, GamepadButton.A, () -> {
                subMenu.outputToggle = true;
                subMenu.inMenu = true;
                subMenu.runOnMenuEnter();
                subMenu.menuEnter();
            });
            closeSubMenu = new ButtonOperation(opMode.gamepad1, GamepadButton.B, () -> {
                superMenu.outputToggle = true;
                superMenu.inMenu = true;
                superMenu.runOnMenuEnter();
                superMenu.menuEnter();
            });
        }
        public void create()
        {
            subMenu.create();
            subItem.itemNumber = numberOfMenuItems;
            if (superMenu.inMenu)
            {
                if (subItem.itemNumber == menuCounter)
                {
                    opMode.telemetry.addLine(">  " + subItem.itemName);
                    openSubMenu.run();
                } else
                {
                    opMode.telemetry.addLine(subItem.itemName);
                }
            }
            else if (subMenu.inMenu)
            {
                closeSubMenu.run();
            }
        }
    }
}
