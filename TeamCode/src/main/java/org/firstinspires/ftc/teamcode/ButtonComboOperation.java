package org.firstinspires.ftc.teamcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class ButtonComboOperation {
    Boolean[] button;
    Runnable action;
    public ButtonComboOperation(Boolean[] buttons, Runnable action)
    {
        this.button = buttons;
        this.action = action;
    }
    boolean buttonWasDown;
    boolean arePressed;
    Map<Integer, Boolean> buttonsPressed = new HashMap<>();
    public void run() {
        for (int i = 1; i < this.button.length - 1; i++)
        {
            if (this.button[i] == this.button[i + 1]) {
                arePressed = true;
                break;
            }
        }

        if (this.arePressed && !this.buttonWasDown) {
            this.action.run();
        }
        this.buttonWasDown = this.arePressed;
    }
}
