
package stud.g01.solver;

import core.problem.Problem;
import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.g01.problem.npuzzle.Position;

import java.util.Deque;
import java.util.Stack;

public class IdAStar extends AbstractSearcher {
    private final Predictor predictor;
    private final Stack<Node> openStack;
    public IdAStar(Predictor predictor) {
        super();
        this.predictor = predictor;
        openStack = new Stack<Node>();
        //closeStack = new HashMap<Integer, Integer>();
    }

    @Override
    public Deque<Node> search(Problem problem) {
        if (!problem.solvable()){
            return null;
        }
        //获取根节点
        openStack.clear();
        //closeStack.clear();
        Node root = problem.root(predictor);
        int cutoff = root.evaluation();

        //最大迭代深度
        int maxIteratorDepth = 70;
        while (cutoff < maxIteratorDepth) {
            openStack.push(root);
            //下一轮迭代的裁剪阈值
            int newCutoff = cutoff;
            //当栈未空时继续，执行带裁剪值的深度优先搜索
            //统计扩展结点数
            while (!openStack.empty()) {
                Node node = openStack.pop();
                State tmps = node.getState();
                Position d =(Position)tmps;
                d.print();
                //更新裁剪值为未被探索节点中最小的评估值
                if (problem.goal(node.getState())) {
                    return generatePath(node);
                }
                //当小于等于裁剪值时，继续向深处搜索
                for (Node child : problem.childNodes(node, predictor)) {

                    //剪枝，防止节点探索回到父节点
                    if (child.evaluation() <= cutoff) {
                        if (node.getParent() == null || !node.getParent().equals(child)) {
                            openStack.push(child);
                        }
                    } else {
                        //记录大于当前cutoff的最小值
                        newCutoff = (newCutoff > cutoff) ? (Math.min(child.evaluation(), newCutoff)) : child.evaluation();
                        //System.out.println("cutoff="+cutoff+" newcutoff="+newCutoff+" child.f="+child.evaluation());
                    }
                }
            }
            cutoff = newCutoff;
        }
        return null;
    }
}
