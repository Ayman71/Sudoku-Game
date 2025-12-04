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
public class BoxChecker extends Worker {

    public BoxChecker(int index, int[][] board, VerificationResult result) {
        super(index, board, result);
    }

    @Override
    public void run() {
        List<Integer>[] positions = new List[10];

        for (int i = 0; i < 10; i++) {
            positions[i] = new ArrayList<>();
        }

        int rowStart = (index / 3) * 3;
        int colStart = (index % 3) * 3;
        int pos = 1;

        for (int r = rowStart; r < rowStart + 3; r++) {
            for (int c = colStart; c < colStart + 3; c++) {
                int value = board[r][c];
                positions[value].add(pos);
                pos++;
            }
        }

        for (int value = 1; value <= 9; value++) {
            if (positions[value].size() > 1) {

                List<Integer> list = positions[value];
                int[] posArray = new int[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    posArray[i] = list.get(i);
                }
                result.addDuplicate(
                        new DuplicateReport("BOX", index + 1, value, posArray)
                );
            }
        }
    }
}
