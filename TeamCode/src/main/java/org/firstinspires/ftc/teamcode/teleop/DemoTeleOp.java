package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.aedricLib.GamepadButton;
import org.firstinspires.ftc.teamcode.aedricLib.Menu;
import org.firstinspires.ftc.teamcode.aedricLib.RobotJson;

@TeleOp
public class DemoTeleOp extends OpMode {
    Robot robot;
    Menu menu;
    double demoNumber = RobotJson.load("Demo Number").getAsDouble();
    int demoNumber2 = RobotJson.load("Demo Number 2").getAsInt();
    boolean demoBoolean = RobotJson.load("Demo Boolean").getAsBoolean();
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
        menu.getSubMenu("Demo SubMenu").addNumericalItem(
                "Demo Number 2", demoNumber2, 5, 0, 100);
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
            // Only render a SubMenu item after its respective SubMenu has been created
            menu.getSubMenu("Demo SubMenu").getItem("Demo Number 2");
        }
    }
}
