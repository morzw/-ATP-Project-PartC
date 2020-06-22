


package View;

import Server.Server;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.util.Observable;

public class PropertiesController extends Controller{
    @FXML
    private MenuButton mazeAlgorithm;
    @FXML
    private MenuButton solvingAlgorithm;
    @FXML
    private MenuItem MyMazeGenerator;
    @FXML
    private MenuItem SimpleMazeGenerator;
    @FXML
    private MenuItem EmptyMazeGenerator;
    @FXML
    private MenuItem Best;
    @FXML
    private MenuItem BFS;
    @FXML
    private MenuItem DFS;
    @FXML
    private Button cancel;
    @FXML
    private Button apply;


    public void mouseClicked(ActionEvent actionEvent) {
        if(actionEvent.getSource() == MyMazeGenerator || actionEvent.getSource() == SimpleMazeGenerator || actionEvent.getSource() == EmptyMazeGenerator){
            mazeAlgorithm.setText(((MenuItem)actionEvent.getSource()).getText());
        }
        if(actionEvent.getSource() == Best || actionEvent.getSource() == BFS || actionEvent.getSource() == DFS ){
            solvingAlgorithm.setText(((MenuItem)actionEvent.getSource()).getText());
        }
        if(actionEvent.getSource() == cancel){
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
            System.out.println("Pressed CANCEL");
        }
        if(actionEvent.getSource() == apply){
            Server.setConfigurations("MazeGenerator",mazeAlgorithm.getText());
            Server.setConfigurations("SearchingAlgorithm", solvingAlgorithm.getText());
            ((Stage)apply.getScene().getWindow()).close();
            System.out.println("Pressed APPLY");
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}



//
//package View;
//
//
//import Server.Server;
//import Server.Configurations;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.Observable;
//import java.util.Properties;
//import java.util.ResourceBundle;
//
//import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;
//
//public class PropertiesController extends Controller{
//
//    @FXML
//    private MenuButton mazeAlgorithm;
//    @FXML
//    private MenuButton solvingAlgorithm;
//    @FXML
//    private MenuItem MyMazeGenerator;
//    @FXML
//    private MenuItem SimpleMazeGenerator;
//    @FXML
//    private MenuItem EmptyMazeGenerator;
//    @FXML
//    private MenuItem Best;
//    @FXML
//    private MenuItem BFS;
//    @FXML
//    private MenuItem DFS;
//    @FXML
//    private Button cancel;
//    @FXML
//    private Button apply;
//
//
//    public void handleApply(ActionEvent actionEvent) {
//        if(actionEvent.getSource() == MyMazeGenerator || actionEvent.getSource() == SimpleMazeGenerator || actionEvent.getSource() == EmptyMazeGenerator){
//            mazeAlgorithm.setText(((MenuItem)actionEvent.getSource()).getText());
//        }
//        if(actionEvent.getSource() == Best || actionEvent.getSource() == BFS || actionEvent.getSource() == DFS ){
//            solvingAlgorithm.setText(((MenuItem)actionEvent.getSource()).getText());
//        }
//        if(actionEvent.getSource() == cancel){
//            Stage stage = (Stage) cancel.getScene().getWindow();
//            stage.close();
//            System.out.println("Pressed CANCEL");
//        }
//        if(actionEvent.getSource() == apply){
//
//            Server.setConfigurations("GeneratingAlgorithm",mazeAlgorithm.getText());
//            Server.setConfigurations("SearchingAlgorithm", solvingAlgorithm.getText());
//            showAlert("Information","The Properties has changed");
//            Stage stage = ((Stage)apply.getScene().getWindow());
//            stage.close();
//        }
//
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//
//    }


//
//    @FXML
//    public Button okB;
//    @FXML
//    public ComboBox Generator;
//    @FXML
//    public ComboBox Search;
//
//    public Properties p;
////            Scene scene = new Scene(layout, 768, 614);
////            scene.getStylesheets().add(getClass().getResource("/View/LoadScene.css").toExternalForm());
//
//
////    public void OK(){
////        try {
////            FileInputStream in = new FileInputStream("resources/config.properties");
////            Properties p = new Properties();
////            p.load(in);
////            in.close();
////
////            FileOutputStream out = new FileOutputStream("resources/config.properties");
////            p.setProperty("SearchingAlgorithm", Search.getSelectionModel().getSelectedItem().toString());
////            p.setProperty("MazeGenerator", Generator.getSelectionModel().getSelectedItem().toString());
////            p.store(out, null);
////            out.close();
////        }
////        catch(IOException e){
////            e.printStackTrace();
////            return;
////        }
//////        showAlert("The Properties has changed");
////        Stage s = (Stage)okB.getScene().getWindow();
////        s.close();
////    }
//
//
//    public void OK(){
//
//        if(actionEvent.getSource() == MyMazeGenerator || actionEvent.getSource() == SimpleMazeGenerator || actionEvent.getSource() == EmptyMazeGenerator){
//            mazeAlgorithm.setText(((MenuItem)actionEvent.getSource()).getText());
//        }
//        if(actionEvent.getSource() == Best || actionEvent.getSource() == BFS || actionEvent.getSource() == DFS ){
//            solvingAlgorithm.setText(((MenuItem)actionEvent.getSource()).getText());
//        }
//        if(actionEvent.getSource() == cancel){
//            Stage stage = (Stage) cancel.getScene().getWindow();
//            stage.close();
//            System.out.println("Pressed CANCEL");
//        }
//        if(actionEvent.getSource() == apply){
//            Server.setConfigurations("GeneratingAlgorithm",mazeAlgorithm.getText());
//            Server.setConfigurations("SearchingAlgorithm", solvingAlgorithm.getText());
//            ((Stage)apply.getScene().getWindow()).close();
//            System.out.println("Pressed APPLY");
//        }
//
//
//        showAlert("Information","The Properties has changed");
//        Stage s = (Stage)okB.getScene().getWindow();
//        s.close();
//
//    }
//
//    //from part b-new jar
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        Generator.getItems().addAll(
//                "MyMazeGenerator",
//                "SimpleMazeGenerator"
//        );
//        Search.getItems().addAll(
//                "BestFirstSearch",
//                "BreadthFirstSearch",
//                "DepthFirstSearch"
//        );
//        try {
//            FileInputStream in = new FileInputStream("resources/config.properties");
//            p = new Properties();
//            p.load(in);
//            in.close();
////            Search.setValue(p.getProperty("SearchingAlgorithm"));
////            Generator.setValue((p.getProperty("MazeGenerator")));
//
//            Search.setValue(getProperty("SearchingAlgorithm"));
//            Generator.setValue((getProperty("MazeGenerator")));
//        }
//        catch(IOException e)
//        {
//        }
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//
//    }
