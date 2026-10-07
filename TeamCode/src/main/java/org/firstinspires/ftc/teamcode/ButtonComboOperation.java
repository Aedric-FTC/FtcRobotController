package org.firstinspires.ftc.teamcode;

import java.util.function.BooleanSupplier;

public class ButtonComboOperation {
    BooleanSupplier button;
    Runnable action;
    public ButtonComboOperation(BooleanSupplier button, Runnable action)
    {
        this.button = button;
        this.action = action;
    }
    boolean buttonWasDown;
    public void run() {
        boolean isPressed = this.button.getAsBoolean();
        if (isPressed && !this.buttonWasDown) {
            this.action.run();
        }
        this.buttonWasDown = isPressed;
    }
}
