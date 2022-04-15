package puzzles.jam.solver;

import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.IOException;

public class Jam {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        } else {
            try {
                JamConfig config = new JamConfig(args[0]);
            } catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
    }
}