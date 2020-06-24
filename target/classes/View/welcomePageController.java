package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class WelcomePageController extends Controller implements Initializable {

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.pauseMusic();
        try{
            viewModel.playMusic((new Media(getClass().getResource("/Music/SpongeBobCaptain.mp3").toURI().toString())),200);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void properties() {
        handlePropertiesButton();
    }
}
