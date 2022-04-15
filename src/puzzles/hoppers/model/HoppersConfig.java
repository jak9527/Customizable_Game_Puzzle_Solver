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
                    if(fields[c].charAt(0) == 'G' || fields[c].charAt(0) == 'R'){
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
