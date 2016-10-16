package ch.hepia.it.opt.tp1.core.heuristics;

import ch.hepia.it.opt.tp1.core.State;

/**
 * Created by Thomas on 16.10.16.
 */
public class MisplacedTilesFunction implements HeuristicFunction {

    @Override
    public int getStateScore(State n, State finalState) {
        int cost = 0;
        for (int i = 0; i < n.getTiles().length; i++) {
            for (int j = 0; j < n.getTiles()[i].length; j++) {
                if(n.getTiles()[i][j] != finalState.getTiles()[i][j]) {
                    cost++;
                }
            }
        }
        return cost;
    }
}
