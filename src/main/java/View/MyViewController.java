package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
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

    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();

    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);
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
        viewModel.addObserver(this);
        String strRows = textField_mazeRows.getText();
        String strCols = textField_mazeColumns.getText();
        if (isValidNumber(strRows) && isValidNumber(strCols)) {
            int rows = Integer.valueOf(strRows);
            int cols = Integer.valueOf(strCols);
            viewModel.generateMaze(rows, cols);
        }
        else
            showErrorAlert("Values inserted aren't valid! Please enter only numbers that are greater than 1 and smaller than 501 (:");
    }

    public void solveMaze()
    {
        //try to solve null maze
        if (mazeDisplayer.getMaze()==null)
            showErrorAlert("There is no maze to solve :(" +
                    "\nPlease click on the new maze or load maze button first.");
        else
            viewModel.solve();
    }

    //move character
    public void keyPressed(KeyEvent keyEvent) {
//        int player_row_position = mazeDisplayer.getRow_player();
//        int player_col_position = mazeDisplayer.getCol_player();
//        switch (keyEvent.getCode()){
//            case UP:
//                mazeDisplayer.set_player_position(player_row_position-1,player_col_position);
//                set_update_player_position_row(player_row_position-1 + "");
//                set_update_player_position_col(player_col_position + "");
//                break;
//            case DOWN:
//                mazeDisplayer.set_player_position(player_row_position+1,player_col_position);
//                set_update_player_position_row(player_row_position+1 + "");
//                set_update_player_position_col(player_col_position + "");
//                break;
//            case RIGHT:
//                mazeDisplayer.set_player_position(player_row_position,player_col_position+1);
//                set_update_player_position_row(player_row_position +"");
//                set_update_player_position_col(player_col_position+1 +"");
//                break;
//            case LEFT:
//                mazeDisplayer.set_player_position(player_row_position,player_col_position-1);
//                set_update_player_position_row(player_row_position +"");
//                set_update_player_position_col(player_col_position-1 +"");
//                break;
//            default:
//                mazeDisplayer.set_player_position(player_row_position,player_col_position);
//                set_update_player_position_row(player_row_position +"");
//                set_update_player_position_col(player_col_position +"");
//
//        }
        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MyViewModel) {
            if (arg == "update") {
                mazeDisplayer.setMaze(viewModel.getMazeArray());
                mazeDisplayer.set_goal_position(viewModel.getGoalPosRow(), viewModel.getGoalPosCol());
                mazeDisplayer.set_player_position(viewModel.getCurrPosRow(), viewModel.getCurrPosCol());
                mazeDisplayer.drawMaze(mazeDisplayer.getMaze());
            }
            else if (arg == "move") {
                if (viewModel.isWonGame() == true)
                    showAlert("YOU WON!");
                else
                    mazeDisplayer.set_player_position(viewModel.getCurrPosRow(), viewModel.getCurrPosCol());
            }
            //solve
            else if (arg == "solve") {
                mazeDisplayer.drawSol(viewModel.getSolution());
            }
        }
    }
}
