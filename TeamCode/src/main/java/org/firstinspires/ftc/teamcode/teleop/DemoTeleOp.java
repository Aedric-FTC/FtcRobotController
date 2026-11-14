package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.aedricLib.GamepadButton;
import org.firstinspires.ftc.teamcode.aedricLib.Menu;
import org.firstinspires.ftc.teamcode.aedricLib.PWMLight;
import org.firstinspires.ftc.teamcode.aedricLib.RobotJson;

@TeleOp
public class DemoTeleOp extends OpMode {
    Robot robot;
    Menu menu;
    double demoNumber = RobotJson.load("Demo Number").getAsDouble();
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
    }

    @Override
    public void loop()
    {
        // Always .create() any menus immediately at the start of your loop
        menu.create();
        if (!Menu.inAMenu) {
            // Call SubSystem methods from the Robot class like this
            robot.demoMotor.spin(gamepad1.right_bumper);
            // Set solid light colors like this
            robot.demoLight.setColor(PWMLight.Color.GREEN);
        }
        else {
            // Set blinking light colors like this
            robot.demoLight.blink(500, PWMLight.Color.YELLOW);
            // Render numerical menu items by using the .getItem().get<number type>Value() methods
            demoNumber = menu.getItem("Demo Number").getDoubleValue();
            // Render boolean menu items by using the .getItem().getBooleanValue() methods
            demoBoolean = menu.getItem("Demo Boolean").getBooleanValue();
            // Render plain lines of text by using the .getItem().displayLine() methods
            menu.getItem("Demo Plain Line").displayLine();
        }
    }
}
