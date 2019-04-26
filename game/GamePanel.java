package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    //Panel where the game happens
    private GridLayout layout = new GridLayout();

    //rows = x axis
    private final int rows = 25;
    //cols = y axis
    private final int cols = 50;

    ArrayList<GameCell> gameCells = new ArrayList<>();
    public GamePanel(){
        setPreferredSize(new Dimension(1000, 500));

        this.layout.setRows(this.rows);
        this.layout.setColumns(this.cols);
        this.layout.setHgap(1);
        this.layout.setVgap(1);
        setLayout(this.layout);

        createGame();
        //when play is pressed, get the alive and dead cells and store them somewhere
        //getAliveAndDeadCells();
    }
    private void createGame(){
        //Create the game, add game cells for each row and col
        for(var x = 0; x < this.rows; x++){
            for(var y = 0; y < this.cols; y++){
                GameCell temp = new GameCell();
                add(temp);
                //give each game cell a mouse listener to detect when it is pressed
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        temp.updateCell();
                    }
                });
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        super.mouseDragged(e);
                        temp.updateCell();
                    }
                });
                //Also add each gamecell to the arraylist to store them
                this.gameCells.add(temp);
            }
        }
    }
}
