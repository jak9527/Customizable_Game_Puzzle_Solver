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
                board[endR+1][endC+1] = '.';
            }
            if(endC<startC){
                frogPos.remove(new Frog(endR+1, endC-1, board[endR+1][endC-1]));
                board[endR+1][endC-1] = '.';
            }
            else{
                frogPos.remove(new Frog(endR+1, endC, board[endR+1][endC]));
                board[endR+1][endC] = '.';
            }
        }
        if(endR<startR){
            if(endC>startC){
                frogPos.remove(new Frog(endR-1, endC+1, board[endR+1][endC+1]));
                board[endR-1][endC+1] = '.';
            }
            if(endC<startC){
                frogPos.remove(new Frog(endR-1, endC-1, board[endR+1][endC-1]));
                board[endR-1][endC-1] = '.';
            }
            else{
                frogPos.remove(new Frog(endR-1, endC, board[endR+1][endC]));
                board[endR-1][endC] = '.';
            }
        }
        else{
            if(endC>startC){
                frogPos.remove(new Frog(endR, endC+1, board[endR][endC+1]));
                board[endR][endC+1] = '.';
            }
            if(endC<startC){
                frogPos.remove(new Frog(endR, endC-1, board[endR+1][endC-1]));
                board[endR][endC-1] = '.';
            }
        }

    }


    @Override
    public boolean isSolution() {
        return frogPos.size()==1;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        return null;
    }
}
