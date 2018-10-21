package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Game {

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        ter.initialize(WIDTH, HEIGHT);

        drawGUI();

        handleInput();

    }


    public void handleInput() {
        while (!StdDraw.hasNextKeyTyped()) {
            continue;
        }

        char key = Character.toUpperCase(StdDraw.nextKeyTyped());

        if (key == 'N') {
            String introduction = "Please enter a random seed (end with 'S'): ";
            drawString(introduction);
            String seedString = handleSeed(introduction);
            long seed = Long.parseLong(seedString.substring(0, seedString.length() - 1));
            MapGenerator map = new MapGenerator(WIDTH, HEIGHT, seed);
            map.buildMap();

            roundPlay(map);


        }

        else if (key == 'L') {
            MapGenerator map = loadMap();

            roundPlay(map);
        }

        else if (key == 'Q') {
            String quitString = "Good Bye!";
            drawString(quitString);
        }
    }

    public void roundPlay(MapGenerator map) {
        while (true) {
            ter.renderFrame(map.world);
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            String description = map.world[x][y].description();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(0, 2, description);
            StdDraw.show();


            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = Character.toUpperCase(StdDraw.nextKeyTyped());
            boolean quit = play(map, key);

            if (map.door.x == map.player.p.x && map.door.y == map.player.p.y) {
                StdDraw.clear();
                StdDraw.clear(Color.black);
                drawString("Congratulations! You succeeded to get out of the maze！");
                break;
            }

            if (quit == true) {
                StdDraw.pause(500);

                StdDraw.clear(Color.black);
                drawString("The game has been saved, bye! ");
                break;
            }
            StdDraw.show();
        }

    }

    public String handleSeed(String introduction) {
        String input = "";
        String display = introduction + input;
        drawString(display);

        while (Character.toUpperCase(display.charAt(display.length() - 1)) != 'S') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            display = introduction + input;
            drawString(display);
        }
        StdDraw.pause(500);

        return input;

    }
    public void drawString(String s) {


        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font stringFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(stringFont);
        StdDraw.setPenColor(Color.white);

        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);

        StdDraw.show();
    }

    public void drawGUI() {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        // Draw the GUI
        Font bigFont = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, HEIGHT - 5, "GET OUT! ");
        Font smallFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, midHeight, "New Game (N)");
        StdDraw.text(midWidth, midHeight - 3, "Load Game (L) ");
        StdDraw.text(midWidth, midHeight - 6, "Quit (Q)");


        StdDraw.show();


        // Draw the actual text
//        Font bigFont = new Font("Monaco", Font.BOLD, 30);
//        StdDraw.setFont(bigFont);
//        StdDraw.setPenColor(Color.green);
//        StdDraw.text(midWidth, midHeight, s);
//        StdDraw.show();
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


        ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = null;
        input = input.toUpperCase();
        if (input.charAt(0) == 'N') {
            long random;
            String seed = parseSeed(input);
            random = Long.parseLong(seed);
            MapGenerator map = new MapGenerator(WIDTH, HEIGHT, random);
            map.buildMap();
            parseControl(map, input, seed.length() + 2);

            finalWorldFrame = map.world;
            ter.renderFrame(finalWorldFrame);
        }
        else if(input.charAt(0) == 'L') {
            MapGenerator map = loadMap();
            parseControl(map, input, 1);
            finalWorldFrame = map.world;
            ter.renderFrame(finalWorldFrame);
        }

        return finalWorldFrame;
    }

    /** 处理输入的input，得到input中的seed（字符串形式）。 */
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


    /** 根据input对于player的指令对player进行操作。 */
    public void parseControl(MapGenerator map, String input, int start) {
        for (int i = start; i < input.length(); i += 1) {
            play(map, input.charAt(i));
        }
    }

    /** 既对player进行移动，又判断是否是以：q结尾。 */
    public boolean play(MapGenerator map, char cmd) {
        if (cmd != ':' && cmd != 'Q') {
            map.player.move(map.world, cmd);
        }
        else {
            if (cmd == 'Q') {
                quitSaving(map);
                return true;
            }
        }
        return false;
    }

    /** 在遇到：q后保存当前的map文件到map.ser. */
    void quitSaving(MapGenerator map) {
        File f = new File("./map.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(map);
            os.close();
            System.out.printf("Serialized data is saved in ./map.ser");
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }

    }
    /** 当这次的命令行开头是l时，加载上次保存的map文件。 */
    public static MapGenerator loadMap() {
        File f = new File("./map.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                MapGenerator loadMap = (MapGenerator) os.readObject();
                os.close();
                return loadMap;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new MapGenerator(123, WIDTH, HEIGHT);
    }
}
