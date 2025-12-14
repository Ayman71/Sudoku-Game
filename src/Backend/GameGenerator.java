/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.List;

/**
 *
 * @author Ayman
 */
public class GameGenerator {

    public Game generate(Game solved, int removeCount) {

        int[][] copy = deepCopy(solved.getBoard());
        RandomPairs rp = new RandomPairs();

        int[] cells = new int[removeCount];
        int count = 0;

        while (count < removeCount) {
            List<int[]> pairs = rp.generateDistinctPairs(removeCount*3);
            for (int[] xy : pairs) {
                int row = xy[0] % 9;
                int col = xy[1] % 9;
                int cell = row * 9 + col;
                boolean exists = false;
                for (int i = 0; i < count; i++) {
                    if (cells[i] == cell) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    cells[count] = cell;
                    count++;
                    if (count == removeCount) {
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < removeCount; i++) {
            int cell = cells[i];
            int r = cell / 9;
            int c = cell % 9;
            copy[r][c] = 0;
        }

        return new Game(copy);
    }

    private int[][] deepCopy(int[][] b) {
        int[][] out = new int[9][9];
        for (int r = 0; r < 9; r++)
            out[r] = b[r].clone();
        return out;
    }
}

