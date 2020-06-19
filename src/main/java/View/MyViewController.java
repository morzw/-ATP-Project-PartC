package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MyViewController extends Controller implements IView, Initializable  {

    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_column;
    @FXML
    public Button ShowSolution;
    @FXML
    public Button HideSolution;
    @FXML
    public Button soundOn;
    @FXML
    public Button soundOff;

    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();

    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    private void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    private void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);
        viewModel.pauseMusic();
        try{
            viewModel.playMusic((new Media(getClass().getResource("/Music/SpongeBobNice.mp3").toURI().toString())),200);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //validation check for generate maze
    private boolean isValidNumber(String str)
    {
        String regex = "\\d+";
        if (str.matches(regex)) {
            int val = Integer.valueOf(str);
            if (val >= 2 && val <= 500)
                return true;
        }
        return false;
    }

    //generate maze
    public void generateMaze()
    {
        String strRows = textField_mazeRows.getText();
        String strCols = textField_mazeColumns.getText();
        if (isValidNumber(strRows) && isValidNumber(strCols)) {
            int rows = Integer.valueOf(strRows);
            int cols = Integer.valueOf(strCols);
            viewModel.generateMaze(rows, cols);
            ShowSolution.setDisable(false);
        }
        else
            showErrorAlert("Values inserted aren't valid!" +
                    "\nPlease enter only numbers between 2 to 500 (:");
    }

    public void solveMaze()
    {
        viewModel.solve();
        HideSolution.setDisable(false);
    }

    public void hideSolution() {
        mazeDisplayer.drawMaze(mazeDisplayer.getMaze());
        HideSolution.setDisable(true);
    }

    //move character
    public void keyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MyViewModel) {
            if (arg == "update") { //generate & load
                mazeDisplayer.setMaze(viewModel.getMazeArray());
                mazeDisplayer.set_goal_position(viewModel.getGoalPosRow(), viewModel.getGoalPosCol());
                mazeDisplayer.set_player_position(viewModel.getCurrPosRow(), viewModel.getCurrPosCol());
                mazeDisplayer.drawMaze(mazeDisplayer.getMaze());
                set_update_player_position_row(viewModel.getCurrPosRow() + "");
                set_update_player_position_col(viewModel.getCurrPosCol() + "");
                //added
                ShowSolution.setDisable(false);
            }
            else if (arg == "load incorrect file type")
            {
                showErrorAlert("You tried to upload an unsuitable file type. Please reload a file with .maze extension only.");
            }
            else if (arg == "move") {
                if (viewModel.isWonGame() == true)
                {
                    showAlert("YOU WON!");
                    viewModel.pauseMusic();
                    try{
                        viewModel.playMusic((new Media(getClass().getResource("/Music/SpongeBobThemeSong.mp3").toURI().toString())),200);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    mazeDisplayer.set_player_position(viewModel.getCurrPosRow(), viewModel.getCurrPosCol());
                    set_update_player_position_row(viewModel.getCurrPosRow() + "");
                    set_update_player_position_col(viewModel.getCurrPosCol() + "");
                }
            }
            else if (arg == "solve") {
                mazeDisplayer.drawSol(viewModel.getSolution());
            }
            else if (arg == "save") {
                showAlert("Your maze was successfully saved.");
            }
//            else if (arg == "no path selected")
//            {
//
//            }
        }
    }

    public void soundOn() throws URISyntaxException {
        viewModel.playMusic((new Media(getClass().getResource("/Music/SpongeBobNice.mp3").toURI().toString())),200);
        soundOn.setDisable(true);
        soundOff.setDisable(false);
    }

    public void soundOff(){
        viewModel.pauseMusic();
        soundOn.setDisable(false);
        soundOff.setDisable(true);
    }
}
