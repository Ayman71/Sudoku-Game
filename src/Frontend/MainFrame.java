/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.Catalog;
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
import Backend.SolutionInvalidException;
import Backend.StorageManager;
import Backend.SudokuController;
import Backend.UndoLog;
import Backend.UserAction;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayman
 */
public class MainFrame extends javax.swing.JFrame {

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
    SudokuController sudokuController;
    StorageManager storageManager;

    public MainFrame() throws FileNotFoundException, SolutionInvalidException, IOException {
        initComponents();
        this.setSize(750, 450);
        this.setLocationRelativeTo(null);
        gamePanel.setLayout(new GridLayout(9, 9));
        gamePanel.setPreferredSize(new Dimension(450, 450));
        pack();
        storageManager = new StorageManager("");
        sudokuController = new SudokuController(storageManager);

        Catalog catalog = sudokuController.getCatalog();
        int[][] board;
        int[][] source;
        if (catalog.hasCurrent()) {
            board = sudokuController.getGame(DifficultyEnum.INCOMPLETE).getBoard();
            source = storageManager.loadSource().getBoard();
        } else {
            board = sudokuController.getGame(DifficultyEnum.EASY).getBoard();
            source = board;
        }
        int editables = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                editable[r][c] = (source[r][c] == 0);
                if (editable[r][c]) {
                    editables++;
                }
            }
        }
        switch (editables) {
            case 10:
                difficultyLabel.setText("Difficulty: Easy");
                break;
            case 20:
                difficultyLabel.setText("Difficulty: Medium");
                break;
            default:
                difficultyLabel.setText("Difficulty: Hard");
                break;
        }
        undoLog = new UndoLog("incomplete");
        currentBoard = board;
        setupBoard(board);
        setupKeyPad();
    }

    void setupBoard(int[][] board) throws IOException {
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
                        if (buttonSelected == null) {
                            JOptionPane.showMessageDialog(this, "No number selected! please try again.", "Selection warining", JOptionPane.WARNING_MESSAGE);

                        } else {
                            String newNumber = buttonSelected.getText();
                            String oldNumber = tile.getText();
                            if (oldNumber.equals("")) {
                                oldNumber = "0";
                            }
                            currentBoard[row][col] = Integer.parseInt(newNumber);
                            UserAction userAction = new UserAction(row, col, Integer.parseInt(newNumber), Integer.parseInt(oldNumber));
                            try {
                                undoLog.append(userAction);
                                storageManager.saveCurrent(currentBoard);
                            } catch (IOException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            tile.setText(newNumber);

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
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton11.setText("Verify");
        jPanel1.add(jButton11);

        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton12.setText("Solve");
        jPanel1.add(jButton12);

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keyPadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed

        try {
            UserAction userAction = undoLog.undoLast();
            if (userAction == null) {
                JOptionPane.showMessageDialog(this, "No previous log.", "Log Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String oldValue = String.valueOf(userAction.getOldValue());
            Tile tile = tiles[userAction.getRow()][userAction.getCol()];
            currentBoard[userAction.getRow()][userAction.getCol()] = Integer.parseInt(oldValue);
            if (Integer.parseInt(oldValue) == 0) {
                tile.setText("");
            } else {
                tile.setText(oldValue);
            }
            storageManager.saveCurrent(currentBoard);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_undoButtonActionPerformed

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
                    new MainFrame().setVisible(true);
                } catch (FileNotFoundException ex) {
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
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel keyPadPanel;
    private javax.swing.JButton undoButton;
    // End of variables declaration//GEN-END:variables
}
