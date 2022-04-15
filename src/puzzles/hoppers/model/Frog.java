package puzzles.hoppers.model;

public class Frog {
    private int row;
    private int col;
    private boolean isRed;

    public Frog(int row, int col, char frog){
        this.row = row;
        this.col = col;
        this.isRed = (frog == 'R');
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
}
