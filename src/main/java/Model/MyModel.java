package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private ArrayList<int[]> sol;
    private static final Logger LOG = LogManager.getLogger(); //Log4j2

    public int[][] getMazeArray() { return mazeArray; }

    public int getGoalPosRow() { return goalPosRow; }

    public int getGoalPosCol() { return goalPosCol; }

    public int getCurrPosRow() { return currPosRow; }

    public int getCurrPosCol() { return currPosCol; }

    public boolean isWonGame() { return wonGame; }

    public ArrayList<int[]> getSol() { return sol; }

    //constructor
    private MyModel() {
        Server.setConfigurations("MazeGenerator","MyMazeGenerator");
        Server.setConfigurations("SearchingAlgorithm","Breadth First Search");
        Server.setConfigurations("ThreadPoolSize","3");
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        mazeGeneratingServer.start();
        LOG.info("Generate maze server started");
        solveSearchProblemServer.start();
        LOG.info("Solve maze server started");
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
        initMaze(maze);
        LOG.info("A new maze has been created. Maze dimensions - "+row+ " X " +col);
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
                    LOG.error("Exception: ", e);
                    e.printStackTrace();
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            LOG.error("Unknown Host Exception: ", e);
            e.printStackTrace();
        }
    }

    @Override
    public void solveMaze() {
        sol = new ArrayList<>();
        CommunicateWithServer_SolveSearchProblem();
        //updates sol
        for (AState state:mazeSolutionSteps) {
            int[] currPosState = new int[2];
            currPosState[0] = state.getRowState();
            currPosState[1] = state.getColState();
            sol.add(currPosState);
        }
        LOG.info("Solution for a maze was created");
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
                    maze.setStartPosition(currPosRow, currPosCol);
                    toServer.writeObject(maze);
                    toServer.flush();
                    Solution mazeSolution = (Solution)fromServer.readObject();
                    mazeSolutionSteps = mazeSolution.getSolutionPath();
                } catch (Exception e) {
                    LOG.error("Exception: ", e);
                    e.printStackTrace();
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            LOG.error("Unknown Host Exception: ", e);
            e.printStackTrace();
        }
    }

    //need to add diagonal moves!!!
    @Override
    public void updateCharacterLocation(int direction) {
        /*
            direction = 1 -> Up
            direction = 2 -> Down
            direction = 3 -> Left
            direction = 4 -> Right
            direction = 5 -> Up Left
            direction = 6 -> Up Right
            direction = 7 -> Down Left
            direction = 8 -> Down Right
         */
        switch(direction) {
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
            case 5: //Up Left
                if (isValidMove(currPosRow-1, currPosCol-1))
                {
                    currPosRow--;
                    currPosCol--;
                }
                break;
            case 6: //Up Right
                if (isValidMove(currPosRow-1, currPosCol+1))
                {
                    currPosRow--;
                    currPosCol++;
                }
                break;
            case 7: //Down Left
                if (isValidMove(currPosRow+1, currPosCol-1))
                {
                    currPosRow++;
                    currPosCol--;
                }
                break;
            case 8: //Down Right
                if (isValidMove(currPosRow+1, currPosCol+1))
                {
                    currPosRow++;
                    currPosCol++;
                }
                break;
        }
        //checks if won the game
        if (currPosRow == goalPosRow && currPosCol == goalPosCol)
        {
            wonGame = true;
            LOG.info("The user won the game");
        }
        //set & notify
        setChanged();
        notifyObservers("move");
    }

    //checks if the player's move is valid
    private boolean isValidMove(int wantedRow, int wantedCol) {
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
            LOG.info("The maze has been successfully saved to the disk");
            setChanged();
            notifyObservers("save");
        } catch (IOException e) {
            LOG.error("IO Exception: ", e);
            //e.printStackTrace();
        }
    }

    //check if to save maze to the curr maze , save player location?
    @Override
    public void loadUserMaze(String filePath) {
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream input = new ObjectInputStream(file);
            maze = (Maze)input.readObject();
            initMaze(maze);
            LOG.info("A maze has been successfully uploaded from the disk");
            setChanged();
            notifyObservers("load");
            file.close();
        } catch (IOException|ClassNotFoundException e) {
            LOG.error("IO/Class Not Found Exception : ", e);
            setChanged();
            notifyObservers("load incorrect file type");
            //e.printStackTrace();
        }
    }

    private void initMaze(Maze newMaze) {
        mazeArray = maze.getMaze();
        currPosRow = maze.getStartPosition().getRowIndex();
        currPosCol = maze.getStartPosition().getColumnIndex();
        goalPosRow = maze.getGoalPosition().getRowIndex();
        goalPosCol = maze.getGoalPosition().getColumnIndex();
        wonGame = false;
    }

    @Override
    public void addObserver(MyViewModel viewModel) {
        super.addObserver(viewModel);
    }

    @Override
    public void stopServers() {
        mazeGeneratingServer.stop();
        LOG.info("Generate maze server stopped");
        solveSearchProblemServer.stop();
        LOG.info("Solve maze server stopped");
    }
}
