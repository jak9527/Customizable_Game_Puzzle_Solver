package puzzles.common.solver;

import java.util.*;

/**
 * The Solver that works for any puzzle with a working configuration
 * @author Jacob Karvelis
 */
public class Solver {
    /** The counter for the number of unique configs generated when solving */
    private int uniqueConfigs = 0;
    /** The counter for the number of configs generated when solving */
    private int totalConfigs = 0;

    /**
     * the actual solving function that finds the path of steps needed to solve a puzzle
     * @param start: The starting config
     * @return the path generated by the path constructor
     */
    public Collection< Configuration > findPath(
            Configuration start) {
        Configuration startConfig;
        startConfig = start;
        Configuration finishConfig = null;
        this.totalConfigs+=1;

        // prime the queue with the starting node
        List<Configuration> queue = new LinkedList<>();
        queue.add(startConfig);

        // construct the predecessors data structure
        Map<Configuration, Configuration> predecessors = new HashMap<>();
        // put the starting node in, and just assign itself as predecessor
        predecessors.put(startConfig, startConfig);
        uniqueConfigs += 1;

        // loop until either the finish node is found, or the queue is empty
        // (no path)
        while (!queue.isEmpty()) {
            // the next node to process is at the front of the queue
            Configuration current = queue.remove(0);
            if (current.isSolution()) {
                finishConfig = current;
                break;
            }
            // loop over all neighbors of current
            for (Configuration nbr : current.getNeighbors()) {
                this.totalConfigs++;
                // process unvisited neighbors
                if (!predecessors.containsKey(nbr)) {
                    this.uniqueConfigs+=1;
                    predecessors.put(nbr, current);
                    queue.add(nbr);
                }
            }
        }

        // construct the path from the predecessor map and return the
        // sequence from start to finish nodes
        return constructPath(predecessors, startConfig, finishConfig);
    }

    /**
     * Method to return a path from the starting to finishing node.
     *
     * @param predecessors Map used to reconstruct the path
     * @param startConfig starting node
     * @param finishConfig finishing node
     * @return a list containing the sequence of nodes comprising the path.
     * An empty list if no path exists.
     */
    private List<Configuration> constructPath(Map<Configuration,Configuration> predecessors,
                                              Configuration startConfig, Configuration finishConfig) {
        // use predecessors to work backwards from finish to start,
        // all the while dumping everything into a linked list
        List<Configuration> path = new LinkedList<>();

        if (!(finishConfig==null)) {
            Configuration currNode = finishConfig;
            while (currNode != startConfig) {
                path.add(0, currNode);
                currNode = predecessors.get(currNode);
            }
            path.add(0, startConfig);
        }
        return path;
    }

    public int getTotalConfigs() {
        return totalConfigs;
    }

    public int getUniqueConfigs(){
        return uniqueConfigs;
    }
}
