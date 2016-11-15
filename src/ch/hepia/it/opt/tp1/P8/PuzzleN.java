package ch.hepia.it.opt.tp1.P8;

import ch.hepia.it.opt.tp1.core.heuristics.HeuristicFunction;
import ch.hepia.it.opt.tp1.core.State;
import ch.hepia.it.opt.tp1.core.StateSpace;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Thomas on 01.10.16.
 */
public class PuzzleN {

    private StateSpace space;
    private Queue<State> queue;
    private int SIZE = 4;
    private final int MAX_QUEUE = (int)100E5;

    public PuzzleN(State initialState, State finalState) {
        this.SIZE = initialState.getTiles().length;
        this.space = new StateSpace(initialState, finalState);
    }

    /**
     * Solves the puzzle with blind research
     * @return the solved state or null
     */
    public State solveBlindSearch() {
        int sc = 0;
        queue = new LinkedList<>();
        queue.add(space.getInitialState());
        State solved = null;
        if (isSolved(space.getInitialState(), space.getFinalState())) {
            return space.getInitialState();
        } else {
            queue.add(space.getInitialState());

            while (solved == null) {
                if (queue.isEmpty()) {
                    return null;
                } else {
                    //poll() removes and return the first element
                    State s = queue.poll();
                    if(queue.size() > MAX_QUEUE) {
                        solved = s;
                        System.out.println("Stopped search");
                    }
                    space.getVisitedStates().put(s.getHash(), true);
                    List<State> children = getChildren(s);
                    sc++;
                    for (State c : children) {
                        //don't insert already visited states
                        if (!space.getVisitedStates().containsKey(c.getHash())) {
                            if (isSolved(c, space.getFinalState())) {
                                solved = c;
                            }
                            queue.add(c);
                        }
                    }
                }
            }
        }
        System.out.println("States visited "+sc);
        return solved;
    }

    /**
     * Solves the puzzle with an heuristic method
     * @param h the heuristic function to use
     * @return the solved state or null
     */
    public State solveHeuristic(HeuristicFunction h) {
        int sc = 0;
        queue = new PriorityQueue<>((o1, o2) -> Integer.compare(h.getStateScore(o1, space.getFinalState()), h.getStateScore(o2, space.getFinalState())));
        queue.add(space.getInitialState());
        State solved = null;
        if (isSolved(space.getInitialState(), space.getFinalState())) {
            return space.getInitialState();
        } else {
            queue.add(space.getInitialState());
            while (solved == null) {
                if (queue.isEmpty()) {
                    return null;
                } else {
                    //poll() removes and return the first element, but we need a non-visited state
                    State s = null;
                    do {
                        s = queue.poll();
                    } while(space.getVisitedStates().containsKey(s.getHash()));
                    if(queue.size() > MAX_QUEUE) {
                        solved = s;
                        System.out.println("Stopped search");
                    }
                    space.getVisitedStates().put(s.getHash(), true);
                    sc++;
                    List<State> children = getChildren(s);
                    //lambda expression compare score of h(s)
                    for (State c : children) {
                        if (isSolved(c, space.getFinalState())) {
                            solved = c;
                        }
                        queue.add(c);
                    }
                }
            }
        }
        System.out.println("States visited "+sc);
        return solved;
    }

    /**
     * Generate all children for a given state
     * @param s the parent state
     * @return List of children
     */
    private List<State> getChildren(State s) {
        List<State> children = new LinkedList<State>();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (s.getTiles()[i][j] == 0) {
                    State left = moveLeft(s, i, j);
                    State right = moveRight(s, i, j);
                    State up = moveUp(s, i, j);
                    State down = moveDown(s, i, j);
                    if (left != null) {
                        children.add(left);
                    }
                    if (right != null) {
                        children.add(right);
                    }
                    if (up != null) {
                        children.add(up);
                    }
                    if (down != null) {
                        children.add(down);
                    }
                }
            }
        }
        return children;
    }

    private State moveLeft(State s, int x, int y) {
        State left = null;
        if (x + 1 < SIZE) {
            int[][] ntiles = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i + 1][j];
                    } else if (i == x + 1 && j == y) {
                        ntiles[i][j] = 0;
                    } else {
                        ntiles[i][j] = s.getTiles()[i][j];
                    }
                }
            }
            left = new State(s, ntiles);
        }
        return left;
    }

    private State moveRight(State s, int x, int y) {
        State right = null;
        if (x - 1 >= 0) {
            int[][] ntiles = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i - 1][j];
                    } else if (i == x - 1 && j == y) {
                        ntiles[i][j] = 0;
                    } else {
                        ntiles[i][j] = s.getTiles()[i][j];
                    }
                }
            }
            right = new State(s, ntiles);
        }
        return right;
    }

    private State moveUp(State s, int x, int y) {
        State up = null;
        if (y + 1 < SIZE) {
            int[][] ntiles = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i][j + 1];
                    } else if (i == x && j == y + 1) {
                        ntiles[i][j] = 0;
                    } else {
                        ntiles[i][j] = s.getTiles()[i][j];
                    }
                }
            }
            up = new State(s, ntiles);
        }
        return up;
    }

    private State moveDown(State s, int x, int y) {
        State down = null;
        if (y - 1 >= 0) {
            int[][] ntiles = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i][j - 1];
                    } else if (i == x && j == y - 1) {
                        ntiles[i][j] = 0;
                    } else {
                        ntiles[i][j] = s.getTiles()[i][j];
                    }
                }
            }
            down = new State(s, ntiles);
        }
        return down;
    }

    private boolean isSolved(State currentState, State finalState) {
        return currentState.getHash() == finalState.getHash();
    }
}
