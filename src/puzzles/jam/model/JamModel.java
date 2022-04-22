package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersModel;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JamModel {

    /** the collection of observers of this model */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;

    /**
     * Possible game states
     */
    public enum GameState { ONGOING, WON, LOST, ILLEGAL_MOVE }

    /**
     * Game's current state
     */
    private HoppersModel.GameState gameState;

    private static final EnumMap< HoppersModel.GameState, String > STATE_MSGS =
            new EnumMap<>( Map.of(
                    HoppersModel.GameState.WON, "You won!",
                    HoppersModel.GameState.LOST, "You lost 😥.",
                    HoppersModel.GameState.ONGOING, "Make a guess!",
                    HoppersModel.GameState.ILLEGAL_MOVE, "Illegal move."
            ) );

    private String puzzleName;

    private Solver solver = new Solver();

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
}
