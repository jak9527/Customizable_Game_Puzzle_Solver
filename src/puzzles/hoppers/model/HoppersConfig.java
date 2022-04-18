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
    private static HashSet<Frog> frogPos = new HashSet<>();

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

    public HoppersConfig(int startR, int startC, int endR, int endC, HoppersConfig other){
        this.board =new char[row][col];
        for(int r = 0; r<row; r++){
            System.arraycopy(other.board[r], 0, this.board[r], 0, col);
        }
        board[endR][endC] = board[startR][startC];
        board[startR][startC] = '.';
        frogPos.remove(new Frog(startR, startC, board[endC][endR]));
        frogPos.add(new Frog(endR, endC, board[endC][endR]));

        if(endR>startR){
            if(endC>startC){
                frogPos.remove(new Frog(endR+1, endC+1, board[endR+1][endC+1]));
                board[endR+1][endC+1] = EMPTY;
            }
            if(endC<startC){
                frogPos.remove(new Frog(endR+1, endC-1, board[endR+1][endC-1]));
                board[endR+1][endC-1] = EMPTY;
            }
            else{
                frogPos.remove(new Frog(endR+1, endC, board[endR+1][endC]));
                board[endR+1][endC] = EMPTY;
            }
        }
        if(endR<startR){
            if(endC>startC){
                frogPos.remove(new Frog(endR-1, endC+1, board[endR+1][endC+1]));
                board[endR-1][endC+1] = EMPTY;
            }
            if(endC<startC){
                frogPos.remove(new Frog(endR-1, endC-1, board[endR+1][endC-1]));
                board[endR-1][endC-1] = EMPTY;
            }
            else{
                frogPos.remove(new Frog(endR-1, endC, board[endR+1][endC]));
                board[endR-1][endC] = EMPTY;
            }
        }
        else{
            if(endC>startC){
                frogPos.remove(new Frog(endR, endC+1, board[endR][endC+1]));
                board[endR][endC+1] = EMPTY;
            }
            if(endC<startC){
                frogPos.remove(new Frog(endR, endC-1, board[endR+1][endC-1]));
                board[endR][endC-1] = EMPTY;
            }
        }

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
        for(Frog f : frogPos){//nuke everything after this point. do the kelly recommendation. check if it is not the first column, not the first row etc and do the things there, then
            try{//Check Left neighbor
                if(board[f.getRow()][f.getCol()-2] == GREEN_FROG || board[f.getRow()][f.getCol()-2]== RED_FROG){
                    if(board[f.getRow()][f.getCol()-4] == GREEN_FROG || board[f.getRow()][f.getCol()-4]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow(),f.getCol()-4,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Right neighbor
                if(board[f.getRow()][f.getCol()+2] == GREEN_FROG || board[f.getRow()][f.getCol()+2]== RED_FROG){
                    if(board[f.getRow()][f.getCol()+4] == GREEN_FROG || board[f.getRow()][f.getCol()+4]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow(),f.getCol()+4,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Top neighbor
                if(board[f.getRow()-2][f.getCol()] == GREEN_FROG || board[f.getRow()][f.getCol()-2]== RED_FROG){
                    if(board[f.getRow()-4][f.getCol()] == GREEN_FROG || board[f.getRow()-4][f.getCol()]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()-4,f.getCol(),this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Bottom neighbor
                if(board[f.getRow()+2][f.getCol()] == GREEN_FROG || board[f.getRow()][f.getCol()-2]== RED_FROG){
                    if(board[f.getRow()+4][f.getCol()] == GREEN_FROG || board[f.getRow()+4][f.getCol()]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()+4,f.getCol(),this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Left Top neighbor
                if(board[f.getRow()-2][f.getCol()-2] == GREEN_FROG || board[f.getRow()-2][f.getCol()-2]== RED_FROG){
                    if(board[f.getRow()-4][f.getCol()-4] == GREEN_FROG || board[f.getRow()-4][f.getCol()-4]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()-4,f.getCol()-4,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Left Bottom neighbor
                if(board[f.getRow()+2][f.getCol()-2] == GREEN_FROG || board[f.getRow()+2][f.getCol()-2]== RED_FROG){
                    if(board[f.getRow()+4][f.getCol()-4] == GREEN_FROG || board[f.getRow()+4][f.getCol()-4]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()+4,f.getCol()-4,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Right Top neighbor
                if(board[f.getRow()-2][f.getCol()+2] == GREEN_FROG || board[f.getRow()-2][f.getCol()+2]== RED_FROG){
                    if(board[f.getRow()-4][f.getCol()+4] == GREEN_FROG || board[f.getRow()-4][f.getCol()+4]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()-4,f.getCol()+4,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
            try{//Check Right Bottom neighbor
                if(board[f.getRow()+2][f.getCol()+2] == GREEN_FROG || board[f.getRow()+2][f.getCol()+2]== RED_FROG){
                    if(board[f.getRow()+4][f.getCol()+4] == GREEN_FROG || board[f.getRow()+4][f.getCol()+4]== RED_FROG){
                        neighbors.add(new HoppersConfig(f.getRow(),f.getCol(),f.getRow()+4,f.getCol()+4,this));
                    }
                }
            }
            catch (IndexOutOfBoundsException ignored){
            }
        }
        return neighbors;
    }

    @Override
    public String toString(){
        return Arrays.deepToString(board);
    }
}

//old neighbor code. REMOVE LATER
//if(f.getRow()==0){
//        if(f.getCol()==0){
//        if(board[0][2]==GREEN_FROG || board[0][2]==RED_FROG) {
//        if(board[0][4]==EMPTY) {
//        neighbors.add(new HoppersConfig(0, 0, 0, 4, this));
//        }
//        }
//        if(board[1][1]==GREEN_FROG||board[1][1]==RED_FROG){
//        if(board[2][2]==EMPTY){
//        neighbors.add(new HoppersConfig(0, 0, 2, 2, this));
//        }
//        }
//        if(board[2][0]==GREEN_FROG||board[2][0]==RED_FROG){
//        if(board[4][0]==EMPTY){
//        neighbors.add(new HoppersConfig(0, 0, 4, 0, this));
//        }
//        }
//        }
//        else if(f.getCol()==col){
//        if(board[0][f.getCol()-2]==GREEN_FROG || board[0][f.getCol()-2]==RED_FROG) {
//        if(board[0][f.getCol()-4]==EMPTY) {
//        neighbors.add(new HoppersConfig(0, f.getCol(), 0, f.getCol()-4, this));
//        }
//        }
//        if(board[1][f.getCol()-1]==GREEN_FROG||board[1][f.getCol()-1]==RED_FROG){
//        if(board[2][f.getCol()-2]==EMPTY){
//        neighbors.add(new HoppersConfig(0, f.getCol(), 2, f.getCol()-2, this));
//        }
//        }
//        if(board[2][f.getCol()]==GREEN_FROG||board[2][f.getCol()]==RED_FROG){
//        if(board[4][0]==EMPTY){
//        neighbors.add(new HoppersConfig(0, f.getCol(), 4, f.getCol(), this));
//        }
//        }
//        }
//        else{
//
//        }
//        }
