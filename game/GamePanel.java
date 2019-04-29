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

    private int gameSpeed;

    private ArrayList<GameCell> gameCells = new ArrayList<>();
    private int[][] gameCellsArr;

    private Thread threadObject = new Thread();

    private GridLayout layout;

    public GamePanel(){
        setPreferredSize(new Dimension(1000, 500));
        
        createGame();
    }
    public void createGame(){
        //Create the game, add game cells for each row and col
        setLayoutSettings();
        this.gameCells = new ArrayList<>();
        removeAll();

        for(int x = 0; x < this.rows; x++){
            for(int y = 0; y < this.cols; y++){
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
        this.gameCellsArr = boolArrListToIntArr(this.gameCells);
    }
    public void startGame(){
        this.isPaused.set(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (!isPaused.get()) {
                        //this runs while the game is running
                        gameCellsArr = boolArrListToIntArr(gameCells);
                        int aListCounter = 0;
                        for(int x = 0; x < rows; x++){
                            for(int y = 0; y < cols; y++){
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
                        threadObject.sleep(gameSpeed);
                    }catch(InterruptedException e){
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
    public void restartGame(){
        for(GameCell cell : this.gameCells){
            cell.setCondition(false);
        }
    }
    public void setGameSizeRow(int row){
        this.rows = row;
    }
    public void setGameSizeCol(int col){
        this.cols = col;
    }
    public void setGameSpeed(int val){
        this.gameSpeed = val;
    }
    private void setLayoutSettings(){
        this.layout = new GridLayout();
        this.layout.setRows(this.rows);
        this.layout.setColumns(this.cols);
        this.layout.setHgap(1);
        this.layout.setVgap(1);
        setLayout(this.layout);
    }
    private int[][] boolArrListToIntArr(ArrayList<GameCell> gameCells){
        int[][] arr = new int[rows][cols];

        int aListCounter = 0;
        for(int x = 0; x < rows; x++){
            for (int y = 0; y < cols; y++){
                //Go through the whole array list and if the cell is alive, write 1, otherwise write 0
                arr[x][y] = gameCells.get(aListCounter).isAlive() ? 1 : 0;
                aListCounter++;
            }
        }
        return arr;
    }
    private int checkNeighbours(int[][] arr, int x, int y){
        int aliveN = 0;

        for (int xSmall = -1; xSmall <= 1; xSmall++) {
            for (int ySmall = -1; ySmall <= 1; ySmall++) {
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