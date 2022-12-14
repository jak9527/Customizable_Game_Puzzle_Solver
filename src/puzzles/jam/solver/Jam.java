package puzzles.jam.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class used to solve a jam puzzle using the Solver class
 * and prints the path and config numbers
 *
 * @author Kelly Showers kds1653
 */

public class Jam {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        } else {
            try {
                JamConfig startConfig = new JamConfig(args[0]);
                Solver jamSolver = new Solver();
                Collection<Configuration> jamPath= jamSolver.findPath(startConfig);
                System.out.println("File: " + args[0]);
                System.out.println(startConfig.toString());
                System.out.println("Total configs: " + jamSolver.getTotalConfigs());
                System.out.println("Unique configs: " + jamSolver.getUniqueConfigs());
                int i = 0;
                if (jamPath == null || jamPath.size() == 0){
                    System.out.println("No solution");
                } else {
                    for (Configuration config : jamPath) {
                        System.out.println("Step " + i + ": \n" + config.toString());
                        i++;
                    }
                }
            } catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
    }
}