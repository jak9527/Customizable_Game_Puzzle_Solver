package puzzles.common.solver;

import java.util.Collection;

/**
 * Interface with the requirements for use with the solver
 */
public interface Configuration {
    /**
     * @return boolean representing if the current config is the solution
     */
    boolean isSolution();

    /**
     * @return the neighbors of the current config
     */
    Collection<Configuration> getNeighbors();
    @Override
    boolean equals(Object other);
    @Override
    int hashCode();
    @Override
    String toString();
}
