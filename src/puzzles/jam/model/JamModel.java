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

public class JamModel {

    /** the collection of observers of this model */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;


    private String puzzleName;

    private Solver solver = new Solver();

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


    public JamModel(String filename) {
        try {
            this.puzzleName = filename;
            this.currentConfig = new JamConfig(filename);
            this.carSelected = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame(String filename){
        this.puzzleName = filename;
        try {
            this.currentConfig = new JamConfig(filename);
            alertObservers("Loaded: " + filename.substring(filename.lastIndexOf(File.separator)+1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void reset(){
        try {
            currentConfig = new JamConfig(this.puzzleName);
            carSelected = null;
            alertObservers("Reset puzzle");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public JamConfig getCurrentConfig(){
        return currentConfig;
    }

    public String getPuzzleName() {
        return puzzleName;
    }
}
