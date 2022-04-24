package puzzles.jam.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;

public class JamGUI extends Application  implements Observer<JamModel, String>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    private JamModel model;

    private boolean initialized;

    private String currentFile;

    private BorderPane puzzle = new BorderPane();
    private Stage stage;

    // for demonstration purposes
    private final static String X_CAR_COLOR = "#DF0101";
    private final static int BUTTON_FONT_SIZE = 20;
    private final static int ICON_SIZE = 75;

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
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.initialized = true;
        this.stage = stage;
        Label status = new  Label("Loaded: " + currentFile);
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
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> model.reset());
        Button hintButton = new Button("Hint");
        hintButton.setOnAction(event -> model.hint());
        HBox puzzleControls = new HBox(loadButton, resetButton, hintButton);
        puzzle = new BorderPane();
        puzzle.setTop(status);
        puzzle.setCenter(board);
        puzzle.setBottom(puzzleControls);
        Scene currentScene = new Scene(puzzle);
        stage.setScene(currentScene);
        stage.show();




//        Button button1 = new Button();
//        button1.setStyle(
//                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
//                "-fx-background-color: " + X_CAR_COLOR + ";" +
//                "-fx-font-weight: bold;");
//        button1.setText("X");
//        button1.setMinSize(ICON_SIZE, ICON_SIZE);
//        button1.setMaxSize(ICON_SIZE, ICON_SIZE);
//        Scene scene = new Scene(button1);
//        stage.setScene(scene);
//        stage.show();
    }

    @Override
    public void update(JamModel jamModel, String msg) {
        if(!initialized){
            return;        //GUI not yet set up
        }
        puzzle.setCenter(createBoard(jamModel.getCurrentConfig()));
        puzzle.setTop(new Label(msg));
        stage.sizeToScene();
    }

    public GridPane createBoard(JamConfig config){
        GridPane board = new GridPane();

        for (int r = 0; r < config.getRows(); r++){
            for (int c = 0; c < config.getColumns(); c++){
                Button currentButton = new Button();
                Coordinates currentCoord = new Coordinates(r, c);
                currentButton.setOnAction( event -> model.selectCar(currentCoord));
                if (config.isCarAt(currentCoord)){
                    currentButton.setText("" + config.getCar(currentCoord).getCarLtr());
                }
                board.add(currentButton, c, r);
            }
        }
        return board;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
