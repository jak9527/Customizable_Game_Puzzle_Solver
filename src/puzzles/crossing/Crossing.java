package puzzles.crossing;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.strings.StringsConfig;

import java.util.Collection;

/**
 * Takes a number of pups and wolves from args representing
 * a crossing puzzle and solves it using the Solver printing the path
 */

public class Crossing {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Crossing pups wolves"));
        } else {
            // TODO
            System.out.println("Pups: " + args[0] +", Wolves: " + args[1]);
            Solver jacobCringe = new Solver();
            Collection<Configuration> path = jacobCringe.findPath(
                    new CrossingConfig(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
            int i = 0;
            System.out.println("Total configs: " + jacobCringe.getTotalConfigs());
            System.out.println("Unique configs: " + jacobCringe.getUniqueConfigs());
            if (path == null){
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
