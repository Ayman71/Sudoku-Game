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


    public final Path root;        // root of project folder
    private final Path gameFolder; // projectfolder/game
    private final Path incompleteFolder; // projectfolder/incomplete

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

    // ----- SAVE -----

    public void saveGame(DifficultyEnum level, Game game) throws IOException {
        Path file = gameFolder.resolve(level.name().toLowerCase()).resolve("game.csv");
        writeBoard(file, game.getBoard());
    }

    public void saveCurrent(Game game) throws IOException {
        Path file = incompleteFolder.resolve("current.csv");
        writeBoard(file, game.getBoard());
    }

    // ----- LOAD -----

    public Game loadGame(DifficultyEnum level) throws IOException {
        Path file = gameFolder.resolve(level.name().toLowerCase()).resolve("game.csv");
        return new Game(readBoard(file));
    }

    public Game loadCurrent() throws IOException {
        Path file = incompleteFolder.resolve("current.csv");
        return new Game(readBoard(file));
    }

    // ----- VALIDATION -----

    public boolean hasCurrentGame() {
        return Files.exists(incompleteFolder.resolve("current.csv"));
    }

    public boolean hasDifficultyGames() {
        return Files.exists(gameFolder.resolve("easy/game.csv")) &&
               Files.exists(gameFolder.resolve("medium/game.csv")) &&
               Files.exists(gameFolder.resolve("hard/game.csv"));
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
                if (c < 8) line += ","; 
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
                for (int c = 0; c < 9; c++)
                    b[r][c] = Integer.parseInt(line[c]);
            }
        }
        return b;
    }
}
