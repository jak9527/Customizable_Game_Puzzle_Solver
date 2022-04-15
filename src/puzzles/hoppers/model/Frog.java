package puzzles.hoppers.model;

import java.util.Objects;

public class Frog {
    private int row;
    private int col;
    private boolean isRed;
    private final char RED_FROG = 'R';

    public Frog(int row, int col, char frog){
        this.row = row;
        this.col = col;
        this.isRed = (frog == RED_FROG);
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
    public boolean isRed(){
        return isRed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frog frog = (Frog) o;
        return row == frog.row && col == frog.col && isRed == frog.isRed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, isRed);
    }
}
