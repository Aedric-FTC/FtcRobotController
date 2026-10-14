package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonOperation {
    GamepadButton gb;
    Gamepad gamepad;
    boolean buttonWasDown;
    public Runnable action;

    /**
     * Runs code once when a button is pressed
     * @param gamepadButton The button that will trigger the action
     * @param action The custom code block to run
     */
    public ButtonOperation(Gamepad gamepad, GamepadButton gamepadButton, Runnable action)
    {
        this.gb = gamepadButton;
        this.action = action;
        this.gamepad = gamepad;
    }
    public void run() {
        boolean isPressed = this.gb.isPressed(this.gamepad);
        if (isPressed && !this.buttonWasDown) {
            this.action.run();
        }
        this.buttonWasDown = isPressed;
    }
}
