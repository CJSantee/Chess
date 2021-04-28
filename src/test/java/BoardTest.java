import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import chess.Board;
import chess.Spot;
import chess.Pieces.Bishop;
import chess.Pieces.King;
import chess.Pieces.Knight;
import chess.Pieces.Pawn;
import chess.Pieces.Queen;
import chess.Pieces.Rook;

public class BoardTest {
    
    private Board board;

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "8/8",
        "8/8/8/8/9/8/8/8",
        "4P4/8/8/8/8/8/8/8",
        "4T3/8/8/8/8/8/8/8",
        "PPPPPPPPP/8/8/8/8/8/8/8",
        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    })
    void testBoardException(String input) throws Exception{
        Throwable exception = assertThrows(Exception.class, () -> { board = new Board(input); },input+" Should throw an Exception" );
        assertEquals("Invalid input String", exception.getMessage(), input+" Should respond with Invalid input msg");
    }

    @Test
    void testFENConstructor() throws Exception{
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        testStandardSetup();
    }

    @Test
    void testDefaultConstructor() throws Exception{
        board = new Board();
        testStandardSetup();
    }

    @Test
    void testResetBoard() throws Exception{
        board = new Board("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR");
        board.resetBoard();
        testStandardSetup();
    }

    void testStandardSetup() throws Exception{
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                assertNotNull(board.getBox(i, j), "Index (" + i + ", " + j + ") should not be null");
            }
        }
        assertTrue(board.getBox(0, 0).getPiece() instanceof Rook, "(0, 0) should be a Rook");
        assertTrue(board.getBox(0, 1).getPiece() instanceof Knight, "(0, 1) should be a Knight");
        assertTrue(board.getBox(0,2).getPiece() instanceof Bishop, "(0, 2) should be a Bishop");
        assertTrue(board.getBox(0,3).getPiece() instanceof Queen, "(0, 3) should be a Queen");
        assertTrue(board.getBox(0,4).getPiece() instanceof King, "(0, 4) should be a King");
        assertTrue(board.getBox(0,5).getPiece() instanceof Bishop, "(0, 5) should be a Bishop");
        assertTrue(board.getBox(0,6).getPiece() instanceof Knight, "(0, 6) should be a Knight");
        assertTrue(board.getBox(0,7).getPiece() instanceof Rook, "(0, 7) should be a Rook");

        for(int i = 0; i < 8; i++){
            assertTrue(board.getBox(1, i).getPiece() instanceof Pawn, "(1, "+i+") should be a Pawn");
        }
        for(int i = 0; i < 8; i++){
            assertTrue(board.getBox(6, i).getPiece() instanceof Pawn, "(6, "+i+") should be a Pawn");
        }
        assertTrue(board.getBox(7, 0).getPiece() instanceof Rook, "(7, 0) should be a Rook");
        assertTrue(board.getBox(7, 1).getPiece() instanceof Knight, "(7, 1) should be a Knight");
        assertTrue(board.getBox(7,2).getPiece() instanceof Bishop, "(7, 2) should be a Bishop");
        assertTrue(board.getBox(7,3).getPiece() instanceof Queen, "(7, 3) should be a Queen");
        assertTrue(board.getBox(7,4).getPiece() instanceof King, "(7, 4) should be a King");
        assertTrue(board.getBox(7,5).getPiece() instanceof Bishop, "(7, 5) should be a Bishop");
        assertTrue(board.getBox(7,6).getPiece() instanceof Knight, "(7, 6) should be a Knight");
        assertTrue(board.getBox(7,7).getPiece() instanceof Rook, "(7, 7) should be a Rook");

        for(int r = 2; r < 6; r++){
            for(int c = 0; c < 8; c++){
                assertNull(board.getBox(r, c).getPiece(), "("+r+", "+c+") should not contain a piece");
            }
        }
    }
    
    @Nested
    @DisplayName("Functions with Standard Setup")
    class BoardFunctions {

        @BeforeEach
        void setUp(){
            board = new Board();
        }

        @ParameterizedTest
        @CsvSource({
            "0, 0",
            "0, 1",
            "0, 2",
            "0, 3",
            "0, 4",
            "0, 5",
            "0, 6",
            "0, 7",
            "1, 0",
            "1, 1",
            "1, 2",
            "1, 3",
            "1, 4",
            "1, 5",
            "1, 6",
            "1, 7",
            "6, 0",
            "6, 1",
            "6, 2",
            "6, 3",
            "6, 4",
            "6, 5",
            "6, 6",
            "6, 7",
            "7, 0",
            "7, 1",
            "7, 2",
            "7, 3",
            "7, 4",
            "7, 5",
            "7, 6",
            "7, 7",
        })
        void testHasPiece(int x, int y) throws Exception{
            assertTrue(board.getBox(x, y).hasPiece());
        }

        @ParameterizedTest
        @CsvSource({
            "2, 0",
            "2, 1",
            "2, 2",
            "2, 3",
            "2, 4",
            "2, 5",
            "2, 6",
            "2, 7",
            "3, 0",
            "3, 1",
            "3, 2",
            "3, 3",
            "3, 4",
            "3, 5",
            "3, 6",
            "3, 7",
            "4, 0",
            "4, 1",
            "4, 2",
            "4, 3",
            "4, 4",
            "4, 5",
            "4, 6",
            "4, 7",
            "5, 0",
            "5, 1",
            "5, 2",
            "5, 3",
            "5, 4",
            "5, 5",
            "5, 6",
            "5, 7",
        })
        void testHasPieceFalse(int x, int y) throws Exception{
            assertFalse(board.getBox(x, y).hasPiece());
        }

        @Test
        void testGetBox() throws Exception{
            Throwable exception = assertThrows(Exception.class, () -> board.getBox(-1, -1));
            assertThrows(Exception.class, () -> board.getBox(8, 4), "x value out of bound should throw exception");
            assertThrows(Exception.class, () -> board.getBox(4, 8), "y value out of bound should throw exception");
            assertEquals("Index out of bound", exception.getMessage(), "should display \"Index out of bound\" message");
            assertTrue(board.getBox(0, 0) instanceof Spot, "should return Spot object");
        }

        @Test
        void testGetBoxByPiece() throws Exception{
            King whiteKing = (King) board.getBox(0, 4).getPiece();
            King blackKing = (King) board.getBox(7, 4).getPiece();
            assertEquals(board.getBox(0, 4), board.getBox(whiteKing), "Spot (0, 4) should be returned by searching the location of the whiteKing");
            assertEquals(board.getBox(7, 4), board.getBox(blackKing), "Spot (7, 4) should be returned by searching the location of the blackKing");
        }

        @Test
        void testFen(){
            assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", board.fen(), "Should export standard setup in FEN");
        }

    }

}
