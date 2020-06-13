package sample;

import Model.IModel;
import Model.MyModel;
import View.IView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MyModel model = MyModel.getInstance(); //??
        MyViewModel viewModel = MyViewModel.getInstance();
        model.addObserver(viewModel);

        Parent root = FXMLLoader.load(getClass().getResource("../View/welcomePage.fxml"));
        primaryStage.setTitle("MorGalMaze");
        primaryStage.setScene(new Scene(root, 768, 614));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
