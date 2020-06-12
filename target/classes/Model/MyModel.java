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
    private int currPosRow;
    private int currPosCol;
    private ArrayList<AState> mazeSolutionSteps;

    //constructor
    private MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
//        mazeGeneratingServer.stop();
//        solveSearchProblemServer.stop();
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
        currPosRow = maze.getStartPosition().getRowIndex();
        currPosCol = maze.getStartPosition().getColumnIndex();
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

}
