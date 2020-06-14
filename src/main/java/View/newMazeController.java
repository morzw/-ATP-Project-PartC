package View;

import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class newMazeController extends Controller implements IView, Initializable  {

//    private MazeGenerator mazeGenerator;
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

    public void generateMaze()
    {
//        if(mazeGenerator == null)
//            mazeGenerator = new MazeGenerator();
//        int rows = Integer.valueOf(textField_mazeRows.getText());
//        int cols = Integer.valueOf(textField_mazeColumns.getText());
//        int [][] maze = this.mazeGenerator.generateRandomMaze(rows,cols);
//        mazeDisplayer.drawMaze(maze);
    }

    public void solveMaze()
    {
        showAlert("Solving Maze ... ");
    }

    //in abstract
//    public void showAlert(String message)
//    {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText(message);;
//        alert.show();
//    }

    public void keyPressed(KeyEvent keyEvent) {
        int player_row_position = mazeDisplayer.getRow_player();
        int player_col_position = mazeDisplayer.getCol_player();
        switch (keyEvent.getCode()){
            case UP:
                mazeDisplayer.set_player_position(player_row_position-1,player_col_position);
                set_update_player_position_row(player_row_position-1 + "");
                set_update_player_position_col(player_col_position + "");
                break;
            case DOWN:
                mazeDisplayer.set_player_position(player_row_position+1,player_col_position);
                set_update_player_position_row(player_row_position+1 + "");
                set_update_player_position_col(player_col_position + "");
                break;
            case RIGHT:
                mazeDisplayer.set_player_position(player_row_position,player_col_position+1);
                set_update_player_position_row(player_row_position +"");
                set_update_player_position_col(player_col_position+1 +"");
                break;
            case LEFT:
                mazeDisplayer.set_player_position(player_row_position,player_col_position-1);
                set_update_player_position_row(player_row_position +"");
                set_update_player_position_col(player_col_position-1 +"");
                break;
            default:
                mazeDisplayer.set_player_position(player_row_position,player_col_position);
                set_update_player_position_row(player_row_position +"");
                set_update_player_position_col(player_col_position +"");

        }
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
