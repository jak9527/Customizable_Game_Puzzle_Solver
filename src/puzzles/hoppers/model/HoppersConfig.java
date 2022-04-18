package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// TODO: implement your HoppersConfig for the common solver

public class HoppersConfig implements Configuration{
    /** Row Dim */
    private static int row;
    /** Column Dim */
    private static int col;
    /** Character for green frogs */
    private final char GREEN_FROG = 'G';
    /** Character for red frogs */
    private final char RED_FROG = 'R';
    /** Empty pos character */
    private final char EMPTY = '.';
    /** 2d char array holding positions */
    private char[][] board;
    /** hashSet holding all frog positions */
    private HashSet<Frog> frogPos = new HashSet<>();

    public HoppersConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String[] fields = in.readLine().split("\\s+");
            row = Integer.parseInt(fields[0]);
            col = Integer.parseInt(fields[1]);
            board = new char[row][col];
            for(int r = 0; r < row; r++){
                fields = in.readLine().split("\\s+");
                for(int c = 0; c < col; c++){
                    board[r][c] = fields[c].charAt(0);
                    if(fields[c].charAt(0) == GREEN_FROG || fields[c].charAt(0) == RED_FROG){
                        frogPos.add(new Frog(r, c, fields[c].charAt(0)));
                    }
                }
            }
        }
    }

    public HoppersConfig(int startR, int startC, int endR, int endC, int jumpedR, int jumpedC, HoppersConfig other){
        this.board =new char[row][col];
        for(int r = 0; r<row; r++){
            System.arraycopy(other.board[r], 0, this.board[r], 0, col);
        }
        this.frogPos = new HashSet<Frog>();
        frogPos.addAll(other.frogPos);
        board[endR][endC] = board[startR][startC];
        board[startR][startC] = EMPTY;
        frogPos.remove(new Frog(startR, startC, board[endR][endC]));
        frogPos.add(new Frog(endR, endC, board[endR][endC]));

        frogPos.remove(new Frog(jumpedR,jumpedC, board[jumpedR][jumpedC]));
        board[jumpedR][jumpedC] = EMPTY;

    }

    public HashSet<Frog> getFrogs(){
        return frogPos;
    }

    @Override
    public boolean isSolution() {
        boolean result = frogPos.size()==1;
        ArrayList<Frog> frog = new ArrayList<>(frogPos);
        result &= frog.get(0).isRed();
        return result;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        for(Frog f : frogPos){
            try{//Check Left neighbor
                if(board[f.getRow()][f.getCol()-2] == GREEN_FROG){
                    if(!(board[f.getRow()][f.getCol()-4] == GREEN_FROG || board[f.getRow()][f.getCol()-4]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow(),f.getCol()-4,f.getRow(),f.getCol()-2,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Right neighbor
                if(board[f.getRow()][f.getCol()+2] == GREEN_FROG){
                    if(!(board[f.getRow()][f.getCol()+4] == GREEN_FROG || board[f.getRow()][f.getCol()+4]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow(),f.getCol()+4,f.getRow(),f.getCol()+2,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Top neighbor
                if(board[f.getRow()-2][f.getCol()] == GREEN_FROG){
                    if(!(board[f.getRow()-4][f.getCol()] == GREEN_FROG || board[f.getRow()-4][f.getCol()]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()-4,f.getCol(),f.getRow()-2,f.getCol(),this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Bottom neighbor
                if(board[f.getRow()+2][f.getCol()] == GREEN_FROG){
                    if(!(board[f.getRow()+4][f.getCol()] == GREEN_FROG || board[f.getRow()+4][f.getCol()]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()+4,f.getCol(),f.getRow()+2,f.getCol(),this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Left Top neighbor
                if(board[f.getRow()-1][f.getCol()-1] == GREEN_FROG){
                    if(!(board[f.getRow()-2][f.getCol()-2] == GREEN_FROG || board[f.getRow()-2][f.getCol()-2]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()-2,f.getCol()-2,f.getRow()-1,f.getCol()-1,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Left Bottom neighbor
                if(board[f.getRow()+1][f.getCol()-1] == GREEN_FROG){
                    if(!(board[f.getRow()+2][f.getCol()-2] == GREEN_FROG || board[f.getRow()+2][f.getCol()-2]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()+2,f.getCol()-2,f.getRow()+1,f.getCol()-1,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Right Top neighbor
                if(board[f.getRow()-1][f.getCol()+1] == GREEN_FROG){
                    if(!(board[f.getRow()-2][f.getCol()+2] == GREEN_FROG || board[f.getRow()-2][f.getCol()+2]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()-2,f.getCol()+2,f.getRow()-1,f.getCol()+1,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Right Bottom neighbor
                if(board[f.getRow()+1][f.getCol()+1] == GREEN_FROG){
                    if(!(board[f.getRow()+2][f.getCol()+2] == GREEN_FROG || board[f.getRow()+2][f.getCol()+2]== RED_FROG)){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()+2,f.getCol()+2,f.getRow()+1,f.getCol()+1,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
        }
        return neighbors;
    }

    public char[][] getBoard(){
        return board;
    }

    @Override
    public String toString(){
        String result = "";
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                result+=Character.toString(board[i][j]);
                if(!(j==(col-1))){
                    result+= " ";
                }

            }
            result+= "\n";

        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoppersConfig that = (HoppersConfig) o;
        return Arrays.deepEquals(this.board, ((HoppersConfig) o).board);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(board);
        return result;
    }
}

