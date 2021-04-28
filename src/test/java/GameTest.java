import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import chess.Game;
import chess.Move;
import chess.Spot;
import chess.Player.HumanPlayer;

public class GameTest {
    
    private Game game;
    private HumanPlayer p1, p2;

    @BeforeEach
    void setUp(){
        HumanPlayer p1 = new HumanPlayer(true);
        HumanPlayer p2 = new HumanPlayer(false);
        game = new Game(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "a3, 1, 0, 2, 0", 
        "b3, 1, 1, 2, 1",
        "c3, 1, 2, 2, 2",
        "d3, 1, 3, 2, 3",
        "e3, 1, 4, 2, 4",
        "f3, 1, 5, 2, 5",
        "g3, 1, 6, 2, 6",
        "h3, 1, 7, 2, 7",
        "a4, 1, 0, 3, 0",
        "b4, 1, 1, 3, 1",
        "c4, 1, 2, 3, 2",
        "d4, 1, 3, 3, 3",
        "e4, 1, 4, 3, 4",
        "f4, 1, 5, 3, 5",
        "g4, 1, 6, 3, 6",
        "h4, 1, 7, 3, 7",
        "Na3, 0, 1, 2, 0",
        "Nc3, 0, 1, 2, 2",
        "Nf3, 0, 6, 2, 5",
        "Ng3, 0, 6, 2, 7",
    })
    void testSANtoMove(String SAN, int startX, int startY, int endX, int endY) throws Exception{
        Move move = game.sanToMove(SAN);
        Spot start = move.getStart();
        Spot end = move.getEnd();
        assertEquals(startX, start.getX(), "Start x should equal: "+startX);
        assertEquals(startY, start.getY(), "Start y should equal: "+startY);
        assertEquals(endX, end.getX(), "End x should equal: "+endX);
        assertEquals(endY, end.getY(), "End y should equal: "+endY);
    }

    @Test
    void testSANtoOneMove() throws Exception{
        Move move = game.sanToMove("e4");
        Spot start = move.getStart();
        Spot end = move.getEnd();
        assertEquals(1, start.getX(), "Start x should equal: "+1);
        assertEquals(4, start.getY(), "Start y should equal: "+4);
        assertEquals(3, end.getX(), "End x should equal: "+3);
        assertEquals(4, end.getY(), "End y should equal: "+4);
    }

    @ParameterizedTest
    @CsvSource({
        "a1, 0, 0",
        "a2, 0, 1",
        "a3, 0, 2",
        "a4, 0, 3",
        "a5, 0, 4",
        "a6, 0, 5",
        "a7, 0, 6",
        "a8, 0, 7",
        "b1, 1, 0",
        "b2, 1, 1",
        "c3, 2, 2",
        "d4, 3, 3",
        "e5, 4, 4",
        "f6, 5, 5",
        "g7, 6, 6",
        "h8, 7, 7",
    })
    void testSANtoSpot(String SAN, int x, int y){
        Spot spot = game.sanToSpot(SAN);
        assertEquals(x, spot.getX(), SAN+" should be at X="+x);
        assertEquals(y, spot.getY(), SAN+" should be at Y="+y);
    }

    @ParameterizedTest
    @CsvSource({
        "a4, 2",
        "b4, 2",
        "c4, 2",
        "d4, 2",
        "e4, 2",
        "f4, 2",
        "g4, 2",
        "h4, 2",
        "a3, 2",
        "b3, 2",
        "c3, 2",
        "d3, 2",
        "e3, 2",
        "f3, 2",
        "g3, 2",
        "h3, 2",
    })
    void testWhiteFindPawnSrcRank(String dest, char src) throws Exception{
        assertEquals(src, game.findPawnSrcRank(dest,true), dest+" should return "+src+" for standard setup");
    }

    @ParameterizedTest
    @CsvSource({
        "a5, 7",
        "b5, 7",
        "c5, 7",
        "d5, 7",
        "e5, 7",
        "f5, 7",
        "g5, 7",
        "h5, 7",
        "a6, 7",
        "b6, 7",
        "c6, 7",
        "d6, 7",
        "e6, 7",
        "f6, 7",
        "g6, 7",
        "h6, 7",
    })
    void testBlackFindPawnSrcRank(String dest, char src) throws Exception{
        assertEquals(src, game.findPawnSrcRank(dest,false), dest+" should return "+src+" for standard setup");
    }

}
