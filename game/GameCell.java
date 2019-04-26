package game;

import javax.swing.*;
import java.awt.*;

public class GameCell extends JPanel {
    //One cell of the game. If it is alive, its background color is white, otherwise it's background is black
    boolean alive = false;

    public GameCell(){
        setBackground(Color.BLACK);
    }/*
    @Override
    protected void paintComponent(Graphics g){
        if(alive){
            setBackground(Color.WHITE);
        }
        else{
            setBackground(Color.BLACK);
        }
    }*/
    public void updateCell(){
        this.alive = !this.alive;
        trypaint();
    }
    private void trypaint(){
        if(alive){
            setBackground(Color.WHITE);
        }
        else{
            setBackground(Color.BLACK);
        }
    }
}
