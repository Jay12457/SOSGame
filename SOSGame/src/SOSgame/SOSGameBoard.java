package SOSgame;

import java.util.ArrayList;
import java.util.List;

public class SOSGameBoard {
    private char[][] cells;
    private int size;

    public SOSGameBoard(int size) {
        this.size = size;
        cells = new char[size][size];
        clearBoard();
    }

    public void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = ' ';
            }
        }
    }

    public boolean placeSymbol(int row, int col, char symbol) {
        if (row < 0 || col < 0 || row >= size || col >= size)
            return false;
        if (cells[row][col] != ' ')
            return false;
        if (symbol != 'S' && symbol != 'O')
            return false;
        cells[row][col] = symbol;
        return true;
    }

    public char getCell(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size)
            return ' ';
        return cells[row][col];
    }

    public int getSize() {
        return size;
    }

    // Returns the list of buttons to highlight for an SOS formed at the last move, or empty if none.
    public List<int[]> findSOS(int row, int col) {
        List<int[]> sosCells = new ArrayList<>();
        char symbol = cells[row][col];
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 1, 1 }, { -1, 1 } };

        for (int[] d : directions) {
            int dr = d[0], dc = d[1];
            // Check for S O S forward
            if (checkSOS(row - dr, col - dc, dr, dc))
                sosCells.add(new int[] { row - dr, col - dc, row, col, row + dr, col + dc });
            // Check for S O S backward
            if (checkSOS(row + dr, col + dc, -dr, -dc))
                sosCells.add(new int[] { row + dr, col + dc, row, col, row - dr, col - dc });
        }
        return sosCells;
    }

    // Helper for checking SOS patterns
    private boolean checkSOS(int r, int c, int dr, int dc) {
        try {
            return getCell(r, c) == 'S' &&
                   getCell(r + dr, c + dc) == 'O' &&
                   getCell(r + 2 * dr, c + 2 * dc) == 'S';
        } catch (Exception ex) {
            return false;
        }
    }
}
