package puzzles.jam.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class that uses a Model object to run
 * the jam game via a visual display
 *
 * @author Kelly Showers kds1653
 */

public class JamGUI extends Application  implements Observer<JamModel, String>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // the model being observed
    private JamModel model;

    // whether or not the program has initialized yet
    private boolean initialized;

    //filename of the current puzzle
    private String currentFile;

    /** store the gui components that need updates */
    private BorderPane puzzle = new BorderPane();
    private Label status;
    private Stage stage;

    // color of solution car
    private final static String X_CAR_COLOR = "#DF0101";

    //size of text
    private final static int BUTTON_FONT_SIZE = 20;
    //size of buttons
    private final static int ICON_SIZE = 75;
    //colors to be used for cars
    private static HashMap<Character, Color> colorMap;

    /**
     * Initialize the application
     */
    public void init() {
        initialized = false;
        String filename = getParameters().getRaw().get(0);
        this.model = new JamModel(filename);
        this.model.addObserver(this);
        currentFile = filename;
        this.model.newGame(filename);
        createColorMap();
    }

    /**
     * Creates the map of colors to the possible cars
     */
    private void createColorMap(){
        colorMap = new HashMap<>();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWYZ";
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
                Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PURPLE,
                Color.PINK, Color.VIOLET, Color.TEAL, Color.DARKGREEN, Color.DARKBLUE,
                Color.ALICEBLUE, Color.GOLD, Color.YELLOWGREEN, Color.CYAN, Color.BROWN,
                Color.SILVER, Color.LIGHTYELLOW, Color.SALMON, Color.SPRINGGREEN, Color.LIME,
                Color.CORNFLOWERBLUE, Color.TAN, Color.HONEYDEW, Color.GRAY, Color.ORANGERED));
        for (int i = 0; i < 25; i++){
            colorMap.put(letters.charAt(i), colors.get(i));
        }
        colorMap.put('X', Color.valueOf(X_CAR_COLOR));

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.initialized = true;
        this.stage = stage;
        Label status = new  Label("Loaded: " +
                currentFile.substring(currentFile.lastIndexOf(File.separator)+1));
        status.setStyle(
                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                        "-fx-font-weight: bold;");
        this.status = status;
        HBox statusBox = new HBox(status);
        statusBox.setAlignment(Pos.CENTER);
        GridPane board = createBoard(model.getCurrentConfig());
        Button loadButton = new Button("Load");
        FileChooser chooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += File.separator + "data" + File.separator + "jam";
        chooser.setInitialDirectory(new File(currentPath));
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = chooser.showOpenDialog(stage);
                if (file != null) {
                    currentFile = file.getPath();
                    model.newGame(currentFile);
                }
            }
        });
        loadButton.setStyle(
                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                        "-fx-font-weight: bold;");
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> model.reset());
        resetButton.setStyle(
                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                        "-fx-font-weight: bold;");
        Button hintButton = new Button("Hint");
        hintButton.setOnAction(event -> model.hint());
        hintButton.setStyle(
                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                        "-fx-font-weight: bold;");
        HBox puzzleControls = new HBox(loadButton, resetButton, hintButton);
        puzzleControls.setAlignment(Pos.CENTER);
        puzzle = new BorderPane();
        puzzle.setTop(statusBox);
        puzzle.setCenter(board);
        puzzle.setBottom(puzzleControls);
        Scene currentScene = new Scene(puzzle);
        stage.setResizable(false);
        stage.setScene(currentScene);
        stage.setTitle("Jam GUI");
        stage.show();
    }

    @Override
    public void update(JamModel jamModel, String msg) {
        if(!initialized){
            return;        //GUI not yet set up
        }
        puzzle.setCenter(createBoard(jamModel.getCurrentConfig()));
        status.setText(msg);
        stage.sizeToScene();
    }

    /**
     * Creates the board of cars for the puzzle
     * @param config current board to be represented
     * @return gridpane representation of current board
     */
    public GridPane createBoard(JamConfig config){
        GridPane board = new GridPane();

        for (int r = 0; r < config.getRows(); r++){
            for (int c = 0; c < config.getColumns(); c++){
                Button currentButton = new Button();
                Coordinates currentCoord = new Coordinates(r, c);
                currentButton.setOnAction( event -> model.selectCar(currentCoord));
                currentButton.setStyle(
                    "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                    "-fx-font-weight: bold;");
                currentButton.setMinSize(ICON_SIZE, ICON_SIZE);
                currentButton.setMaxSize(ICON_SIZE, ICON_SIZE);
                if (config.isCarAt(currentCoord)){
                    Character currentCarLetter = config.getCar(currentCoord).getCarLtr();
                    currentButton.setText("" + currentCarLetter);
                    currentButton.setBackground(new Background(
                            new BackgroundFill(colorMap.get(currentCarLetter),
                                    null, null)));
                }
                board.add(currentButton, c, r);
            }
        }
        return board;
    }

    /**
     * launches the application
     * @param args initial puzzle file to load
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
