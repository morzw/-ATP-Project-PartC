package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class AboutPageController extends Controller {
    @FXML
    public Button algoButton;

    public void aboutAlgo() {
        changeScene("AlgoPage.fxml",(Stage)algoButton.getScene().getWindow(),"What happens behind the scenes...");
    }

    public void aboutUs() {
        changeScene("AboutUsPage.fxml",(Stage)algoButton.getScene().getWindow(),"A little bit about us");
    }

    @Override
    public void update(Observable o, Object arg) {}
}
