/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ayman
 */
public class RowChecker extends Worker {

    public RowChecker(int index, int[][] board, VerificationResult result) {
        super(index, board, result);
    }

    @Override
    public void run() {
        int[] row = board[index];
        List<Integer>[] positions = new List[10];

        for (int i = 0; i < 10; i++) {
            positions[i] = new ArrayList<>();
        }

        for (int col = 0; col < 9; col++) {
            int value = row[col];
            positions[value].add(col + 1);
        }

        for (int value = 1; value <= 9; value++) {
            if (positions[value].size() > 1) {

                List<Integer> list = positions[value];
                int[] posArray = new int[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    posArray[i] = list.get(i); 
                }
                result.addDuplicate(
                        new DuplicateReport("ROW", index + 1, value, posArray)
                );
            }
        }
    }
}
