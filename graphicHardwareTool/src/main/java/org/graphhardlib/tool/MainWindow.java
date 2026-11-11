package org.graphhardlib.tool;

import static org.graphhardlib.tool.JSONSaver.DEVICE_PATH;
import static org.graphhardlib.tool.JSONSaver.save;

import com.google.gson.Gson;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class MainWindow extends JFrame {
    public String loadedHub = "ctrl";
    public static int loadedProfile = JSONSaver.loadGlobal("Current Profile").getAsInt();
    public final JLayeredPane layeredPane = new JLayeredPane();
    public final JLabel ctrlHub = new JLabel();
    public final JLabel topBar = new JLabel();
    public JButton saveButton = new JButton("Save");
    public JButton resetButton = new JButton("Reset");
    public JButton switchButton = new JButton((loadedHub.equals("ctrl")) ? "Switch to Expansion Hub" : "Switch to Control Hub");
    public JOptionPane optionPane = new JOptionPane(
            "Reset all values in " + loadedHub + " hub of profile " + loadedProfile + "?",
            JOptionPane.WARNING_MESSAGE,
            JOptionPane.YES_NO_OPTION,
            null,
            new Object[]{"Reset", "Cancel"},
            "Cancel"
    );
    public final JLabel profileLabel = new JLabel("Select Profile:");
    public final JLabel currentProfile = new JLabel();
    public JLabel profile1;
    public JLabel profile2;
    public JLabel profile3;
    public JLabel profile4;
    public JLabel profile5;
    public final Color bgColor = new Color(50,50,50);
    public final Border border = BorderFactory.createMatteBorder(0,0,1,0,Color.LIGHT_GRAY);
    public final double sizeMultiplier = 1.5;
    public static Map<String, JTextField> boxes = new HashMap<>();
    public static Map<String, JComboBox<String>> dropDowns = new LinkedHashMap<>();
    private final List<JComponent> labelComponents = new ArrayList<>();
    boolean swapBorders = false;
    public JLabel[] profiles = new JLabel[]{null,null,null,null,null,null};
    public static final String[] motors = {"Unspecified Motor", "goBILDA 5201 Series", "goBILDA 5202/3/4 Series", "Matrix 12V Motor", "NeveRest 3.7 v1 Gearmotor", "NeveRest 20 Gearmotor", "NeveRest 40 Gearmotor", "NeveRest 60 Gearmotor", "NeveRest Orbital 20 Gearmotor", "REV Robotics 20:1 HD Hex Motor", "REV Robotics 40:1 HD Hex Motor", "REV Robotics Core Hex Motor", "REV Robotics UltraPlanetary HD HexMotor", "Studica Maverick", "Tetrix Motor"};
    public static final String[] servos = {"Nothing", "Continuous Rotation Servo", "Full Range Servo", "REV Blinkin LED Driver", "REV SPARKmini Controller", "Servo"};
    public static final String[] digitalDevices = {"Nothing", "Digital Device", "LED", "REV Touch Sensor"};
    public static final String[] analogInputDevices = {"Nothing", "Analog Input", "MR Optical Distance Sensor", "MR Touch Sensor"};
    public static final String[] i2cDevices = {"Nothing", "Adafruit Color Sensor", "Adafruit IMU", "AndyMark 9-Axis IMU", "AndyMark Proximity and Color Sensor", "AndyMark TOF Lidar", "HuskyLens", "MR Color Sensor", "MR Compass Sensor", "MR Gyro", "MR IR Seeker v3", "MR Range Sensor", "MaxSonar I2CXL", "OctoQuadFTC", "REV 2M Distance Sensor", "REV 9-Axis IMU", "REV Color Sensor V3", "REV Color/Range Sensor", "REV internal IMU (BHI260AP)", "REV internal IMU (BNO055)", "SparkFun OTOS", "SparkFun QWIIC LED Stick"};

    public MainWindow() {
        super("Hardware Config");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.DARK_GRAY);

        layeredPane.setPreferredSize(new Dimension(1280, 720));
        layeredPane.setOpaque(true);
        layeredPane.setBackground(bgColor);

        topBar.setBounds(0,0,1280,100);
        topBar.setBackground(new Color(25,25,25));
        topBar.setOpaque(true);
        topBar.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.LIGHT_GRAY));
        layeredPane.add(topBar, 1);

        createLabels();

        saveButton.setBounds(1000, 650, 100, 35);
        saveButton.setBackground(new Color(100,100,100));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveButton.setOpaque(true);
        saveButton.setEnabled(false);
        saveButton.setFont(font(25));
        saveButton.setFocusable(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (saveButton.isEnabled()) saveButton.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        });
        saveButton.addActionListener((e) -> {
            saveLikeButton();
        });
        layeredPane.add(saveButton, JLayeredPane.PALETTE_LAYER);

        int[] willReset = {1};
        resetButton.setBounds(1120, 650, 100, 35);
        resetButton.setBackground(new Color(100,100,100));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorder(BorderFactory.createRaisedBevelBorder());
        resetButton.setOpaque(true);
        resetButton.setFont(font(25));
        resetButton.setFocusable(false);
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButton.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resetButton.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        });
        resetButton.addActionListener((e) ->
        {
            JDialog resetDialog = optionPane.createDialog(this, "Reset");
            resetDialog.setLocation((int)(this.getLocation().getX() + 500), (int)(this.getLocation().getY() + 330));
            resetDialog.setVisible(true);
            if (optionPane.getValue() != null) {
                if (optionPane.getValue().equals("Reset")) {
                    willReset[0] = JOptionPane.YES_OPTION;
                } else if (optionPane.getValue().equals("Cancel")) {
                    willReset[0] = JOptionPane.NO_OPTION;
                }
            } else {
                willReset[0] = JOptionPane.CLOSED_OPTION;
            }
            if (willReset[0] == 0)
            {
                boxes.forEach((key, value) -> value.setText(""));
                dropDowns.forEach((k, cb) -> cb.setSelectedIndex(0));
                saveLikeButton();
            }
            JSONSaver.save(loadedHub, loadedProfile, boxes);
        });
        layeredPane.add(resetButton, JLayeredPane.PALETTE_LAYER);

        switchButton.setBounds(60, 650, 220, 35);
        switchButton.setBackground(new Color(100,100,100));
        switchButton.setForeground(Color.WHITE);
        switchButton.setBorder(BorderFactory.createRaisedBevelBorder());
        switchButton.setOpaque(true);
        switchButton.setFont(font(18));
        switchButton.setFocusable(false);
        switchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switchButton.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switchButton.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        });
        switchButton.addActionListener((e) ->
        {
            saveLikeButton();
            ctrlHub.setIcon(loadIcon((loadedHub.equals("ctrl")) ? "/ExpansionHub.png" : "/ControlHub.png", (int) (415 * sizeMultiplier), (int) (300 * sizeMultiplier)));
            loadedHub = (loadedHub.equals("ctrl")) ? "exp" : "ctrl";
            switchButton.setText((loadedHub.equals("ctrl")) ? "Switch to Expansion Hub" : "Switch to Control Hub");
            JSONSaver.save("Current Hub", loadedHub, JSONSaver.HUB_PATH, loadedProfile, false);
            boxes.clear();
            dropDowns.clear();
            createLabels();
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
        profileLabel.setFont(font(20));

        profiles[loadedProfile].setBorder(BorderFactory.createLoweredBevelBorder());

        currentProfile.setBounds((1280/2) - (100/2), 100, 100, 35);
        currentProfile.setText("Profile " + loadedProfile);
        currentProfile.setForeground(Color.WHITE);
        currentProfile.setVerticalAlignment(SwingConstants.CENTER);
        currentProfile.setHorizontalAlignment(SwingConstants.CENTER);
        currentProfile.setFont(font(25));


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
    public static Font font(int size) { return new Font("Roboto Light", Font.PLAIN, size); }
    private JLabel createPreset(int number)
    {
        JLabel label = new JLabel(Integer.toString(number));
        label.setBounds(470 + ((number - 1) * 70), 20, 60, 60);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setForeground(Color.WHITE);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font(30));
        label.setBorder(BorderFactory.createRaisedBevelBorder());
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profiles[number] = label;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                saveLikeButton();
                loadedProfile = number;
                createLabels();
                currentProfile.setText("Profile " + loadedProfile);
                JSONSaver.save("Current Profile", loadedProfile, JSONSaver.HUB_PATH, loadedProfile);
                profile1.setBorder(BorderFactory.createRaisedBevelBorder());
                profile2.setBorder(BorderFactory.createRaisedBevelBorder());
                profile3.setBorder(BorderFactory.createRaisedBevelBorder());
                profile4.setBorder(BorderFactory.createRaisedBevelBorder());
                profile5.setBorder(BorderFactory.createRaisedBevelBorder());
                label.setBorder(BorderFactory.createLoweredBevelBorder());
            }
        });
        return label;
    }
    private void saveLikeButton()
    {
        JSONSaver.save(loadedHub, loadedProfile, boxes);
        JSONSaver.saveDropdowns(loadedHub, loadedProfile, dropDowns);
        saveButton.setBackground(new Color(100,100,100));
        saveButton.setEnabled(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
        field.setFont(font(12));
        field.setText(JSONSaver.load(loadedHub, loadedProfile, name).getAsString());
        field.setBorder(border);
        field.setBackground(bgColor);
        field.setForeground(Color.WHITE);
        field.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        field.setCaretColor(Color.WHITE);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }

            private void onTextChanged() {
                saveButton.setBackground(new Color(0,100,200));
                saveButton.setEnabled(true);
                saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        boxes.put(name, field);
        labelComponents.add(field);
        return field;
    }

    public void drawLine(Point startPos, Point endPos)
    {
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 220, 180));
                g2d.setStroke(new BasicStroke(2.0f));
                g2d.drawLine(startPos.x, startPos.y, endPos.x, endPos.y);
            }
            @Override
            public boolean contains(int x, int y) {
                return false;
            }
        };
        linePanel.setOpaque(false);
        linePanel.setBounds(0, 0, 1280, 720);
        layeredPane.add(linePanel, JLayeredPane.PALETTE_LAYER);
        labelComponents.add(linePanel);
    }
    void createLabels()
    {
        for (JComponent c : labelComponents) {
            layeredPane.remove(c);
        }
        labelComponents.clear();
        boxes.clear();
        dropDowns.clear();
        Label.labelCountR = 0;
        Label.labelCountL = 0;
        Label.labelCountB = 0;
        Label.bYOffset = 0;

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

        JSONSaver.loadDropdowns(loadedHub, loadedProfile);
    }
    public void trackComponent(JComponent c) { labelComponents.add(c); }
}

class Label {
    public static int labelCountR;
    public static int labelCountL;
    public static int labelCountB;
    public String fullLabel;
    public static int bYOffset;
    public int x;
    public int y;
    public Label(MainWindow w, Side side, String fullLabel, int extraYOffset)
    {
        this.fullLabel = fullLabel;
        switch (side)
        {
            case RIGHT: {
                labelCountR++;
                x = w.labelX();
                y = w.labelY() + ((labelCountR + 1) * 21) + extraYOffset;
                w.layeredPane.add(w.textBox(fullLabel, x, w.labelY() + ((labelCountR + 1) * 21) + extraYOffset), JLayeredPane.PALETTE_LAYER);
                JComboBox<String> cb = new Dropdown(fullLabel, x + 130, y).getBox();
                w.layeredPane.add(cb, JLayeredPane.PALETTE_LAYER);
                w.trackComponent(cb);
                w.drawLine(new Point(x - 90, y + 18),
                        new Point(x + 100, y + 18));
                break;
            }
            case LEFT: {
                labelCountL++;
                x = 1280 - w.labelX() - 110;
                y = w.labelY() + ((labelCountL - 1) * 25) + extraYOffset;
                w.layeredPane.add(w.textBox(fullLabel, x, y), JLayeredPane.PALETTE_LAYER);
                JComboBox<String> cb = new Dropdown(fullLabel, x - 170, y).getBox();
                w.layeredPane.add(cb, JLayeredPane.PALETTE_LAYER);
                w.trackComponent(cb);
                w.drawLine(new Point(x, y + 18),
                        new Point(x + 170, y + 18));
                break;
            }
            case BOTTOM:
            {
                labelCountB++;
                bYOffset++;
                x = 1280 - w.labelX() + ((labelCountB - 1) * 43) + 150;
                y = w.labelY() - ((labelCountB - 1) * 20) + extraYOffset + 500;
                if (bYOffset == 4) bYOffset = 1;
                w.layeredPane.add(w.textBox(fullLabel, x, y), JLayeredPane.PALETTE_LAYER);
                JComboBox<String> cb = new Dropdown(fullLabel, x + 130, y).getBox();
                w.layeredPane.add(cb, JLayeredPane.PALETTE_LAYER);
                w.trackComponent(cb);
                w.drawLine(new Point(x - 2, y + 16),
                        new Point(x - 2, w.labelY() + 16 + extraYOffset + 330));
                break;
            }
            default: throw new RuntimeException("Invalid Box Position");
        }
    }
}
class Dropdown {
    JComboBox<String> box;
    public Dropdown(String fullLabel, int x, int y)
    {
        boolean display = true;
        int number = Label.labelCountR + Label.labelCountL + Label.labelCountB;
        String[] dispStr = {""};
        if (number >= 3 && number <= 6) dispStr = MainWindow.i2cDevices;
        else if (number >= 7 && number <= 10) dispStr = MainWindow.digitalDevices;
        else if (number >= 11 && number <= 12) dispStr = MainWindow.analogInputDevices;
        else if (number >= 13 && number <= 20)
            switch (number) {
                case 13:
                case 15:
                case 17:
                case 19:
                    dispStr = MainWindow.motors; break;

                case 14:
                case 16:
                case 18:
                case 20:
                    dispStr = new String[]{""}; display = false; break;
            }
        else if (number >= 21 && number <= 26) dispStr = MainWindow.servos;
        else display = false;


        box = new JComboBox<>(dispStr);
        box.setBounds(x, y, 150, 16);
        box.setBackground(new Color(80, 80, 80));
        box.setOpaque(true);
        box.setForeground(Color.WHITE);
        box.setFont(MainWindow.font(12));
        box.setFocusable(false);
        box.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        box.setUI(new BasicComboBoxUI() {
            @Override
            public void paintCurrentValue(
                    Graphics g, Rectangle bounds, boolean hasFocus)
            {
                super.paintCurrentValue(g, bounds, false);
            }

            @Override
            public void paintCurrentValueBackground(
                    Graphics g, Rectangle bounds, boolean hasFocus)
            {
                super.paintCurrentValueBackground(g, bounds, false);
            }
        });
        MainWindow.dropDowns.put(fullLabel, box);
        if (!display) box.setVisible(false);
    }
    public JComboBox<String> getBox()
    {
        return box;
    }
}

enum Side {
    RIGHT, BOTTOM, LEFT
}