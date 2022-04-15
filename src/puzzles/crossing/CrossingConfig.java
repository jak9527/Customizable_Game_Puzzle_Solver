package puzzles.crossing;

import puzzles.common.solver.Configuration;
import puzzles.strings.StringsConfig;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Used by solver to hold the steps of the crossing puzzle
 * generates its neighbors and checks if all animals have crossed
 *
 * @author Kelly Showers kds1653
 */

public class CrossingConfig implements Configuration{
    // pups on left
    int pupsL;
    //pups on right
    int pupsR;
    //wolves on left
    int wolvesL;
    //wolves on right
    int wolvesR;
    // represents what side boat is on 0 is left 1 is right
    int boat;

    /**
     * Constructor used for neighbor configs generated
     * @param pupsL pups on left side
     * @param pupsR pups on right side
     * @param wolvesL wolves on left side
     * @param wolvesR wolves on right side
     * @param boat represents what side the boat is on
     */
    public CrossingConfig(int pupsL, int pupsR, int wolvesL, int wolvesR, int boat) {
        this.pupsL = pupsL;
        this.pupsR = pupsR;
        this.wolvesL = wolvesL;
        this.wolvesR = wolvesR;
        this.boat = boat;
    }

    /**
     * constructor for initial problem
     * @param pupsL pups on left
     * @param wolvesL wolves on left
     */
    public CrossingConfig(int pupsL, int wolvesL) {
        this.pupsL = pupsL;
        this.wolvesL = wolvesL;
        this.boat = 0;
    }

    @Override
    public boolean isSolution() {
        if (this.pupsL == 0 && this.wolvesL == 0){
            return true;
        }
        return false;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        if (boat == 0){
            if (pupsL > 1){
                neighbors.add(new CrossingConfig(pupsL - 2, pupsR + 2, wolvesL, wolvesR, 1));
            }
            if (pupsL > 0){
                neighbors.add(new CrossingConfig(pupsL - 1, pupsR + 1, wolvesL, wolvesR, 1));
            }
            if (wolvesL > 0){
                neighbors.add(new CrossingConfig(pupsL, pupsR, wolvesL - 1, wolvesR + 1, 1));
            }
        } else {
            if (pupsR > 1){
                neighbors.add(new CrossingConfig(pupsL + 2, pupsR - 2, wolvesL, wolvesR, 0));
            }
            if (pupsR > 0){
                neighbors.add(new CrossingConfig(pupsL + 1, pupsR - 1, wolvesL, wolvesR, 0));
            }
            if (wolvesR > 0){
                neighbors.add(new CrossingConfig(pupsL, pupsR, wolvesL + 1, wolvesR - 1, 0));
            }
        }
        return neighbors;
    }


    @Override
    public int hashCode() {
        return Integer.parseInt("" + pupsL + pupsR + wolvesL + wolvesR + boat);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CrossingConfig){
            CrossingConfig other = (CrossingConfig) o;
            if (this.pupsL == other.pupsL &&
                this.pupsR == other.pupsR &&
                this.wolvesL == other.wolvesL &&
                this.wolvesR == other.wolvesR &&
                this.boat == other.boat){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        if (boat == 0){
            return "(BOAT) left=[" + pupsL + ", " + wolvesL
            + "], right=[" + pupsR + ", " + wolvesR + "]";
        } else {
            return "       left=[" + pupsL + ", " + wolvesL
                    + "], right=[" + pupsR + ", " + wolvesR + "] (BOAT)";
        }
    }
}
