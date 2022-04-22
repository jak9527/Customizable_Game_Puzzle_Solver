package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.crossing.CrossingConfig;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Run a particular puzzle and print the path
 * @author Jacob Karvelis
 */
public class Hoppers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        Solver solver = new Solver();
        try {
            HoppersConfig init = new HoppersConfig(args[0]);
            Collection<Configuration> path = solver.findPath(
                    init);
//            System.out.println("path made");
            System.out.println(args[0]);
            System.out.println(init);
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());
            if(path.size() == 0){
                System.out.println("No solution");
            }
            else{
                int i = 0;
                for(Configuration c : path){
                    System.out.println("Step " + i + ":");
                    System.out.println(c);
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
