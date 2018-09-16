package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs){
        this.totalBombs = totalBombs;
        bombMap = new Matrix(Box.ZERO);
    }

    void start (){
        for (int j = 0; j < totalBombs; j ++)
            placeBomb();
    }

    Box get (Coord coord){
        return bombMap.get(coord);
    }

    private void placeBomb(){
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            placeBombAt(coord);
            break;
        }
    }

    void placeBombAt(Coord coord) {
        bombMap.set(coord, Box.BOMB);
        incNumbersAroundBomb(coord);
    }

    private void incNumbersAroundBomb (Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
