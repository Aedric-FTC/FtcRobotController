package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonOperation {
    GamepadButton gb;
    Gamepad gamepad;
    boolean buttonWasDown;
    Runnable action;
    public OpMode opMode;
    int gamepadNumber;

    /**
     * Runs code once when a button is pressed
     * @param gamepadButton The button that will trigger the action
     * @param action The custom code block to run
     */
    public ButtonOperation(OpMode opMode, GamepadButton gamepadButton, int gamepadNumber, Runnable action)
    {
        this.gb = gamepadButton;
        this.action = action;
        this.opMode = opMode;
        this.gamepadNumber = gamepadNumber;
    }
    boolean buttonIsDown() {
        if (gamepadNumber == 1) {
            switch (this.gb) {
                case A:
                    return opMode.gamepad1.a;
                case B:
                    return opMode.gamepad1.b;
                case X:
                    return opMode.gamepad1.x;
                case Y:
                    return opMode.gamepad1.y;
                case LEFT_BUMPER:
                    return opMode.gamepad1.left_bumper;
                case RIGHT_BUMPER:
                    return opMode.gamepad1.right_bumper;
                case LEFT_STICK_BUTTON:
                    return opMode.gamepad1.left_stick_button;
                case RIGHT_STICK_BUTTON:
                    return opMode.gamepad1.right_stick_button;
                case DPAD_UP:
                    return opMode.gamepad1.dpad_up;
                case DPAD_DOWN:
                    return opMode.gamepad1.dpad_down;
                case DPAD_RIGHT:
                    return opMode.gamepad1.dpad_right;
                case DPAD_LEFT:
                    return opMode.gamepad1.dpad_left;
                case START:
                    return opMode.gamepad1.start;
                default:
                    return false;
            }
        }
            else {
                switch (this.gb) {
                    case A:
                        return opMode.gamepad2.a;
                    case B:
                        return opMode.gamepad2.b;
                    case X:
                        return opMode.gamepad2.x;
                    case Y:
                        return opMode.gamepad2.y;
                    case LEFT_BUMPER:
                        return opMode.gamepad2.left_bumper;
                    case RIGHT_BUMPER:
                        return opMode.gamepad2.right_bumper;
                    case LEFT_STICK_BUTTON:
                        return opMode.gamepad2.left_stick_button;
                    case RIGHT_STICK_BUTTON:
                        return opMode.gamepad2.right_stick_button;
                    case DPAD_UP:
                        return opMode.gamepad2.dpad_up;
                    case DPAD_DOWN:
                        return opMode.gamepad2.dpad_down;
                    case DPAD_RIGHT:
                        return opMode.gamepad2.dpad_right;
                    case DPAD_LEFT:
                        return opMode.gamepad2.dpad_left;
                    case START:
                        return opMode.gamepad2.start;
                    default:
                        return false;
                }
            }
    }
    public void run() {
        boolean isPressed = this.buttonIsDown();
        if (isPressed && !this.buttonWasDown) {
            this.action.run();
        }
        this.buttonWasDown = isPressed;
    }
}
