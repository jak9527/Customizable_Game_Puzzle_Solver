package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Takes a start and end string from args and calls upon solver to find the shortest
 * path between the two and prints out the steps
 *
 * @author Kelly Showers kds1653
 */

public class Strings {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            // TODO
            System.out.println("Start: " + args[0] +", End: " + args[1]);
            Solver jacobCringe = new Solver();
            Collection<Configuration> path = jacobCringe.findPath(new StringsConfig(args[0], args[1]));
            System.out.println("Total configs: " + jacobCringe.getTotalConfigs());
            System.out.println("Unique configs: " + jacobCringe.getUniqueConfigs());
            int i = 0;
            if (path == null || path.size() == 0){
                System.out.println("No solution");
            } else {
                for (Configuration config : path) {
                    System.out.println("Step " + i + ": " + config.toString());
                    i++;
                }
            }
        }
    }
}
