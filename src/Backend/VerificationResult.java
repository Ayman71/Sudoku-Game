/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author Ayman
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerificationResult {

    private Set<Integer> duplicatePositions = new HashSet<>();
    private State state = State.VALID;

    public void addDuplicatePosition(int position) {
        duplicatePositions.add(position);
    }

    public boolean isValid() {
        return state == State.VALID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Integer> getDuplicatePositions() {
        return duplicatePositions;
    }
}
