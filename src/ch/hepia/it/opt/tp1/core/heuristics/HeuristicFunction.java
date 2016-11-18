package ch.hepia.it.opt.tp1.core.heuristics;

import ch.hepia.it.opt.tp1.core.State;

/**
 * Created by Thomas on 16.10.16.
 */
public interface HeuristicFunction {

    /**
     * Compute the score of a given state
     * @param n the state evalute
     * @param finalState the state to reach
     * @return the score
     */
    int getStateScore(State n, State finalState);
}
