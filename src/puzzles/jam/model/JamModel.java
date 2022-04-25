package puzzles.jam.model;

import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class used to hold the data and methods
 * needed to play a game of jam
 *
 * @author Kelly Showers kds1653
 */

public class JamModel {

    /** the collection of observers of this model */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;

    /** holds the filename of current puzzle */
    private String puzzleName;
    /** stores a solver instance to use for hints */
    private Solver solver = new Solver();
    /** holds a car if one is selected */
    private JamCar carSelected;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, String> observer) {
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
     * Constructor to make a new model
     * @param filename puzzle file to start with
     */
    public JamModel(String filename) {
        try {
            this.puzzleName = filename;
            this.currentConfig = new JamConfig(filename);
            this.carSelected = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new game of jam
     * @param filename  the puzzle file to be used
     */
    public void newGame(String filename){
        this.puzzleName = filename;
        try {
            this.currentConfig = new JamConfig(filename);
            alertObservers("Loaded: " + filename.substring(filename.lastIndexOf(File.separator)+1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if there is a solution in the
     * game's current state and if so it advances the
     * current puzzle one step towards the solution
     */
    public void hint(){
        Collection<Configuration> path = solver.findPath(currentConfig);
        ArrayList<Configuration> pathList = new ArrayList<>(path);
        if(pathList.size() == 0) {
            alertObservers("No solution");
        } else if (pathList.size() == 1) {
            alertObservers("You already won!");
        } else {
            currentConfig = (JamConfig) pathList.get(1);
            if(currentConfig.isSolution()){
                alertObservers("You Won!");
            }
            else{
                alertObservers("Here is the next move!");
            }

        }

    }

    /**
     * resets progress on the current puzzle
     */
    public void reset(){
        try {
            currentConfig = new JamConfig(this.puzzleName);
            carSelected = null;
            alertObservers("Reset puzzle");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * if no car is selected attemps to find one at given coord
     * otherwise attempts to move selected car to given coord
     * if it is a valid move
     * @param coord coordinate on the grid
     */
    public void selectCar(Coordinates coord){
        if (carSelected == null){
            if (currentConfig.isCarAt(coord)){
                carSelected = currentConfig.getCar(coord);
                alertObservers("Selected Car " + carSelected.getCarLtr());
            } else {
                alertObservers("Invalid selection");
            }
        } else {
            JamConfig potentialConfig = currentConfig.tryMove(carSelected, coord);
            if (potentialConfig == null){
                alertObservers("Can't move " + carSelected.getCarLtr() + " to " + coord);
            } else {
                currentConfig = potentialConfig;
                if(currentConfig.isSolution()){
                    alertObservers("You Won!");
                } else {
                    alertObservers("Car " + carSelected.getCarLtr() + " moved to " + coord);
                }
            }
            carSelected = null;
        }
    }

    /**
     * @return the current config representing the puzzle
     */
    public JamConfig getCurrentConfig(){
        return currentConfig;
    }

    /**
     * @return the filename of current puzzle
     */
    public String getPuzzleName() {
        return puzzleName;
    }
}
