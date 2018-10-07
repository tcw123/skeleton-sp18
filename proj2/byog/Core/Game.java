package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    static TETile[][] temp = null;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().



        TETile[][] finalWorldFrame = null;
        input = input.toUpperCase();
        boolean saving = false;
        if (input.charAt(0) == 'N') {
            int i = 1;
            int random = 0;
            String seed = parseSeed(input);
            random = Integer.parseInt(seed);
            MapGenerator map = new MapGenerator(WIDTH, HEIGHT, random);
            map.buildMap();
            saving = parseControl(map, input, seed.length() + 2);
            if (saving == true) {
                temp = map.world;
            }
            else {
                MapGenerator newmap = new MapGenerator(WIDTH, HEIGHT, 123);
                temp = newmap.world;
            }

            finalWorldFrame = map.world;
        }
        else if (input.charAt(0) == 'L') {
            finalWorldFrame = temp;
        }

        return finalWorldFrame;
    }


    public String parseSeed(String input) {
        String seed = "";
        for (int i = 1; i < input.length(); i += 1) {
            if (input.charAt(i) == 'S') {
                break;
            }
            seed += String.valueOf(input.charAt(i));
        }
        return seed;
    }

    public boolean parseControl(MapGenerator map, String input, int start) {
        boolean saving = false;
        for (int i = start; i < input.length(); i += 1) {
            saving = play(map, input.charAt(i));
        }
        return saving;
    }


    public boolean play(MapGenerator map, char cmd) {
        if (cmd != ':' && cmd != 'Q') {
            map.player.move(map.world, cmd);
        }
        else {
            if (cmd =='Q') {
                return true;
            }
        }
        return false;
    }



}
