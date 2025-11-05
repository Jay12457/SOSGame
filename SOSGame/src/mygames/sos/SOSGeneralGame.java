package mygames.sos;

public class SOSGeneralGame extends SOSGame {
    private boolean isGameOver;

    public SOSGeneralGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public void makeMove(int row, int col, String symbol) {
        super.getGameBoard()[row][col] = symbol;
    }

    @Override
    public boolean isGameOver() {
        isGameOver = isBoardFull();
        return isGameOver;
    }
}
