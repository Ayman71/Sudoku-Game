/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author husse
 */


import java.util.ArrayList;
import java.util.List;


    
    
    
    
public class ConcreteBoard implements Flyweight {
    private final int[][] originalBoard;
    private final int[][] emptyCellMap;
    private final int[][] permutationIndices;

    public ConcreteBoard(int[][] board) {
        this.originalBoard = board;
        this.permutationIndices = new int[9][9];


        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                permutationIndices[r][c] = -1;
            }
        }

 
        int counter = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    permutationIndices[r][c] = counter++;
                }
            }
        }

      
        this.emptyCellMap = null;
    }

    @Override
    public int getValue(int row, int col, int[] currentPermutation) {
        int index = permutationIndices[row][col];
        if (index != -1 && currentPermutation != null) {
            return currentPermutation[index];
        }
        return originalBoard[row][col];
    }
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

