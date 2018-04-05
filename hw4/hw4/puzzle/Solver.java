package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import edu.princeton.cs.algs4.Stack;
import java.util.List;

//@source url * http://joshh.ug/neighbors.html

public class Solver {
    private MinPQ vals = new MinPQ();
    private int move;
    private SearchNode min;
    private WorldState start;
    private Stack StackSolution = new Stack();
    private List ListSolution = new LinkedList();

    public Solver(WorldState initial) {
        start = initial;
        SearchNode x = new SearchNode(initial);
        ListSolution.add(x);
        vals.insert(x);
        solveHelper(vals);
    }

    public int moves() {
        return move;
    }

    public Iterable<WorldState> solution() {
        return StackSolution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private WorldState prev, curr;
        private SearchNode parent;
        private int goaltodistance;
        private SearchNode(WorldState initial) {
            curr = initial;
            moves = 0;
            prev = null;
            parent = null;
            goaltodistance = -1;
        }

        @Override
        public int compareTo(SearchNode s) {
            int thisDistance = (this.moves + this.distanceEstimate());
            int sDistance = (s.moves + s.distanceEstimate());
            return thisDistance - sDistance;
        }
        private int distanceEstimate() {
            if (goaltodistance != -1) {
                return goaltodistance;
            }
            goaltodistance = curr.estimatedDistanceToGoal();
            return goaltodistance;
        }
    }

    //modifies solvehelper node
    private void neighborHelper(MinPQ val, SearchNode node) {
        node.moves = min.moves + 1;
        node.prev = min.curr;
        node.parent = min;
        val.insert(node);
    }

    //adds to both solution adt's the min
    private void solveHelper(MinPQ val) {
        SearchNode node;
        min = (SearchNode) val.delMin();
        while (!min.curr.isGoal()) {
            for (WorldState neighbor: min.curr.neighbors()) {
                if (min.prev == null) {
                    node = new SearchNode(neighbor);
                    neighborHelper(val, node);
                } else if (!min.prev.equals(neighbor)) {
                    node = new SearchNode(neighbor);
                    neighborHelper(val, node);
                }
            }
            min = (SearchNode) vals.delMin();
            ListSolution.add(min);
        }
        while (!min.curr.equals(start)) {
            move += 1;
            StackSolution.push(min.curr);
            int index = ListSolution.indexOf(min.parent);
            if (index < 0) {
                break;
            }
            min = (SearchNode) ListSolution.get(index);
        }
        StackSolution.push(min.curr);
    }
}
