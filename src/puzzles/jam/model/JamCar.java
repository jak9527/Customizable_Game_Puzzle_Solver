package puzzles.jam.model;

import puzzles.common.Coordinates;

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
    }

    public boolean isSolved(){
        return (endCoord.col() == JamConfig.columns);

    }
}
