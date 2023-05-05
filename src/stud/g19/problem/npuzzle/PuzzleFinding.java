package stud.g19.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.solver.queue.Node;

import java.util.Deque;

public class PuzzleFinding extends Problem {

    public PuzzleFinding(Position initialState, Position goal, int size) {
        super(initialState,goal,size);
    }

    @Override
    public boolean solvable() {

        Position.init(size, (Position) goal);

        int space = super.size * super.size;
        int[] origin = new int[space];
        Position iS=(Position)initialState;
        int[][] present =iS.getPresent();

        for(int i = 0;i<size;++i)
        {
            System.arraycopy(present[i], 0, origin, i * size, size);
        }

        //inversion counter
        int inv = 0;
        for(int i = 0 ; i < origin.length ; ++i){
            if(origin[i] == 0) continue;
            for(int j = i+1 ; j < origin.length ; ++j){
                //wrong precedence, count an inversion
                if(origin[j] != 0 && origin[i] > origin[j]) ++inv;
            }
        }
        //the board is solvable if the number of inversions is even
        if(size==3)
            return  inv%2==0;
        else{
            int emptyRow = size - ((Position)initialState).getX_0();
            return (inv+emptyRow)%2 == 1;
        }
    }

    @Override
    public int stepCost(State state, Action action) {
        return 1;
    }

    @Override
    public boolean applicable(State state, Action action) {
        int[] offsets = Direction.offset(((Move)action).getDirection());
        int x_new = ((Position)state).getX_0() + offsets[0];
        int y_new = ((Position)state).getY_0() + offsets[1];
        return x_new >= 0 && x_new < size && y_new >= 0 && y_new < size;
    }

    @Override
    public void showSolution(Deque<Node> path) {
//        GUI gui = new GUI();
//        gui.setVisible(true);
        Position g=(Position)goal;
        for (Node node : path) {
            Position p = (Position) node.getParent().getState();

            int[][] present =p.getPresent();


            for(int i=0;i<size;++i){
                for(int j=0;j<size;++j)
                {
                    System.out.print(present[i][j]+" ");
                }
                System.out.println();
            }

            System.out.println("--------------------------------");

        }
        g.print();
        System.out.println("OK");
    }


}
