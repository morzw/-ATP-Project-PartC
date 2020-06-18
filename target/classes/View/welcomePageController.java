package View;

import Model.IModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Maze");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("maze files","*.maze");
        fc.getExtensionFilters().add(filter);
        File file = fc.showOpenDialog(newGame.getScene().getWindow());
        changeScene("../View/MyView.fxml",(Stage)newGame.getScene().getWindow(),"Load Maze");
        viewModel.loadMaze(file.getPath());
    }

    @Override
    public void update(Observable o, Object arg) {
//        if (o instanceof IModel) {
//        }
    }
}
