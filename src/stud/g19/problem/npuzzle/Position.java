package stud.g19.problem.npuzzle;

import core.problem.Action;
import core.problem.State;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;

import java.io.*;
import java.util.*;

import static core.solver.algorithm.heuristic.HeuristicType.*;

public class Position extends State {
    static final int[] tilePositions = {-1, 0, 0, 1, 2, 1, 2, 0, 1, 3, 4, 2, 3, 5, 4, 5};
    static final int[] tileSubsets = {-1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
    public static final int[] costTable_15_puzzle_0 = new int[4096],
            costTable_15_puzzle_1 = new int[16777216],
            costTable_15_puzzle_2 = new int[16777216];
    private static final String file1="resources/663_0.txt";
    private static final String file2="resources/663_1.txt";
    private static final String file3="resources/663_2.txt";
    private final int[][] present;
    private int x_0;
    private int y_0;
    private final int size;

    public static int[][][] zobrist;

    public int hash_code=0;
    static {
        File firstFile=new File(file1);
        File secondFile=new File(file2);
        File thirdFile=new File(file3);
        try {
            BufferedReader br1=new BufferedReader(new FileReader(firstFile));
            BufferedReader br2=new BufferedReader(new FileReader(secondFile));
            BufferedReader br3=new BufferedReader(new FileReader(thirdFile));
            String line=null;
            int tmp;
            int count=0;
            while((line=br1.readLine())!=null)
            {
                tmp=Integer.parseInt(line);
                costTable_15_puzzle_0[count++]= tmp;
            }
            count=0;
            while((line=br2.readLine())!=null)
            {
                tmp=Integer.parseInt(line);
                costTable_15_puzzle_1[count++]= tmp;
            }
            count=0;
            while((line=br3.readLine())!=null)
            {
                tmp=Integer.parseInt(line);
                costTable_15_puzzle_2[count++]= tmp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

        //������״̬���ڵĵ�
        Position nextState=new Position(this);
        //��һ״̬�հ׸��λ��(i,j)
        int i=nextState.x_0+offsets[0];
        int j=nextState.y_0+offsets[1];
        //�հ׸�state(blank[0],blank[1])��state(i,j)����
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
    private int disjoint_pattern(Position goal)  {
        int num=0;
        num=getH(convert(this));
        return num;
    }
    public static int getH(int [] tmp) {

        int index0 = 0, index1 = 0, index2 = 0;
        for (int pos = 15; pos >= 0; --pos) {
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
        return costTable_15_puzzle_0[index0] +
                costTable_15_puzzle_1[index1] +
                costTable_15_puzzle_2[index2];
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
    public int[] convert(Position position)
    {
        int size=position.getSize();
        int[] tmp=new int[size*size];
        int[][] state=position.getPresent();
        for(int i=0;i<size*size;i++)
        {
            tmp[i]=state[i/size][i%size];
        }
        return tmp;
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
            //����Position���������������ͬ������Ϊ����ͬ��
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
