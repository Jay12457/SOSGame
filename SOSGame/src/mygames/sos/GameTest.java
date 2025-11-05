package mygames.sos;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void testChooseBoardSize() {
        SOSGame game = new SOSSimpleGame(8);
        game.setBoardSize(10);
        assertEquals(10, game.getBoardSize());
    }

    @Test
    public void testBoardSizeEnteredIsNumeric() {
        SOSGame game = new SOSSimpleGame(8);
        assertEquals(false, game.isBoardSizeTextNumeric("a"));
        assertEquals(true, game.isBoardSizeTextNumeric("5"));
    }

    @Test
    public void testBoardSizeEnteredIsGreaterThanTwo() {
        SOSGame game = new SOSSimpleGame(8);
        assertEquals(false, game.isBoardSizeGreaterThanTwo(2));
        assertEquals(true, game.isBoardSizeGreaterThanTwo(5));
    }

    @Test
    public void testSimpleGameMode() {
        SOSGame game = new SOSSimpleGame(8);
        assertTrue(game instanceof SOSSimpleGame);
    }

    @Test
    public void testGeneralGameMode() {
        SOSGame game = new SOSGeneralGame(8);
        assertTrue(game instanceof SOSGeneralGame);
    }

    @Test
    public void testStartGameWithChosenSizeAndMode() {
        SOSGame game = new SOSSimpleGame(8);
        int boardSize = 9;
        game.reset(boardSize);
        assertEquals(boardSize, game.getBoardSize());
        assertEquals(false, game instanceof SOSGeneralGame);
        assertEquals("", game.getGameBoard()[0][0]);
    }

    @Test
    public void testMakeSMoveInSimpleGame() {
        SOSGame game = new SOSSimpleGame(8);
        game.reset(8);
        game.makeMove(5, 0, "S");
        assertEquals("S", game.getGameBoard()[5][0]);
    }

    @Test
    public void testMakeOMoveInSimpleGame() {
        SOSGame game = new SOSSimpleGame(8);
        game.reset(8);
        game.makeMove(0, 2, "O");
        assertEquals("O", game.getGameBoard()[0][2]);
    }

    @Test
    public void testMakeSMoveInGeneralGame() {
        SOSGame game = new SOSGeneralGame(8);
        game.reset(8);
        game.makeMove(5, 0, "S");
        assertEquals("S", game.getGameBoard()[5][0]);
    }

    @Test
    public void testMakeOMoveInGeneralGame() {
        SOSGame game = new SOSGeneralGame(8);
        game.reset(8);
        game.makeMove(0, 2, "O");
        assertEquals("O", game.getGameBoard()[0][2]);
    }

    @Test
    public void testInitialBoardSize() {
        SOSGame game = new SOSSimpleGame(8);
        assertEquals(8, game.getBoardSize());
    }

    @Test
    public void testInitializeBoard() {
        SOSGame game = new SOSSimpleGame(8);
        game.initializeBoard();
        assertEquals("", game.getGameBoard()[0][0]);
    }

    @Test
    public void testInitialPlayerTurn() {
        SOSGame game = new SOSSimpleGame(8);
        assertTrue(game.isBluePlayersTurn());
    }

    @Test
    public void testResetGame() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(0, 0, "S");
        game.reset(10);
        assertEquals(10, game.getBoardSize());
        assertEquals("", game.getGameBoard()[0][0]);
    }

    // test game over in simple game mode
    @Test
    public void testIsSimpleGameOver_SOSWinner() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        boolean result = game.isGameOver();
        assertTrue(result);
    }

    @Test
    public void testIsSimpleGameOver_NotOver() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "S");
        boolean result = game.isGameOver();
        assertFalse(result);
    }

    @Test
    public void testIsSimpleGameOver_BoardFull() {
        SOSGame game = new SOSSimpleGame(3);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "S");
        game.makeMove(1, 0, "S");
        game.makeMove(1, 1, "S");
        game.makeMove(1, 2, "S");
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "S");
        game.makeMove(2, 2, "S");
        boolean result = game.isGameOver();
        assertTrue(result);
    }

    // test game over in General game mode
    @Test
    public void testIsGeneralGameOver_SOSSequence() {
        SOSGame game = new SOSGeneralGame(8);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        boolean result = game.isGameOver();
        assertFalse(result);
    }

    @Test
    public void testIsGeneralGameOver_BoardFull() {
        SOSGame game = new SOSGeneralGame(3);
        game.makeMove(0, 0, "S");
        boolean sequence = game.checkSequenceAt(0, 0);
        assertFalse(sequence);
        game.makeMove(0, 1, "S");
        sequence = game.checkSequenceAt(0, 1);
        assertFalse(sequence);
        game.makeMove(0, 2, "S");
        sequence = game.checkSequenceAt(0, 2);
        assertFalse(sequence);
        game.makeMove(1, 0, "S");
        sequence = game.checkSequenceAt(1, 0);
        assertFalse(sequence);
        game.makeMove(1, 1, "S");
        sequence = game.checkSequenceAt(1, 1);
        assertFalse(sequence);
        game.makeMove(1, 2, "S");
        sequence = game.checkSequenceAt(1, 2);
        assertFalse(sequence);
        game.makeMove(2, 0, "S");
        sequence = game.checkSequenceAt(2, 0);
        assertFalse(sequence);
        game.makeMove(2, 1, "S");
        sequence = game.checkSequenceAt(2, 1);
        assertFalse(sequence);
        game.makeMove(2, 2, "S");
        sequence = game.checkSequenceAt(2, 2);
        assertFalse(sequence);
        boolean result = game.isGameOver();
        assertTrue(result);
        assertTrue(game.getBluePlayerSOSCount() == game.getRedPlayerSOSCount());
    }

    @Test
    public void testIsGeneralGameOver_BluePlayerWins() {
        SOSGame game = new SOSGeneralGame(3);
        game.makeMove(0, 0, "S");
        boolean sequence = game.checkSequenceAt(0, 0);
        assertFalse(sequence);
        game.makeMove(0, 1, "O");
        sequence = game.checkSequenceAt(0, 1);
        assertFalse(sequence);
        game.makeMove(0, 2, "S");
        sequence = game.checkSequenceAt(0, 2);
        assertTrue(sequence);
        game.makeMove(1, 0, "S");
        sequence = game.checkSequenceAt(1, 0);
        assertFalse(sequence);
        game.makeMove(1, 1, "S");
        sequence = game.checkSequenceAt(1, 1);
        assertFalse(sequence);
        game.makeMove(1, 2, "S");
        sequence = game.checkSequenceAt(1, 2);
        assertFalse(sequence);
        game.makeMove(2, 0, "S");
        sequence = game.checkSequenceAt(2, 0);
        assertFalse(sequence);
        game.makeMove(2, 1, "S");
        sequence = game.checkSequenceAt(2, 1);
        assertFalse(sequence);
        game.makeMove(2, 2, "S");
        sequence = game.checkSequenceAt(2, 2);
        assertFalse(sequence);
        boolean result = game.isGameOver();
        assertTrue(result);
        assertTrue(game.getBluePlayerSOSCount() > game.getRedPlayerSOSCount());
    }

    // check sos sequences in a simple game
    @Test
    public void testCheckSimpleGameForwardHorizontalSOS() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 3, "S");
        boolean result = game.checkForwardHorizontalSOS(0, 1);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameBackwardHorizontalSOS() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(0, 3, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 1, "S");
        boolean result = game.checkBackwardHorizontalSOS(0, 3);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameTopVerticalSOS() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(2, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(0, 0, "S");
        boolean result = game.checkTopVerticalSOS(2, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameBottomVerticalSOS() {
        SOSGame game = new SOSSimpleGame(8);
        game.makeMove(0, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(2, 0, "S");
        boolean result = game.checkBottomVerticalSOS(0, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromTopLeft() {
        SOSGame game = new SOSSimpleGame(5);
        game.makeMove(0, 0, "S");
        game.makeMove(1, 1, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromTopLeft(0, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromTopRight() {
        SOSGame game = new SOSSimpleGame(5);
        game.makeMove(0, 4, "S");
        game.makeMove(1, 3, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromTopRight(0, 4);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromBottomLeft() {
        SOSGame game = new SOSSimpleGame(5);
        game.makeMove(4, 0, "S");
        game.makeMove(3, 1, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromBottomLeft(4, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckSimpleGameDiagonalSOSFromBottomRight() {
        SOSGame game = new SOSSimpleGame(5);
        game.makeMove(4, 4, "S");
        game.makeMove(3, 3, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromBottomRight(4, 4);
        assertTrue(result);
    }

    // check sos sequences in a general game
    @Test
    public void testCheckGeneralGameForwardHorizontalSOS() {
        SOSGame game = new SOSGeneralGame(8);
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 3, "S");
        boolean result = game.checkForwardHorizontalSOS(0, 1);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameBackwardHorizontalSOS() {
        SOSGame game = new SOSGeneralGame(8);
        game.makeMove(0, 3, "S");
        game.makeMove(0, 2, "O");
        game.makeMove(0, 1, "S");
        boolean result = game.checkBackwardHorizontalSOS(0, 3);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameTopVerticalSOS() {
        SOSGame game = new SOSGeneralGame(8);
        game.makeMove(2, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(0, 0, "S");
        boolean result = game.checkTopVerticalSOS(2, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameBottomVerticalSOS() {
        SOSGame game = new SOSGeneralGame(8);
        game.makeMove(0, 0, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(2, 0, "S");
        boolean result = game.checkBottomVerticalSOS(0, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromTopLeft() {
        SOSGame game = new SOSGeneralGame(5);
        game.makeMove(0, 0, "S");
        game.makeMove(1, 1, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromTopLeft(0, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromTopRight() {
        SOSGame game = new SOSGeneralGame(5);
        game.makeMove(0, 4, "S");
        game.makeMove(1, 3, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromTopRight(0, 4);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromBottomLeft() {
        SOSGame game = new SOSGeneralGame(5);
        game.makeMove(4, 0, "S");
        game.makeMove(3, 1, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromBottomLeft(4, 0);
        assertTrue(result);
    }

    @Test
    public void testCheckGeneralGameDiagonalSOSFromBottomRight() {
        SOSGame game = new SOSGeneralGame(5);
        game.makeMove(4, 4, "S");
        game.makeMove(3, 3, "O");
        game.makeMove(2, 2, "S");
        boolean result = game.checkDiagonalSOSFromBottomRight(4, 4);
        assertTrue(result);
    }
}
