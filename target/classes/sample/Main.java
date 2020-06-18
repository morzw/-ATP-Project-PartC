package sample;

import Model.IModel;
import Model.MyModel;
import View.Controller;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //View/MyView.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/WelcomePage.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("***SpongeBob SquarePants Maze 2020***");
        //primaryStage.setScene(new Scene(root, 768, 614));
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();

        IModel model = MyModel.getInstance();
        MyViewModel viewModel = MyViewModel.getInstance();
        model.addObserver(viewModel);
        Controller welcomeController = fxmlLoader.getController();
        viewModel.addObserver(welcomeController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
