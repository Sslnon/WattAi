package stud.g19.queue;

import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PqFrontier extends PriorityQueue<Node> implements Frontier {

    private final Comparator<Node> evaluator;

    public PqFrontier(Comparator<Node> evaluator) {this.evaluator = evaluator;}


    @Override
    public boolean contains(Node node) {
        return false;
    }

    @Override
    public boolean offer(Node node) {
        return false;
    }
}
