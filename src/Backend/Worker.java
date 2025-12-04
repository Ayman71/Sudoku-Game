/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author Ayman
 */
public abstract class Worker implements Runnable {
    protected int index;
    protected int[][] board;
    protected VerificationResult result;

    public Worker(int index, int[][] board, VerificationResult result) {
        this.index = index;
        this.board = board;
        this.result = result;
    }
}