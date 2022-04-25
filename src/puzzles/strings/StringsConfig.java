package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 *A class to hold a step of the string BFS process used by solver
 * contains the current string at a given step, and can generate
 * neighboring configs as well as check if it is the end string
 *
 * @author kds1653
 */

public class StringsConfig implements Configuration {

    // String the program is trying to reach
    private static String end;
    // current config's string
    private String current;

    /**
     * Constructor for starting string
     * @param start initial string
     * @param end goal string
     */
    public StringsConfig(String start, String end){
        this.end = end;
        this.current = start;
    }

    /**
     * Constructor for neighbor configs generated
     * @param current the current string the solver is on
     */
    public StringsConfig(String current){
        this.current = current;
    }


    @Override
    public boolean isSolution() {
        if (this.current.equals(end)){
            return true;
        }
        return false;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        for (int i = 0; i < current.length(); i++){
            char chr = current.charAt(i);
            if (chr == 'Z'){
                neighbors.add(new StringsConfig(replaceChar(current, 'A', i)));
            } else {
                neighbors.add(new StringsConfig(replaceChar(current, (char)(chr + 1), i)));
            }
            if (chr == 'A'){
                neighbors.add(new StringsConfig(replaceChar(current, 'Z', i)));
            } else {
                neighbors.add(new StringsConfig(replaceChar(current, (char)(chr - 1), i)));
            }

        }
        return neighbors;
    }

    /**
     * Used by get neighbors to change a character in a string
     * @param str current string
     * @param ch character to be added
     * @param index index the new character is replacing
     * @return new string
     */
    public String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    @Override
    public int hashCode() {
        return current.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StringsConfig){
            if (this.current.equals(((StringsConfig) other).current)){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        return current;
    }
}
