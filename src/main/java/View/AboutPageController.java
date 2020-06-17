package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutPageController {
    public void aboutAlgo() {
        Stage algoStage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AlgoPage.fxml"));
            algoStage.setTitle("What happens behind the scenes...");
            algoStage.setScene(new Scene(root, 800, 650));
            algoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aboutUs() {
        Stage aboutStage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AboutUsPage.fxml"));
            aboutStage.setTitle("Don't Worry...");
            aboutStage.setScene(new Scene(root, 800, 650));
            aboutStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
