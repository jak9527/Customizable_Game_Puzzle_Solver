package puzzles.hoppers.gui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    private HoppersModel model;
    private boolean initDone;


    // for demonstration purposes
    private Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));

    public void init() {
        initDone = false;
        String filename = getParameters().getRaw().get(0);
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        this.model.newGame(filename);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.initDone = true;
        Button button = new Button();
        button.setGraphic(new ImageView(redFrog));
        Scene scene = new Scene(button);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(HoppersModel hoppersModel, String msg) {
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
