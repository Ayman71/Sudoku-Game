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
    private int totalPermutations = (int) Math.pow(9, 5);
    private int currentIndex = 0;

    public PermutationIterator() {
        currentPermutation = new int[5];
        Arrays.fill(currentPermutation, 1);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < totalPermutations;
    }

    @Override
    public int[] next() {
        int[] permutation = currentPermutation.clone();

        incrementPermutation();

        currentIndex++;
        return permutation;
    }

    private void incrementPermutation() {
        for (int i = currentPermutation.length - 1; i >= 0; i--) {
            if (currentPermutation[i] < 9) {
                currentPermutation[i]++; 
                return;
            } else {
                currentPermutation[i] = 1; 
            }
        }
    }
}

