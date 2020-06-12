package ViewModel;

import Model.IModel;
import Model.MyModel;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private static MyViewModel myViewModel;
    private IModel model;

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

    }

    //generate maze
    public void generateMaze(int row, int col) {
        model.generateMaze(row, col);
    }
}
