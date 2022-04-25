package puzzles.jam.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class that represents a given board state
 * in a jam puzzle
 *
 * @author Kelly Showers kds1653
 */

public class JamConfig implements Configuration{

    // dimensions of the current puzzle
    public static int rows;
    public static int columns;

    //Hashmap to store what coordinates on the grid are occupied
    private HashMap<Coordinates, JamCar> carMap;

    //Stores the car that needs to cross
    private JamCar goalCar;


    /**
     * initial constructor that makes a new board from a file
     * @param filename file representing a puzzle
     */
    public JamConfig(String filename) throws IOException{
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String[] dimensions = in.readLine().split(" ");
            rows = Integer.parseInt(dimensions[0]);
            columns = Integer.parseInt(dimensions[1]);
            int numCars = Integer.parseInt(in.readLine());
            HashMap<Coordinates, JamCar> cars = new HashMap<>();
            JamCar currentCar = null;
            for (int i = 0; i < numCars; i++){
                String[] carCoords = in.readLine().split(" ");
                currentCar = (new JamCar(carCoords[0].charAt(0),
                        new Coordinates(carCoords[1], carCoords[2]),
                        new Coordinates(carCoords[3], carCoords[4])));
                if (currentCar.isHorizontal()){
                    for (int j = Integer.parseInt(carCoords[2]); j <= Integer.parseInt(carCoords [4]); j++){
                        cars.put(new Coordinates(Integer.parseInt(carCoords[1]), j), currentCar);
                    }
                } else {
                    for (int j = Integer.parseInt(carCoords[1]); j <= Integer.parseInt(carCoords [3]); j++){
                        cars.put(new Coordinates(j, Integer.parseInt(carCoords[2])), currentCar);
                    }

                }
            }
            carMap = cars;
            goalCar = currentCar;

        }
    }

    /**
     * Constructor used to update the board
     * @param carMap stores the board
     * @param goalCar stores the solution car
     */
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
                        && (carEndCoordRow + 1) < rows) {
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
                        && (carStartCoordRow - 1) >= 0) {
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
                            && (carEndCoordCol + 1 < columns)) {
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
                        && (carStartCoordCol - 1 >= 0)) {
                    HashMap<Coordinates, JamCar> newCarMap = (HashMap<Coordinates, JamCar>) carMap.clone();
                    newCarMap.remove(car.getEndCoord());
                    JamCar currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow, carStartCoordCol - 1),
                            new Coordinates(carEndCoordRow, carEndCoordCol - 1));
                    newCarMap.put(new Coordinates(carEndCoordRow, carStartCoordCol - 1), currentCar);
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

    /** checks if car is at a coordinate */
    public boolean isCarAt(Coordinates coord){
        return carMap.containsKey(coord);
    }

    /** gets the car at a coordinate */
    public JamCar getCar(Coordinates coord){
        return carMap.get(coord);
    }

    /** return the dimensions of the board */
    public  int getRows() {return rows;}
    public  int getColumns() {return columns;}

    /**
     * Return the string representation of the current row
     * @param row the row needed
     * @return the string
     */
    public String rowToString(int row){
        String result = "";
        for(int i = 0; i < columns; i++){
            result += " ";
            Coordinates coord = new Coordinates(row, i);
            if (carMap.containsKey(coord)){
                result += carMap.get(coord).getCarLtr();
            } else {
                result += ".";
            }
        }
        return result;
    }

    /**
     * attempts to move a car to a given coordinate
     * @param car car to be moved
     * @param coord coordinate to be moved to
     * @return null if move fails, new board state if move succeeds
     */
    public JamConfig tryMove(JamCar car, Coordinates coord){
        int carEndCoordCol = car.getEndCoord().col();
        int carEndCoordRow = car.getEndCoord().row();
        int carStartCoordCol = car.getStartCoord().col();
        int carStartCoordRow = car.getStartCoord().row();
        HashMap<Coordinates, JamCar> newCarMap = (HashMap<Coordinates, JamCar>) carMap.clone();
        JamCar currentCar = null;
        if (car.isHorizontal()){
            if ((coord.row() == carStartCoordRow) && !(carMap.containsKey(coord))){
                if (carStartCoordCol - 1 == coord.col() && carStartCoordCol > 0){
                    newCarMap.remove(car.getEndCoord());
                    currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow, carStartCoordCol - 1),
                            new Coordinates(carEndCoordRow, carEndCoordCol - 1));
                    newCarMap.put(new Coordinates(carEndCoordRow, carStartCoordCol - 1), currentCar);
                    for (int i = carStartCoordCol; i < carEndCoordCol; i++){
                        newCarMap.replace(new Coordinates(carStartCoordRow, i), currentCar);
                    }

                } else if (carEndCoordCol + 1 == coord.col() && coord.col() < columns) {
                    newCarMap.remove(car.getStartCoord());
                    currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow, carStartCoordCol + 1),
                            new Coordinates(carEndCoordRow, carEndCoordCol + 1));
                    newCarMap.put(new Coordinates(carEndCoordRow, carEndCoordCol + 1), currentCar);
                    for (int i = carEndCoordCol; i > carStartCoordCol; i--){
                        newCarMap.replace(new Coordinates(carStartCoordRow, i), currentCar);
                    }

                }
            }
        } else {
            if ((coord.col() == carStartCoordCol) && !(carMap.containsKey(coord))){
                if (carStartCoordRow - 1 == coord.row() && carStartCoordRow > 0){
                    newCarMap.remove(car.getEndCoord());
                    currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow - 1, carStartCoordCol),
                            new Coordinates(carEndCoordRow - 1, carEndCoordCol));
                    newCarMap.put(new Coordinates(carStartCoordRow - 1, carEndCoordCol), currentCar);
                    for (int i = carStartCoordRow; i < carEndCoordRow; i++){
                        newCarMap.replace(new Coordinates(i, carStartCoordCol), currentCar);
                    }

                } else if (carEndCoordRow + 1 == coord.row() && coord.row() < rows) {
                    newCarMap.remove(car.getStartCoord());
                    currentCar = new JamCar(car.getCarLtr(),
                            new Coordinates(carStartCoordRow + 1, carStartCoordCol),
                            new Coordinates(carEndCoordRow + 1, carEndCoordCol));
                    newCarMap.put(new Coordinates(carEndCoordRow + 1, carEndCoordCol), currentCar);
                    for (int i = carEndCoordRow; i > carStartCoordRow; i--){
                        newCarMap.replace(new Coordinates(i, carStartCoordCol), currentCar);
                    }

                }
            }

        }
        if (currentCar == null){
            return null;
        } else if (currentCar.getCarLtr() == 'X'){
            return new JamConfig(newCarMap, currentCar);
        } else {
            return new JamConfig(newCarMap, goalCar);
        }

    }

    @Override
    public boolean isSolution() {
        return goalCar.isSolved();
    }

    @Override
    public String toString(){
        String result ="";
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (carMap.containsKey(new Coordinates(i,j))){
                    result += carMap.get(new Coordinates(i,j)).getCarLtr() + " ";
                } else {
                    result += ". ";
                }
            }
            result = result.substring(0, result.length() - 1);
            result += "\n";
        }
        return result;
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
