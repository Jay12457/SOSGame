package SOSgame;

public class SOSGameSession {
    private SOSGameBoard board;
    private Player bluePlayer;
    private Player redPlayer;
    private Player currentPlayer;
    private String gameMode; // "Simple" or "General"
    // Track turn: even = S, odd = O
    private int turnCount = 0;

    public SOSGameSession(int boardSize, String gameMode, Player blue, Player red) {
        this.board = new SOSGameBoard(boardSize);
        this.gameMode = gameMode;
        this.bluePlayer = blue;
        this.redPlayer = red;
        this.currentPlayer = bluePlayer;
        this.turnCount = 0;
    }

    public SOSGameBoard getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == bluePlayer) ? redPlayer : bluePlayer;
        turnCount++;
    }

    // Returns the symbol for the next move based on turn count
    public char getCurrentSymbol() {
        return (turnCount % 2 == 0) ? 'S' : 'O';
    }

    // Handles move and switches turn if move is valid; returns true if move placed.
    public boolean makeMove(int row, int col) {
        char symbol = getCurrentSymbol();
        boolean placed = board.placeSymbol(row, col, symbol);
        if (placed)
            switchTurn();
        return placed;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void startNewGame(int boardSize, String gameMode) {
        this.board = new SOSGameBoard(boardSize);
        this.gameMode = gameMode;
        this.currentPlayer = bluePlayer;
        this.turnCount = 0;
    }
}
