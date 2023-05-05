package stud.g19.DPDB;
/*
 * Search node class for solving the 15-puzzle
 */

class Position implements Comparable<Position>
{
    public Puzzle puzzle;			// the puzzle state
    public int pathCost;			// current path cost to this state
    public int estimate;			// estimate of # of moves to goal state (used only by informed search methods)
    public Position parent;		// state which preceded this state
    public int action;				// action which produced this state

    public Position(Puzzle puz, int p, int e, Position par, int a)
    {
        puzzle = new Puzzle(puz);
        pathCost = p;
        estimate = e;
        parent = par;
        action = a;
    }

    public int compareTo(Position other)
    {
        return pathCost + estimate - (other.pathCost + other.estimate);
    }


}