package org.graphhardlib.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class MainWindow extends JFrame {
    public final JLayeredPane layeredPane = new JLayeredPane();
    public final JLabel ctrlHub = new JLabel();
    public final JLabel topBar = new JLabel();
    public final Color bgColor = new Color(50,50,50);
    public final Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    public final double sizeMultiplier = 1.5;

    public MainWindow() {
        super("Hardware Config");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.DARK_GRAY);

        layeredPane.setBounds(0,0,1280,720);
        layeredPane.setOpaque(true);
        layeredPane.setBackground(bgColor);

        topBar.setBounds(0,0,1280,100);
        topBar.setBackground(new Color(25,25,25));
        topBar.setOpaque(true);
        layeredPane.add(topBar, 1);

        new Label(this, Side.RIGHT, "UART 2", 0);
        new Label(this, Side.RIGHT, "UART 4", 0);
        new Label(this, Side.RIGHT, "RS485 6", 0);
        new Label(this, Side.RIGHT, "RS485 8", 0);

        new Label(this, Side.RIGHT, "I2C 3", 6);
        new Label(this, Side.RIGHT, "I2C 2", 6);
        new Label(this, Side.RIGHT, "I2C 1", 6);
        new Label(this, Side.RIGHT, "I2C 0", 6);

        new Label(this, Side.RIGHT, "DIGITAL 6-7", 13);
        new Label(this, Side.RIGHT, "DIGITAL 4-5", 13);
        new Label(this, Side.RIGHT, "DIGITAL 2-3", 13);
        new Label(this, Side.RIGHT, "DIGITAL 0-1", 13);

        new Label(this, Side.RIGHT, "ANALOG 2-3", 20);
        new Label(this, Side.RIGHT, "ANALOG 0-1", 20);

        new Label(this, Side.LEFT, "MOTOR 0", 80);
        new Label(this, Side.LEFT, "ENCODER 0", 80);
        new Label(this, Side.LEFT, "MOTOR 0", 89);
        new Label(this, Side.LEFT, "ENCODER 0", 89);


        ctrlHub.setIcon(loadIcon("/ControlHub.png", (int)(415 * sizeMultiplier), (int)(300 * sizeMultiplier)));
        ctrlHub.setBounds(centeredX(), centeredY(), (int)(415 * sizeMultiplier), (int)(300 * sizeMultiplier));
        layeredPane.add(ctrlHub, 0);
        add(layeredPane);
    }

    int centeredX() { return (int)((1280 - (415 * sizeMultiplier)) / 2); }
    int centeredY() { return (int)((720 - (300 * sizeMultiplier)) / 2); }
    int labelX() { return centeredX() + (int)(415 * sizeMultiplier) + 10; }
    int labelY() { return centeredY() + 57; }

    private static ImageIcon loadIcon(String path, int w, int h) {
        URL url = MainWindow.class.getResource(path);
        if (url == null) {
            System.err.println("Missing resource: " + path);
            return null;
        }
        Image scaled = new ImageIcon(url).getImage()
                .getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public JTextField textBox(int x, int y)
    {
        JTextField field = new JTextField();
        field.setBounds(x, y, 100, 16);
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        field.setText("Device Name");
        field.setBorder(border);
        field.setBackground(bgColor);
        field.setForeground(Color.WHITE);
        return field;
    }

    public void drawLine(Coordinate startPos, Coordinate endPos)
    {
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 220, 180));
                g2d.setStroke(new BasicStroke(2.0f)); // Line thickness
                g2d.drawLine(startPos.x, startPos.y, endPos.x, endPos.y);       // (x1, y1, x2, y2)
            }
        };
        linePanel.setOpaque(false);
        linePanel.setBounds(0, 0, 1280, 720);
        layeredPane.add(linePanel, JLayeredPane.PALETTE_LAYER);
    }
}

class Label {
    public static int labelCountR;
    public static int labelCountL;
    public String portLabel;
    public Label(MainWindow w, Side side, String portLabel, int extraYOffset)
    {
        if (side == Side.RIGHT)
        {
            labelCountR++;
            w.layeredPane.add(w.textBox(w.labelX(), w.labelY() + ((labelCountR - 1) * 21) + extraYOffset), 2);
            w.drawLine(new Coordinate(w.labelX() - 90, w.labelY() + ((labelCountR - 1) * 21) + 18 + extraYOffset),
                    new Coordinate(w.labelX() + 100, w.labelY() + ((labelCountR - 1) * 21) + 18 + extraYOffset));
            this.portLabel = portLabel;
        }
        else if (side == Side.LEFT)
        {
            labelCountL++;
            w.layeredPane.add(w.textBox(1280 - w.labelX() - 110, w.labelY() + ((labelCountL - 1) * 25) + extraYOffset), 2);
            w.drawLine(new Coordinate(1280 - w.labelX() - 110, w.labelY() + ((labelCountL - 1) * 25) + 18 + extraYOffset),
                    new Coordinate(1280 - w.labelX() + 60, w.labelY() + ((labelCountL - 1) * 25) + 18 + extraYOffset));
            this.portLabel = portLabel;
        }
    }
}

enum Side {
    RIGHT, BOTTOM, LEFT
}

class Coordinate {
    public int x;
    public int y;
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}