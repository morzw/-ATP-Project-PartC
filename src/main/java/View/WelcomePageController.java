package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;

public class WelcomePageController extends Controller {

    @FXML
    public Button newGame;

    public void handleNewGame() {
        changeScene("../View/MyView.fxml",(Stage)newGame.getScene().getWindow(),"New Maze");
    }

    public void handleLoadMaze() {
        handleLoadAdSave("load",(Stage)newGame.getScene().getWindow(), true);
    }

    public void about() {
        handleAboutButton();
    }

    public void help() {
        handleHelpButton();
    }

    @Override
    public void update(Observable o, Object arg) {}


}
