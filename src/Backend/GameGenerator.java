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

        List<int[]> coords = rp.generateDistinctPairs(removeCount);

        for (int[] xy : coords) {
            int index = xy[0] % 81;
            int r = index / 9;
            int c = index % 9;
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
