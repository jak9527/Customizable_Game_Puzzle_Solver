package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HoppersModel {

    /**
     * Possible game states
     */
    public enum GameState { ONGOING, WON, LOST, ILLEGAL_MOVE }

    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;

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
            this.currentConfig = new HoppersConfig(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
