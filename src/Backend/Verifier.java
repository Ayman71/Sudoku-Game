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

    public Verifier() {
        this.result = new VerificationResult();
    }

    public VerificationResult verify(int[][] board) {
        this.board = board;
        boolean hasZero = false;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    hasZero = true;
                }
                
                for (int c = col + 1; c < 9; c++) {
                    if (board[row][col] == board[row][c] && board[row][col] != 0) {
                        result.addDuplicatePosition(row * 9 + col);
                    }
                }
                
                for (int r = row + 1; r < 9; r++) {
                    if (board[row][col] == board[r][col] && board[row][col] != 0) {
                        result.addDuplicatePosition(row * 9 + col);
                    }
                }
                
                int boxRowStart = (row / 3) * 3;
                int boxColStart = (col / 3) * 3;
                for (int r = boxRowStart; r < boxRowStart + 3; r++) {
                    for (int c = boxColStart; c < boxColStart + 3; c++) {
                        if (r != row && c != col && board[row][col] == board[r][c] && board[row][col] != 0) {
                            result.addDuplicatePosition(row * 9 + col );
                        }
                    }
                }
            }
        }

        if (hasZero && result.getState() == State.VALID) {
            result.setState(State.INCOMPLETE);
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
}
