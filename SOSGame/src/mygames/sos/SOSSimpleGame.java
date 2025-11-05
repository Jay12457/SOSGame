package mygames.sos;

public class SOSSimpleGame extends SOSGame {
    private boolean isGameOver;

    public SOSSimpleGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public void makeMove(int row, int col, String symbol) {
        super.getGameBoard()[row][col] = symbol;
    }

    @Override
    public boolean isGameOver() {
        for (int row = 0; row < getBoardSize(); row++) {
            for (int col = 0; col < getBoardSize(); col++) {
                isGameOver = checkSequenceAt(row, col);
                if (isGameOver)
                    return true;
            }
        }
        return isBoardFull();
    }
}
