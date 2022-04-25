package puzzles.jam.model;

import puzzles.common.Coordinates;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class used to represent a car in a jam puzzle
 *
 * @author Kelly Showers kds1653
 */

public class JamCar {
    //Letter of car
    private char carLtr;
    // Coordinates of car
    private Coordinates startCoord;
    private Coordinates endCoord;
    //length of car
    private int length;
    //Whether or not the car is facing horizontal
    private boolean horizontal;

    /**
     * Constructor for a car
     * @param letter letter of a car
     * @param start start of car coordinate
     * @param end end of car coordinate
     */
    public JamCar(char letter, Coordinates start, Coordinates end){
        this.carLtr = letter;
        this.startCoord = start;
        this.endCoord = end;
        if (startCoord.col() == endCoord.col()) {
            horizontal = false;
            length = endCoord.row() - startCoord.row();
        } else {
            horizontal = true;
            length = endCoord.col() - startCoord.col();
        }
    }

    /** checks if solution car has made it to the right side */
    public boolean isSolved(){
        return (endCoord.col() == JamConfig.columns - 1);

    }

    /** gets the letter of the car */
    public char getCarLtr() {
        return carLtr;
    }

    /** gets the start coord of the car */
    public Coordinates getStartCoord() {
        return startCoord;
    }

    /** gets the end coord of the car */
    public Coordinates getEndCoord() {
        return endCoord;
    }

    /** checks if car is horizontal */
    public boolean isHorizontal() {
        return horizontal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JamCar jamCar = (JamCar) o;
        return carLtr == jamCar.carLtr && Objects.equals(startCoord, jamCar.startCoord) && Objects.equals(endCoord, jamCar.endCoord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carLtr, startCoord, endCoord);
    }
}
