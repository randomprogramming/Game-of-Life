package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GamePanel extends JPanel {
    //Panel where the game happens
    private AtomicBoolean isPaused = new AtomicBoolean();
    //rows = x axis
    private int rows = 25;
    //cols = y axis
    private int cols = 50;

    private ArrayList<GameCell> gameCells = new ArrayList<>();
    private int[][] gameCellsArr;

    private Thread threadObject;

    public GamePanel(Thread threadObject){
        this.threadObject = threadObject;

        setPreferredSize(new Dimension(1000, 500));

        GridLayout layout = new GridLayout();
        layout.setRows(this.rows);
        layout.setColumns(this.cols);
        layout.setHgap(1);
        layout.setVgap(1);
        setLayout(layout);

        createGame();
    }
    private void createGame(){
        //Create the game, add game cells for each row and col
        for(var x = 0; x < this.rows; x++){
            for(var y = 0; y < this.cols; y++){
                GameCell cell = new GameCell();
                add(cell);
                //give each game cell a mouse listener to detect when it is pressed
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        cell.updateCell();
                    }
                });
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        super.mouseDragged(e);
                    }
                });
                //Also add each gamecell to the arraylist to store them
                this.gameCells.add(cell);
            }
        }
    }
    public void startGame(){
        this.isPaused.set(false);
        this.gameCellsArr = boolArrListToIntArr(this.gameCells);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (!isPaused.get()) {
                        //this runs while the game is running
                        gameCellsArr = boolArrListToIntArr(gameCells);
                        var aListCounter = 0;
                        for(var x = 0; x < rows; x++){
                            for(var y = 0; y < cols; y++){
                                int aliveN = checkNeighbours(gameCellsArr, x, y);

                                //game logic
                                if(gameCellsArr[x][y] == 1 && aliveN < 2){
                                    gameCells.get(aListCounter).setCondition(false);
                                } else if(gameCellsArr[x][y] == 1 && (aliveN == 2 || aliveN == 3)){
                                    gameCells.get(aListCounter).setCondition(true);
                                } else if(gameCellsArr[x][y] == 1 && aliveN > 3){
                                    gameCells.get(aListCounter).setCondition(false);
                                } else if(gameCellsArr[x][y] == 0 && aliveN == 3){
                                    gameCells.get(aListCounter).setCondition(true);
                                }
                                aListCounter++;
                            }
                        }
                    }
                    try{
                        //Sleep the thread after each iteration to add a delay between the draws
                        threadObject.sleep(250);
                    }catch(InterruptedException e){
                    }
                }
            }
        };
        threadObject = new Thread(runnable);
        threadObject.start();
    }
    public void restartGame(){
        for(GameCell cell : this.gameCells){
            cell.setCondition(false);
        }
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
    private int checkNeighbours(int[][] arr, int x, int y){
        var aliveN = 0;

        for (var xSmall = -1; xSmall <= 1; xSmall++) {
            for (var ySmall = -1; ySmall <= 1; ySmall++) {
                //we take a x and y and check its neighbours
                //this is in a try catch to prevent index out of bounds error, which will happen
                //with the cells on the border
                try {
                    int cellN = arr[x + xSmall][y + ySmall];
                    aliveN += cellN;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
        //we have to subtract the cell itself because we're only counting neighbours
        aliveN -= arr[x][y];
        return aliveN;
    }
}