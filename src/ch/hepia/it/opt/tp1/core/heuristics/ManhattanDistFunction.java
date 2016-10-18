package ch.hepia.it.opt.tp1.core.heuristics;

import ch.hepia.it.opt.tp1.core.State;

/**
 * Created by Thomas on 16.10.16.
 */
public class ManhattanDistFunction implements HeuristicFunction {

    @Override
    public int getStateScore(State n, State finalState) {
        int cost = 0;
        for (int i = 0; i < n.getTiles().length; i++) {
            for (int j = 0; j < n.getTiles()[i].length; j++) {
                if(n.getTiles()[i][j] != 0) {
                    int[] fp = getFinalPosition(n.getTiles()[i][j], finalState);
                    cost += Math.abs(fp[0]-i);
                    cost += Math.abs(fp[1]-j);
                }
            }
        }
        return cost;
    }

    /**
     * Gets the position of a given tile the finalState
     * @param i The tile to look for
     * @param finalState the final state
     * @return array of int with at 0 the x coord and at 1 the y coord
     */
    private int[] getFinalPosition(int i, State finalState) {
        for (int j = 0; j < finalState.getTiles().length; j++) {
            for (int k = 0; k < finalState.getTiles()[j].length; k++) {
                 if(finalState.getTiles()[j][k] == i) {
                     return new int[] {j, k};
                 }
            }
        }
        throw new RuntimeException("Couldn't find tile "+i+" in the final state");
    }
}
