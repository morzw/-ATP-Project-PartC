package Model;


import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
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
    private int startPosRow;
    private int startPosCol;
    private int goalPosRow;
    private int goalPosCol;
    private ArrayList<AState> mazeSolutionSteps;
    private ArrayList<int[][]> solution;

    public int[][] getMazeArray() {
        return mazeArray;
    }

    public int getStartPosRow() {
        return startPosRow;
    }

    public int getStartPosCol() {
        return startPosCol;
    }

    public int getGoalPosRow() {
        return goalPosRow;
    }

    public int getGoalPosCol() {
        return goalPosCol;
    }

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
        startPosRow = maze.getStartPosition().getRowIndex();
        startPosCol = maze.getStartPosition().getColumnIndex();
        goalPosRow = maze.getGoalPosition().getRowIndex();
        goalPosCol = maze.getGoalPosition().getColumnIndex();
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
}
