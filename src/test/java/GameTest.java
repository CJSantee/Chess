import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import chess.Game;
import chess.Move;
import chess.Spot;

public class GameTest {
    
    private Game game;

    @BeforeEach
    void setUp(){
        game = new Game();
    }

    @ParameterizedTest
    @CsvSource({
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
        "Nh3, 0, 6, 2, 7",
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
    @CsvFileSource(resources = "/sanCords.csv", numLinesToSkip = 1)
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

    @Nested
    @DisplayName("Test Pawn Moves")
    class testPawnMoves{

        @BeforeEach
        void setUp() throws Exception{
            game = new Game("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 1");
        }

        @Test
        void testPawnCapture() throws Exception{
            Move move = game.sanToMove("exd5");
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(3, start.getX(), "Start x should equal: "+3);
            assertEquals(4, start.getY(), "Start y should equal: "+4);
            assertEquals(4, end.getX(), "End x should equal: "+4);
            assertEquals(3, end.getY(), "End y should equal: "+3);
        }
    }

    @Nested
    @DisplayName("Test Rook Moves")
    class testRookMoves{

        @BeforeEach
        void setUp() throws Exception{
            game = new Game("7r/8/8/8/8/8/8/b6R w KQkq - 0 1");
        }

        @ParameterizedTest
        @CsvSource({
            "Rb1, 0, 7, 0, 1",
            "Rh4, 0, 7, 3, 7",
            "Rh7, 0, 7, 6, 7"
        })
        void testRookMove(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

        @ParameterizedTest
        @CsvSource({
            "Rxh8, 0, 7, 7, 7",
            "Rxa1, 0, 7, 0, 0"
        })
        void testRookCapture(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

    }

    @Nested
    @DisplayName("Test Knight Moves")
    class testKnightMoves{
        
        @BeforeEach
        void setUp() throws Exception{
            game = new Game("8/8/4N3/2p5/3p4/8/8/6N1 w KQkq - 0 1");
        }

        @ParameterizedTest
        @CsvSource({
            "Nc7, 5, 4, 6, 2",
            "Nd8, 5, 4, 7, 3",
            "Nf8, 5, 4, 7, 5",
            "Ng7, 5, 4, 6, 6",
            "Ng5, 5, 4, 4, 6",
            "Nf4, 5, 4, 3, 5",
            "Ne2, 0, 6, 1, 4",
            "Nf3, 0, 6, 2, 5",
            "Nh3, 0, 6, 2, 7"
        })
        void testKnightMove(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

        @ParameterizedTest
        @CsvSource({
            "Nxc5, 5, 4, 4, 2",
            "Nxd4, 5, 4, 3, 3"
        })
        void testKnightCapture(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

    }

    @Nested
    @DisplayName("Test Bishop Moves")
    class testBishopMoves{
        
        @BeforeEach
        void setUp() throws Exception{
            game = new Game("r7/7b/8/8/4B3/8/6p1/1n6 w KQkq - 0 1");
        }

        @ParameterizedTest
        @CsvSource({
            "Bb7, 3, 4, 6, 1",
            "Bg6, 3, 4, 5, 6",
            "Bf3, 3, 4, 2, 5",
            "Bc2, 3, 4, 1, 2"
        })
        void testBishopMove(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

        @ParameterizedTest
        @CsvSource({
            "Bxa8, 3, 4, 7, 0",
            "Bxh7, 3, 4, 6, 7",
            "Bxg2, 3, 4, 1, 6",
            "Bxb1, 3, 4, 0, 1"
        })
        void testBishopCapture(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

    }

    @Nested
    @DisplayName("Test Queen Moves")
    class testQueenMoves{
        
        @BeforeEach
        void setUp() throws Exception{
            game = new Game("r3n3/7p/8/8/p3Q2q/8/8/1n2b2b w KQkq - 0 1");
        }

        @ParameterizedTest
        @CsvSource({
            "Qb7, 3, 4, 6, 1",
            "Qe7, 3, 4, 6, 4",
            "Qg6, 3, 4, 5, 6",
            "Qg4, 3, 4, 3, 6",
            "Qg2, 3, 4, 1, 6",
            "Qe2, 3, 4, 1, 4",
            "Qc2, 3, 4, 1, 2",
            "Qb4, 3, 4, 3, 1"
        })
        void testQueenMove(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

        @ParameterizedTest
        @CsvSource({
            "Qxa8, 3, 4, 7, 0",
            "Qxe8, 3, 4, 7, 4",
            "Qxh7, 3, 4, 6, 7",
            "Qxh4, 3, 4, 3, 7",
            "Qxh1, 3, 4, 0, 7",
            "Qxe1, 3, 4, 0, 4",
            "Qxb1, 3, 4, 0, 1",
            "Qxa4, 3, 4, 3, 0"
        })
        void testQueenCapture(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }
    
    }

    @Nested
    @DisplayName("Test King Moves")
    class testKingMoves{
        
        @BeforeEach
        void setUp() throws Exception{
            game = new Game("8/8/8/3n1n2/4K3/3p1p2/8/8 w KQkq - 0 1");
        }

        @ParameterizedTest
        @CsvSource({
            "Ke5, 3, 4, 4, 4",
            "Kf4, 3, 4, 3, 5",
            "Ke3, 3, 4, 2, 4",
            "Kd4, 3, 4, 3, 3"
        })
        void testKingMove(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }

        @ParameterizedTest
        @CsvSource({
            "Kxd5, 3, 4, 4, 3",
            "Kxf5, 3, 4, 4, 5",
            "Kxf3, 3, 4, 2, 5",
            "Kxd3, 3, 4, 2, 3"
        })
        void testKingCapture(String san, int startX, int startY, int endX, int endY) throws Exception{
            Move move = game.sanToMove(san);
            Spot start = move.getStart();
            Spot end = move.getEnd();
            assertEquals(startX, start.getX(), "Start x should equal: "+startX+" for "+san);
            assertEquals(startY, start.getY(), "Start y should equal: "+startY+" for "+san);
            assertEquals(endX, end.getX(), "End x should equal: "+endX+" for "+san);
            assertEquals(endY, end.getY(), "End y should equal: "+endY+" for "+san);
        }
    
    }

}
