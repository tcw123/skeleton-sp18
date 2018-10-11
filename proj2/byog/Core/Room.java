/** 作为地图中的房间对象，属性有房间左下角的坐标，以及房间的长和宽。 */

package byog.Core;


/** a room is a rectangular wall with one or two breaks,
 * the inside space is floor
 */

public class Room implements Comparable<Room> {
    Position roomPos;
    int no;
    int x1;
    int x2;
    int y1;
    int y2;
    int width;
    int height;

    public Room(int no, Position p, int w, int h) {
        this.no = no;
        roomPos = p;
        width = w;
        height = h;
        x1 = p.x;
        y1 = p.y;
        x2 = p.x + w - 1;
        y2 = p.y + h - 1;

    }

    @Override
    public int compareTo(Room tmproom) {
        return this.x2 - tmproom.x1;
    }
}
