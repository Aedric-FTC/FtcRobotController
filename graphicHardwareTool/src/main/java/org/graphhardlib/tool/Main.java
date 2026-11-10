package org.graphhardlib.tool;

import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless())
        {
            System.err.println("Requires a display.");
            return;
        }
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
