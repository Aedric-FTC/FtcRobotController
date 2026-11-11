package org.graphhardlib.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class MainWindow extends JFrame {
    public final JLayeredPane layeredPane = new JLayeredPane();
    public final JLabel ctrlHub = new JLabel();
    public final JLabel topBar = new JLabel();
    public final JLabel rightSwap = new JLabel("➡");
    public final JLabel leftSwap = new JLabel("⬅");
    public final JLabel presetLabel = new JLabel("Profiles:");
    public JLabel preset1;
    public JLabel preset2;
    public JLabel preset3;
    public JLabel preset4;
    public JLabel preset5;
    public final Color bgColor = new Color(50,50,50);
    public final Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    public final Border presetBorder = BorderFactory.createLineBorder(Color.WHITE, 2);
    public final double sizeMultiplier = 1.5;
    public static Map<String, JTextField> boxes = new HashMap<>();
    public String loadedHub = "ctrl";

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

        createLabels();

        JSONSaver.save("TestMap", boxes);

        if (loadedHub.equals("ctrl")) {
            ctrlHub.setIcon(loadIcon("/ControlHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
        } else {
            ctrlHub.setIcon(loadIcon("/ExpansionHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
        }
        ctrlHub.setBounds(centeredX(), centeredY(), (int)(415 * sizeMultiplier), (int)(300 * sizeMultiplier));
        layeredPane.add(ctrlHub, 0);

        rightSwap.setBounds(1180, 360, 60, 60);
        rightSwap.setBackground(new Color(75,75,75));
        rightSwap.setOpaque(true);
        rightSwap.setHorizontalAlignment(SwingConstants.CENTER);
        rightSwap.setVerticalAlignment(SwingConstants.CENTER);
        rightSwap.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 45));
        rightSwap.setForeground(Color.WHITE);
        rightSwap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ctrlHub.setIcon(loadIcon("/ExpansionHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
                rightSwap.setVisible(false);
                leftSwap.setVisible(true);
                loadedHub = "exp";
            }
        });
        layeredPane.add(rightSwap, 2);

        leftSwap.setVisible(false);
        leftSwap.setBounds(40, 360, 60, 60);
        leftSwap.setBackground(new Color(75,75,75));
        leftSwap.setOpaque(true);
        leftSwap.setHorizontalAlignment(SwingConstants.CENTER);
        leftSwap.setVerticalAlignment(SwingConstants.CENTER);
        leftSwap.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 45));
        leftSwap.setForeground(Color.WHITE);
        leftSwap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ctrlHub.setIcon(loadIcon("/ControlHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
                leftSwap.setVisible(false);
                rightSwap.setVisible(true);
                loadedHub = "ctrl";
            }
        });
        layeredPane.add(leftSwap, 2);

        preset1 = createPreset(1);
        preset2 = createPreset(2);
        preset3 = createPreset(3);
        preset4 = createPreset(4);
        preset5 = createPreset(5);
        presetLabel.setBounds(275, 20, 100, 60);
        presetLabel.setBackground(Color.DARK_GRAY);
        presetLabel.setForeground(Color.WHITE);
        presetLabel.setVerticalAlignment(SwingConstants.CENTER);
        presetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        presetLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        layeredPane.add(preset1, 0);
        layeredPane.add(preset2, 0);
        layeredPane.add(preset3, 0);
        layeredPane.add(preset4, 0);
        layeredPane.add(preset5, 0);
        layeredPane.add(presetLabel, 0);

        add(layeredPane);
    }
    private JLabel createPreset(int number)
    {
        JLabel label = new JLabel(Integer.toString(number));
        label.setBounds(400 + ((number - 1) * 70), 20, 60, 60);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setForeground(Color.WHITE);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        label.setBorder(presetBorder);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        return label;
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

    public JTextField textBox(String name, int x, int y)
    {
        JTextField field = new JTextField();
        field.setBounds(x, y, 100, 16);
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        field.setText(JSONSaver.load(name).getAsString());
        field.setBorder(border);
        field.setBackground(bgColor);
        field.setForeground(Color.WHITE);
        boxes.put(name, field);
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
    void createLabels()
    {
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
        new Label(this, Side.LEFT, "MOTOR 1", 89);
        new Label(this, Side.LEFT, "ENCODER 1", 89);
        new Label(this, Side.LEFT, "MOTOR 2", 98);
        new Label(this, Side.LEFT, "ENCODER 2", 98);
        new Label(this, Side.LEFT, "MOTOR 3", 107);
        new Label(this, Side.LEFT, "ENCODER 3", 107);

        new Label(this, Side.BOTTOM, "SERVO 0", 0);
        new Label(this, Side.BOTTOM, "SERVO 1", 0);
        new Label(this, Side.BOTTOM, "SERVO 2", 0);
        new Label(this, Side.BOTTOM, "SERVO 3", 0);
        new Label(this, Side.BOTTOM, "SERVO 4", 0);
        new Label(this, Side.BOTTOM, "SERVO 5", 0);
    }
}

class Label {
    public static int labelCountR;
    public static int labelCountL;
    public static int labelCountB;
    public String portLabel;
    public static int bYOffset;
    public Label(MainWindow w, Side side, String portLabel, int extraYOffset)
    {
        Map<String, String> portLabels = new HashMap<>();
        if (side == Side.RIGHT)
        {
            labelCountR++;
            w.layeredPane.add(w.textBox(portLabel, w.labelX(), w.labelY() + ((labelCountR - 1) * 21) + extraYOffset), 2);
            w.drawLine(new Coordinate(w.labelX() - 90, w.labelY() + ((labelCountR - 1) * 21) + 18 + extraYOffset),
                    new Coordinate(w.labelX() + 100, w.labelY() + ((labelCountR - 1) * 21) + 18 + extraYOffset));
            this.portLabel = portLabel;
        }
        else if (side == Side.LEFT)
        {
            labelCountL++;
            w.layeredPane.add(w.textBox(portLabel, 1280 - w.labelX() - 110, w.labelY() + ((labelCountL - 1) * 25) + extraYOffset), 2);
            w.drawLine(new Coordinate(1280 - w.labelX() - 110, w.labelY() + ((labelCountL - 1) * 25) + 18 + extraYOffset),
                    new Coordinate(1280 - w.labelX() + 60, w.labelY() + ((labelCountL - 1) * 25) + 18 + extraYOffset));
            this.portLabel = portLabel;
        }
        else
        {
            labelCountB++;
            bYOffset++;
            if (bYOffset == 4) bYOffset = 1;
            w.layeredPane.add(w.textBox(portLabel, 1280 - w.labelX() + ((labelCountB - 1) * 43) + 150, w.labelY() - ((labelCountB - 1) * 20) + extraYOffset + 500), 2);
            w.drawLine(new Coordinate(1280 - w.labelX() + ((labelCountB - 1) * 43) + 150 - 2, w.labelY() - ((labelCountB - 1) * 20) + 16 + extraYOffset + 500),
                    new Coordinate(1280 - w.labelX() + ((labelCountB - 1) * 43) + 150 - 2, w.labelY() + 16 + extraYOffset + 330));
            this.portLabel = portLabel;
        }
        portLabels.put(portLabel, "Device Name");
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