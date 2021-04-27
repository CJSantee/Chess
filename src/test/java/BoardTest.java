import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

    @Test
    void testBoardException() throws Exception{
        Throwable exception = assertThrows(Exception.class, () -> { board = new Board(" "); });
        assertEquals("Invalid input String", exception.getMessage());
    }

    @Test
    void testFENConstructor() throws Exception{
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        testStandardSetup();
    }

    @Test
    void testDefaultConstructor() throws Exception{
        board = new Board();
        testStandardSetup();
    }

    @Test
    void testResetBoard() throws Exception{
        board = new Board("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        board.resetBoard();
        testStandardSetup();
    }

    @Test
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

        @Test
        void testGetBox() throws Exception{
            Throwable exception = assertThrows(Exception.class, () -> board.getBox(-1, -1));
            assertThrows(Exception.class, () -> board.getBox(8, 4), "x value out of bound should throw exception");
            assertThrows(Exception.class, () -> board.getBox(4, 8), "y value out of bound should throw exception");
            assertEquals("Index out of bound", exception.getMessage(), "should display \"Index out of bound\" message");
            assertTrue(board.getBox(0, 0) instanceof Spot, "should return Spot object");
        }

        @Test
        void testFen(){
            assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", board.fen(), "Should export standard setup in FEN");
        }

    }

    

}
