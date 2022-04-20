package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.solver.Hoppers;

import java.io.IOException;
import java.util.*;

public class HoppersModel {

    /**
     * Possible game states
     */
    public enum GameState { ONGOING, WON, LOST, ILLEGAL_MOVE }

    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;

    private String puzzleName;

    private boolean frogSelected;

    private int startR;
    private int startC;
    private int endR;
    private int endC;

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
     * Game's current state
     */
    private GameState gameState;

    private static final EnumMap< HoppersModel.GameState, String > STATE_MSGS =
            new EnumMap<>( Map.of(
                    HoppersModel.GameState.WON, "You won!",
                    HoppersModel.GameState.LOST, "You lost 😥.",
                    HoppersModel.GameState.ONGOING, "Make a guess!",
                    HoppersModel.GameState.ILLEGAL_MOVE, "Illegal move."
            ) );

    public HoppersModel(String filename) {
        try {
            this.puzzleName = filename;
            this.currentConfig = new HoppersConfig(filename);
            this.frogSelected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame(String filename){
        this.puzzleName = filename;
        try {
            newGameUtil(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newGameUtil(String filename) throws IOException {
        this.currentConfig = new HoppersConfig(filename);
    }

    public void hint(){
        Collection<Configuration> path = solver.findPath(currentConfig);
        ArrayList<Configuration> pathList = new ArrayList<>(path);
        currentConfig = (HoppersConfig) pathList.get(1);
        if(currentConfig.isSolution()){
            alertObservers("You Won!");
        }
        else{
            alertObservers("Make a guess");
        }
    }

    public void reset(){
        try {
            currentConfig = new HoppersConfig(this.puzzleName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        alertObservers("Make a move!");
    }

    public void selectFrog(int row, int col){
        int jumpedR = 0;
        int jumpedC = 0;
        if(frogSelected){
            endR = row;
            endC = col;
            if(row > currentConfig.getRow() || col > currentConfig.getCol() || row < 0 || col < 0 ||
                    (Math.abs(endR-startR)==2 || Math.abs(endR-startR)==4)){
                alertObservers("Outside of area. Please select a new coordinate");
            }
            else if(!(currentConfig.getBoard()[row][col] == currentConfig.getEMPTY())){
                alertObservers("No Space to jump! Please select a new coordinate");
            }
            else{

                frogSelected = false;
                if(Math.abs(endR-startR)==2){
                    if(endR < startR){
                        jumpedR = endR+1;
                        if(endC > startC){
                            jumpedC = endC+1;
                        }
                        else{
                            jumpedC = endC-1;
                        }

                    }
                    else if(endR > startR){
                        jumpedR = endR-1;
                        if(endC > startC){
                            jumpedC = endC+1;
                        }
                        else{
                            jumpedC = endC-1;
                        }
                    }
                }
                else{
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
                        jumpedC = endC - 2;
                    }
                    else if(endC > startC){
                        jumpedR = endR;
                        jumpedC = endC+2;
                    }
                }
                currentConfig = new HoppersConfig(startR, startC, endR, endC, jumpedR, jumpedC, currentConfig);
                frogSelected = false;
                alertObservers("Make a move!");
            }

        }
        else{
            if(row > currentConfig.getRow() || col > currentConfig.getCol() || row < 0 || col < 0){
                alertObservers("Invalid selection. Please select a new coordinate");
            }
            else if(!(currentConfig.getBoard()[row][col] == currentConfig.getGREEN_FROG() || currentConfig.getBoard()[row][col] == currentConfig.getRED_FROG())){
                alertObservers("Invalid selection. Please select a new coordinate");
            }
            else{
                startR = row;
                startC = col;
                frogSelected = true;
                alertObservers("Frog selected. Please select the point to jump to");
            }
        }
    }

    public HoppersConfig getCurrentConfig(){
        return currentConfig;
    }
}
