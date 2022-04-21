package puzzles.jam.model;

import puzzles.common.Coordinates;

import java.util.ArrayList;
import java.util.Objects;

public class JamCar {
    private char carLtr;
    private Coordinates startCoord;
    private Coordinates endCoord;
    private int length;
    private boolean horizontal;

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

    public boolean isSolved(){
        return (endCoord.col() == JamConfig.columns - 1);

    }

    public char getCarLtr() {
        return carLtr;
    }

    public Coordinates getStartCoord() {
        return startCoord;
    }

    public Coordinates getEndCoord() {
        return endCoord;
    }

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
