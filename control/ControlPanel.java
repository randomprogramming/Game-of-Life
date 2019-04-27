package control;

import game.GamePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlPanel extends JPanel {
    //Panel with the controls for the game
    private JButton playButton = new JButton("Play");
    private JButton pauseButton = new JButton("Pause");
    private JButton restartButton = new JButton("Restart");
    private JButton setGameSizeButton = new JButton("Set size");
    private JTextField rowsField = new JTextField();
    private JTextField colsField = new JTextField();
    private JSlider gameSpeedSlider = new JSlider(JSlider.HORIZONTAL, 0, 2000, 100);
    private JLabel gameStatus = new JLabel();
    private JLabel gameSpeedSliderText = new JLabel();
    private JLabel fastText = new JLabel();
    private JLabel slowText = new JLabel();

    private GamePanel gamePanel;

    private AtomicBoolean isPaused = new AtomicBoolean();
    private AtomicBoolean gameStarted = new AtomicBoolean();

    public ControlPanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        setPreferredSize(new Dimension(1000, 75));
        setBackground(Color.WHITE);

        SpringLayout layout = new SpringLayout();
        add(this.playButton);
        add(this.pauseButton);
        add(this.restartButton);

        add(this.gameStatus);
        setTextPaused();
        this.gameStatus.setFont(this.gameStatus.getFont().deriveFont(Font.BOLD, 14));

        add(this.gameSpeedSlider);
        this.gameSpeedSlider.setBackground(Color.WHITE);
        this.gamePanel.setGameSpeed(gameSpeedSlider.getValue());
        this.gameSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gamePanel.setGameSpeed(gameSpeedSlider.getValue());
            }
        });
        this.gameSpeedSliderText.setText("Control game speed");
        this.gameSpeedSliderText.setFont(this.gameSpeedSliderText.getFont().deriveFont(Font.BOLD, 14));
        add(this.gameSpeedSliderText);
        this.fastText.setText("Fast");
        add(this.fastText);
        this.slowText.setText("Slow");
        add(this.slowText);

        //add(this.setGameSizeButton);
        //add(this.rowsField);
        //add(this.colsField);

        setLayoutSettings(layout);
        setLayout(layout);

        //If the game is already started, just continue and dont create a new game
        this.gameStarted.set(false);
        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameStarted.get()){
                    gamePanel.unpauseGame();
                }
                else{
                    gamePanel.startGame();
                    gameStarted.set(true);
                }
                setTextPlaying();
            }
        });
        this.pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.pauseGame();
                setTextPaused();
            }
        });
        this.restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.restartGame();
            }
        });
    }
    private void setTextPlaying(){
        this.gameStatus.setForeground(new Color(40, 150, 0));
        this.gameStatus.setText("Game isn't paused");
    }
    private void setTextPaused(){
        this.gameStatus.setForeground(Color.RED);
        this.gameStatus.setText("Game is paused");
    }
    private void setLayoutSettings(SpringLayout layout){
        //layout settings to position all the components
        //layout.putConstraint(this side, of this component, is moved by this amount, from this side, of this component)
        layout.putConstraint(SpringLayout.NORTH, this.playButton, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, this.pauseButton, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, this.restartButton, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, this.gameStatus, 9, SpringLayout.SOUTH, this.playButton);
        layout.putConstraint(SpringLayout.NORTH, this.gameSpeedSliderText, 7, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, this.gameSpeedSlider, 20, SpringLayout.NORTH, this.gameSpeedSliderText);
        layout.putConstraint(SpringLayout.NORTH, this.fastText, 7, SpringLayout.SOUTH, this.gameSpeedSlider);
        layout.putConstraint(SpringLayout.NORTH, this.slowText, 7, SpringLayout.SOUTH, this.gameSpeedSlider);

        layout.putConstraint(SpringLayout.WEST, this.playButton, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, this.pauseButton, 15, SpringLayout.EAST, this.playButton);
        layout.putConstraint(SpringLayout.WEST, this.restartButton, 15, SpringLayout.EAST, this.pauseButton);
        layout.putConstraint(SpringLayout.WEST, this.gameStatus, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, this.gameSpeedSliderText, 7, SpringLayout.WEST, this.gameSpeedSlider);
        layout.putConstraint(SpringLayout.EAST, this.gameSpeedSlider, -15, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, this.fastText, 0, SpringLayout.WEST, this.gameSpeedSliderText);
        layout.putConstraint(SpringLayout.EAST, this.slowText, -7, SpringLayout.EAST, this.gameSpeedSlider);
    }
}