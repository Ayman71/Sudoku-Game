/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.*;
import java.nio.file.*;

/**
 *
 * @author Ayman
 */
public class StorageManager {

    public final Path root;
    private final Path gameFolder;
    private final Path incompleteFolder;

    public StorageManager(String rootFolder) {
        this.root = Paths.get(rootFolder);
        this.gameFolder = root.resolve("games");
        this.incompleteFolder = root.resolve("incomplete");
        createFolders();
    }

    private void createFolders() {
        try {
            Files.createDirectories(gameFolder.resolve("easy"));
            Files.createDirectories(gameFolder.resolve("medium"));
            Files.createDirectories(gameFolder.resolve("hard"));

            Files.createDirectories(incompleteFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGame(DifficultyEnum level, Game game) throws IOException {
        Path file = gameFolder.resolve(level.name().toLowerCase()).resolve("game.csv");
        writeBoard(file, game.getBoard());
    }

    public void saveSourceGame(DifficultyEnum level, Game game) throws IOException {
        Path file = gameFolder.resolve(level.name().toLowerCase()).resolve("source.csv");
        writeBoard(file, game.getBoard());
    }

    public void saveCurrent(int[][] board) throws IOException {
        Path file = incompleteFolder.resolve("game.csv");
        writeBoard(file, board);
    }

    public void saveNewCurrentSource(int[][] board) throws IOException {
        Path file = incompleteFolder.resolve("source.csv");
        writeBoard(file, board);
    }

    public Game loadGame(DifficultyEnum level) throws IOException {
        Path file = gameFolder.resolve(level.name().toLowerCase()).resolve("game.csv");
        return new Game(readBoard(file));
    }

    public Game loadCurrent() throws IOException {
        Path file = incompleteFolder.resolve("game.csv");
        return new Game(readBoard(file));
    }

    public Game loadSource(DifficultyEnum difficultyEnum) throws IOException {
        Path file;
        if (difficultyEnum == DifficultyEnum.INCOMPLETE) {
            file = incompleteFolder.resolve("source.csv");
        } else {
            file = gameFolder.resolve(difficultyEnum.name().toLowerCase()).resolve("source.csv");
        }
        return new Game(readBoard(file));
    }

    public Game loadSourceGameFromPath(String path) throws IOException {
        Path file = Paths.get(path);
        return new Game(readBoard(file));
    }

    public boolean hasCurrentGame() {
        return Files.exists(incompleteFolder.resolve("game.csv"));
    }

    public boolean hasDifficultyGames() {
        return Files.exists(gameFolder.resolve("easy/game.csv"))
                && Files.exists(gameFolder.resolve("medium/game.csv"))
                && Files.exists(gameFolder.resolve("hard/game.csv"));
    }

    private void writeBoard(Path file, int[][] board) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(
                file,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            for (int r = 0; r < 9; r++) {
                String line = "";

                for (int c = 0; c < 9; c++) {
                    line += board[r][c];
                    if (c < 8) {
                        line += ",";
                    }
                }

                bw.write(line);
                bw.newLine();
            }
        }
    }

    private int[][] readBoard(Path file) throws IOException {
        int[][] b = new int[9][9];
        try (BufferedReader br = Files.newBufferedReader(file)) {
            for (int r = 0; r < 9; r++) {
                String[] line = br.readLine().trim().split(",");
                for (int c = 0; c < 9; c++) {
                    b[r][c] = Integer.parseInt(line[c]);
                }
            }
        }
        return b;
    }
}
