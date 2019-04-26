package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GamePanel extends JPanel {
    //Panel where the game happens
    private GridLayout layout = new GridLayout();

    private AtomicBoolean isPaused = new AtomicBoolean();
    //rows = x axis
    private final int rows = 25;
    //cols = y axis
    private final int cols = 50;

    private ArrayList<GameCell> gameCells = new ArrayList<>();
    private int[][] gameCellsArr;

    private Thread threadObject;

    public GamePanel(Thread threadObject){
        this.threadObject = threadObject;

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
    public void startGame(){
        this.isPaused.set(false);
        this.gameCellsArr = boolArrListToIntArr(this.gameCells);
        printArray(this.gameCellsArr);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (!isPaused.get()) {
                        //this runs while the game is running

                    } else {
                        //this runs while the game is paused
                        
                    }
                }
            }
        };
        threadObject = new Thread(runnable);
        threadObject.start();
    }
    public void pauseGame(){
        this.isPaused.set(true);
    }
    public void unpauseGame(){
        this.isPaused.set(false);
    }
    private int[][] boolArrListToIntArr(ArrayList<GameCell> gameCells){
        int[][] arr = new int[rows][cols];

        var aListCounter = 0;
        for(var x = 0; x < rows; x++){
            for (var y = 0; y < cols; y++){
                //Go through the whole array list and if the cell is alive, write 1, otherwise write 0
                arr[x][y] = gameCells.get(aListCounter).isAlive() ? 1 : 0;
                aListCounter++;
            }
        }
        return arr;
    }
    private void printArray(int[][] arr){
        //used for debugging, remove later
        for(var x = 0; x < rows; x++){
            for (var y = 0; y < cols; y++){
                System.out.print(arr[x][y]);
            }
            System.out.println();
        }
    }
}
