package org.firstinspires.ftc.teamcode.aedricLib;

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
    public final List<Menu> children = new ArrayList<>();
    //region Menu Constructors
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
            if (outputToggle && this.subMenuName == null)
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
    public Menu(OpMode OpMode, String menuName)
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
    //endregion

    //region Variables
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
    public final Map<String, MenuItem> itemNameRegistry = new HashMap<>();
    public final Map<String, Menu> subMenuRegistry = new HashMap<>();

    public interface menuChange { void run(); }
    public menuChange menuEnter = () -> {};
    public menuChange menuExit = () -> {};
    public void onMenuEnter(menuChange menuChange)  { menuEnter = menuChange; }
    public void onMenuExit(menuChange menuChange)  { menuExit = menuChange; }
    public void runOnMenuEnter() { menuEnter.run(); }
    public void runOnMenuExit() { menuExit.run(); }
    ButtonOperation toggleMenu;
    ButtonOperation closeMenu;
    //endregion

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
        else if (!inAMenu && this.subMenuName == null){
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
    void menuEnter()
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
            MenuItem thisItem = itemFromReg(i);
            if (!thisItem.isBoolean) {
                RobotJson.save(thisItem.itemName, thisItem.itemValue);
            } else {
                RobotJson.save(thisItem.itemName, thisItem.booleanInputValue);
            }
        }
    }
    public MenuItem itemFromReg(int itemID) { return itemRegistry.get(itemID); }
    public MenuItem getItem(String itemName) { return itemNameRegistry.get(itemName); }
    public Menu getSubMenu(String subMenuName) { return subMenuRegistry.get(subMenuName); }
    public void addNumericalItem(String itemName, Number inputValue, int increment, Number min, Number max)
    { new MenuItem(this, itemName, inputValue, increment, min, max);}
    public void addBooleanItem(String itemName, boolean inputValue)
    { new MenuItem(this, itemName, inputValue); }
    public void addLine(String itemName)
    { new MenuItem(this, itemName); }
    public void addSubMenu(String subMenuName)
    { new SubMenu(this, subMenuName); }
    public Menu thisMenu()
    {
        return this;
    }

}
