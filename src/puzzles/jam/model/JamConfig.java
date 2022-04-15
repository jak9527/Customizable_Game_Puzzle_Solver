package puzzles.jam.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// TODO: implement your JamConfig for the common solver

public class JamConfig {


    public JamConfig(String filename) throws IOException{
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){

        }
    }
}
