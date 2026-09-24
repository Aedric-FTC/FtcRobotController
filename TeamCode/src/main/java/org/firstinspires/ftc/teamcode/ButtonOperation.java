package org.firstinspires.ftc.teamcode;

import java.util.HashMap;

public class ButtonOperation {

    private static final HashMap<String, Boolean> buttonStates = new HashMap<>();

    /**
     * Runs code once when a button is pressed
     * @param buttonId Any unique identifier for this specific action
     * @param isPressed The current raw gamepad button boolean (e.g., gamepad1.a)
     * @param action The custom code block to run
     */
    public static void onClick(String buttonId, boolean isPressed, Runnable action) {
        // Get the last state (default to false if it's the first time running)
        boolean lastState = Boolean.TRUE.equals(buttonStates.getOrDefault(buttonId, false));

        if (isPressed && !lastState) {
            action.run();
        }

        buttonStates.put(buttonId, isPressed);
    }
}
