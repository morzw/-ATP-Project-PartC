package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.Optional;

public abstract class Controller implements Observer, IView {

    MyViewModel viewModel = MyViewModel.getInstance();

    //changes the scene
    public void changeScene(String fxmlPath, Stage stage, String title)
    {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            root = fxmlLoader.load();
            viewModel.addObserver(fxmlLoader.getController());
            stage.setTitle(title);
            if (fxmlPath.equals("AboutUsPage.fxml"))
                stage.setScene(new Scene(root,768,432));
            else
                stage.setScene(new Scene(root,900,614));
            stage.show();
        } catch (IOException e) {
            //e.printStackTrace(); //check?
        }
    }

    //Saves and loads user's maze
    public void handleLoadAdSave(String loadOrSave, Stage stage, boolean changeScene)
    {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("maze files","*.maze");
        fc.getExtensionFilters().add(filter);
        if (loadOrSave.equals("load"))
        {
            fc.setTitle("Load Maze");
            File file = fc.showOpenDialog(stage);
            if (file != null)
            {
                if (changeScene)
                    changeScene("MyView.fxml",stage,"Load Maze");
                viewModel.loadMaze(file.getPath());
            }
            else
                showAlert("Load Maze","No path selected, Please try again.");
        }
        else //save
        {
            if (viewModel.getMazeArray() != null)
            {
                fc.setTitle("Save Maze");
                File file = fc.showSaveDialog(stage);
                if (file != null)
                    viewModel.saveMaze(file.getPath());
                else
                    showAlert("Save Maze","No path selected, Please try again.");
            }
            else
                showErrorAlert("There is no maze to save. Please generate an new maze first.");
        }
    }

    public void handleAboutButton() {
        Stage aboutStage = new Stage();
        changeScene("AboutPage.fxml",aboutStage,"About");
    }

    public void handleHelpButton() {
        showAlert("Help!", "Please help SpongeBob come back to his home... \n" +
                "In order to do so, you will have to solve a maze!\n\n" +
                "If you want to move RIGHT - press 6, If you want to move LEFT - press 4, for UP - press 8, and for DOWN - press 2.\n" +
                "You can also move diagonally: for UP RIGHT - press 9, for UP LEFT - press 7, for DOWN RIGHT - press 3 and for DOWN LEFT - press 1.\n\n"+
                "DON'T WORRY!!! we'll be with you all the way home. If you need help, just click on the Show Solution button."
        );
    }


    public void handlePropertiesButton() {
        Stage propStage = new Stage();
        changeScene("../View/Properties.fxml",propStage,"Properties");

    }

    //Alert for Information
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    //Alert for Error
    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }


    //Alert for Confirmation
    public void showConformationAlert(String message, Stage stage)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            stage.close();
    }
}
