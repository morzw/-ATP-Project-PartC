package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Observable implements IModel {

    private static MyModel myModel;
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;
    private Maze maze;
    private int[][] mazeArray;
    private int currPosRow;
    private int currPosCol;
    private int goalPosRow;
    private int goalPosCol;
    private boolean wonGame;
    private ArrayList<AState> mazeSolutionSteps;
    private ArrayList<int[][]> solution;

    public int[][] getMazeArray() {
        return mazeArray;
    }

    public int getGoalPosRow() {
        return goalPosRow;
    }

    public int getGoalPosCol() {
        return goalPosCol;
    }

    public int getCurrPosRow() { return currPosRow; }

    public int getCurrPosCol() { return currPosCol; }

    public boolean isWonGame() { return wonGame; }

    //constructor
    private MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
    }

    //get instance
    public static MyModel getInstance() {
        if (myModel == null) {
            myModel = new MyModel();
        }
        return myModel;
    }

    @Override
    public void generateMaze(int row, int col) {
        CommunicateWithServer_MazeGenerating(row, col);
        mazeArray = maze.getMaze();
        currPosRow = maze.getStartPosition().getRowIndex();
        currPosCol = maze.getStartPosition().getColumnIndex();
        goalPosRow = maze.getGoalPosition().getRowIndex();
        goalPosCol = maze.getGoalPosition().getColumnIndex();
        wonGame = false;
        setChanged();
        notifyObservers("generate");
    }

    private void CommunicateWithServer_MazeGenerating(int row, int col) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, (IClientStrategy) (inFromServer, outToServer) -> {
                try {
                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                    toServer.flush();
                    int[] mazeDimensions = new int[]{row, col};
                    toServer.writeObject(mazeDimensions);
                    toServer.flush();
                    byte[] compressedMaze = (byte[])fromServer.readObject();
                    InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                    byte[] decompressedMaze = new byte[(row*col)+24];
                    is.read(decompressedMaze);
                    maze = new Maze(decompressedMaze);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void solveMaze() {
        CommunicateWithServer_SolveSearchProblem();
        setChanged();
        notifyObservers("solve");
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, (IClientStrategy) (inFromServer, outToServer) -> {
                try {
                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                    toServer.flush();
                    toServer.writeObject(maze);
                    toServer.flush();
                    Solution mazeSolution = (Solution)fromServer.readObject();
                    mazeSolutionSteps = mazeSolution.getSolutionPath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopServers() {
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

    //need to add diagonal moves!!!
    @Override
    public void updateCharacterLocation(int direction)
    {
        /*
            direction = 1 -> Up
            direction = 2 -> Down
            direction = 3 -> Left
            direction = 4 -> Right
         */
        switch(direction)
        {
            case 1: //Up
                if (isValidMove(currPosRow-1, currPosCol))
                    currPosRow--;
                break;

            case 2: //Down
                if (isValidMove(currPosRow+1, currPosCol))
                    currPosRow++;
                break;
            case 3: //Left
                if (isValidMove(currPosRow, currPosCol-1))
                    currPosCol--;
                break;
            case 4: //Right
                if (isValidMove(currPosRow, currPosCol+1))
                    currPosCol++;
                break;
        }
        //check if won the game
        if (currPosRow == goalPosRow && currPosCol == goalPosCol)
            wonGame = true;
        //set & notify
        setChanged();
        notifyObservers("move");
    }

    //checks if the player's move is valid
    public boolean isValidMove(int wantedRow, int wantedCol) {
        if (wantedRow < 0 || wantedRow >= mazeArray.length ||
                wantedCol < 0 || wantedCol >= mazeArray[0].length || mazeArray[wantedRow][wantedCol] == 1)
            return false;
        return true;
    }

    @Override
    public void saveMazeToFile(String filePath) {
        try {
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(maze);
            output.flush();
            output.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //check if to save maze to the curr maze , save player location?
    @Override
    public void loadUserMaze(String filePath) {
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream input = new ObjectInputStream(file);
            maze = (Maze)input.readObject();
            file.close();
            setChanged();
            notifyObservers("load");
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
