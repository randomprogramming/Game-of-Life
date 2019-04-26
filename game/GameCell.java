package game;

import javax.swing.*;
import java.awt.*;

public class GameCell extends JPanel {
    //One cell of the game. If it is alive, its background color is white, otherwise it's background is black
    boolean alive = false;

    //this counter keeps track of each of the game cells created, and gives each cell their "id" which
    //will be used to easily identify them
    static int counter = 0;
    final int id;

    public GameCell(){
        this.id = counter;
        counter++;
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
    public boolean isAlive(){
        return this.alive;
    }
    public int getId(){
        return this.id;
    }
}
