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
import java.util.List;

public class VerificationResult {

    private List<DuplicateReport> duplicates = new ArrayList<>();
    private State state = State.VALID;

    public void addDuplicate(DuplicateReport report) {
        duplicates.add(report);
        state = State.INVALID;
    }

    public void markIncomplete() {
        if (state == State.VALID) {
            state = State.INCOMPLETE;
        }
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

    public List<DuplicateReport> getDuplicates() {
        return duplicates;
    }
}
