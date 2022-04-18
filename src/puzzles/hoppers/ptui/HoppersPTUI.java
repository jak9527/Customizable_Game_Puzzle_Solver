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
//        this.initialized = false;
//        this.model = new HoppersModel();
//        this.model.addObserver( this );
//
//        List< String > paramStrings = super.getArguments();
//        if ( paramStrings.size() == 1 ) {
//            final String filename = paramStrings.get( 0 );
//            try{
//                this.model.newGame( filename );
//            }
//            catch(IOException e){
//
//            }
//            else {
//                throw new Exception(
//                        String.format(
//                                "\"%s\" is not the required word length (%d)." +
//                                        System.lineSeparator(),
//                                firstWord, Model.WORD_SIZE
//                        )
//                );
//            }
//        }
//        else {
//            this.model.newGame();
//        }
    }

    @Override
    public void update(HoppersModel model, String msg) {
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }
    }

    @Override
    public void start(PrintWriter console) throws Exception {

    }
}
