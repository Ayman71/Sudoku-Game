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

    public void addDuplicate(DuplicateReport report) {
        duplicates.add(report);
    }

    public boolean isValid() {
        return duplicates.isEmpty();
    }

    public List<DuplicateReport> getDuplicates() {
        return duplicates;
    }
}
