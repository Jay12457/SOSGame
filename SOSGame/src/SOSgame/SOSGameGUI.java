package SOSgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SOSGameGUI extends JFrame {
    private SOSGameSession gameSession;
    private JPanel boardPanel;
    private JComboBox<Integer> boardSizeCombo;
    private JComboBox<String> gameModeCombo;
    private JLabel currentPlayerLabel;
    private JButton[][] boardButtons;

    public SOSGameGUI() {
        super("SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        controlPanel.add(new JLabel("Board Size:"));
        boardSizeCombo = new JComboBox<>();
        for (int i = 3; i <= 10; i++) boardSizeCombo.addItem(i);
        controlPanel.add(boardSizeCombo);

        controlPanel.add(new JLabel("Game Mode:"));
        gameModeCombo = new JComboBox<>(new String[]{"Simple", "General"});
        controlPanel.add(gameModeCombo);

        JButton startButton = new JButton("Start New Game");
        controlPanel.add(startButton);

        add(controlPanel, BorderLayout.NORTH);

        currentPlayerLabel = new JLabel("Current Turn: Blue (S)");
        add(currentPlayerLabel, BorderLayout.SOUTH);

        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);

        // Event Listeners
        startButton.addActionListener(e -> startNewGame());
    }

    private void startNewGame() {
        int size = (int) boardSizeCombo.getSelectedItem();
        String mode = (String) gameModeCombo.getSelectedItem();

        Player blue = new Player("Blue", "Blue");
        Player red = new Player("Red", "Red");
        gameSession = new SOSGameSession(size, mode, blue, red);

        currentPlayerLabel.setText("Current Turn: Blue (S)");

        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(size, size));
        boardButtons = new JButton[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                JButton btn = new JButton("");
                final int row = r;
                final int col = c;
                btn.setFont(new Font("Arial", Font.BOLD, 24));
                btn.setBackground(Color.WHITE);
                btn.setOpaque(true);
                btn.addActionListener(e -> handleBoardButtonClick(row, col, btn));
                boardButtons[r][c] = btn;
                boardPanel.add(btn);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void handleBoardButtonClick(int row, int col, JButton btn) {
        if (gameSession == null) return;
        if (!btn.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Invalid move! Cell occupied.");
            return;
        }
        char symbol = gameSession.getCurrentSymbol();
        boolean success = gameSession.makeMove(row, col);
        if (success) {
            btn.setText(String.valueOf(symbol));
            btn.setForeground(symbol == 'S' ? Color.BLUE : Color.RED);

            // Highlight SOS if formed
            List<int[]> sosList = gameSession.getBoard().findSOS(row, col);
            for (int[] cells : sosList) {
                for (int i = 0; i < 6; i += 2) {
                    int r = cells[i], c = cells[i+1];
                    if (r >= 0 && c >= 0 && r < boardButtons.length && c < boardButtons.length)
                        boardButtons[r][c].setBackground(Color.YELLOW);
                }
            }

            Player next = gameSession.getCurrentPlayer();
            String nextPlayerName = next.getName();
            String nextSymbol = gameSession.getCurrentSymbol() + "";
            currentPlayerLabel.setText("Current Turn: " + nextPlayerName + " (" + nextSymbol + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SOSGameGUI gui = new SOSGameGUI();
            gui.setVisible(true);
        });
    }
}
