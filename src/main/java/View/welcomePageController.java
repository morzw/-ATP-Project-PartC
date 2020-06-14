package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Observable;

public class welcomePageController extends Controller {

    public MenuItem newMaze;

    public Label exitLable;

    public void handleNewFile(ActionEvent actionEvent) {
        //changeScene("../View/newMazePage.fxml"); //newMaze


//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("../View/newMazePage.fxml"));
//            Stage welcome = (Stage)(newMaze.getParentPopup().getScene().getWindow());
//            welcome.setTitle("newMaze");
//            welcome.setScene(new Scene(root, 768, 614));
//            welcome.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        Parent root;
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


    public void handleExit() {
        Window welcome = exitLable.getScene().getWindow();
        ((Stage)welcome).close();

    }
}
