package stud.g19.DPDB;

public class Puzzle {
    // Constants for the puzzle moves and blank tile value
    public static final int BLANK = 0;

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;
    public static final int DOWN = 4;
    // 4x4 grid and position of the blank tile
    public int[][] grid = new int[4][4];
    public int blankRow, blankCol;
    // Constructor that creates a copy of the puzzle
    public Puzzle(Puzzle p)

    {
        for(int i=0; i<grid.length; i++)
            for(int j=0; j<grid[0].length; j++)
                grid[i][j] = p.grid[i][j];
        blankRow = p.blankRow;
        blankCol = p.blankCol;
    }
    // Constructor that initializes the puzzle with given values
    public Puzzle(int [][] g)

    {
        for(int i=0; i<grid.length; i++)
            for(int j=0; j<grid[0].length; j++) {
                grid[i][j] = g[i][j];
                if (grid[i][j] == BLANK) {
                    blankRow = i;
                    blankCol = j;
                }
            }
    }


}
