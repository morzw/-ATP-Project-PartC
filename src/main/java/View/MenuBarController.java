package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
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
        handleLoadAdSave("save",(Stage)menuBar.getScene().getWindow(),false);
    }

    public void handleLoadFile(ActionEvent actionEvent) {
        handleLoadAdSave("load",(Stage)menuBar.getScene().getWindow(),false);
    }

    public void handleAbout() {
        handleAboutButton();
    }

    public void handleHelp() {
        handleHelpButton();
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
    public void update(Observable o, Object arg) {}
}
