/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Ayman
 */
public class PermutationIterator implements Iterator<int[]> {

    private int[] currentPermutation;
    private int totalPermutations = (int) Math.pow(9, 5); // 9^5 possibilities
    private int currentIndex = 0;

    public PermutationIterator() {
        currentPermutation = new int[5];
    }

    @Override
    public boolean hasNext() {
        return currentIndex < totalPermutations;
    }

    @Override
    public int[] next() {
        int[] permutation = currentPermutation.clone();
        //System.out.println("Generated permutation: " + Arrays.toString(permutation));
        for (int i = 0; i < currentPermutation.length; i++) {
            if (currentPermutation[i] < 9) {
                currentPermutation[i]++;
                break;
            } else {
                currentPermutation[i] = 0;
            }
        }

        currentIndex++;
        return permutation;
    }
}

