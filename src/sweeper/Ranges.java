package sweeper;

import java.util.ArrayList;
import java.util.Random;

class Ranges {
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();
    static void setSize(Coord _size){
        size = _size;
        allCoords = new ArrayList<>();
        for (int y = 0; y < size.y; y++)
            for (int x = 0; x < size.x; x++)
                allCoords.add(new Coord(x,y));
    }

    static Coord getSize() {
        return size;
    }

    static ArrayList<Coord> getAllCoords(){
        return allCoords;
    }

    static boolean inRange(Coord coord){
        return coord.x >= 0 && coord.x < size.x &&
                coord.y >= 0 && coord.y < size.y;
    }

    static Coord getRandomCoord(){
        return new Coord(random.nextInt(size.x),
                         random.nextInt(size.y));
    }

    static ArrayList<Coord> getCoordsAround(Coord coord){
        Coord around;
        ArrayList<Coord> list = new ArrayList<>();
        int add = ~coord.x & 1;
        for (int y = coord.y - 1 + add; y <= coord.y + add; y ++)
            for (int x = coord.x - 1; x <= coord.x + 1; x ++) {
                if (x == coord.x) continue;
                if (inRange(around = new Coord(x, y)))
                    if(!around.equals(coord))
                        list.add(around);
            }
        if (inRange(around = new Coord(coord.x, coord.y + 1)))
            list.add(around);
        if (inRange(around = new Coord(coord.x, coord.y - 1)))
            list.add(around);
        return list;
    }
}
