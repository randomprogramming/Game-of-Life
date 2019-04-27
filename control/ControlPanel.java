package control;

import game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlPanel extends JPanel {
    //Panel with the controls for the game
    private JButton playButton = new JButton("Play");
    private JButton pauseButton = new JButton("Pause");

    private GamePanel gamePanel;

    private Thread threadObject;

    private AtomicBoolean isPaused = new AtomicBoolean();
    private AtomicBoolean gameStarted = new AtomicBoolean();

    public ControlPanel(GamePanel gamePanel, Thread threadObject){
        this.gamePanel = gamePanel;
        this.threadObject = threadObject;

        setPreferredSize(new Dimension(1000, 75));
        setBackground(Color.WHITE);

        this.gameStarted.set(false);

        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If the game is already started, just continue and dont create a new game
                //Should be updated to false when the reset button is pressed
                if(gameStarted.get()){
                    gamePanel.unpauseGame();
                }
                else{
                    gamePanel.startGame();
                    gameStarted.set(true);
                }
            }
        });
        this.pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.pauseGame();
            }
        });

        add(this.playButton);
        add(this.pauseButton);
    }
}