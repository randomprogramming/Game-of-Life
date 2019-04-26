package game;

import javax.swing.*;
import java.awt.*;

public class GameCell extends JPanel {
    //One cell of the game. If it is alive, its background color is white, otherwise it's background is black
    private boolean alive;

    public GameCell(){
        //This is the condition in which the cell is when the game starts
        this.alive = false;
        paint();
    }
    public void updateCell(){
        this.alive = !this.alive;
        paint();
    }
    private void paint(){
        if(alive){
            setBackground(Color.WHITE);
        }
        else{
            setBackground(Color.BLACK);
        }
    }
    public boolean isAlive(){
        return this.alive;
    }
    public void setCondition(boolean cond){
        this.alive = cond;
        paint();
    }
}