


package View;

import Server.Server;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.xml.transform.Source;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Optional;

public class PropertiesController extends Controller{

    @FXML
    private Button cancel;
    @FXML
    private Button applyChanges;
    @FXML
    private MenuItem BFS;
    @FXML
    private MenuItem Best;
    @FXML
    private MenuItem DFS;
    @FXML
    private MenuButton mazeAlgo;
    @FXML
    private MenuButton searchingAlgo;
    @FXML
    private MenuItem SimpleMaze;
    @FXML
    private MenuItem EmptyMaze;
    @FXML
    private MenuItem MyMaze;


    public void mouseClicked(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (source == cancel){
            Stage stage = (Stage)cancel.getScene().getWindow();
            showAlert("Information", "Maze Properties have not been changed.");
            stage.close();
        }

        if (source == BFS || source == DFS || source == Best){
            String solverText = ((MenuItem)source).getText();
            searchingAlgo.setText(solverText);
        }

        if (source == EmptyMaze || source == SimpleMaze ||source == MyMaze )
        {
            String generatorText = ((MenuItem)source).getText();
            mazeAlgo.setText(generatorText);
        }
        if (source == applyChanges){
            Server.setConfigurations("MazeGenerator",mazeAlgo.getText());
            Server.setConfigurations("SearchingAlgorithm", searchingAlgo.getText());

            showConformationAlert("The Properties have been changed, Please start an new game in order load the the chosen settings.", (Stage)applyChanges.getScene().getWindow());
        }

//        if(actionEvent.getSource() == MyMaze || actionEvent.getSource() == SimpleMaze || actionEvent.getSource() == EmptyMaze){
//            mazeAlgo.setText(((MenuItem)actionEvent.getSource()).getText());
//        }
//        if(actionEvent.getSource() == Best || actionEvent.getSource() == BFS || actionEvent.getSource() == DFS ){
//            searchingAlgo.setText(((MenuItem)actionEvent.getSource()).getText());
//        }
//        if(actionEvent.getSource() == cancel){
//            Stage stage = (Stage) cancel.getScene().getWindow();
//            stage.close();
//            System.out.println("Pressed CANCEL");
//        }
//        if(actionEvent.getSource() == apply){
//            Server.setConfigurations("MazeGenerator",mazeAlgo.getText());
//            Server.setConfigurations("SearchingAlgorithm", searchingAlgo.getText());
//            ((Stage)apply.getScene().getWindow()).close();
//            System.out.println("Pressed APPLY");
//
//        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

