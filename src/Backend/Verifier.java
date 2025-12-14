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

    public Verifier(int[][] board) {
        this.board = board;
        this.result = new VerificationResult();
    }
    
    public VerificationResult verify() {

        for (int r = 0; r < 9; r++)
            new RowChecker(r, board, result).run();

        for (int c = 0; c < 9; c++)
            new ColumnChecker(c, board, result).run();

        for (int b = 0; b < 9; b++)
            new BoxChecker(b, board, result).run();

        return result;
    }
}