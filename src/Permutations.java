/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author husse
 */
public class Permutations implements Aggregate {
    private final int size;
    
    public Permutations (int size){
    
    this.size=size;
    }
    
    public int getSize()
    {
      return this.size;
    }
    @Override
    public Iterator iterator(){
    return new PermutationIterator(this);
    }
}
