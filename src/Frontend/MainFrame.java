/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.Catalog;
import Backend.ControllerFacade;
import Backend.DifficultyEnum;
import Backend.Game;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import Backend.Loader;
import Backend.NotFoundException;
import Backend.SolutionInvalidException;
import Backend.StorageManager;
import Backend.SudokuController;
import Backend.GameObserver;
import Backend.PlaceNumberCommand;
import Backend.SudokuSolver;
import Backend.UndoLog;
import Backend.UserAction;
import Backend.Verifier;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayman
 */
public class MainFrame extends javax.swing.JFrame implements GameObserver {

    @Override
    public void update(int[][] board) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Creates new form MainFrame
     */
    class Tile extends JButton {

        int row;
        int col;

        public Tile(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    JToggleButton buttonSelected = null;
    UndoLog undoLog;
    private Tile tiles[][] = new Tile[9][9];
    private int currentBoard[][] = new int[9][9];
    private boolean editable[][] = new boolean[9][9];
    ControllerFacade controllerFacade;

    public MainFrame(char difficulty, String sourcePath) throws NotFoundException, SolutionInvalidException, IOException {
        initComponents();
        this.setSize(750, 450);
        this.setLocationRelativeTo(null);
        DifficultyEnum difficultyEnum = switch (Character.toUpperCase(difficulty)) {
            case 'E' ->
                DifficultyEnum.EASY;
            case 'M' ->
                DifficultyEnum.MEDIUM;
            case 'H' ->
                DifficultyEnum.HARD;
            default ->
                DifficultyEnum.INCOMPLETE;
        };
        gamePanel.setLayout(new GridLayout(9, 9));
        gamePanel.setPreferredSize(new Dimension(450, 450));
        pack();
        controllerFacade = new ControllerFacade();
        int[][] board;
        int[][] source;

        board = controllerFacade.getGame(difficulty);
        source = controllerFacade.loadSource(difficultyEnum).getBoard();

        int editables = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                editable[r][c] = (source[r][c] == 0);
                if (editable[r][c]) {
                    editables++;
                }
            }
        }
        if (editables == 10) {
            difficultyLabel.setText("Difficulty: Easy");
        } else if (editables == 20) {
            difficultyLabel.setText("Difficulty: Medium");
        } else {
            difficultyLabel.setText("Difficulty: Hard");
        }
        undoLog = new UndoLog("incomplete");
        if (difficultyEnum != DifficultyEnum.INCOMPLETE) {
            undoLog.clearLog();
            controllerFacade.saveCurrent(board);
            controllerFacade.saveNewCurrentSource(board);
        }
        currentBoard = board;
        setupBoard(board);
        setupKeyPad();
    }

    void setupBoard(int[][] board) throws IOException {
        if (zeros(currentBoard) == 5) {
            solveBtn.setEnabled(true);
        } else {
            solveBtn.setEnabled(false);
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                final int row = r;
                final int col = c;

                Tile tile = new Tile(row, col);
                tiles[row][col] = tile;
                if (!editable[row][col]) {
                    tile.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    tile.setText(String.valueOf(board[row][col]));
                    tile.setBackground(Color.GRAY);
                } else {
                    tile.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    tile.setForeground(Color.BLACK);
                    if (board[row][col] == 0) {
                        tile.setText("");
                    } else {
                        tile.setText(String.valueOf(board[row][col]));
                    }
                    tile.setBackground(Color.LIGHT_GRAY);
                    tile.addActionListener(e -> {
                        if (!undoButton.isEnabled()) {
                            JOptionPane.showMessageDialog(this, "Game over! try again in a new game.", "Game Over", JOptionPane.WARNING_MESSAGE);

                        } else if (buttonSelected == null) {
                            JOptionPane.showMessageDialog(this, "No number selected! please try again.", "Selection warining", JOptionPane.WARNING_MESSAGE);

                        } else {
                            String newNumber = buttonSelected.getText();
                            String oldNumber = tile.getText();
                            if (!newNumber.equals(oldNumber)) {
                                if (oldNumber.equals("")) {
                                    oldNumber = "0";
                                }
                                int newVal = Integer.parseInt(newNumber);
                                int oldVal = Integer.parseInt(oldNumber);

                                PlaceNumberCommand command = new PlaceNumberCommand(row, col, newVal, oldVal,
                                        currentBoard, undoLog);
                                try {
                                    command.execute();
                                    controllerFacade.saveCurrent(currentBoard);
                                } catch (Exception ex) {
                                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                tile.setText(newNumber);
                                paintTiles();
                                if (zeros(currentBoard) == 5) {
                                    solveBtn.setEnabled(true);
                                } else {
                                    solveBtn.setEnabled(false);
                                }
                            }
                        }

                    });
                }
                if ((row == 2 && col == 2) || (row == 2 && col == 5) || (row == 5 && col == 2) || (row == 5 && col == 5)) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 4, Color.BLACK));
                } else if (row == 2 || row == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, Color.BLACK));
                } else if (col == 2 || col == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 4, Color.BLACK));
                } else {
                    tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }

                tile.setFocusable(false);
                gamePanel.add(tile);
            }
        }
    }

    void setupKeyPad() {
        keyPadPanel.setLayout(new GridLayout(3, 3, 10, 10));
        keyPadPanel.setPreferredSize(new Dimension(280, 180));
        pack();
        for (int i = 1; i <= 9; i++) {
            JToggleButton btn = new JToggleButton(String.valueOf(i));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setFocusable(false);
            btn.setMargin(new Insets(10, 10, 10, 10));
            btn.addActionListener(e -> {
                if (buttonSelected == btn) {
                    buttonSelected = null;
                    btn.setSelected(false);
                } else if (buttonSelected == null) {
                    btn.setSelected(true);
                    buttonSelected = btn;
                } else {
                    buttonSelected.setSelected(false);
                    btn.setSelected(true);
                    buttonSelected = btn;
                }

            });
            keyPadPanel.add(btn);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        difficultyLabel = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        keyPadPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        undoButton = new javax.swing.JButton();
        verifyBtn = new javax.swing.JButton();
        solveBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        difficultyLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        difficultyLabel.setText("Difficulty: ");

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 465, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout keyPadPanelLayout = new javax.swing.GroupLayout(keyPadPanel);
        keyPadPanel.setLayout(keyPadPanelLayout);
        keyPadPanelLayout.setHorizontalGroup(
            keyPadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        keyPadPanelLayout.setVerticalGroup(
            keyPadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.setPreferredSize(new java.awt.Dimension(280, 50));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        undoButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        undoButton.setText("Undo");
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });
        jPanel1.add(undoButton);

        verifyBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        verifyBtn.setText("Verify");
        verifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyBtnActionPerformed(evt);
            }
        });
        jPanel1.add(verifyBtn);

        solveBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        solveBtn.setText("Solve");
        solveBtn.setEnabled(false);
        solveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveBtnActionPerformed(evt);
            }
        });
        jPanel1.add(solveBtn);

        backBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        backBtn.setText("Back To Main Menu");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(keyPadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(difficultyLabel))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(difficultyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(keyPadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed

        try {

            PlaceNumberCommand command = new PlaceNumberCommand(0, 0, 0, 0, currentBoard, undoLog);
            command.undo();

            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    int val = currentBoard[r][c];
                    Tile tile = tiles[r][c];
                    if (editable[r][c]) {
                        if (val == 0) {
                            tile.setText("");
                        } else {
                            tile.setText(String.valueOf(val));
                        }
                    }
                }
            }
            paintTiles();
            controllerFacade.saveCurrent(currentBoard);
            if (zeros(currentBoard) == 5) {
                solveBtn.setEnabled(true);
            } else {
                solveBtn.setEnabled(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_undoButtonActionPerformed

    private void paintTiles() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (editable[row][col]) {
                    tiles[row][col].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }
    private void verifyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyBtnActionPerformed

        if (hasZeros(currentBoard)) {
            JOptionPane.showMessageDialog(this, "Game is incomplete!", "Icomplete", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean[][] result = controllerFacade.verifyGame(currentBoard);

        boolean allValid = true;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!result[r][c]) {
                    allValid = false;
                }
            }
        }
        if (allValid) {
            JOptionPane.showMessageDialog(this, "Game Solved! No further actions are allowed.", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (!result[r][c] && editable[r][c]) {
                        tiles[r][c].setBackground(Color.decode("#fa776e"));
                    }
                }
            }
        }

    }//GEN-LAST:event_verifyBtnActionPerformed


    private void solveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveBtnActionPerformed
        try {
            int[][] solution = controllerFacade.solveGame(currentBoard);
            if (solution != null) {
                for (int[] move : solution) {
                    int r = move[0];
                    int c = move[1];
                    int val = move[2];
                    currentBoard[r][c] = val;
                }

                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        if (editable[r][c]) {
                            int v = currentBoard[r][c];
                            if (v != 0) {
                                tiles[r][c].setText(String.valueOf(v));
                            }
                            tiles[r][c].setBackground(Color.decode("#95edad"));
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Game Solved! No further actions are allowed.", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                EndGame();
            } else {
                EndGame();
                JOptionPane.showMessageDialog(this, "No valid solution found for current board!", "Game Over",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_solveBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        StartupFrame startupFrame = null;
        try {
            startupFrame = new StartupFrame();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SolutionInvalidException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        startupFrame.setVisible(true);
    }//GEN-LAST:event_backBtnActionPerformed

    private void EndGame() {
        for (Component comp : keyPadPanel.getComponents()) {
            if (comp instanceof JToggleButton) {
                JToggleButton btn = (JToggleButton) comp;
                btn.setEnabled(false);
                btn.setSelected(false);
                buttonSelected = null;
            }
        }

        solveBtn.setEnabled(false);
        undoButton.setEnabled(false);
        verifyBtn.setEnabled(false);

        try {
            controllerFacade.deleteCurrent();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting the current game from storage.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    private boolean hasZeros(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int zeros(int[][] board) {
        int count = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            // Set FlatLaf Dark look and feel
            FlatDarkLaf.setup();

        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame('E', "").setVisible(true);
                } catch (NotFoundException ex) {
                    ex.printStackTrace();
                } catch (SolutionInvalidException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel keyPadPanel;
    private javax.swing.JButton solveBtn;
    private javax.swing.JButton undoButton;
    private javax.swing.JButton verifyBtn;
    // End of variables declaration//GEN-END:variables
}
