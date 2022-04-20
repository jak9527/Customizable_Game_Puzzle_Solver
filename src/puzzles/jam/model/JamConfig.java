package puzzles.jam.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// TODO: implement your JamConfig for the common solver

public class JamConfig implements Configuration{

    public static int rows;
    public static int columns;

    private HashMap<Coordinates, JamCar> carMap;

    private JamCar goalCar;


    public JamConfig(String filename) throws IOException{
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String[] dimensions = in.readLine().split(" ");
        }
    }

    public JamConfig(HashMap<Coordinates, JamCar> carMap, JamCar goalCar){
        this.carMap = carMap;
        this.goalCar = goalCar;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        HashSet<JamCar> cars = new HashSet<>();
        cars.addAll(carMap.values());
        for (JamCar car:cars){
            int carEndCoordCol = car.getEndCoord().col();
            int carEndCoordRow = car.getEndCoord().row();
            int carStartCoordCol = car.getStartCoord().col();
            int carStartCoordRow = car.getStartCoord().row();
            if (!car.isHorizontal()) {
                if (!carMap.containsKey(new Coordinates(carEndCoordRow + 1, carEndCoordCol))
                        && (carEndCoordRow + 1) <= rows) {
                    HashMap<Coordinates, JamCar> newCarMap = (HashMap<Coordinates, JamCar>) carMap.clone();
                    newCarMap.remove(car.getStartCoord());
                    JamCar currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow + 1, carStartCoordCol),
                            new Coordinates(carEndCoordRow + 1, carEndCoordCol));
                    newCarMap.put(new Coordinates(carEndCoordRow + 1, carEndCoordCol), currentCar);
                    for (int i = carEndCoordRow; i > carStartCoordRow; i--){
                        newCarMap.replace(new Coordinates(i, carStartCoordCol), currentCar);
                    }
                    if (currentCar.getCarLtr() == 'X'){
                        neighbors.add(new JamConfig(newCarMap, currentCar));
                    } else {
                        neighbors.add(new JamConfig(newCarMap, goalCar));
                    }
                }
                if (!carMap.containsKey(new Coordinates(carStartCoordRow - 1, carStartCoordCol))
                        && (carStartCoordRow - 1) >= rows) {
                    HashMap<Coordinates, JamCar> newCarMap = (HashMap<Coordinates, JamCar>) carMap.clone();
                    newCarMap.remove(car.getEndCoord());
                    JamCar currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow - 1, carStartCoordCol),
                            new Coordinates(carEndCoordRow - 1, carEndCoordCol));
                    newCarMap.put(new Coordinates(carStartCoordRow - 1, carEndCoordCol), currentCar);
                    for (int i = carStartCoordRow; i < carEndCoordRow; i++){
                        newCarMap.replace(new Coordinates(i, carStartCoordCol), currentCar);
                    }
                    if (currentCar.getCarLtr() == 'X'){
                        neighbors.add(new JamConfig(newCarMap, currentCar));
                    } else {
                        neighbors.add(new JamConfig(newCarMap, goalCar));
                    }
                }
            } else {
                    if (!carMap.containsKey(new Coordinates(carEndCoordRow, carEndCoordCol + 1))
                            && (carEndCoordCol + 1 <= columns)) {
                        HashMap<Coordinates, JamCar> newCarMap = (HashMap<Coordinates, JamCar>) carMap.clone();
                        newCarMap.remove(car.getStartCoord());
                        JamCar currentCar = new JamCar(car.getCarLtr(),
                                new Coordinates(carStartCoordRow, carStartCoordCol + 1),
                                new Coordinates(carEndCoordRow, carEndCoordCol + 1));
                        newCarMap.put(new Coordinates(carEndCoordRow, carEndCoordCol + 1), currentCar);
                        for (int i = carEndCoordCol; i > carStartCoordCol; i--){
                            newCarMap.replace(new Coordinates(carStartCoordRow, i), currentCar);
                        }
                        if (currentCar.getCarLtr() == 'X'){
                            neighbors.add(new JamConfig(newCarMap, currentCar));
                        } else {
                            neighbors.add(new JamConfig(newCarMap, goalCar));
                        }
                    }
                if (!carMap.containsKey(new Coordinates(carStartCoordRow, carStartCoordCol - 1))
                        && (carStartCoordCol - 1 >= columns)) {
                    HashMap<Coordinates, JamCar> newCarMap = (HashMap<Coordinates, JamCar>) carMap.clone();
                    newCarMap.remove(car.getStartCoord());
                    JamCar currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow, carStartCoordCol - 1),
                            new Coordinates(carEndCoordRow, carEndCoordCol - 1));
                    newCarMap.put(new Coordinates(carEndCoordRow, carEndCoordCol - 1), currentCar);
                    for (int i = carStartCoordCol; i < carEndCoordCol; i++){
                        newCarMap.replace(new Coordinates(carStartCoordRow, i), currentCar);
                    }
                    if (currentCar.getCarLtr() == 'X'){
                        neighbors.add(new JamConfig(newCarMap, currentCar));
                    } else {
                        neighbors.add(new JamConfig(newCarMap, goalCar));
                    }
                }
            }

        }
        return neighbors;
    }

    @Override
    public boolean isSolution() {
        return goalCar.isSolved();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JamConfig jamConfig = (JamConfig) o;
        return Objects.equals(carMap, jamConfig.carMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carMap);
    }
}
