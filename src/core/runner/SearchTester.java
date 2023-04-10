package core.runner;

import algs4.util.StopwatchCPU;
import core.problem.Problem;
import core.problem.ProblemType;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Node;
import core.solver.algorithm.heuristic.HeuristicType;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

import static core.solver.algorithm.heuristic.HeuristicType.*;
import static stud.g01.problem.npuzzle.GUI.createAndShowGUI;
//rex 10��45��
//test 2
/**
 * ��ѧ���������㷨���м���������
 * arg0: ������������      resources/pathfinding.txt
 * arg1: ��������         PATHFINDING
 * arg2: ��Ŀ���ĸ��׶�    1
 * arg3: ��С���Feeder   stud.runner.WalkerFeeder
 */
public final class SearchTester {
    //ͬѧ�ǿ��Ը����Լ�����Ҫ�������޸ġ�
    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, FileNotFoundException {

        //����args[3]�ṩ����������ѧ����EngineFeeder����
        EngineFeeder feeder = (EngineFeeder)
                Class.forName("stud.g01.runner.PuzzleFeeder")
                        .getDeclaredConstructor().newInstance();

    ////���ļ��������������������ı��� args[0]�����������ļ������·��
        Scanner scanner = new Scanner(new File("resources/problems for astar.txt"));
        ArrayList<String> problemLines = getProblemLines(scanner);

        //��ǰ��������� args[1]    Ѱ·���⣬�������̣�Ұ�˴���ʿ���ӵ�
        ProblemType type = ProblemType.valueOf("NPUZZLE");

        //����ڼ��׶� args[2]
        int step = Integer.parseInt("1");
        //�����������ͺ͵�ǰ�׶Σ���ȡ������������������
        //Ѱ·����ֱ�ʹ��Grid�����Euclid������Ϊ��������
        //NPuzzle����ĵ�һ�׶Σ�ʹ�ò���λ���ƺ������پ���
        ArrayList<HeuristicType> heuristics = getHeuristicTypes(type, step);

        //feeder�����������ı���ȡѰ·���������ʵ��
        ArrayList<Problem> problems = feeder.getProblems(problemLines);
        //����ʵ�����뵽ArrayList��

        for (HeuristicType heuristicType : heuristics) {
            //solveProblems�������ݲ�ͬ�����������ɲ�ͬ��searcher
            //��Feeder��ȡ��ʹ�õ��������棨AStar��IDAStar�ȣ���
            solveProblems(problems, feeder.getIdaStar(heuristicType), heuristicType);
            System.out.println(problems);
            System.out.println();
        }
        System.out.println("<---------------OK--------------->");
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
    }

    /**
     * �����������ͺ͵�ǰ�׶Σ���ȡ������������������
     * @param type
     * @param step
     * @return
     */
    private static ArrayList<HeuristicType> getHeuristicTypes(ProblemType type, int step) {
        //��⵱ǰ�����ڵ�ǰ�׶ο��õ��������������б�
        ArrayList<HeuristicType> heuristics = new ArrayList<>();
        //���ݲ�ͬ���������ͣ����в�ͬ�Ĳ���
        if (type == ProblemType.PATHFINDING) {
            heuristics.add(PF_GRID);
            heuristics.add(PF_EUCLID);
        }
        else {
            //NPuzzle����ĵ�һ�׶Σ�ʹ�ò���λ���ƺ������پ���
            if (step == 1) {
                heuristics.add(MANHATTAN); //todo ��ͬ�Ľ�
            }

            //NPuzzle����ĵ����׶Σ�ʹ��Disjoint Pattern
            else if (step == 3){
                heuristics.add(DISJOINT_PATTERN);
            }
        }
        return heuristics;
    }

    /**
     * ʹ�ø�����searcher��������⼯���е��������⣬ͬʱʹ�ý���������õĽ���м��
     * @param problems     ���⼯��
     * @param searcher     searcher
     * @param heuristicType ʹ����������������
     */
    private static void solveProblems(ArrayList<Problem> problems, AbstractSearcher searcher, HeuristicType heuristicType) {
        for (Problem problem : problems) {
            // ʹ��AStar�����������
            StopwatchCPU timer1 = new StopwatchCPU();
            Deque<Node> path = searcher.search(problem);
            double time1 = timer1.elapsedTime();

            if (path == null) {
                System.out.println("No Solution" + "��ִ����" + time1 + "s��"+
                        "��������" + searcher.nodesGenerated() + "����㣬" +
                        "��չ��" + searcher.nodesExpanded() + "�����");
                continue;
            }

//             ��·���Ŀ��ӻ�
            problem.showSolution(path);


            System.out.println("����������" + heuristicType + "����·�����ȣ�" + path.size() + "��ִ����" + time1 + "s��" +
                    "��������" + searcher.nodesGenerated() + "����㣬" +
                    "��չ��" + searcher.nodesExpanded() + "�����");
        }
    }

    /**
     * ���ļ���������ʵ�����ַ����������ַ���������
     * @param scanner
     * @return
     */
    public static ArrayList<String> getProblemLines(Scanner scanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()){
            lines.add(scanner.nextLine());
        }
        return lines;
    }
}