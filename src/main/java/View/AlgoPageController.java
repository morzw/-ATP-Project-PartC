package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;

public class AlgoPageController extends Controller {

    @FXML
    public Button backToAbout;
    @FXML
    public Hyperlink linkMazeGeneration;
    @FXML
    public Hyperlink linkBFS;

    public void handleBackToAbout() {
        changeScene("AboutPage.fxml",(Stage)backToAbout.getScene().getWindow(),"About");
    }

    @Override
    public void update(Observable o, Object arg) {}
    public void handleMazeGenerationLink(ActionEvent actionEvent) {
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(linkMazeGeneration.getText()));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void handleBFSLink(ActionEvent actionEvent) {
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(linkBFS.getText()));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }
}
