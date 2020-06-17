package ViewModel;

import Model.IModel;
import Model.MyModel;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private static MyViewModel myViewModel;
    private MyModel model;
    private int[][] mazeArray;
    private int goalPosRow;
    private int goalPosCol;
    private int currPosRow;
    private int currPosCol;
    private boolean wonGame;

    private ArrayList<int[]> solution;

    public ArrayList<int[]> getSolution() {
        return solution;
    }

    public int[][] getMazeArray() {
        return mazeArray;
    }

    public int getCurrPosRow() { return currPosRow; }

    public int getCurrPosCol() { return currPosCol; }

    public int getGoalPosRow() {
        return goalPosRow;
    }

    public int getGoalPosCol() {
        return goalPosCol;
    }

    public boolean isWonGame() { return wonGame; }

    //constructor
    private MyViewModel() {
        model = MyModel.getInstance();
    }

    //get instance
    public static MyViewModel getInstance() {
        if (myViewModel == null) {
            myViewModel = new MyViewModel();
        }
        return myViewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MyModel) {
            if (arg == "generate" || arg == "load") {
                mazeArray = model.getMazeArray();
                currPosRow = model.getCurrPosRow();
                currPosCol = model.getCurrPosCol();
                goalPosRow = model.getGoalPosRow();
                goalPosCol = model.getGoalPosCol();
                setChanged();
                notifyObservers("update");
            }
            else if (arg == "move") {
                currPosRow = model.getCurrPosRow();
                currPosCol = model.getCurrPosCol();
                wonGame = model.isWonGame();
                setChanged();
                notifyObservers("move");
            }
            //solve-instead of save the sol in viewModel and view
            else if (arg == "solve")
            {
                solution = model.getSol();
                setChanged();
                notifyObservers("solve");
            }
            else if (arg == "save")
            {

                setChanged();
                notifyObservers("save");
            }
        }
    }

    //generate maze
    public void generateMaze(int row, int col) {
        model.generateMaze(row, col);
    }

    //saves the maze
    public void saveMaze(String path) {
        if (path != null)
            model.saveMazeToFile(path);
    }

    //load maze
    public void loadMaze(String path) {
        if (path != null)
            model.loadUserMaze(path);
    }

    //move character
    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction = -1;
        switch (keyEvent.getCode()){
            case UP:
                direction = 1;
                break;
            case DOWN:
                direction = 2;
                break;
            case LEFT:
                direction = 3;
                break;
            case RIGHT:
                direction = 4;
                break;
        }
        model.updateCharacterLocation(direction);
    }

    public void solve() {
        model.solveMaze();
    }
}
