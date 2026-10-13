package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class EasyPID {
    public double kP = RobotJson.load("kP").getAsDouble();
    public double kI = RobotJson.load("kI").getAsDouble();
    public double kD = RobotJson.load("kD").getAsDouble();
    private double integral, lastError;
    private long lastTime = System.nanoTime();
    Menu tuningMenu;
    Menu.MenuItem menuKP;
    Menu.MenuItem menuKI;
    Menu.MenuItem menuKD;

    public EasyPID(OpMode opMode) {
        tuningMenu = new Menu(opMode);
        menuKP = tuningMenu.new MenuItem("kP", this.kP, 0.05, 0, 5);
        menuKI = tuningMenu.new MenuItem("kI", this.kI, 0.05, 0, 5);
        menuKD = tuningMenu.new MenuItem("kD", this.kD, 0.05, 0, 5);
    }

    public double update(double target, double current, boolean useLiveTuningMenu) {
        if (useLiveTuningMenu) {
            tuningMenu.create(GamepadButton.Y, 1);
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
