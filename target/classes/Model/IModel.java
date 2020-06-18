package Model;

import ViewModel.MyViewModel;

public interface IModel {

    void generateMaze(int row, int col);
    void solveMaze();
    void updateCharacterLocation(int direction);
    void stopServers();
    void saveMazeToFile(String filePath);
    void loadUserMaze (String filePath);
    void addObserver(MyViewModel viewModel);

}
