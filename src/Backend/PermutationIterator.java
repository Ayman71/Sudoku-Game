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
        currentPermutation = new int[5];  // Array for 5 positions (the number of empty cells)
        // Initialize with the first permutation, which will be all 1s (i.e., [1, 1, 1, 1, 1])
        Arrays.fill(currentPermutation, 1);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < totalPermutations;
    }

    @Override
    public int[] next() {
        int[] permutation = currentPermutation.clone();  // Clone the current permutation to return it

        // Increment the current permutation
        incrementPermutation();

        currentIndex++;
        return permutation;
    }

    private void incrementPermutation() {
        // Start from the rightmost element and carry over if needed
        for (int i = currentPermutation.length - 1; i >= 0; i--) {
            if (currentPermutation[i] < 9) {
                currentPermutation[i]++;  // Increment the current element (base 9, 1 to 9)
                return;  // Done, no need to carry over further
            } else {
                currentPermutation[i] = 1;  // Reset to 1 and carry over to the next element
            }
        }
    }
}

