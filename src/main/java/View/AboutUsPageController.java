package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Observable;

public class AboutUsPageController extends Controller {
    @FXML
    public Button backToAbout;

    public void handleBackToAbout() {
        changeScene("AboutPage.fxml",(Stage)backToAbout.getScene().getWindow(),"About");
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
