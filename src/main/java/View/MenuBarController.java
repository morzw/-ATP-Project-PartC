package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class MenuBarController extends Controller {

    @FXML
    public MenuBar menuBar;
    @FXML
    public Label exitLable;


    public void handleNewFile(ActionEvent actionEvent) {
        changeScene("../View/MyView.fxml",(Stage)menuBar.getScene().getWindow(),"New Maze");
    }

    public void handleSaveFile(ActionEvent actionEvent) {
//        viewModel.addObserver(this);
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Maze");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("maze files","*.maze");
        fc.getExtensionFilters().add(filter);
        File file = fc.showSaveDialog(menuBar.getScene().getWindow());
        viewModel.saveMaze(file.getPath());
    }

    public void handleLoadFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Maze");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("maze files","*.maze");
        fc.getExtensionFilters().add(filter);
        File file = fc.showOpenDialog(menuBar.getScene().getWindow());
        viewModel.loadMaze(file.getPath());
    }

    public void handleAbout() {
        Stage aboutStage = new Stage();
        changeScene("../View/AboutPage.fxml",aboutStage,"About");
    }

    public void handleHelp() {
        showAlert("Please help SpongeBob come back to his home ... \n" +
                "In order to do so, you will have to solve a maze!\n" +
                "If you want to move right - press on 6, If you want to move left - press on 4, for up - press on 8, and for down - press on 2." +
                "\n\n\n DON'T WORRY!!! we'll be with you all the way home. If you need help, just click on.."
        );
    }

    public void handleExit() {
        viewModel.exit();
        Window welcome = exitLable.getScene().getWindow();
        ((Stage)welcome).close();
    }

    public void handleProperties() {
        changeScene("../View/Properties.fxml",(Stage)menuBar.getScene().getWindow(),"Properties");
    }

    @Override
    public void update(Observable o, Object arg) {
//        if (o instanceof IModel)
//        {
//            if (arg == "save")
//            {
////                showAlert("Your maze was successfully saved");
//            }
//            else if (arg == "load")
//            {
//                //in MyViewController
//                //change scene to myView
//            }
//        }
    }
}
