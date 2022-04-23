package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.solver.Hoppers;

import java.io.IOException;
import java.util.*;

/**
 * The model for the hoppers game
 * @author Jacob Karvelis
 */
public class HoppersModel {

    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;

    /** name of the puzzle */
    private String puzzleName;

    /** Is a frog already selected? */
    private boolean frogSelected;

    /** The starting coordinate of a guess and the ending coordinate of a guess */
    private int startR;
    private int startC;
    private int endR;
    private int endC;

    /** Solver for this model */
    private Solver solver = new Solver();

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }

    /**
     * Make a new model given an initial file
     * @param filename the puzzle file to load
     */
    public HoppersModel(String filename) {
        try {
            this.puzzleName = filename;
            this.currentConfig = new HoppersConfig(filename);
            this.frogSelected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Make a new game given a filename
     * @param filename the puzzle file name
     */
    public void newGame(String filename){
        this.puzzleName = filename;
        try {
            newGameUtil(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Make a new game given a file name
     * @param filename name of puzzle file
     * @throws IOException catch any file errors
     */
    private void newGameUtil(String filename) throws IOException {
        this.currentConfig = new HoppersConfig(filename);
        alertObservers("Make a guess!");
    }

    /**
     * Move the puzzle to the next move towards solving
     */
    public void hint(){
        Collection<Configuration> path = solver.findPath(currentConfig);
        ArrayList<Configuration> pathList = new ArrayList<>(path);
        if(pathList.size()<2 && !currentConfig.isSolution()) {
            alertObservers("No solution");
        }
        else if(currentConfig.isSolution()){
            alertObservers("You Won!");
        }
        else{
            currentConfig = (HoppersConfig) pathList.get(1);
            if(currentConfig.isSolution()){
                alertObservers("You Won!");
            }
            else{
                alertObservers("Here is the next move!");
            }
        }

    }

    /**
     * Reset the puzzle to the initial state
     */
    public void reset(){
        try {
            currentConfig = new HoppersConfig(this.puzzleName);
            startR = -1;
            endR = -1;
            startC = -1;
            endC = -1;
            frogSelected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        alertObservers("Make a move!");
    }

    /**
     * Select a frog to move. If it is the second selection, move the frog
     * @param row selected row
     * @param col selected column
     */
    public void selectFrog(int row, int col){
        int jumpedR = 0;
        int jumpedC = 0;
        if(frogSelected){
            endR = row;
            endC = col;
            if(row > currentConfig.getRow() || col > currentConfig.getCol() || row < 0 || col < 0 ||
                    !(Math.abs(endR-startR)==2 || Math.abs(endR-startR)==4 || endR-startR==0)){
                frogSelected = false;
                alertObservers("(" + endR + ", " + endC + ")" +" Outside of movable area");
            }
            else if(!(currentConfig.getBoard()[row][col] == currentConfig.getEMPTY())){
                frogSelected = false;
                alertObservers("No Space to jump at " + "(" + endR + ", " + endC + ")" + "!");
            }
            else{
                frogSelected = false;
                if(Math.abs(endR-startR)==2){
                    if(endR < startR){
                        jumpedR = endR+1;
                        if(endC > startC){
                            jumpedC = endC-1;
                        }
                        else if(endC < startC){
                            jumpedC = endC+1;
                        }
                        else{
                            alertObservers("(" + endR + ", " + endC + ")" +" Outside of movable area");
                            return;
                        }

                    }
                    else if(endR > startR){
                        jumpedR = endR-1;
                        if(endC > startC){
                            jumpedC = endC-1;
                        }
                        else{
                            jumpedC = endC+1;
                        }
                    }
                }
                else{
                    if(endR==startR && Math.abs(endC-startC)!=4){
                        alertObservers("(" + endR + ", " + endC + ")" +" Outside of movable area");
                        return;
                    }
                    if(startR%2==0){
                        if(endR < startR){
                            jumpedR = endR+2;
                            jumpedC = endC;
                        }
                        else if(endR > startR){
                            jumpedR = endR-2;
                            jumpedC = endC;
                        }
                        else if(endC < startC){
                            jumpedR = endR;
                            jumpedC = endC + 2;
                        }
                        else if(endC > startC){
                            jumpedR = endR;
                            jumpedC = endC-2;
                        }
                    }
                    else{
                        alertObservers("(" + endR + ", " + endC + ")" +" Outside of movable area");
                        return;
                    }

                }
                if(!(currentConfig.getBoard()[jumpedR][jumpedC] == currentConfig.getEMPTY())){
                    currentConfig = new HoppersConfig(startR, startC, endR, endC, jumpedR, jumpedC, currentConfig);
                    if(currentConfig.isSolution()){
                        alertObservers("You Won!");
                    }
                    else if(!currentConfig.containsRed()){
                        alertObservers("Failed!");
                        return;
                    }
                    else{
                        alertObservers("Hopped " + "(" + startR + ", " + startC + ")" + " to " + "(" + endR + ", " + endC + ")");
                    }
                }
                else{
                    alertObservers("No frog to hop at " + "(" + jumpedR + ", " + jumpedC + ")" + "!");
                }
            }

        }
        else{
            if(row > currentConfig.getRow() || col > currentConfig.getCol() || row < 0 || col < 0){
                alertObservers("Invalid selection");
            }
            else if(!(currentConfig.getBoard()[row][col] == currentConfig.getGREEN_FROG() || currentConfig.getBoard()[row][col] == currentConfig.getRED_FROG())){
                alertObservers("Invalid selection");
            }
            else{
                startR = row;
                startC = col;
                frogSelected = true;
                alertObservers("(" + startR + ", " + startC + ")" + " Frog selected");
            }
        }
    }

    /**
     * return the current config of the model
     * @return the currentConfig
     */
    public HoppersConfig getCurrentConfig(){
        return currentConfig;
    }
}
