package puzzles.hoppers.ptui;

import puzzles.common.ConsoleApplication;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class HoppersPTUI extends ConsoleApplication implements Observer<HoppersModel, String> {
    private HoppersModel model;

    /**
     * Used to prevent this class displaying any info before the UI
     * has been completely set up.
     * Scenario:
     * <ol>
     *     <li>This class creates the model and registers with it.</li>
     *     <li>Model initializes itself and updates its observers.</li>
     *     <li>
     *         This class attempts to display information, but
     *         {@link #start(PrintWriter)} has not yet been called,
     *         therefore the output stream has not yet been established.
     *     </li>
     *     <li>Pandemonium ensues.</li>
     * </ol>
     */
    private boolean initialized;

    /** Where this class's messages must be sent */
    private PrintWriter out;

    /**
     * Create the Wordle model and register this object as an observer
     * of it. If there was a command line argument, use that as the first
     * secret word.
     */
    @Override public void init() throws Exception {
        this.initialized = false;

        List<String> paramStrings = super.getArguments();
        if (paramStrings.size() == 1) {
            final String filename = paramStrings.get(0);
            this.model = new HoppersModel(filename);
            this.model.newGame(filename);
            this.model.addObserver(this);
        }
    }

    @Override
    public void update(HoppersModel model, String msg) {
        if(!(this.initialized)) return; //too soon, no PTUI
        this.out.println(msg);
        this.out.println(this.getCurrentStep());
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }
        else{
            ConsoleApplication.launch(HoppersPTUI.class, args);
        }
    }

    @Override
    public void start(PrintWriter console) throws Exception {
        this.out = console;
        this.initialized = true;
        super.setOnCommand( "s", 2, "<Move>: Select a coordinate",
                args -> this.model.selectFrog( Integer.parseInt(args[ 0 ]), Integer.parseInt(args [ 1 ]) )
        );
        super.setOnCommand( "h", 0, ": show the next step",
                args -> this.hint()
        );
        super.setOnCommand("l", 1, "<file name>: make a new game",
                args -> this.newGame( args[ 0 ] )
        );
        super.setOnCommand("r", 0, ": reset the current puzzle",
                args -> this.reset()
        );
        this.out.println("Make a move!");
        this.out.println(this.getCurrentStep());
    }

    public void hint(){
        this.model.hint();
    }

    public void newGame(String filename){
        this.model.newGame(filename);
    }

    public void reset(){
        this.model.reset();
    }

    public String getCurrentStep(){
        String result = "  ";
        String config = model.getCurrentConfig().toString();
        for(int i = 0; i < model.getCurrentConfig().getCol(); i++){
            result += " " + i;
        }
        result += "\n  ";
        for(int i = 0; i < model.getCurrentConfig().getCol(); i++){
            result += "--";
        }
        result += "\n";
        for(int i = 0; i < model.getCurrentConfig().getRow(); i++){
            result += i + "|";
//            for(int j = 0; j < model.getCurrentConfig().getCol(); j++){
//                result += config.substring(j,j+2);
//            }
            result += model.getCurrentConfig().rowToString(i);
            result += "\n";
        }
        return result;
    }
}
