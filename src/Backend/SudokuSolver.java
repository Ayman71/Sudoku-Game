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

import java.util.*;

public class SudokuSolver {

    private Game game;
    private Verifier verifier;
    private boolean solutionFound = false;  // Flag to stop threads when a solution is found
    private int[] validPermutation = null;

    public SudokuSolver(Game game, Verifier verifier) {
        this.game = game;
        this.verifier = verifier;
    }

    // Solver task that is submitted to each thread
    class SolverTask extends Thread {

        private int[] permutation;
        List<int[]> emptyCells;

        public SolverTask(int[] permutation, List<int[]> emptyCells) {
            this.permutation = permutation;
            this.emptyCells = emptyCells;
        }

        @Override
        public void run() {
            // Check if a solution was already found to stop processing
            if (solutionFound) {
                return;
            }

            // Clone the board from the shared game object to work with a copy
            int[][] clonedBoard = cloneBoard(game.getBoard());  // Create a thread-specific board

            // Fill the cloned board with the permutation values
            for (int i = 0; i < emptyCells.size(); i++) {
                int[] emptyCell = emptyCells.get(i);
                int row = emptyCell[0];
                int col = emptyCell[1];
                clonedBoard[row][col] = permutation[i];  // Modify the cloned board
            }

            // Verify if the cloned board is valid
            Verifier verifier = new Verifier();
            VerificationResult result = verifier.verify(clonedBoard);  // Check the cloned board

            if (result.getState() == Backend.State.VALID) {
                if (!solutionFound) {
                    System.out.println("found!!!");
                    solutionFound = true;
                    validPermutation = permutation.clone();
                    System.out.println("Solution found by thread " + Thread.currentThread().getName());
                    printBoard(new Game(clonedBoard));  // Print the valid solution found by the thread
                }
            }
        }

        private int[][] cloneBoard(int[][] originalBoard) {
            int[][] clonedBoard = new int[9][9];
            for (int i = 0; i < 9; i++) {
                clonedBoard[i] = originalBoard[i].clone();  // Clone each row
            }
            return clonedBoard;
        }

        // Helper method to print the board (solution)
        private void printBoard(Game game) {
            int[][] board = game.getBoard();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    System.out.print(board[row][col] + " ");
                }
                System.out.println();
            }
        }
    }

    public int [] solve() {
        PermutationIterator permutationIterator = new PermutationIterator();
        List<Thread> threads = new ArrayList<>();
        List<int[]> emptyCells = findEmptyCells(game);

        // Iterate over the permutations and assign each permutation to a separate thread
        while (permutationIterator.hasNext()) {
            int[] permutation = permutationIterator.next();
            SolverTask task = new SolverTask(permutation, emptyCells);
            threads.add(task);
            task.start();  // Start the thread
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();  // Ensure the main thread waits for each worker thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!solutionFound) {
            System.out.println("No solution found.");
        } else {
            
            System.out.println("finished and found");
            return validPermutation;
        }
return null;
    }

    // Helper method to find the empty cells
    private List<int[]> findEmptyCells(Game game) {
        List<int[]> emptyCells = new ArrayList<>();
        int[][] board = game.getBoard();

        // Identify the positions of empty cells (cells with value 0)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        System.out.println(Arrays.toString(emptyCells.get(2)));
        return emptyCells;
    }
}
