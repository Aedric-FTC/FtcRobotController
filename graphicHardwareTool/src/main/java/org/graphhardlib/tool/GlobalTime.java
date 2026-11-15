package org.graphhardlib.tool;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GlobalTime {
    public static final int FRAME_COUNT = 30;
    static boolean makeBlue = false;
    static int colorR = 100;
    static int colorG = 100;
    static int colorB = 100;
    public static Timer frames = new Timer(100/FRAME_COUNT, e -> {
        if (makeBlue && colorB < 200) { colorB += 4; colorG ++; colorR = 0; }
        else if (!makeBlue) {
            if (colorB > 100) colorB -= 2;
            if (colorG > 100) colorG --;
            if (colorR < 100) colorR ++;
        }
        MainWindow.saveButton.setBackground(new Color(colorR, colorG, colorB));
    });

    public static void startTimers() {
        frames.start();
    }
}
