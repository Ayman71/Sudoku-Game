/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.Arrays;

/**
 *
 * @author Ayman
 */
public class DuplicateReport {

    private String regionType;
    private int regionIndex;
    private int duplicateValue;
    private int[] duplicatePositions;

    public DuplicateReport(String regionType, int regionIndex, int duplicateValue, int[] duplicatePositions) {
        this.regionType = regionType;
        this.regionIndex = regionIndex;
        this.duplicateValue = duplicateValue;
        this.duplicatePositions = duplicatePositions;
    }

    @Override
    public String toString() {
        return String.format(
            "%s %d, #%d, %s",
            regionType,
            regionIndex,
            duplicateValue,
            Arrays.toString(duplicatePositions)
        );
    }
}