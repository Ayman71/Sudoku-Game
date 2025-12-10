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
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    hasZero = true;
                }
            }
        }
        for (int r = 0; r < 9; r++) {
            new RowChecker(r, board, result).run();
        }

        for (int c = 0; c < 9; c++) {
            new ColumnChecker(c, board, result).run();
        }

        for (int b = 0; b < 9; b++) {
            new BoxChecker(b, board, result).run();
        }

        if (hasZero && result.getState() == State.VALID) {
            result.markIncomplete();
        }
        if (!result.getDuplicates().isEmpty()) {
            result.setState(State.INVALID);
        } else if (hasZero) {
            result.setState(State.INCOMPLETE);
        } else {
            result.setState(State.VALID);
        }
        return result;
    }
}
