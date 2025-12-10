/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Ayman
 */
public class Catalog {

    boolean current;
    boolean allModesExist;

    public Catalog() {
        checkGames();
    }

    public boolean isCurrent() {
        return current;
    }

    public boolean isAllModesExist() {
        return allModesExist;
    }

    void checkGames() {
        File incompleteDir = new File("incomplete");
        File[] files = incompleteDir.listFiles(File::isFile);

        if (files != null && files.length > 0) {
            this.current = true;
        } else {
            this.current = false;
        }

        File gamesDir = new File("games");
        String[] levels = {"easy", "medium", "hard"};
        this.allModesExist = true;

        for (String level : levels) {
            File subfolder = new File(gamesDir, level);

            if (subfolder.exists() && subfolder.isDirectory()) {
                File[] gameFiles = subfolder.listFiles(File::isFile);

                if (gameFiles == null || gameFiles.length == 0) {
                    this.allModesExist = false;
                }
            }
        }
    }
}
