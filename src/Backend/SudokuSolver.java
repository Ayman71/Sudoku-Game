/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Ayman
 */
import java.util.*;

public class SudokuSolver {

    private Game game;
    private Verifier verifier;

    private final int[] status = new int[]{0};
    private final int[][] solutionHolder = new int[1][];

    public SudokuSolver(Game game, Verifier verifier) {
        this.game = game;
        this.verifier = verifier;
    }

    public int[] solve() {
        // 1. Identify empty cells
        List<int[]> emptyCells = new ArrayList<>();
        int[][] board = game.getBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }

        PermutationIterator iterator = new PermutationIterator();
        List<Thread> threads = new ArrayList<>();

        status[0] = 0;
        solutionHolder[0] = null;

        while (iterator.hasNext()) {
            if (status[0] == 1) {
                break;
            }
            int[] permutation = iterator.next();
            System.out.println(Arrays.toString(permutation));

            Thread worker = new Thread(() -> {

                if (status[0] == 1) {
                    return;
                }

                VerificationResult result = verifier.verify(game.getBoard(), emptyCells, permutation);

                if (result.getState() == State.VALID) {
                    publish(permutation);
                }
            });

            threads.add(worker);
            worker.start();
        }
        boolean running = true;
        while (running && status[0] == 0) {
            running = false;
            for (Thread t : threads) {
                if (t.isAlive()) {
                    running = true;
                    break;
                }
            }
        }
        return solutionHolder[0];
    }

    private void publish(int[] permutation) {
        status[0] = 1;
        solutionHolder[0] = permutation;
    }
}
