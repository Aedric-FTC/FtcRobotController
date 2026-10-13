package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public enum GamepadButton {
    A {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.a; }
    },
    B {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.b; }
    },
    X {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.x; }
    },
    Y {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.y; }
    },
    RIGHT_BUMPER {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.right_bumper; }
    },
    LEFT_BUMPER {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.left_bumper; }
    },
    RIGHT_STICK_BUTTON {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.right_stick_button; }
    },
    LEFT_STICK_BUTTON {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.left_stick_button; }
    },
    DPAD_UP {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.dpad_up; }
    },
    DPAD_DOWN {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.dpad_down; }
    },
    DPAD_LEFT {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.dpad_left; }
    },
    DPAD_RIGHT {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.dpad_right; }
    },
    START {
        @Override
        public boolean isPressed(Gamepad gamepad) { return gamepad.start; }
    };
    public abstract boolean isPressed(Gamepad gamepad);
}
