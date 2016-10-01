package ch.hepia.it.opt.tp1.P8;

import ch.hepia.it.opt.tp1.core.State;
import ch.hepia.it.opt.tp1.core.StateSpace;

import java.util.Arrays;
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

    public State solve() {
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
                    if(!space.getVisitedStates().containsKey(s.getHash())) {
                        space.getVisitedStates().put(s.getHash(), true);
                        State[] children = getChildren(s);
                        for (State c : children) {
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

    private State[] getChildren(State s) {
        List<State> children = new LinkedList<State>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (s.getTiles()[i][j] == 0) {
                    State left = moveLeft(s, i, j);
                    State right = moveRight(s, i, j);
                    State up = moveUp(s, i, j);
                    State down = moveDown(s, i, j);
                    if(left != null) {
                        children.add(left);
                    }
                    if(right != null) {
                        children.add(right);
                    }
                    if(up != null) {
                        children.add(up);
                    }
                    if(down != null) {
                        children.add(down);
                    }
                }
            }
        }
        return children.toArray(new State[children.size()]);
    }

    private State moveLeft(State s, int x, int y) {
        State left = null;
        if (x + 1 < 3) {
            int[][] ntiles = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i+1][j];
                    } else if(i == x+1 && j == y) {
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
                    if(i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i-1][j];
                    } else if(i == x-1 && j == y) {
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
                    if(i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i][j+1];
                    } else if(i == x && j == y+1) {
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
                    if(i == x && j == y) {
                        ntiles[i][j] = s.getTiles()[i][j-1];
                    } else if(i == x && j == y-1) {
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

    public static void main(String[] args) {
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
        State solution = p8.solve();
        Stack<State> path = new Stack<State>();

        if(solution != null) {
            State tmp = solution;
            int i = 0;
            while(tmp != null) {
                path.push(tmp);
                tmp = tmp.getParent();
                i++;
            }

            while(!path.isEmpty()) {
                System.out.println(path.pop().toString());
            }
            System.out.println("Solution found in "+(i-1)+" steps");

        } else {
            System.out.println("not found");
        }
    }
}
