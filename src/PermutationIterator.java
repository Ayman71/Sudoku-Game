/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author husse
 */
public class PermutationIterator implements Iterator {
    
    Permutations permutation;
    private final int size;
    private final int[] current;
    private boolean hasNext = true;
    public PermutationIterator (Permutations permutation){
    
        this.permutation=permutation;
    this.size = permutation.getSize();
        this.current = new int[size];
      
        for (int i = 0; i < size; i++) {
            current[i] = 1;
        }
    }
    
    
     @Override
     public boolean hasNext()
     {
       return this.hasNext;
     }
     @Override
   public Object next()
   {
   if (!hasNext) {
           throw new RuntimeException("No more elements");
        }

        
        int[] result = current.clone();

      
        int i = size - 1;
        while (i >= 0) {
            if (current[i] < 9) {
                current[i]++;
                return result;
            } else {
                current[i] = 1;
                i--;
            }
        }

       
        hasNext = false;
        return result;
    }
   
   }

