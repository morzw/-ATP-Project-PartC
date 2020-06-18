package View;

import javafx.scene.canvas.Canvas;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private int row_player;
    private int col_player;
    private int row_goal;
    private int col_goal;
    private boolean isSolved;
    private ArrayList<int[]> solution;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameTarget = new SimpleStringProperty();
    StringProperty imageFileNameSol = new SimpleStringProperty();

    public String getImageFileNameSol() {
        return imageFileNameSol.get();
    }

    public void setImageFileNameSol(String imageFileNameSol) {
        this.imageFileNameSol.set(imageFileNameSol);
    }

    public String getImageFileNameTarget() {
        return imageFileNameTarget.get();
    }

    public void setImageFileNameTarget(String imageFileNameTarget) {
        this.imageFileNameTarget.set(imageFileNameTarget);
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public int getRow_player() {
        return row_player;
    }

    public int getCol_player() {
        return col_player;
    }

    public void set_player_position(int row, int col) {
        this.row_player = row;
        this.col_player = col;
        draw();
    }

    public int getRow_goal() { return row_goal; }

    public int getCol_goal() { return col_goal; }

    public void set_goal_position(int row, int col) {
        this.row_goal = row;
        this.col_goal = col;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void drawMaze(int [][] maze) {
        this.maze = maze;
        isSolved = false;
        draw();
    }

    public void draw()
    {
        if(maze != null)
        {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.length;
            int col = maze[0].length;
            double cellHeight = canvasHeight/row;
            double cellWidth = canvasWidth/col;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0,0, canvasWidth, canvasHeight);
            graphicsContext.strokeRect(0,0, canvasWidth, canvasHeight);
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setLineWidth(2);
            graphicsContext.stroke();
//            graphicsContext.setFill(Color.RED);
            double w, h;
            //Draw Maze
            Image wallImage = null;
            try {
                wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no file....");
            }
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(maze[i][j] == 1) //Wall
                    {
                        h = i * cellHeight;
                        w = j * cellWidth;
                        if (wallImage == null) {
                            graphicsContext.fillRect(w, h, cellWidth, cellHeight);
                        }
                        else {
                            graphicsContext.drawImage(wallImage, w, h, cellWidth, cellHeight);
                        }
                    }
                }
            }
            //Draw Sol
            if (isSolved)
            {
                Image solImage = null;
                try {
                    solImage = new Image(new FileInputStream(getImageFileNameSol()));
                } catch (FileNotFoundException e) {
                    System.out.println("There is no file....");
                }
                double hSol,wSol;
                for (int i=1; i<solution.size()-1; i++) {
                    hSol = solution.get(i)[0] * cellHeight;
                    wSol = solution.get(i)[1] * cellWidth;
                    if (solImage == null)
                        graphicsContext.fillRect(wSol, hSol, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(solImage, wSol, hSol, cellWidth, cellHeight);
                }
            }
            //Player
            double h_player = getRow_player() * cellHeight;
            double w_player = getCol_player() * cellWidth;
            Image playerImage = null;
            try {
                playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }
            graphicsContext.drawImage(playerImage, w_player, h_player, cellWidth, cellHeight);
            //Target
            double h_target = getRow_goal() * cellHeight;
            double w_target = getCol_goal() * cellWidth;
            Image targetImage = null;
            try {
                targetImage = new Image(new FileInputStream(getImageFileNameTarget()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }
            graphicsContext.drawImage(targetImage, w_target, h_target, cellWidth, cellHeight);
        }
    }

    public void drawSol(ArrayList<int[]> sol) {
        isSolved = true;
        solution = sol;
        draw();
    }
}
