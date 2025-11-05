package mygames.sos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SOSGUIGame extends JFrame {
    JRadioButton redORadioButton;
    JRadioButton redSRadioButton;
    JRadioButton blueORadioButton;
    JRadioButton blueSRadioButton;
    JPanel bluePlayerEmptyPanel;
    JPanel bluePlayerRadioPanel;
    JPanel redPlayerRadioPanel;
    JPanel mainPanel;
    JPanel topPanel;
    JLabel sosLabel;
    JLabel boardSizeLabel;
    JTextField boardSizeTextField;
    JRadioButton simpleGameRadioButton;
    JRadioButton generalGameRadioButton;
    JPanel centerPanel;
    JPanel bluePlayerPanel;
    JLabel bluePlayerLabel;
    JPanel redPlayerPanel;
    JPanel redPlayerEmptyPanel;
    JLabel redPlayerLabel;
    JPanel boardPanel;
    JPanel bottomPanel;
    JLabel currentTurnLabel;
    JButton newGameButton;
    boolean gameModeChanged = false;
    boolean boardSizeChanged = false;
    boolean gameOver = false;
    private SOSGame sosGame;
    CustomButton[][] buttons;

    public SOSGUIGame() {
        sosGame = new SOSSimpleGame(8);
        setTitle("SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 650));
        mainPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new FlowLayout());
        sosLabel = new JLabel("SOS");

        simpleGameRadioButton = new JRadioButton("Simple Game");
        simpleGameRadioButton.setSelected(true);
        simpleGameRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    gameModeChanged = true;
                }
            }
        });

        generalGameRadioButton = new JRadioButton("General Game");
        generalGameRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    sosGame = new SOSGeneralGame(Integer.parseInt(boardSizeTextField.getText()));
                    gameModeChanged = true;
                }
            }
        });

        ButtonGroup gameTypeGroup = new ButtonGroup();
        gameTypeGroup.add(simpleGameRadioButton);
        gameTypeGroup.add(generalGameRadioButton);

        boardSizeLabel = new JLabel("Board Size:");
        boardSizeLabel.setBorder(new EmptyBorder(0, 100, 0, 0));
        boardSizeTextField = new JTextField(String.valueOf(sosGame.getBoardSize()));
        boardSizeTextField.setColumns(2);
        boardSizeTextField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e) {
                boardSizeChanged = true;
                handleBoardSizeTextChange(boardSizeTextField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        topPanel.add(sosLabel);
        topPanel.add(simpleGameRadioButton);
        topPanel.add(generalGameRadioButton);
        topPanel.add(boardSizeLabel);
        topPanel.add(boardSizeTextField);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        bluePlayerPanel = new JPanel(new BorderLayout());
        bluePlayerLabel = new JLabel("Blue Player");
        bluePlayerLabel.setBorder(new EmptyBorder(40, 20, 10, 20));
        bluePlayerRadioPanel = new JPanel(new BorderLayout());
        bluePlayerRadioPanel.setBorder(new EmptyBorder(40, 20, 10, 20));
        bluePlayerPanel.add(bluePlayerLabel, BorderLayout.NORTH);
        blueSRadioButton = new JRadioButton("S");
        blueSRadioButton.setSelected(true);
        blueORadioButton = new JRadioButton("O");
        bluePlayerRadioPanel.add(blueSRadioButton, BorderLayout.NORTH);
        bluePlayerRadioPanel.add(blueORadioButton, BorderLayout.SOUTH);
        ButtonGroup bluePlayerRadioGroup = new ButtonGroup();
        bluePlayerRadioGroup.add(blueSRadioButton);
        bluePlayerRadioGroup.add(blueORadioButton);
        bluePlayerPanel.add(bluePlayerRadioPanel, BorderLayout.CENTER);
        bluePlayerEmptyPanel = new JPanel();
        bluePlayerEmptyPanel.setPreferredSize(new Dimension(50, 300));
        bluePlayerPanel.add(bluePlayerEmptyPanel, BorderLayout.SOUTH);

        redPlayerPanel = new JPanel(new BorderLayout());
        redPlayerLabel = new JLabel("Red Player");
        redPlayerLabel.setBorder(new EmptyBorder(40, 20, 10, 20));
        redPlayerPanel.add(redPlayerLabel, BorderLayout.NORTH);
        redPlayerRadioPanel = new JPanel(new BorderLayout());
        redPlayerRadioPanel.setBorder(new EmptyBorder(40, 10, 10, 20));
        redSRadioButton = new JRadioButton("S");
        redSRadioButton.setSelected(true);
        redORadioButton = new JRadioButton("O");
        ButtonGroup redPlayerRadioGroup = new ButtonGroup();
        redPlayerRadioGroup.add(redSRadioButton);
        redPlayerRadioGroup.add(redORadioButton);
        redPlayerRadioPanel.add(redSRadioButton, BorderLayout.NORTH);
        redPlayerRadioPanel.add(redORadioButton, BorderLayout.SOUTH);
        redPlayerPanel.add(redPlayerRadioPanel, BorderLayout.CENTER);
        redPlayerEmptyPanel = new JPanel();
        redPlayerEmptyPanel.setPreferredSize(new Dimension(50, 300));
        redPlayerPanel.add(redPlayerEmptyPanel, BorderLayout.SOUTH);

        centerPanel = new JPanel(new BorderLayout());
        updateBoard();
        centerPanel.add(bluePlayerPanel, BorderLayout.WEST);
        centerPanel.add(redPlayerPanel, BorderLayout.EAST);

        bottomPanel = new JPanel(new BorderLayout());
        currentTurnLabel = new JLabel("Current Turn: blue");
        currentTurnLabel.setBorder(new EmptyBorder(0, 250, 0, 0));
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameButtonClicked();
            }
        });
        bottomPanel.add(currentTurnLabel, BorderLayout.CENTER);
        bottomPanel.add(newGameButton, BorderLayout.EAST);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void newGameButtonClicked() {
        gameModeChanged = false;
        boardSizeChanged = false;
        gameOver = false;
        boolean isValidBoardSize = handleBoardSizeTextChange(boardSizeTextField.getText());
        if (isValidBoardSize) {
            if (sosGame instanceof SOSSimpleGame) {
                sosGame.reset(Integer.parseInt(boardSizeTextField.getText()));
                JOptionPane.showMessageDialog(SOSGUIGame.this, "Starting new Simple Game with board size " + sosGame.getBoardSize(), "Starting New Game",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                sosGame.reset(Integer.parseInt(boardSizeTextField.getText()));
                JOptionPane.showMessageDialog(SOSGUIGame.this, "Starting new General Game with board size " + sosGame.getBoardSize(), "Starting New Game",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            currentTurnLabel.setText("Current Turn: blue");
        }
    }

    boolean handleBoardSizeTextChange(String txtBoardSize) {
        boolean isSuccess = false;
        if (sosGame.isBoardSizeTextNumeric(txtBoardSize)) {
            int newSize = Integer.parseInt(txtBoardSize);
            if (sosGame.isBoardSizeGreaterThanTwo(newSize)) {
                sosGame.setBoardSize(newSize);
                updateBoard();
                isSuccess = true;
            } else {
                JOptionPane.showMessageDialog(SOSGUIGame.this, "Board size must be greater than 2.", "Invalid Board Size", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(SOSGUIGame.this, "Invalid input for board size.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        return isSuccess;
    }

    private void updateBoard() {
        if (boardPanel != null) {
            centerPanel.remove(boardPanel);
        }
        int boardSize = sosGame.getBoardSize();
        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        buttons = new CustomButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                CustomButton button = new CustomButton("");
                int finalI = i;
                int finalJ = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cellClicked(button, finalI, finalJ);
                    }
                });
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void cellClicked(JButton button, int finalI, int finalJ) {
        boolean sos;
        if (boardSizeChanged) {
            JOptionPane.showMessageDialog(SOSGUIGame.this, "Board size has been changed, please press New Game button to start the new game", "Start new game",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gameModeChanged) {
            JOptionPane.showMessageDialog(SOSGUIGame.this, "Game mode has been changed, please press New Game button to start the new game", "Start new game",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (gameOver) {
            JOptionPane.showMessageDialog(SOSGUIGame.this, "Game over, please press New Game button to start the new game", "Start new game",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (button.getText().contains("S") || button.getText().contains("O")) {
            JOptionPane.showMessageDialog(SOSGUIGame.this, "Please click on an empty square", "Square already filled", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (sosGame.isBluePlayersTurn()) {
            if (blueSRadioButton.isSelected()) {
                button.setText("S");
                sosGame.makeMove(finalI, finalJ, "S");
                sos = checkSequenceAndDrawLine(finalI, finalJ, "S", Color.BLUE);
            } else {
                button.setText("O");
                sosGame.makeMove(finalI, finalJ, "O");
                sos = checkSequenceAndDrawLine(finalI, finalJ, "O", Color.BLUE);
            }
            if (sosGame.isGameOver()) {
                String msg = sosGame.getBluePlayerSOSCount() == sosGame.getRedPlayerSOSCount() ? "Its a draw" : sosGame.getBluePlayerSOSCount() >
                        sosGame.getRedPlayerSOSCount() ? "Blue player won!!" : "Red player won!!";
                JOptionPane.showMessageDialog(SOSGUIGame.this, msg, "Game Over!!!",
                        JOptionPane.INFORMATION_MESSAGE);
                gameOver = true;
            } else {
                if (!sos) {
                    sosGame.setBluePlayersTurn(false);
                    currentTurnLabel.setText("Current Turn: red");
                }
            }
        } else {
            if (redSRadioButton.isSelected()) {
                button.setText("S");
                sosGame.makeMove(finalI, finalJ, "S");
                sos = checkSequenceAndDrawLine(finalI, finalJ, "S", Color.RED);
            } else {
                button.setText("O");
                sosGame.makeMove(finalI, finalJ, "O");
                sos = checkSequenceAndDrawLine(finalI, finalJ, "O", Color.RED);
            }
            if (sosGame.isGameOver()) {
                String msg = sosGame.getBluePlayerSOSCount() == sosGame.getRedPlayerSOSCount() ? "Its a draw" : sosGame.getBluePlayerSOSCount() >
                        sosGame.getRedPlayerSOSCount() ? "Blue player won!!" : "Red player won!!";
                JOptionPane.showMessageDialog(SOSGUIGame.this, msg, "Game Over!!!",
                        JOptionPane.INFORMATION_MESSAGE);
                gameOver = true;
            } else {
                if (!sos) {
                    sosGame.setBluePlayersTurn(true);
                    currentTurnLabel.setText("Current Turn: blue");
                }
            }
        }
    }

    private boolean checkSequenceAndDrawLine(int row, int col, String symbol, Color
            color) {
        boolean sos = false;
        if (symbol.equals("S")) {
            if (sosGame.checkTopVerticalSOS(row, col)) {
                sos = true;
                drawVerticalLine(color, row - 2, col);
            } else if (sosGame.checkBottomVerticalSOS(row, col)) {
                sos = true;
                drawVerticalLine(color, row, col);
            } else if (sosGame.checkForwardHorizontalSOS(row, col)) {
                sos = true;
                drawHorizontalLine(color, row, col);
            } else if (sosGame.checkBackwardHorizontalSOS(row, col)) {
                sos = true;
                drawHorizontalLine(color, row, col - 2);
            } else if (sosGame.checkDiagonalSOSFromBottomLeft(row, col)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row - 2, col + 2);
            } else if (sosGame.checkDiagonalSOSFromBottomRight(row, col)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row - 2, col - 2);
            } else if (sosGame.checkDiagonalSOSFromTopLeft(row, col)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row, col);
            } else if (sosGame.checkDiagonalSOSFromTopRight(row, col)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row, col);
            }
        } else if (symbol.equals("O")) {
            if (sosGame.checkTopVerticalSOS(row + 1, col)) {
                sos = true;
                drawVerticalLine(color, row - 2 + 1, col);
            } else if (sosGame.checkBottomVerticalSOS(row - 1, col)) {
                sos = true;
                drawVerticalLine(color, row - 1, col);
            } else if (sosGame.checkForwardHorizontalSOS(row, col - 1)) {
                sos = true;
                drawHorizontalLine(color, row, col - 1);
            } else if (sosGame.checkBackwardHorizontalSOS(row, col + 1)) {
                sos = true;
                drawHorizontalLine(color, row, col - 2 + 1);
            } else if (sosGame.checkDiagonalSOSFromBottomLeft(row + 1, col - 1)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row - 2 + 1, col + 2 - 1);
            } else if (sosGame.checkDiagonalSOSFromBottomRight(row + 1, col + 1)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row - 2 + 1, col - 2 + 1);
            } else if (sosGame.checkDiagonalSOSFromTopLeft(row - 1, col - 1)) {
                sos = true;
                drawDiagonalLineFromTopLeft(color, row - 1, col - 1);
            } else if (sosGame.checkDiagonalSOSFromTopRight(row - 1, col + 1)) {
                sos = true;
                drawDiagonalLineFromTopRight(color, row - 1, col + 1);
            }
        }
        return sos;
    }

    public void drawVerticalLine(Color color, int startRow, int col) {
        for (int row = startRow; row < startRow + 3; row++) {
            buttons[row][col].addCenterVerticalLine(color);
        }
    }

    public void drawHorizontalLine(Color color, int row, int startCol) {
        for (int col = startCol; col < startCol + 3; col++) {
            buttons[row][col].addCenterHorizontalLine(color);
        }
    }

    public void drawDiagonalLineFromTopLeft(Color color, int startRow, int
            startCol) {
        for (int i = 0; i < 3; i++) {
            buttons[startRow++][startCol++].addTopLeftToBottomRightLine(color);
        }
    }

    public void drawDiagonalLineFromTopRight(Color color, int startRow, int
            startCol) {
        for (int i = 0; i < 3; i++) {
            buttons[startRow++][startCol--].addTopRightToBottomLeftLine(color);
        }
    }

    private class CustomButton extends JButton {
        private boolean topLeftToBottomRightLine;
        private boolean topRightToBottomLeftLine;
        private boolean centerHorizontalLine;
        private boolean centerVerticalLine;
        private Color topLeftToBottomRightLineColor;
        private Color topRightToBottomLeftLineColor;
        private Color centerHorizontalLineColor;
        private Color centerVerticalLineColor;

        public CustomButton(String text) {
            super(text);
            topLeftToBottomRightLine = false;
            topRightToBottomLeftLine = false;
            centerHorizontalLine = false;
            centerVerticalLine = false;
        }

        public void addTopLeftToBottomRightLine(Color color) {
            topLeftToBottomRightLine = true;
            topLeftToBottomRightLineColor = color;
            repaint();
        }

        public void addTopRightToBottomLeftLine(Color color) {
            topRightToBottomLeftLine = true;
            topRightToBottomLeftLineColor = color;
            repaint();
        }

        public void addCenterHorizontalLine(Color color) {
            centerHorizontalLine = true;
            centerHorizontalLineColor = color;
            repaint();
        }

        public void addCenterVerticalLine(Color color) {
            centerVerticalLine = true;
            centerVerticalLineColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (topLeftToBottomRightLine) {
                g.setColor(topLeftToBottomRightLineColor);
                g.drawLine(0, 0, getWidth(), getHeight());
            }
            if (topRightToBottomLeftLine) {
                g.setColor(topRightToBottomLeftLineColor);
                g.drawLine(getWidth(), 0, 0, getHeight());
            }
            if (centerHorizontalLine) {
                g.setColor(centerHorizontalLineColor);
                int y = getHeight() / 2;
                g.drawLine(0, y, getWidth(), y);
            }
            if (centerVerticalLine) {
                g.setColor(centerVerticalLineColor);
                int x = getWidth() / 2;
                g.drawLine(x, 0, x, getHeight());
            }
        }
    }
}
