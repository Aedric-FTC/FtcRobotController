package org.graphhardlib.tool; // must match Main's package

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class MainWindow extends JFrame {
    Pane pane;
    JLabel ctrlHub = new JLabel();

    public MainWindow() {
        super("Hardware Config");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);
        pane = new Pane();
        add(pane);
    }
}

class Pane extends JLayeredPane {
    public Pane()
    {
        add(new CTRLHub(), 0);
    }
}
class CTRLHub extends JLabel {
    ImageIcon ctrlIcon = new ImageIcon("/resources/ControlHub.png");
    public CTRLHub() {
        setIcon(ctrlIcon);
    }
}