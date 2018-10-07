package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Player {
    Position p;

    public Player(Position p) {
        this.p = p;
    }


    public void move(TETile[][] world, char cmd) {
        if (cmd == 'W') {
            if (p.y < world[0].length - 1 && !world[p.x][p.y + 1].equals(Tileset.WALL)) {
                world[p.x][p.y] = Tileset.FLOWER;
                p.y += 1;
            }

        }
        if (cmd == 'S') {
            if (p.y > 0 && !world[p.x][p.y - 1].equals(Tileset.WALL)) {
                world[p.x][p.y] = Tileset.FLOWER;
                p.y -= 1;

            }
        }

        if (cmd == 'A') {
            if (p.x > 0 && !world[p.x - 1][p.y].equals(Tileset.WALL)) {
                world[p.x][p.y] = Tileset.FLOWER;
                p.x -= 1;
            }
        }

        if (cmd == 'D') {
            if (p.x < world.length - 1 && !world[p.x + 1][p.y].equals(Tileset.WALL)) {
                world[p.x][p.y] =Tileset.FLOWER;
                p.x += 1;
            }
        }
        world[p.x][p.y] = Tileset.PLAYER;
    }
}
