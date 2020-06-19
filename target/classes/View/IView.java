package View;

import javafx.stage.Stage;

public interface IView {

    void changeScene(String fxmlPath, Stage stage, String title);
    void handleLoadAdSave(String loadOrSave, Stage stage, boolean changeScene); //check?
    void showAlert(String message);
    void showErrorAlert(String message);

}
