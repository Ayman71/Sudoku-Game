/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 *
 * @author Ayman
 */
public class UndoLog {
    final Path logFile;

    public UndoLog(String folder) {
        this.logFile = Paths.get(folder, "log.txt");
    }

    public void append(UserAction action) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(logFile,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(action.toString());
            bw.newLine();
        }
    }

    public UserAction undoLast() throws IOException {
        List<String> lines = Files.readAllLines(logFile);
        if (lines.isEmpty()) return null;

        String last = lines.remove(lines.size() - 1);
        Files.write(logFile, lines);

        String[] nums = last.replace("(", "").replace(")", "").split(",");
        return new UserAction(
                Integer.parseInt(nums[0]),
                Integer.parseInt(nums[1]),
                Integer.parseInt(nums[2]),
                Integer.parseInt(nums[3])
        );
    }
    public void clearLog() throws IOException{
        Files.write(logFile, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
    }
}
