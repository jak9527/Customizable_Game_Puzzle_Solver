package puzzles.hoppers.gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

/**
 * The GUI representation of the Hoppers puzzle
 * @author Jacob Karvelis
 */
public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    /** The model to use in the GUI */
    private HoppersModel model;
    /** Is the init done? */
    private boolean initDone;
    /** Access to board elements for updating */
    private GridPane board;
    private Scene scene;
    private BorderPane borderPane = new BorderPane();
    private GridPane topLabels = new GridPane();
    private HBox bottomButtons = new HBox();
    private Stage stage;

    /** The current puzzle file */
    private String curFile;
    /** Is this puzzle the initial? */
    private boolean initialPuzzle = true;

    /** The images to use in the game along with their size */
    private final Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));
    private final Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green_frog.png"));
    private final Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"water.png"));
    private final Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"lily_pad.png"));
    private final int ICON_SIZE = 75;

    /**
     * Initialize the application
     */
    public void init() {
        initDone = false;
        String filename = getParameters().getRaw().get(0);
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        curFile = filename;
        this.model.newGame(filename);
    }

    /**
     * Start the gui
     * @param stage stage to use
     * @throws Exception catch any file errors
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.initDone = true;
        Button button = new Button();
        button.setGraphic(new ImageView(redFrog));
        this.board = buildBoard(this.model.getCurrentConfig());
        this.borderPane.setCenter(this.board);
        Label msgLabel = new Label("Make a move!");
        msgLabel.setFont(new Font("Roboto", 20));
        this.topLabels.add(msgLabel, 0,1);
        String filename = getParameters().getRaw().get(0);
        this.topLabels.add(new Label(filename.substring(filename.lastIndexOf('/')+1)), 0, 0);
        this.topLabels.setHgap(30);
        this.borderPane.setTop(topLabels);
        Button hintButton = new Button("Hint");
        hintButton.setOnAction(event -> this.model.hint());
        this.bottomButtons.getChildren().add(hintButton);
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> this.model.reset());
        this.bottomButtons.getChildren().add(resetButton);
        FileChooser fileChooser = new FileChooser();
        Button loadButton = new Button("Load");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    curFile = file.getPath();
                    initialPuzzle = false;
                    model.newGame(file.getPath());
                }
            }
        });
        this.bottomButtons.getChildren().add(loadButton);

        this.borderPane.setBottom(this.bottomButtons);

        this.scene = new Scene(borderPane);
        stage.setScene(this.scene);
        BorderPane.setMargin(bottomButtons, new Insets(5, 0, 5, ((75*this.model.getCurrentConfig().getCol())/2.0)-63));
        stage.setResizable(false);
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(18, 145, 227), CornerRadii.EMPTY, Insets.EMPTY)));
//        Color.rgb(18, 145, 227)
        this.stage = stage;
        stage.show();

    }

    /**
     * Update the view
     * @param hoppersModel the model to update from
     * @param msg the msg to update with
     */
    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        if(!this.initDone){
            return;        //GUI not yet set up
        }
        this.borderPane.setCenter(buildBoard(hoppersModel.getCurrentConfig()));
        Label tmp = (Label) this.topLabels.getChildren().get(0);
        tmp.setText(msg);
        tmp = (Label) this.topLabels.getChildren().get(1);
        stage.sizeToScene();
        BorderPane.setMargin(bottomButtons, new Insets(5, 0, 5, ((75*this.model.getCurrentConfig().getCol())/2.0)-63));
        if(!initialPuzzle){
            tmp.setText(curFile.substring(curFile.lastIndexOf(File.separator)+1));
        }
    }

    /**
     * The frog piece representation
     */
    public class FrogButton extends Button{
        public int row;
        public int col;
        public FrogButton(int row, int col){
            this.row = row;
            this.col = col;
            setOnAction(event -> model.selectFrog(this.row, this.col));
        }
    }

    /**
     * Build the current config visually based on the board
     * @param config the config to represent
     * @return the gridpane holding it
     */
    public GridPane buildBoard(HoppersConfig config){
        GridPane board = new GridPane();
        for(int r = 0; r<config.getRow(); r++){
            for(int c = 0; c<config.getCol(); c++){
                FrogButton curButton = new FrogButton(r, c);
//                System.out.println(config.getBoard()[r][c]);
                switch(config.getBoard()[r][c]){
                    case '*': curButton.setGraphic(new ImageView(water));
                        break;
                    case '.': curButton.setGraphic(new ImageView(lilyPad));
                        break;
                    case 'R': curButton.setGraphic(new ImageView(redFrog));
                        break;
                    case 'G': curButton.setGraphic(new ImageView(greenFrog));
                        break;
//                    default:
//                        System.out.println("broke");
                }

                if(r== config.getRow()-1){
                    curButton.setMinSize(ICON_SIZE, ICON_SIZE-3);
                    curButton.setMaxSize(ICON_SIZE, ICON_SIZE-3);
                }
                else{
                    curButton.setMinSize(ICON_SIZE, ICON_SIZE);
                    curButton.setMaxSize(ICON_SIZE, ICON_SIZE);
                }
                board.add(curButton, c, r);
            }
        }
        return board;
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
