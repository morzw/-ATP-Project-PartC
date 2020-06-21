package View;

import ViewModel.MyViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static javafx.geometry.Pos.CENTER;

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
    public BorderPane borderPane;
    @FXML
    public Pane pane;
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

    private StringProperty update_player_position_row = new SimpleStringProperty();
    private StringProperty update_player_position_col = new SimpleStringProperty();

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
        adjustDisplaySize();
        viewModel.pauseMusic();
        try{
            viewModel.playMusic((new Media(getClass().getResource("/Music/SpongeBobNice.mp3").toURI().toString())),200);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void adjustDisplaySize() {
        //adjusts the size of the pane to borderPane
        borderPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            pane.setMinHeight(borderPane.getWidth());
            if (viewModel.getMazeArray() != null)
                mazeDisplayer.draw();
        });
        borderPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            pane.setMinHeight(borderPane.getHeight());
            if (viewModel.getMazeArray() != null)
                mazeDisplayer.draw();
        });
        //adjusts the size of the maze displayer to pane
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            mazeDisplayer.setWidth(pane.getWidth());
            if (viewModel.getMazeArray() != null)
                mazeDisplayer.draw();
        });
        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            mazeDisplayer.setHeight(pane.getHeight()-40);
            if (viewModel.getMazeArray() != null)
                mazeDisplayer.draw();
        });
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
            if (arg == "update") {
                mazeDisplayer.setMaze(viewModel.getMazeArray());
                mazeDisplayer.set_goal_position(viewModel.getGoalPosRow(), viewModel.getGoalPosCol());
                mazeDisplayer.set_player_position(viewModel.getCurrPosRow(), viewModel.getCurrPosCol());
                mazeDisplayer.drawMaze(mazeDisplayer.getMaze());
                set_update_player_position_row(viewModel.getCurrPosRow() + "");
                set_update_player_position_col(viewModel.getCurrPosCol() + "");
                this.zoom(mazeDisplayer);
            }
            else if (arg == "move") {
                if (viewModel.isWonGame())
                {
                    viewModel.pauseMusic();
                    Stage stage = new Stage();
                    stage.setTitle("C O N G R A T U L A T I O N S ! ! !");
                    VBox layout = new VBox();
                    HBox H = new HBox(5);
                    H.setAlignment(CENTER);
                    layout.setAlignment(CENTER);
                    Button close = new Button();
                    close.setText("CLOSE");
                    H.getChildren().add(close);
                    layout.spacingProperty().setValue(10);
                    Image im = new Image("/Images/giphy.gif");
                    ImageView image = new ImageView(im);
                    layout.getChildren().add(image);
                    layout.getChildren().add(H);
                    Scene scene = new Scene(layout, 494, 365);
                    scene.getStylesheets().add(getClass().getResource("/View/MainStyle.css").toExternalForm());
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                    stage.show();
                    //close button
                    close.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            stage.close();
                        }
                    });
                    try{
                        viewModel.playMusic((new Media(getClass().getResource("/Music/SpongeBobFlute.mp3").toURI().toString())),200);
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
                showAlert("Save Maze", "Your maze was successfully saved");
            }
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

    //zoom in/out
    public void zoom(MazeDisplayer pane) {
        pane.setOnScroll(
            new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                    double zoomFactor = 1.05;
                    double deltaY = event.getDeltaY();

                    if (deltaY < 0) {
                        zoomFactor = 0.95;
                    }
                    pane.setScaleX(pane.getScaleX() * zoomFactor);
                    pane.setScaleY(pane.getScaleY() * zoomFactor);
                    event.consume();
                }
            });
    }
}
