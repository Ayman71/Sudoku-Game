/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.DifficultyEnum;
import Backend.Game;
import Backend.SolutionInvalidException;
import Backend.VerificationResult;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Ayman
 */
public class SudokuController implements Viewable {

    private final StorageManager storage;
    private final Verifier verifier = new Verifier();
    private final GameGenerator generator = new GameGenerator();
    //private final Solver solver = new Solver();

    public SudokuController(StorageManager storage) {
        this.storage = storage;
    }

    @Override
    public Catalog getCatalog() {
        boolean hasCurrent = storage.hasCurrentGame();
        boolean hasAll = storage.hasDifficultyGames();

        return new Catalog(hasCurrent, hasAll);
    }

    @Override
    public Game getGame(DifficultyEnum level) throws FileNotFoundException {
        try {
            if (level == DifficultyEnum.INCOMPLETE) {
                return storage.loadCurrent();
            }

            return storage.loadGame(level);
        } catch (Exception e) {
            throw new FileNotFoundException("Game not found for " + level);
        }
    }

    @Override
    public void driveGames(Game source) throws SolutionInvalidException {
        Game easy = generator.generate(source, 10);
        Game medium = generator.generate(source, 20);
        Game hard = generator.generate(source, 25);

        try {
            storage.saveGame(DifficultyEnum.EASY, easy);
            storage.saveGame(DifficultyEnum.MEDIUM, medium);
            storage.saveGame(DifficultyEnum.HARD, hard);
            storage.saveSourceGame(DifficultyEnum.EASY, easy);
            storage.saveSourceGame(DifficultyEnum.MEDIUM, medium);
            storage.saveSourceGame(DifficultyEnum.HARD, hard);
        } catch (Exception e) {
        }
    }

    @Override
    public String verifyGame(Game game) {
        VerificationResult verificationResult = verifier.verify(game.getBoard());
        if (verificationResult.getState() == State.VALID) {
            System.out.println("valid solution");
            return "valid solution";
        }
        StringBuilder result = new StringBuilder();

        for (int position : verificationResult.getDuplicatePositions()) {
            if (result.length() > 0) {
                result.append(",");
            }
            result.append(position);
        }
        System.out.println(result.toString());
        
        return result.toString();

    }

    @Override
    public int[] solveGame(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void logUserAction(String userAction) throws IOException {
        UndoLog log = new UndoLog(storage.root + "/incomplete");
        try (BufferedWriter bw = Files.newBufferedWriter(
                log.logFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(userAction);
            bw.newLine();
        }
    }

}
