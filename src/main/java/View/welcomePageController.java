package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class welcomePageController extends Controller {

    public MenuItem newMaze;

    public void handleNewFile(ActionEvent actionEvent) {
        changeScene("../View/newMazePage.fxml"); //newMaze

       // newMaze.getParentMenu();
    }

    public void handleSaveFile(ActionEvent actionEvent) {
    }

    public void handleLoadFile(ActionEvent actionEvent) {
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    public void handleAbout() {
        Stage aboutStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/aboutPage.fxml"));
            aboutStage.setTitle("About...");
            aboutStage.setScene(new Scene(root, 768, 432));
            aboutStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleHelp() {
        showAlert("Please help SpongeBob come back to his home ... \n" +
                "In order to do so, you will have to solve a maze!\n" +
                "If you want to move right - press on 6, If you want to move left - press on 4, for up - press on 8, and for down - press on 2." +
                "\n\n\n DON'T WORRY!!! we'll be with you all the way home. If you need help, just click on.."
        );

    }
}
