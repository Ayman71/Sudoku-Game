/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author Ayman
 */
public class ControllerFacade implements Controllable {

    private final Viewable controller;
    private final StorageManager storageManager;
    private final GameGenerator gameGenerator;

    public ControllerFacade() {
        storageManager = new StorageManager("");
        gameGenerator = new GameGenerator();
        controller = new SudokuController(storageManager);
    }

    //storage
    public void saveGame(DifficultyEnum level, Game game) throws IOException {
        storageManager.saveGame(level, game);
    }

    public void saveSourceGame(DifficultyEnum level, Game game) throws IOException {
        storageManager.saveSourceGame(level, game);
    }

    public void saveCurrent(int[][] board) throws IOException {
        storageManager.saveCurrent(board);
    }

    public void saveNewCurrentSource(int[][] board) throws IOException {
        storageManager.saveNewCurrentSource(board);
    }

    public Game loadGame(DifficultyEnum level) throws IOException {
        return storageManager.loadGame(level);
    }

    public Game loadCurrent() throws IOException {
        return storageManager.loadCurrent();
    }

    public Game loadSource(DifficultyEnum difficultyEnum) throws IOException {
        return storageManager.loadSource(difficultyEnum);
    }

    public Game loadSourceGameFromPath(String path) throws IOException {
        return storageManager.loadSourceGameFromPath(path);
    }

    public boolean hasCurrentGame() {
        return storageManager.hasCurrentGame();
    }

    public boolean hasDifficultyGames() {
        return storageManager.hasDifficultyGames();
    }

    public void deleteCurrent() throws IOException{
        storageManager.deleteCurrent();
    }
    
    //generator
    public Game generate(Game solved, int removeCount) {
        return gameGenerator.generate(solved, removeCount);
    }
    //controller
    @Override
    public boolean[] getCatalog() {
        Catalog catalog = controller.getCatalog();
        return new boolean[]{catalog.current, catalog.allModesExist};
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        DifficultyEnum difficultyEnum = switch (Character.toUpperCase(level)) {
            case 'E' ->
                DifficultyEnum.EASY;
            case 'M' ->
                DifficultyEnum.MEDIUM;
            case 'H' ->
                DifficultyEnum.HARD;
            case 'I'->
                DifficultyEnum.INCOMPLETE;
            default ->
                throw new NotFoundException("Invalid difficulty");
        };
        return controller.getGame(difficultyEnum).getBoard();
    }

    @Override
    public void driveGames(String sourcePath) throws SolutionInvalidException {
        try {
            Game source = storageManager.loadSourceGameFromPath(sourcePath);
            controller.driveGames(source);
        } catch (Exception e) {
            throw new SolutionInvalidException(e.getMessage());
        }
    }

    @Override
    public boolean[][] verifyGame(int[][] board) {
        Game g = new Game(board);
        String result = controller.verifyGame(g);

        boolean[][] status = new boolean[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                status[r][c] = true;
            }
        }

        if (result.equals("VALID") || result.equals("INCOMPLETE")) {
            return status;
        } else {
            String[] tokens = result.split(",");
            for (String t : tokens) {
                if (!t.isEmpty()) {
                    int idx = Integer.parseInt(t);
                    int r = idx / 9;
                    int c = idx % 9;
                    status[r][c] = false;
                }
            }
        }
        return status;
    }

    @Override
    public int[][] solveGame(int[][] board) throws InvalidGame {
        Game g = new Game(board);
        int[] solution = controller.solveGame(g);
        if (solution == null)
            throw new InvalidGame("No solution found");

        java.util.List<int[]> empty = new java.util.ArrayList<>();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] == 0)
                    empty.add(new int[] { r, c });

        int[][] result = new int[empty.size()][3];
        for (int i = 0; i < solution.length; i++) {
            result[i][0] = empty.get(i)[0];
            result[i][1] = empty.get(i)[1];
            result[i][2] = solution[i];
        }
        return result;
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        controller.logUserAction(userAction.toString());
    }

    private int[][] cloneBoard(int[][] originalBoard) {
        int[][] clonedBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            clonedBoard[i] = originalBoard[i].clone();  // Clone each row
        }
        return clonedBoard;
    }

}
