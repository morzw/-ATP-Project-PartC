package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observer;

public abstract class Controller implements Observer {

    protected MyViewModel viewModel = MyViewModel.getInstance();

    //changes the scene
    public void changeScene(String fxmlPath, Stage stage, String title)
    {
        Parent root;
        try {
//            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            root = fxmlLoader.load();
            viewModel.addObserver(fxmlLoader.getController());

            stage.setTitle(title);
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Alert for Information
    public void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    //Alert for Error
    public void showErrorAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
