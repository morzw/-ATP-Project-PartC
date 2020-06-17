package sample;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MyModel model = MyModel.getInstance();
        MyViewModel viewModel = MyViewModel.getInstance();
        model.addObserver(viewModel);
       //View/MyView.fxml
        Parent root = FXMLLoader.load(getClass().getResource("../View/WelcomePage.fxml"));
        primaryStage.setTitle("***SpongeBob SquarePants Maze 2020***");
        //primaryStage.setScene(new Scene(root, 768, 614));
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
