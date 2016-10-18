package ch.hepia.it.opt.tp1.P8;

import ch.hepia.it.opt.tp1.core.heuristics.HeuristicFunction;
import ch.hepia.it.opt.tp1.core.State;
import ch.hepia.it.opt.tp1.core.StateSpace;
import ch.hepia.it.opt.tp1.core.heuristics.ManhattanDistFunction;
import ch.hepia.it.opt.tp1.core.heuristics.MisplacedTilesFunction;
import ch.hepia.it.opt.tp1.gui.MainView;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Thomas on 01.10.16.
 */
public class Puzzle8 {

    private StateSpace space;

    public Puzzle8(State initialState, State finalState) {
        this.space = new StateSpace(initialState, finalState);
    }

    /**
     * Solves the puzzle with blind research
     * @return the solved state or null
     */
    public State solveBlindSearch() {
        State solved = null;
        if (isSolved(space.getInitialState(), space.getFinalState())) {
            return space.getInitialState();
        } else {
            space.getSpace().add(space.getInitialState());

            while (solved == null) {
                if (space.getSpace().isEmpty()) {
                    return null;
                } else {
                    //poll() removes and return the first element
                    State s = space.getSpace().poll();
                    space.getVisitedStates().put(s.getHash(), true);
                    List<State> children = getChildren(s);
                    for (State c : children) {
                        //don't insert already visited states
                        if (!space.getVisitedStates().containsKey(c.getHash())) {
                            if (isSolved(c, space.getFinalState())) {
                                solved = c;
                            }
                            space.getSpace().add(c);
                        }
                    }
                }
            }
        }
        return solved;
    }

    /**
     * Solves the puzzle with an heuristic method
     * @param h the heuristic function to use
     * @return the solved state or null
     */
    public State solveHeuristic(HeuristicFunction h) {
        State solved = null;
        if (isSolved(space.getInitialState(), space.getFinalState())) {
            return space.getInitialState();
        } else {
            space.getSpace().add(space.getInitialState());
            while (solved == null) {
                if (space.getSpace().isEmpty()) {
                    return null;
                } else {
                    //poll() removes and return the first element, but we need a non-visited state
                    State s = null;
                    do {
                        s = space.getSpace().poll();
                    } while(space.getVisitedStates().containsKey(s.getHash()));
                    space.getVisitedStates().put(s.getHash(), true);
                    List<State> children = getChildren(s);
                    //lambda expression compare score of h(s)
                    children.sort((o1, o2) -> Integer.compare(h.getStateScore(o1, space.getFinalState()), h.getStateScore(o2, space.getFinalState())));
                    for (State c : children) {
                        if (isSolved(c, space.getFinalState())) {
                            solved = c;
                        }
                        space.getSpace().add(c);
                    }
                }
            }
        }
        return solved;
    }

    /**
     * Generate all children for a given state
     * @param s the parent state
     * @return List of children
     */
    private List<State> getChildren(State s) {
        List<State> children = new LinkedList<State>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
        if (x + 1 < 3) {
            int[][] ntiles = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
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
            int[][] ntiles = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
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
        if (y + 1 < 3) {
            int[][] ntiles = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
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
            int[][] ntiles = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
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

    //Args[0] template: 0 = solve blind
    //                  1 = solve h1()
    //                  2 = solve h2()
    //Args[1] template: 475316280 (initial state)
    //Args[2] template: 123456780 (final state)
    public static void main(String[] args) {
        //TODO use args
        int[][] initialTiles = {
                {4, 7, 5},
                {3, 1, 6},
                {2, 8, 0}
        };

        int[][] finalTiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        Puzzle8 p8 = new Puzzle8(new State(null, initialTiles), new State(null, finalTiles));

        State solution = p8.solveHeuristic(new MisplacedTilesFunction());
        //Solve and print
        Stack<State> path = new Stack<State>();
        if (solution != null) {
            State tmp = solution;
            int i = 0;
            while (tmp != null) {
                path.push(tmp);
                tmp = tmp.getParent();
                i++;
            }

            new MainView(path);
            System.out.println("Solution found in " + (i - 1) + " steps");

        } else {
            System.out.println("not found");
        }
    }
}
