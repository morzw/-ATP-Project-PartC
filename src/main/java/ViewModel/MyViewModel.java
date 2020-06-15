package ViewModel;

import Model.IModel;
import Model.MyModel;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private static MyViewModel myViewModel;
    private MyModel model;
    private int[][] mazeArray;
    private int startPosRow;
    private int startPosCol;
    private int goalPosRow;
    private int goalPosCol;

    public int[][] getMazeArray() {
        return mazeArray;
    }

    public int getStartPosRow() {
        return startPosRow;
    }

    public int getStartPosCol() {
        return startPosCol;
    }

    public int getGoalPosRow() {
        return goalPosRow;
    }

    public int getGoalPosCol() {
        return goalPosCol;
    }

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
        if (o == model) {
            if (arg == "generate") {
                mazeArray = model.getMazeArray();
                startPosRow = model.getStartPosRow();
                startPosCol = model.getStartPosCol();
                goalPosRow = model.getGoalPosRow();
                goalPosCol = model.getGoalPosCol();
                setChanged();
                notifyObservers("generate");
            }
        }
    }

    //generate maze
    public void generateMaze(int row, int col) {
        model.generateMaze(row, col);
    }
}
