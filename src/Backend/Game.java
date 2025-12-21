/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author Ayman
 */
public class Game {

    private int[][] board; // 9×9 grid

    public Game(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setCell(int row, int col, int i) {
         this.board[row][col] = i;
    }
    
}
