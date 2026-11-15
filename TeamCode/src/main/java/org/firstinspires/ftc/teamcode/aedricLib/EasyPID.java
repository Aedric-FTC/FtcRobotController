package org.firstinspires.ftc.teamcode.aedricLib;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class EasyPID {
    public double kP = RobotJson.load("kP").getAsDouble();
    public double kI = RobotJson.load("kI").getAsDouble();
    public double kD = RobotJson.load("kD").getAsDouble();
    private double integral, lastError;
    private long lastTime = System.nanoTime();
    public OpMode opMode;
    Menu tuningMenu;
    MenuItem menuKP;
    MenuItem menuKI;
    MenuItem menuKD;

    public EasyPID(OpMode opMode) {
        this.opMode = opMode;
        tuningMenu = new Menu(opMode, GamepadButton.Y, "PID Tuning");
        menuKP = new MenuItem(tuningMenu, "kP", this.kP, 0.05, 0, 5);
        menuKI = new MenuItem(tuningMenu, "kI", this.kI, 0.05, 0, 5);
        menuKD = new MenuItem(tuningMenu, "kD", this.kD, 0.05, 0, 5);
    }

    public double update(double target, double current, boolean useLiveTuningMenu) {
        if (useLiveTuningMenu) {
            tuningMenu.create();
            kP = menuKP.getDoubleValue();
            kI = menuKI.getDoubleValue();
            kD = menuKD.getDoubleValue();
        }

        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        double error = target - current;
        integral += error * dt;
        double derivative = dt > 0 ? (error - lastError) / dt : 0;
        lastError = error;

        return kP * error + kI * integral + kD * derivative;
    }

    public void reset() { integral = 0; lastError = 0; }
}
