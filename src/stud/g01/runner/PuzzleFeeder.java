package stud.g01.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.EvaluationType;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.g01.problem.npuzzle.Position;
import stud.g01.problem.npuzzle.PuzzleFinding;
import stud.g01.queue.ListFrontier;


import java.util.ArrayList;

public class PuzzleFeeder extends EngineFeeder {
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {
        //�����ģ, ����ĵ�һ��

        /* ����������� */
        ArrayList<Problem> problems = new ArrayList<>();
        int lineNo = 0;
        //while (lineNo < 10){//todo before change
        while (lineNo < 1){//todo
            int size = Integer.parseInt(String.valueOf(problemLines.get(lineNo).charAt(0)));
            //3,4
            //��������ʵ��
            PuzzleFinding problem = getPuzzleFinding(problemLines.get(lineNo), size);

            //��ӵ������б�
            problems.add(problem);
            lineNo++;
        } //�����������

        return problems;

    }
    private PuzzleFinding getPuzzleFinding(String problemLine, int size)
    {
        String[] cells = problemLine.split(" ");;
        int[][] init = new int[size][size];
        int[][] goal = new int[size][size];

        int x_0 = 0,y_0 =0;


        for(int i=1;i<=size*size;++i)
        {
            init[(i-1)/size][(i-1)%size]= Integer.parseInt(cells[i]);
            if(Integer.parseInt(cells[i])==0){
                x_0=(i-1)/size;
                y_0=(i-1)%size;
            }
        }
        for(int i=size*size+1;i<=2*size*size;++i)
        {
            goal[(i-1-size*size)/size][(i-1-size*size)%size]= Integer.parseInt(cells[i]);
        }
        System.out.println(x_0+" "+y_0);
        for(int i=0;i<size;++i)
        {
            for(int j=0;j<size;++j)
                System.out.print(init[i][j]+" ");
            System.out.println();
        }

        Position initialState = new Position(init,x_0,y_0,size);

        Position goalState = new Position(goal,size-1,size-1,size);

        return new PuzzleFinding(initialState,goalState,size);
    }

    @Override
    public Frontier getFrontier(EvaluationType type) {
        return new ListFrontier(Node.evaluator(type));
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {return Position.predictor(type);}


}
