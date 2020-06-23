package sample;

import Model.IModel;
import Model.MyModel;
import View.Controller;
import View.MenuBarController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //View/MyView.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/WelcomePage.fxml"));
        Parent root = fxmlLoader.load();

        IModel model = MyModel.getInstance();
        MyViewModel viewModel = MyViewModel.getInstance();
        model.addObserver(viewModel);
        Controller welcomeController = fxmlLoader.getController();
        viewModel.addObserver(welcomeController);

        Rectangle2D openingScene = Screen.getPrimary().getBounds();
        primaryStage.setTitle("***SpongeBob SquarePants Maze 2020***");
        primaryStage.setScene(new Scene(root,900,614)); //check
        primaryStage.setResizable(true);
        primaryStage.show();

        setStageCloseEvent(primaryStage, (MyModel) model);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setStageCloseEvent(Stage primaryStage, MyModel model) {
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { //want to exit the game
                model.stopServers();
                primaryStage.close();
            } else //doesn't want to exit the game
                event.consume();
        });
    }

}
