package stud.g19.DPDB;
import java.io.*;
import java.util.ArrayList;

public class DPDB {
    public int[][] database = new int[4095][5];

    public DPDB()
    {
            buildDatabase();
    }
    // create an instance of DPDB and build the database
    public static void main(String[] args){
        DPDB d1 = new DPDB();
    }
    // method to build the database
    private void buildDatabase()  {
        ArrayList<Position> allNodes = makeAllNodes();
        // get all possible positions
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Rex\\Desktop\\111\\663_0.txt", false)));
            for (Position n : allNodes) {
                int[] temp = new int[2];
                temp = hasher(n);
                ArrayList<Integer> nums = findNums(n);
                // calculate the heuristic value
                int calc = calculateHeuristic(n, nums.get(0), nums.get(1), nums.get(2));
                database[temp[0]][temp[1]]=calc;
                System.out.println(calc);
                // write the heuristic value to the file
                out.write(calc+"");
                String str="\r\n";
                out.write(str);
            }
            out.flush();
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    // method to find the numbers in a position
    private ArrayList<Integer> findNums(Position sn) {
        ArrayList<Integer> nums = new ArrayList<>();
        for(int i = 0; i <4; i++) {
            for(int j = 0; j < 4;j++) {
                if(sn.puzzle.grid[i][j] != 0) {
                    nums.add(sn.puzzle.grid[i][j]);
                }
            }
        }
        return nums;
    }
    // method to generate all possible positions
    private ArrayList<Position> makeAllNodes(){ //todo
        ArrayList<Position> allNodes = new ArrayList<>();
        for(int c = 0;c < 16; c++) {
            for(int b = 0; b < 16; b++) {
                for(int a = 0; a < 16; a++){
                    if(a !=b && b!=c && a!=c) {
                        Puzzle p = makePartialPuzzle(a, b, c,1,2,3);
                        allNodes.add(new Position(p,0,0,null,0));
                        p = makePartialPuzzle(a, b, c,4,5,6);
                        allNodes.add(new Position(p,0,0,null,0));
                        p = makePartialPuzzle(a, b, c,7,8,9);
                        allNodes.add(new Position(p,0,0,null,0));
                        p = makePartialPuzzle(a, b, c,10,11,12);
                        allNodes.add(new Position(p,0,0,null,0));
                        p = makePartialPuzzle(a, b, c,13,14,15);
                        allNodes.add(new Position(p,0,0,null,0));
                    }
                }
            }
        }
        return allNodes;
    }

    private Puzzle makePartialPuzzle(int a, int b, int c, int valA, int valB, int valC){
        int[][] grid = new int[4][4];
        for(int i = 0; i < 4;i++)
            for(int j = 0;j < 4;j++)
                grid[i][j] = 0;

        int[] indices = {a, b, c};
        int[] values = {valA, valB, valC};
        for (int i = 0; i < 3; i++) {
            int x = indices[i] % 4;
            int y = indices[i] / 4;
            grid[y][x] = values[i];
        }

        Puzzle p = new Puzzle(grid);
        return p;
    }
    // Method to generate a hash value for a given position
    private int[] hasher(Position sn) {
        int ret[] = new int[2];
        int index1 = 0;
        int index2 = 0;
        for(int i = 1; i < 16; i+=3) {
            if(puzzleContains(sn, i)) {
                index1 = findLocation(sn, i)*256 + findLocation(sn, i+1)*16 + findLocation(sn, i+2);
                index2 = (i/3);
            }
            if(index1 !=0)
                ret[0] = index1;
            ret[1] = index2;
        }
        return ret;
    }
    // Method to check if a number n is present in the puzzle grid
    private boolean puzzleContains(Position sn, int n) {
        for(int i =0;i<4;i++) {
            for(int j = 0; j <4;j++) {
                if(sn.puzzle.grid[i][j] == n) {
                    return true;
                }
            }
        }
        return false;
    }
    // Method to calculate the heuristic value for a given position and three numbers
    private int calculateHeuristic(Position sn, int num1, int num2, int num3) {
        int totalHeuristic =0;
        Puzzle p = sn.puzzle;
        for(int r = 0; r < 4; r++)
        {
            for(int c = 0; c < 4; c++)
            {
                if(p.grid[r][c] == num1 || p.grid[r][c] == num2 ||p.grid[r][c] == num3)
                {
                    totalHeuristic += (Math.abs((p.grid[r][c]-1)/4 - r)) + (Math.abs((p.grid[r][c]-1)%4 - c));
                    if(c<3 && p.grid[r][c] > p.grid[r][c+1] && (p.grid[r][c]-1)/4 == (p.grid[r][c+1]-1)/4
                            && (p.grid[r][c]-1)/4 ==r && p.grid[r][c+1] !=0) {
                        totalHeuristic +=2;
                    }
                }
            }
        }
        return totalHeuristic;
    }
    // Method to find the location of a given tile in the puzzle grid
    private int findLocation(Position sn, int tile) {
        for(int i =0;i<4;i++) {
            for(int j = 0; j <4;j++) {
                if(sn.puzzle.grid[i][j] == tile) {
                    return (i*4)+j;
                }
            }
        }
        return 0;
    }

}
