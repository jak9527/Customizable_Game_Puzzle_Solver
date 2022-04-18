package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.crossing.CrossingConfig;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.Collection;

public class Hoppers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        Solver solver = new Solver();
        try {
            System.out.println("into try");
            Collection<Configuration> path = solver.findPath(
                    new HoppersConfig(args[0]));
            System.out.println("path made");
            System.out.println(path);
            if(path == null){
                System.out.println("No solution");
            }
            else{
                for(Configuration c : path){
                    System.out.println(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
