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

    // Flyweight: Original board + Overlay data
    protected Flyweight flyweight;
    protected int[] currentPermutation; // Extrinsic state passed to methods

    public Worker(int index, int[][] board, VerificationResult result) {
        this.index = index;
        // In standard Flyweight, the Client passes the Flyweight.
        // But here we might construct it or have it passed.
        // For now, let's keep the signature but get the Flyweight from factory if
        // possible,
        // or better yet, have the Verifier pass the Flyweight.
        // But the Constructor signature is fixed by legacy.
        // We will allow setting it via setter or lazy load.

        this.board = board;
        this.result = result;
    }

    // Flyweight setter
    public void setFlyweight(Flyweight flyweight, int[] currentPermutation) {
        this.flyweight = flyweight;
        this.currentPermutation = currentPermutation;
    }

    protected int getValue(int r, int c) {
        if (flyweight != null) {
            return flyweight.getValue(r, c, currentPermutation);
        }
        return board[r][c];
    }
}