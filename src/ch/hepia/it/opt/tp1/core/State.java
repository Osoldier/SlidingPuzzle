package ch.hepia.it.opt.tp1.core;

import java.util.Arrays;

/**
 * Created by Thomas on 01.10.16.
 */
public class State {

    //used to track the path to the solution
    private State parent;
    //string version of the state
    private String name;
    private int[][] tiles;

    /**
     * Creates a new state
     * @param parent the parent of the state (can be null)
     * @param tiles tiles of the state
     */
    public State(State parent, int[][] tiles) {
        this.parent = parent;
        this.tiles = tiles;
        this.name = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.name += String.valueOf(tiles[i][j]);
            }
        }
    }

    /**
     * Get the unique hash of this state
     * @return integer hash of the state
     */
    public int getHash() {
        return this.name.hashCode();
    }

    public State getParent() {
        return parent;
    }

    public int[][] getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        String s = "";
        s += ("State: {\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                s += "\t"+tiles[i][j]+" ";
            }
            s += "\n";
        }
        s += ("}");
        return s;
    }
}
