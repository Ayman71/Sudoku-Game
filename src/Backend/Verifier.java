/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author Ayman
 */
public class Verifier {

    private int[][] board;
    private VerificationResult result;

    public VerificationResult verify(int[][] board) {
        result = new VerificationResult();
        this.board = board;
        boolean hasZero = false;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    hasZero = true;
                }

                checkDuplicates(row, col, board[row][col]);
            }
        }

        if (!result.getDuplicatePositions().isEmpty()) {
            result.setState(State.INVALID);
        } else if (hasZero) {
            result.setState(State.INCOMPLETE);
        } else {
            result.setState(State.VALID);
        }
        return result;
    }


    public VerificationResult verify(int[][] board, java.util.List<int[]> emptyCells, int[] permutation) {
        result = new VerificationResult();
        this.board = board;


        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = getValue(row, col, emptyCells, permutation);

                if (value == 0) {

                    result.setState(State.INCOMPLETE);
                }

                checkDuplicatesVirtual(row, col, value, emptyCells, permutation);
            }
        }

        if (!result.getDuplicatePositions().isEmpty()) {
            result.setState(State.INVALID);
        } else {
            result.setState(State.VALID);
        }
        return result;
    }

    private int getValue(int r, int c, java.util.List<int[]> emptyCells, int[] permutation) {
        for (int i = 0; i < emptyCells.size(); i++) {
            int[] cell = emptyCells.get(i);
            if (cell[0] == r && cell[1] == c) {
                return permutation[i];
            }
        }
        return board[r][c];
    }

    private void checkDuplicates(int row, int col, int value) {
        if (value == 0)
            return;

        // Check Row
        for (int c = col + 1; c < 9; c++) {
            if (board[row][c] == value) {
                result.addDuplicatePosition(row * 9 + col);
                result.addDuplicatePosition(row * 9 + c);
            }
        }

        // Check Column
        for (int r = row + 1; r < 9; r++) {
            if (board[r][col] == value) {
                result.addDuplicatePosition(row * 9 + col);
                result.addDuplicatePosition(r * 9 + col);
            }
        }

        // Check Box
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if ((r != row || c != col) && board[r][c] == value) {

                }
            }
        }

    }

    private void checkDuplicatesVirtual(int row, int col, int value, java.util.List<int[]> emptyCells,
            int[] permutation) {
        if (value == 0)
            return;


        for (int c = col + 1; c < 9; c++) {
            int otherVal = getValue(row, c, emptyCells, permutation);
            if (otherVal == value) {
                result.addDuplicatePosition(row * 9 + col);
                result.addDuplicatePosition(row * 9 + c);
            }
        }

        for (int r = row + 1; r < 9; r++) {
            int otherVal = getValue(r, col, emptyCells, permutation);
            if (otherVal == value) {
                result.addDuplicatePosition(row * 9 + col);
                result.addDuplicatePosition(r * 9 + col);
            }
        }

        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {

                if (r > row || (r == row && c > col)) {
                    int otherVal = getValue(r, c, emptyCells, permutation);
                    if (otherVal == value) {
                        result.addDuplicatePosition(row * 9 + col);
                        result.addDuplicatePosition(r * 9 + c);
                    }
                }
            }
        }
    }
}
