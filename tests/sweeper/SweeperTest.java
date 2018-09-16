package sweeper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SweeperTest {
    @Test
    public void getCoordsAroundTest() {
        Ranges.setSize(new Coord(12, 10));
        assertEquals(listOf(new Coord(4, 4), new Coord(6, 4),
                new Coord(4, 5), new Coord(6, 5),
                new Coord(5, 6), new Coord(5, 4)),
                Ranges.getCoordsAround(new Coord(5, 5)));
        assertEquals(listOf(new Coord(2, 0), new Coord(4, 0),
                new Coord(2, 1), new Coord(4, 1),
                new Coord(3, 2), new Coord(3, 0)),
                Ranges.getCoordsAround(new Coord(3, 1)));
        assertEquals(listOf(new Coord(10, 9), new Coord(11, 9)),
                Ranges.getCoordsAround(new Coord(11, 10)));
    }

    @Test
    public void bombPlaceTest() {
        Game game = new Game(9, 9, 0);
        Bomb bomb = game.getBomb();
        bomb.placeBombAt(new Coord(5, 4));
        assertEquals(Box.NUM1, bomb.get(new Coord(5, 5)));
        bomb.placeBombAt(new Coord(5, 6));
        assertEquals(Box.NUM2, bomb.get(new Coord(5, 5)));
        bomb.placeBombAt(new Coord(4, 4));
        assertEquals(Box.NUM3, bomb.get(new Coord(5, 5)));
        bomb.placeBombAt(new Coord(4, 5));
        assertEquals(Box.NUM4, bomb.get(new Coord(5, 5)));
        bomb.placeBombAt(new Coord(6, 4));
        assertEquals(Box.NUM5, bomb.get(new Coord(5, 5)));
        bomb.placeBombAt(new Coord(6, 5));
        assertEquals(Box.NUM6, bomb.get(new Coord(5, 5)));

        assertEquals(Box.NUM1, bomb.get(new Coord(5, 3)));
        assertEquals(Box.NUM1, bomb.get(new Coord(5, 7)));
        assertEquals(Box.NUM2, bomb.get(new Coord(4, 3)));
        assertEquals(Box.NUM2, bomb.get(new Coord(4, 6)));
        assertEquals(Box.NUM2, bomb.get(new Coord(6, 3)));
        assertEquals(Box.NUM2, bomb.get(new Coord(6, 6)));
        assertEquals(Box.NUM2, bomb.get(new Coord(3, 5)));
        assertEquals(Box.NUM2, bomb.get(new Coord(7, 5)));
    }

    @Test
    public void checkWinnerTest() {
        Game game = new Game(3, 5, 7);
        game.start();
        Bomb bomb = game.getBomb();
        Flag flag = game.getFlag();
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB)
                flag.toggleFlagedToBox(coord);
            else
                game.openBox(coord);
        }
        game.checkWinner();
        assertEquals(GameState.WINNER, game.getState());
    }

    @SafeVarargs
    private final <T> List<T> listOf(T... array) {
        List<T> list = new ArrayList<>(array.length);
        list.addAll(Arrays.asList(array));
        return list;
    }
}
