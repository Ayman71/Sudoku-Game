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

    public SudokuSolver(Game game, Verifier verifier) {
        this.game = game;
        this.verifier = verifier;
    }

    public int[] solve() {
        PermutationIterator permutationIterator = new PermutationIterator();
        List<int[]> emptyCells = findEmptyCells(game);

        while (permutationIterator.hasNext()) {
            int[] permutation = permutationIterator.next();

            VerificationResult result = verifier.verify(game.getBoard(), emptyCells, permutation);

            if (result.getState() == State.VALID) {
                return permutation;
            }
        }

        return null;
    }

    private List<int[]> findEmptyCells(Game game) {
        List<int[]> emptyCells = new ArrayList<>();
        int[][] board = game.getBoard();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        return emptyCells;
    }
}
