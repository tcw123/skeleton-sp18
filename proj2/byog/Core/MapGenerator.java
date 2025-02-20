/** 创建出游戏的主题map， map的主体应该分为三部分，
 * 一个是room， 一个是走廊， 一个是墙。 */

package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MapGenerator implements java.io.Serializable {
    private static Random RANDOM;
    private int WIDTH;
    private int HEIGHT;
    private static int numRoom;
    TETile[][] world;

    Position door;

    Player player;

    /** MapGenerator 的构造函数，设定map的长宽以及RANDOM的值。 */
    MapGenerator(int w, int h, long random) {
        WIDTH = w;
        HEIGHT = h;
        RANDOM = new Random(random);//根据Game中给出的seed得到伪随机数RANDOM
        numRoom = (int) RandomUtils.gaussian(RANDOM, 25, 5);
    }

    /** MapGenerator 的主要函数，来完成生成一个map的各项工作。 */
    void buildMap() {
        world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        ArrayList<Room> roomlist = makeRoom(world, numRoom);

        connectRooms(roomlist);

        buildWall(world);

        door = addDoor(world);

        player = addPlayer();


    }

    /** 在适当的位置随机添加player对象。 */
    Player addPlayer() {
        int px = 0;
        int py = 0;
        boolean add = false;
        while (!add) {
            px = RandomUtils.uniform(RANDOM, 0, WIDTH);
            py = 1;
            while (world[px][py] != Tileset.FLOOR && py < HEIGHT - 1) {
                py += 1;
            }
            if (world[px][py] == Tileset.FLOOR) {
                add = true;
            }
            world[px][py] = Tileset.PLAYER;
        }

        return new Player(new Position(px, py));
    }

    /** 在适当的位置添加一个door。 */
    Position addDoor(TETile[][] world) {
        int dx = 0;
        int dy = 0;
        boolean add = false;
        while (!add) {
            dx = RandomUtils.uniform(RANDOM, 0, WIDTH);
            dy = 1;
            while (world[dx][dy] != Tileset.WALL && dy < HEIGHT - 1) {
                dy += 1;
            }
            if (checkNeighbor(world, dx, dy, 2) && world[dx][dy] == Tileset.WALL) {
                world[dx][dy] = Tileset.LOCKED_DOOR;
                add = true;
            }
        }
        return new Position(dx, dy);
    }


    /** 为构造好的room和走廊添加墙。 */
    void buildWall(TETile[][] world) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                if (world[i][j] == Tileset.NOTHING && checkNeighbor(world, i, j, 1)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /** 判断所给位置的四周的Floor个数。 */
    boolean checkNeighbor(TETile[][] world, int x, int y, int numFloors) {
        int checked = 0;
        int xLeft = Math.max(x - 1, 0);
        int xRight = Math.min(x + 1, WIDTH - 1);
        int yBottom = Math.max(y - 1, 0);
        int yTop = Math.min(y + 1, HEIGHT - 1);
        for (int i = xLeft; i <= xRight; i += 1) {
            for (int j = yBottom; j <= yTop; j += 1) {
                if (world[i][j] == Tileset.FLOOR) {
                    checked += 1;
                    if (checked == numFloors) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** 将创建的对象添加到map中。 */
    void makeSpace(TETile[][] world, Position p, int w, int h, TETile t) {
        for (int i = 0; i < w; i += 1) {
            for (int j = 0; j < h; j += 1) {
                if (world[i + p.x][j + p.y] == Tileset.NOTHING) {
                    world[i + p.x][j + p.y] = t;
                }
            }
        }
    }

    /** 将创建出得到的room一一连接形成走廊。 */
    void connectRooms(ArrayList<Room> roomlist) {
        for (int i = 0; i < roomlist.size() - 1; i += 1) {
            Room ra = roomlist.get(i);
            Room rb = roomlist.get(i + 1);
            Position pa = new Position(ra.x1 + RANDOM.nextInt(ra.width),
                    ra.y1 + RANDOM.nextInt(ra.height));
            Position pb = new Position(rb.x1 + RANDOM.nextInt(rb.width),
                    rb.y1 + RANDOM.nextInt(rb.height));
            connectPositions(pa, pb);
        }
    }
    /** 连接坐标和坐标。 */
    void connectPositions(Position pa, Position pb) {
        if (pa.x == pb.x) {
            makeSpace(world, new Position(pa.x, Math.min(pa.y, pb.y)),
                    1, Math.abs(pa.y - pb.y) + 1, Tileset.FLOOR);
        }
        else if (pa.y == pb.y) {
            makeSpace(world, new Position(Math.min(pa.x, pb.x), pa.y),
                    Math.abs(pa.x - pb.x) + 1, 1, Tileset.FLOOR);
        }
        else {
            Position tmpPos = new Position(pa.x, pb.y);
            connectPositions(pa, tmpPos);
            connectPositions(pb, tmpPos);
        }
    }

    /** 在空白的map图中创建出numRoom个room。 */
    ArrayList<Room> makeRoom(TETile[][] world, int numRoom) {
        int countRooms = 0;
        ArrayList<Room> roomlist = new ArrayList<>();
        while (countRooms < numRoom) {
            int px = RandomUtils.uniform(RANDOM, 2, WIDTH - 2);
            int py = RandomUtils.uniform(RANDOM, 2, HEIGHT - 2);
            int w = (int) Math.max(Math.min(RandomUtils.gaussian(RANDOM, 5, 4),
                    WIDTH - px - 1), 2);
            int h = (int) Math.max(Math.min(RandomUtils.gaussian(RANDOM, 5, 4),
                    HEIGHT - py - 1), 2);
            Room ra = new Room(countRooms, new Position(px, py), w, h);
            if (!overlap(roomlist, ra)) {
                roomlist.add(ra);
                makeSpace(world, new Position(px, py), w, h, Tileset.FLOOR);
            }
            Collections.sort(roomlist);
            countRooms += 1;

        }

        return roomlist;
    }

    /** 判断创建出的room是否有重叠。
     * https://stackoverflow.com/questions/306316/determine-if-two-rectangles-overlap-each-other. */
    boolean overlap(ArrayList<Room> roomlist, Room ra) {

        for (Room rb : roomlist) {
            if (ra.x1 < rb.x2 && ra.x2 > rb.x1 && ra.y1 < rb.y2 + 1 && ra.y2 > rb.y1 + 1) {
                return true;
            }
        }
        return false;

    }

}
