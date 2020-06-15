package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.util.Observer;

public abstract class Controller implements Observer {

    protected MyViewModel viewModel = MyViewModel.getInstance();

     public void changeScene(String fxmlPath)
    {

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
