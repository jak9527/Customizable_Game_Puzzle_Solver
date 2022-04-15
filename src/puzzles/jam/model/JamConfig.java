package puzzles.jam.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// TODO: implement your JamConfig for the common solver

public class JamConfig {

    private char[][] grid;

    private HashMap<Character, JamCar> carMap;


    public JamConfig(String filename) throws IOException{
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){

        }
    }
}
