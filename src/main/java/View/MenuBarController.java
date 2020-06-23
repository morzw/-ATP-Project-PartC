package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.util.Observable;
import java.util.Optional;

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

    public void handleProperties() {
        handlePropertiesButton();
    }


    public void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) { //want to exit the game
            viewModel.exit();
            Window welcome = exitLable.getScene().getWindow();
            ((Stage)welcome).close();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }

}
