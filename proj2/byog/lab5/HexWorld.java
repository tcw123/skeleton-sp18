package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    public static int hexagonSize;
    public static int leftStart = 0;

    /** Make the black background. */
    public static void initialize(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    /** Add a Hexagon to the world. */
    public static void drawHex(TETile[][] world, int leftStart, int BottomY, int size, TETile t) {
        if (size < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        int PosX = leftStart + (size - 1);
        int PosY = BottomY;

        for (int yi = 0; yi < 2 * size; yi++) {
            int xStart = PosX + hexRowOffset(size, yi);
            int yStart = PosY + yi;
            int rowWidth = hexRowWidth(size, yi);
            addROw(world, xStart, yStart, rowWidth, t);
        }
    }
    /** Add a row of the hexagon. */
    private static void addROw(TETile[][] world, int PosX, int PosY, int rowWidth, TETile t) {
        for (int xi = 0; xi < rowWidth; xi++) {
            int x = PosX + xi;
            int y = PosY;
            world[x][y] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }
    //参数是size和第几行，返回这一行的字母数量,i=0是最底一行。
    private static int hexRowWidth(int s, int i) {
        int number = i;
        if (i >= s) {
            number = (2 * s) - i - 1;
        }
        return s + 2 * number;
    }
    //Computes relative x coordinate of the leftmost tile in the ith row of a hexagon
    private static int hexRowOffset(int s, int i) {
        int number = i;
        if (i >= s) {
            number = (2 * s) - i - 1;
        }
        return -number;
    }

    /** Return random shape of the tile. */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.LOCKED_DOOR;
            case 5: return Tileset.UNLOCKED_DOOR;
            default: return Tileset.NOTHING;
        }
    }

    public static void drawColumnHex(TETile[][] world, int N, int size, TETile[] tile) {
        int Ypush = size * 2; // 六边形的长度.
        int statPosY = calculateSpace(N);
        for (int i = 0; i < N; i+=1) {
            drawHex(world, leftStart, statPosY + Ypush * i, size, tile[i]);
        }

        changeLeftStart();
    }

    /** 计算这一列六边形最底部开始位置的y坐标。 */
    public static int calculateSpace(int N) {
        int hexLines = hexagonSize * 2;
        int totalLines = N * hexLines;
        return (HEIGHT - totalLines) / 2;
    }

    public static void changeLeftStart() {
        leftStart += 2 * hexagonSize - 1;
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        hexagonSize = 3;

        TETile[][] hexTile = new TETile[WIDTH][HEIGHT];
        initialize(hexTile);

        TETile[] skins = new TETile[]{randomTile(), randomTile(), randomTile()};
        drawColumnHex(hexTile, 3, hexagonSize, skins);

        skins = new TETile[]{randomTile(), randomTile(), randomTile(), randomTile()};
        drawColumnHex(hexTile, 4, hexagonSize, skins);

        skins = new TETile[]{randomTile(), randomTile(), randomTile(), randomTile(), randomTile()};
        drawColumnHex(hexTile, 5, hexagonSize, skins);



        ter.renderFrame(hexTile);
    }
}
