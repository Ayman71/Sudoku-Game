/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author husse
 */


import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    
    
     private static final Map<String, Flyweight> ConcreteBoardMap = new HashMap<>();

    public static Flyweight getFlyweight(String key, int[][] board) {
        if (ConcreteBoardMap.containsKey(key)) {
            return ConcreteBoardMap.get(key);
        }

        Flyweight flyweight = new ConcreteBoard(board);
        ConcreteBoardMap.put(key, flyweight);
        return flyweight;
    }
}
    
    
    
    
    
}
