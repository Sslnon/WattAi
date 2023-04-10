package stud.g01.problem.npuzzle;

import core.problem.Action;
import core.problem.State;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import stud.g01.solver.DPDB;

import java.util.*;

import static core.solver.algorithm.heuristic.HeuristicType.*;

public class Position extends State {

    private final int[][] present;
    private int x_0;
    private int y_0;
    private final int size;

    public static int[][][] zobrist;

    public int hash_code=0;



    public Position(int[][] present,int x_0,int y_0,int size) {
        this.present = present;
        this.x_0 = x_0;
        this.y_0 = y_0;
        this.size = size;
    }

    public Position(Position p) {
        this.size=p.size;
        this.present=new int[p.size][p.size];
        for(int i=0;i<p.size;++i)
        {
            for(int j=0;j<p.size;++j)
            {
                this.present[i][j]=p.present[i][j];
            }
        }
        this.x_0=p.x_0;
        this.y_0=p.y_0;
    }

    @Override
    public void draw() { System.out.println(this);}

    @Override
    public State next(Action action) {
        Direction dir = ((Move)action).getDirection();
        int[] offsets = Direction.offset(dir);

        //生成新状态所在的点
        Position nextState=new Position(this);
        //下一状态空白格的位置(i,j)
        int i=nextState.x_0+offsets[0];
        int j=nextState.y_0+offsets[1];
        //空白格state(blank[0],blank[1])与state(i,j)交换
        nextState.present[this.x_0][this.y_0]=nextState.present[i][j];
        nextState.present[i][j]=0;

        nextState.x_0=i;
        nextState.y_0=j;

        return nextState;
    }

    @Override
    public Iterable<? extends Action> actions() {
        Collection<Move> moves = new ArrayList<>();
        for (Direction d : Direction.DIRECTIONS)
            moves.add(new Move(d));
        return moves;
    }


    private static final EnumMap<HeuristicType, Predictor> predictors = new EnumMap<>(HeuristicType.class);
    static{
        predictors.put(MISPLACED,
                (state, goal) -> ((Position)state).misplaced());
        predictors.put(MANHATTAN,
                (state, goal) -> ((Position)state).manhattan((Position) goal));
        predictors.put(DISJOINT_PATTERN,
                (state, goal) -> ((Position)state).disjoint_pattern((Position) goal));
    }
    public static Predictor predictor(HeuristicType type){
        return predictors.get(type);
    }
    static final int[] tilePositions = {-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};
    static final int[] tileSubsets = {-1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
    private int disjoint_pattern(Position goal)
    {
        int[][] present = this.present;
        int index0 = 0, index1 = 0, index2 = 0;
        int[] tmp=convertTo1D(present);
//        System.out.println();
//        System.out.println(tmp.length);
//        System.out.println("tem1:"+tmp[1]);
//        System.out.println();
        for (int pos = 15; pos >= 0; --pos) {
//            System.out.println();
//            System.out.println("tem2:"+tmp[pos]);
//            System.out.println();
            final int tile = tmp[pos];
            if (tile != 0) {
                final int subsetNumber = tileSubsets[tile];
                switch (subsetNumber) {
                    case 2:
                        index2 |= pos << (tilePositions[tile] << 2);
                        break;
                    case 1:
                        index1 |= pos << (tilePositions[tile] << 2);
                        break;
                    default:
                        index0 |= pos << (tilePositions[tile] << 2);
                        break;
                }
            }
        }
        return DPDB.costTable_15_puzzle_0[index0] +
                DPDB.costTable_15_puzzle_1[index1] +
                DPDB.costTable_15_puzzle_2[index2];
        // TODO
    }
    private int misplaced() {
        int[][] present = this.present;
        int cnt=0;
        for (int i=0;i<size;++i){
            for (int j=0;j<size;++j){
//                if (present[i][j]==0) continue; // 0 don't require ? todo
                if(present[i][j]==0)
                {
                    cnt += 1;
                    continue;
                }
                int x = (present[i][j]-1)/size;
                int y = (present[i][j]-1)%size;
                if(x!=i||y!=j) cnt++;
            }
        }
        return cnt;
    }
    private int manhattan(Position goal) {
        int[][] present = this.present;
        int res=0;
        for (int i=0;i<size;++i){
            for (int j=0;j<size;++j){
                if (present[i][j]==0) continue; // 0 don't require ? todo
                int pos = present[i][j]-1;
                int x = pos/size;
                int y = pos%size;
                res += ((i-x)>0?i-x:x-i)+((j-y)>0?j-y:y-j);
            }
        }
        return res;

    }


    //2D->1D array
    public static int[] convertTo1D(int[][] arr){
        int numRows = arr.length;
        int numCols = arr[0].length;
        int[] flat = new int[numRows * numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                flat[i * numCols + j] = arr[i][j];
            }
        }
        return flat;
    }
    //1D->2D
    public static int[][] convertTo2D(int[] arr, int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = arr[(i * size) + j];
            }
        }
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof Position another) {
            //两个Position对象的行列坐标相同，则认为是相同的
            return hashCode()==another.hashCode();
        }
        return false;
    }
    @Override
    public int hashCode() {
        if(hash_code==0)
        {
            int hash = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (present[i][j] != 0)  {
                        hash ^= zobrist[i][j][present[i][j]];
                    }
                }
            }
            hash_code=hash;
        }
        return hash_code;
    }

    public int[][] getPresent() {
        return present;
    }
    public int getX_0() {
        return x_0;
    }
    public int getY_0() {
        return y_0;
    }
    public int getSize() {
        return size;
    }
    public void print()
    {
        for(int i=0;i<size;++i)
        {
            for(int j=0;j<size;++j)
            {
                System.out.print(this.present[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void init(int size, Position goal) {
        Random r = new Random();
        zobrist = new int[size][size][size * size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                for(int k = 0; k < size * size; k++) {
                    zobrist[i][j][k] = r.nextInt();
                }
            }
        }

    }


}
