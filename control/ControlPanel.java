package control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    //Panel with the controls for the game
    JButton playButton = new JButton("Play");

    public ControlPanel(){
        setPreferredSize(new Dimension(1000, 75));
            setBackground(Color.WHITE);

        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //When the play button is pressed, start the game
            }
        });
        add(this.playButton);
    }
}
