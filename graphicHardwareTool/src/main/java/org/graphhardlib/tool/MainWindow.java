package org.graphhardlib.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class MainWindow extends JFrame {
    public String loadedHub = "ctrl";
    public int loadedProfile = JSONSaver.loadGlobal("Current Profile").getAsInt();
    public final JLayeredPane layeredPane = new JLayeredPane();
    public final JLabel ctrlHub = new JLabel();
    public final JLabel topBar = new JLabel();
    public JButton saveButton = new JButton("Save");
    public JButton resetButton = new JButton("Reset");
    public JButton switchButton = new JButton((loadedHub.equals("ctrl")) ? "Switch to Expansion Hub" : "Switch to Control Hub");
    public JOptionPane resetDialog = new JOptionPane();
    public final JLabel profileLabel = new JLabel("Select Profile:");
    public final JLabel currentProfile = new JLabel();
    public JLabel profile1;
    public JLabel profile2;
    public JLabel profile3;
    public JLabel profile4;
    public JLabel profile5;
    public final Color bgColor = new Color(50,50,50);
    public final Border border = BorderFactory.createMatteBorder(0,0,1,0,Color.LIGHT_GRAY);
    public final Border profileBorder = BorderFactory.createLineBorder(Color.WHITE, 2);
    public final double sizeMultiplier = 1.5;
    public static Map<String, JTextField> boxes = new HashMap<>();
    private final List<JComponent> labelComponents = new ArrayList<>();

    public MainWindow() {
        super("Hardware Config");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.DARK_GRAY);

        layeredPane.setPreferredSize(new Dimension(1280, 720));
        layeredPane.setOpaque(true);
        layeredPane.setBackground(bgColor);

        topBar.setBounds(0,0,1280,100);
        topBar.setBackground(new Color(25,25,25));
        topBar.setOpaque(true);
        layeredPane.add(topBar, 1);

        createLabels(loadedHub);

        saveButton.setBounds(1000, 650, 100, 35);
        saveButton.setBackground(new Color(0,100,200));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createLoweredBevelBorder());
        saveButton.setOpaque(true);
        saveButton.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        saveButton.setFocusable(false);
        saveButton.addActionListener((e) -> JSONSaver.save(loadedHub, loadedProfile, boxes));
        layeredPane.add(saveButton, JLayeredPane.PALETTE_LAYER);

        int[] willReset = {1};
        resetButton.setBounds(1120, 650, 100, 35);
        resetButton.setBackground(new Color(100,100,100));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorder(BorderFactory.createLoweredBevelBorder());
        resetButton.setOpaque(true);
        resetButton.setFont(new Font("Sans Serif", Font.PLAIN, 25));
        resetButton.setFocusable(false);
        resetButton.addActionListener((e) ->
        {
            willReset[0] = JOptionPane.showOptionDialog(resetDialog, "Reset all values in " + loadedHub + " hub of profile " + loadedProfile + "?", "Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Reset", "Cancel"}, "Cancel");
            if (willReset[0] == 0)
            {
                boxes.forEach((key, value) -> value.setText(""));
            }
            JSONSaver.save(loadedHub, loadedProfile, boxes);
        });
        layeredPane.add(resetButton, JLayeredPane.PALETTE_LAYER);

        switchButton.setBounds(60, 650, 220, 35);
        switchButton.setBackground(new Color(100,100,100));
        switchButton.setForeground(Color.WHITE);
        switchButton.setBorder(BorderFactory.createLoweredBevelBorder());
        switchButton.setOpaque(true);
        switchButton.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        switchButton.setFocusable(false);
        switchButton.addActionListener((e) ->
        {
            ctrlHub.setIcon(loadIcon((loadedHub.equals("ctrl")) ? "/ExpansionHub.png" : "/ControlHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
            loadedHub = (loadedHub.equals("ctrl")) ? "exp" : "ctrl";
            switchButton.setText((loadedHub.equals("ctrl")) ? "Switch to Expansion Hub" : "Switch to Control Hub");
            JSONSaver.save("Current Hub", loadedHub);
            boxes.clear();
            createLabels(loadedHub);
        });
        layeredPane.add(switchButton, JLayeredPane.PALETTE_LAYER);

        if (loadedHub.equals("ctrl")) {
            ctrlHub.setIcon(loadIcon("/ControlHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
        } else {
            ctrlHub.setIcon(loadIcon("/ExpansionHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
        }
        ctrlHub.setBounds(centeredX(), centeredY(), (int)(415 * sizeMultiplier), (int)(300 * sizeMultiplier));
        layeredPane.add(ctrlHub, 0);

        profile1 = createPreset(1);
        profile2 = createPreset(2);
        profile3 = createPreset(3);
        profile4 = createPreset(4);
        profile5 = createPreset(5);
        profileLabel.setBounds(300, 20, 160, 60);
        profileLabel.setBackground(Color.DARK_GRAY);
        profileLabel.setForeground(Color.WHITE);
        profileLabel.setVerticalAlignment(SwingConstants.CENTER);
        profileLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        profileLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        currentProfile.setBounds((1280/2) - (100/2), 100, 100, 35);
        currentProfile.setText("Profile " + loadedProfile);
        currentProfile.setForeground(Color.WHITE);
        currentProfile.setVerticalAlignment(SwingConstants.CENTER);
        currentProfile.setHorizontalAlignment(SwingConstants.CENTER);
        currentProfile.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));


        layeredPane.add(profile1, 0);
        layeredPane.add(profile2, 0);
        layeredPane.add(profile3, 0);
        layeredPane.add(profile4, 0);
        layeredPane.add(profile5, 0);
        layeredPane.add(profileLabel, 0);
        layeredPane.add(currentProfile, 0);

        add(layeredPane);
        setContentPane(layeredPane);
        pack();
    }
    private JLabel createPreset(int number)
    {
        JLabel label = new JLabel(Integer.toString(number));
        label.setBounds(470 + ((number - 1) * 70), 20, 60, 60);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setForeground(Color.WHITE);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        label.setBorder(profileBorder);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                loadedProfile = number;
                createLabels(loadedHub);
                currentProfile.setText("Profile " + loadedProfile);
                JSONSaver.save("Current Profile", loadedProfile);
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
        field.setText(JSONSaver.load(loadedHub, loadedProfile, name).getAsString());
        field.setBorder(border);
        field.setBackground(bgColor);
        field.setForeground(Color.WHITE);
        field.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        field.setCaretColor(Color.WHITE);
        boxes.put(name, field);
        labelComponents.add(field);
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
            @Override
            public boolean contains(int x, int y) {
                return false; // never intercept mouse events
            }
        };
        linePanel.setOpaque(false);
        linePanel.setBounds(0, 0, 1280, 720);
        layeredPane.add(linePanel, JLayeredPane.PALETTE_LAYER);
        labelComponents.add(linePanel);
    }
    void createLabels(String hub)
    {
        for (JComponent c : labelComponents) {
            layeredPane.remove(c);
        }
        labelComponents.clear();
        boxes.clear();
        Label.reset();

        new Label(this, hub, Side.RIGHT, "UART 2", 0);
        new Label(this, hub, Side.RIGHT, "UART 4", 0);
        new Label(this, hub, Side.RIGHT, "RS485 6", 0);
        new Label(this, hub, Side.RIGHT, "RS485 8", 0);

        new Label(this, hub, Side.RIGHT, "I2C 3", 6);
        new Label(this, hub, Side.RIGHT, "I2C 2", 6);
        new Label(this, hub, Side.RIGHT, "I2C 1", 6);
        new Label(this, hub, Side.RIGHT, "I2C 0", 6);

        new Label(this, hub, Side.RIGHT, "DIGITAL 6-7", 13);
        new Label(this, hub, Side.RIGHT, "DIGITAL 4-5", 13);
        new Label(this, hub, Side.RIGHT, "DIGITAL 2-3", 13);
        new Label(this, hub, Side.RIGHT, "DIGITAL 0-1", 13);

        new Label(this, hub, Side.RIGHT, "ANALOG 2-3", 20);
        new Label(this, hub, Side.RIGHT, "ANALOG 0-1", 20);

        new Label(this, hub, Side.LEFT, "MOTOR 0", 80);
        new Label(this, hub, Side.LEFT, "ENCODER 0", 80);
        new Label(this, hub, Side.LEFT, "MOTOR 1", 89);
        new Label(this, hub, Side.LEFT, "ENCODER 1", 89);
        new Label(this, hub, Side.LEFT, "MOTOR 2", 98);
        new Label(this, hub, Side.LEFT, "ENCODER 2", 98);
        new Label(this, hub, Side.LEFT, "MOTOR 3", 107);
        new Label(this, hub, Side.LEFT, "ENCODER 3", 107);

        new Label(this, hub, Side.BOTTOM, "SERVO 0", 0);
        new Label(this, hub, Side.BOTTOM, "SERVO 1", 0);
        new Label(this, hub, Side.BOTTOM, "SERVO 2", 0);
        new Label(this, hub, Side.BOTTOM, "SERVO 3", 0);
        new Label(this, hub, Side.BOTTOM, "SERVO 4", 0);
        new Label(this, hub, Side.BOTTOM, "SERVO 5", 0);
    }
}

class Label {
    public static int labelCountR;
    public static int labelCountL;
    public static int labelCountB;
    public String fullLabel;
    public static int bYOffset;
    public Label(MainWindow w, String hub, Side side, String fullLabel, int extraYOffset)
    {
        Map<String, String> portLabels = new HashMap<>();
        if (side == Side.RIGHT)
        {
            labelCountR++;
            w.layeredPane.add(w.textBox(fullLabel, w.labelX(), w.labelY() + ((labelCountR - 1) * 21) + extraYOffset), JLayeredPane.PALETTE_LAYER);
            w.drawLine(new Coordinate(w.labelX() - 90, w.labelY() + ((labelCountR - 1) * 21) + 18 + extraYOffset),
                    new Coordinate(w.labelX() + 100, w.labelY() + ((labelCountR - 1) * 21) + 18 + extraYOffset));
            this.fullLabel = fullLabel;
        }
        else if (side == Side.LEFT)
        {
            labelCountL++;
            w.layeredPane.add(w.textBox(fullLabel, 1280 - w.labelX() - 110, w.labelY() + ((labelCountL - 1) * 25) + extraYOffset), JLayeredPane.PALETTE_LAYER);
            w.drawLine(new Coordinate(1280 - w.labelX() - 110, w.labelY() + ((labelCountL - 1) * 25) + 18 + extraYOffset),
                    new Coordinate(1280 - w.labelX() + 60, w.labelY() + ((labelCountL - 1) * 25) + 18 + extraYOffset));
            this.fullLabel = fullLabel;
        }
        else
        {
            labelCountB++;
            bYOffset++;
            if (bYOffset == 4) bYOffset = 1;
            w.layeredPane.add(w.textBox(fullLabel, 1280 - w.labelX() + ((labelCountB - 1) * 43) + 150, w.labelY() - ((labelCountB - 1) * 20) + extraYOffset + 500), JLayeredPane.PALETTE_LAYER);
            w.drawLine(new Coordinate(1280 - w.labelX() + ((labelCountB - 1) * 43) + 150 - 2, w.labelY() - ((labelCountB - 1) * 20) + 16 + extraYOffset + 500),
                    new Coordinate(1280 - w.labelX() + ((labelCountB - 1) * 43) + 150 - 2, w.labelY() + 16 + extraYOffset + 330));
            this.fullLabel = fullLabel;
        }
        portLabels.put(this.fullLabel, "Device Name");
    }
    public static void reset() {
        labelCountR = 0;
        labelCountL = 0;
        labelCountB = 0;
        bYOffset = 0;
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