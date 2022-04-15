package puzzles.jam.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// TODO: implement your JamConfig for the common solver

public class JamConfig implements Configuration{

    public static int rows;
    public static int columns;

    private HashMap<Coordinates, JamCar> carMap;

    private JamCar goalCar;


    public JamConfig(String filename) throws IOException{
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){

        }
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        for (JamCar car:carMap.){
            carMap.

        }
        return null;
    }

    @Override
    public boolean isSolution() {
        return goalCar.isSolved();
    }
}
