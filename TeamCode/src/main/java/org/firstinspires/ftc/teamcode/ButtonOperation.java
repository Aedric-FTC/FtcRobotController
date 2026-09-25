package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonOperation {
    GamepadButton gb;
    Gamepad gamepad;
    boolean isPressed;
    boolean buttonWasDown;
    Runnable action;

    /**
     * Runs code once when a button is pressed
     * @param gamepadButton The button that will trigger the action
     * @param action The custom code block to run
     */
    public ButtonOperation(GamepadButton gamepadButton, Runnable action)
    {
        this.gb = gamepadButton;
        this.action = action;
    }
    boolean buttonIsDown() {
        switch (this.gb)
        {
            case A: return gamepad.a;
            case B: return gamepad.b;
            case X: return gamepad.x;
            case Y: return gamepad.y;
            case LEFT_BUMPER: return gamepad.left_bumper;
            case RIGHT_BUMPER: return  gamepad.right_bumper;
            case LEFT_STICK_BUTTON: return gamepad.left_stick_button;
            case RIGHT_STICK_BUTTON: return gamepad.right_stick_button;
            default: return false;
        }
    }
    public void run() {
        if (this.buttonIsDown() && !this.buttonWasDown) {
            this.action.run();
        }
        this.buttonWasDown = this.isPressed;
    }
}
