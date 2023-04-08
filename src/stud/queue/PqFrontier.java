package stud.queue;

import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PqFrontier extends PriorityQueue<Node> implements Frontier {
    private final Comparator<Node> evaluator;
    public PqFrontier(Comparator<Node> evaluator) {
        this.evaluator = evaluator;
    }


    @Override
    public boolean contains(Node node) {
        return getNode(node.getState()) != null;
    }

    @Override
    public boolean offer(Node node) {
        return super.offer(node);
    }

    private Node getNode(State state) {
        for (var node : this){
            if (node.getState().equals(state)){
                return node;
            }
        }
        return null;
    }

}
