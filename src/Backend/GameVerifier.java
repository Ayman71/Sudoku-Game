/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author Ayman
 */
public class GameVerifier {
    public String verify(int [][] board, int [][] solution){
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if(board[row][col] != solution[row][col]){
                    if (result.length()>0){
                        result.append(",");
                    }
                    result.append(row * 9 + col);
                }
            }
        }
        if(result.length()==0){
            result.append("VALID");
        }
        return result.toString();
    }
}
