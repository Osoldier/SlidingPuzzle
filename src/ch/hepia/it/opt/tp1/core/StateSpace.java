package ch.hepia.it.opt.tp1.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Thomas on 01.10.16.
 */
public class StateSpace {

    private State initialState, finalState;
    private Queue<State> space;
    private HashMap<Integer, Boolean> visitedStates;

    public StateSpace(State initialState, State finalState) {
        this.initialState = initialState;
        this.finalState = finalState;
        this.space = new LinkedList<State>();
        this.space.add(initialState);
        this.visitedStates = new HashMap<Integer, Boolean>();
    }

    public State getInitialState() {
        return initialState;
    }

    public State getFinalState() {
        return finalState;
    }

    public Queue<State> getSpace() {
        return space;
    }

    public HashMap<Integer, Boolean> getVisitedStates() {
        return visitedStates;
    }
}
