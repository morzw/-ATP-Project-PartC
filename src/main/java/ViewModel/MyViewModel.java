package ViewModel;

import Model.IModel;
import Model.MyModel;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private static MyViewModel myViewModel;
    private IModel model;

    //constructor
    private MyViewModel(IModel model) {
        this.model = model;
    }

    //get instance
    public static MyViewModel getInstance(IModel model) {
        if (myViewModel == null) {
            myViewModel = new MyViewModel(model);
        }
        return myViewModel;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
