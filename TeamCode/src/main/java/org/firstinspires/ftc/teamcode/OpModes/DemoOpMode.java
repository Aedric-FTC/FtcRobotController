package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.aedricLib.GamepadButton;
import org.firstinspires.ftc.teamcode.aedricLib.Menu;

public class DemoOpMode extends OpMode {
    Robot robot;
    Menu menu;
    double demoNumber;
    boolean demoBoolean;
    @Override
    public void init()
    {
        // Always instantiate a Robot class to be able to use the SubSystems within
        robot = new Robot(this);
        menu = new Menu(this, GamepadButton.START, "Demo Menu");
        menu.addNumericalItem("Demo Number", demoNumber, 5, 0, 100);
        menu.addBooleanItem("Demo Boolean", demoBoolean);
        menu.addLine("Demo Plain Line");
        menu.addSubMenu("Demo SubMenu");
    }

    @Override
    public void loop()
    {
        // Always .create() any menus immediately at the start of your loop
        menu.create();
        if (!Menu.inAMenu) {
            // Call SubSystems from the Robot class like this
            robot.demoMotor.spin(gamepad1.right_bumper);
        }
        else {
            // Render numerical menu items by using the .getItem().get<number type>Value() methods
            demoNumber = menu.getItem("Demo Number").getDoubleValue();
            // Render boolean menu items by using the .getItem().getBooleanValue() methods
            demoBoolean = menu.getItem("Demo Boolean").getBooleanValue();
            // Render plain lines of text by using the .getItem().displayLine() methods
            menu.getItem("Demo Plain Line").displayLine();
            // Render SubMenus with the same .create() method as a normal menu
            menu.getSubMenu("Demo SubMenu").create();
        }
    }
}
