/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Ayman
 */
public class Loader {
    public static int[][] load(String path) {
    int[][] board = new int[9][9];

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        for (int r = 0; r < 9; r++) {
            String line = br.readLine();
            if (line == null) break;

            String[] parts = line.split(",");
            for (int c = 0; c < 9; c++) {
                board[r][c] = Integer.parseInt(parts[c].trim());
            }
        }
    } catch (Exception e) {
        System.err.println("Error reading CSV: " + e.getMessage());
    }

    return board;
}

    
}
