package stud.g19.DPDB;
/*
 * Search node class for solving the 15-puzzle
 */

import core.problem.Action;
import core.problem.State;

class Position extends State
{
    // Instance variables for the class Position.
    public Puzzle puzzle;           // A reference to a Puzzle object.
    public int pathCost;            // The cost of the path from the initial state to the current state.
    public int estimate;            // The estimated cost from the current state to the goal state.
    public Position parent;         // The parent Position of the current Position.
    public int action;              // The action taken to reach the current Position.

    // Constructor for creating a new Position object.
    public Position(Puzzle puz, int p, int e, Position par, int a)
    {
        puzzle = new Puzzle(puz);
        pathCost = p;
        estimate = e;
        parent = par;
        action = a;
    }

    @Override
    public void draw() {

    }

    @Override
    public State next(Action action) {
        return null;
    }

    @Override
    public Iterable<? extends Action> actions() {
        return null;
    }
}