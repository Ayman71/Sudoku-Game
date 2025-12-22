/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.IOException;

/**
 *
 * @author Ayman
 */
public class PlaceNumberCommand implements Command{

    private final int row;
    private final int col;
    private final int newValue;
    private final int oldValue;
    private final int[][] board; 
    private final UndoLog undoLog;

    public PlaceNumberCommand(int row, int col, int newValue, int oldValue, int[][] board, UndoLog undoLog) {
        this.row = row;
        this.col = col;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.board = board;
        this.undoLog = undoLog;
    }

    @Override
    public void execute() throws IOException {
       
        board[row][col] = newValue;

        UserAction action = new UserAction(row, col, newValue, oldValue);
        undoLog.append(action);
    }

    @Override
    public void undo() throws IOException {
       
        UserAction lastAction = undoLog.undoLast();
        if (lastAction != null) {
            board[lastAction.getRow()][lastAction.getCol()] = lastAction.getOldValue();
        }
    }
}
