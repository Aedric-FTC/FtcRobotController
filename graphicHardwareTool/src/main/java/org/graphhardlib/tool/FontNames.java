package org.graphhardlib.tool;

import java.awt.GraphicsEnvironment;

public class FontNames {
    public static void main(String[] args)
    {
        String[] fonts = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        for (String font : fonts)
        {
            System.out.println(font);
        }
    }
}