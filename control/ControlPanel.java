package control;

import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    //Panel with the controls for the game
    private JButton playButton = new JButton("Play");

    private GamePanel gamePanel;

    public ControlPanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        setPreferredSize(new Dimension(1000, 75));
            setBackground(Color.WHITE);

        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.startGame();
            }
        });
        add(this.playButton);
    }
}
