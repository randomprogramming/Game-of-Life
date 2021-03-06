import javax.swing.*;

import game.*;
import control.*;

import java.awt.*;

public class MainPanel extends JPanel {
    //Main panel which holds all the other panels
    private GamePanel gamePanel;
    private ControlPanel controlPanel;

    public MainPanel(){
        this.gamePanel = new GamePanel();
        this.controlPanel = new ControlPanel(this.gamePanel);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000, 600));
        setBackground(Color.gray);

        add(this.gamePanel, BorderLayout.CENTER);
        add(this.controlPanel, BorderLayout.SOUTH);
    }
}