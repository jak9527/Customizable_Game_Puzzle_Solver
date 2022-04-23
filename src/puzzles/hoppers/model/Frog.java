package puzzles.hoppers.model;

import java.util.Objects;

public class Frog {
    /** The position of this frog */
    private int row;
    private int col;

    /** Is this frog red? */
    private boolean isRed;
    /** The constant red frog character */
    private final char RED_FROG = 'R';

    /**
     * Construct a new frog
     * @param row row position
     * @param col column position
     * @param frog what color frog
     */
    public Frog(int row, int col, char frog){
        this.row = row;
        this.col = col;
        this.isRed = (frog == RED_FROG);
    }

    /**
     * Return the row position
     * @return row
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Return the column position
     * @return col
     */
    public int getCol(){
        return this.col;
    }

    /**
     * is this frog red?
     * @return if the frog is red
     */
    public boolean isRed(){
        return isRed;
    }

    /**
     * Is this frog equal to another
     * @param o other object to compare to
     * @return if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frog frog = (Frog) o;
        return row == frog.row && col == frog.col && isRed == frog.isRed;
    }

    /**
     * Make the hashcode for this frog
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col, isRed);
    }
}
