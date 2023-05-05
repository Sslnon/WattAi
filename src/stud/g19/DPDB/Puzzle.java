package stud.g19.DPDB;

public class Puzzle {

    public static final int BLANK = 0;

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;
    public static final int DOWN = 4;

    public int[][] grid = new int[4][4];		// stores the tiles
    public int blankRow, blankCol;				// (row, col) of the blank tile

    public Puzzle(Puzzle p)

    {
        for(int i=0; i<grid.length; i++)
            for(int j=0; j<grid[0].length; j++)
                grid[i][j] = p.grid[i][j];
        blankRow = p.blankRow;
        blankCol = p.blankCol;
    }

    public Puzzle(int [][] g)
        /*
         * builds a Puzzle object around a grid representing the 15-puzzle
         */
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

    public void makeMove(int move)
        /*
         * makes the given move on the 15-puzzle.  This method assume canMakeMove(move) was called prior
         */
    {
        if (move == UP) {
            grid[blankRow][blankCol] = grid[blankRow-1][blankCol];
            grid[blankRow-1][blankCol] = BLANK;
            blankRow--;
        }
        else if (move == DOWN) {
            grid[blankRow][blankCol] = grid[blankRow+1][blankCol];
            grid[blankRow+1][blankCol] = BLANK;
            blankRow++;
        }
        else if (move == LEFT) {
            grid[blankRow][blankCol] = grid[blankRow][blankCol-1];
            grid[blankRow][blankCol-1] = BLANK;
            blankCol--;
        }
        else if (move == RIGHT) {
            grid[blankRow][blankCol] = grid[blankRow][blankCol+1];
            grid[blankRow][blankCol+1] = BLANK;
            blankCol++;
        }
    }

}
