import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import chess.Board;
import chess.Pieces.King;
import chess.Pieces.Rook;

@DisplayName("Testing Knight Class")
public class KingTest {

    private Board board;
    private King king;
    private Rook queensideRook;
    private Rook kingsideRook;
    
    @Nested
    @DisplayName("Test unobstructed King movements")
    class OpenBoard{

        @BeforeEach
        void setUp() throws Exception{
            board = new Board("8/8/8/8/8/8/1K6/8");
            king = (King) board.getBox(1, 1).getPiece();
        }

        @ParameterizedTest
        @CsvSource({
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 2",
            "2, 0",
            "2, 1",
            "2, 2"
        })
        void testCanMove(int endX, int endY) throws Exception{
            assertTrue(king.canMove(board, board.getBox(1, 1), board.getBox(endX, endY)), "Should be able to move to ("+endX+", "+endY+")");
        }

        @ParameterizedTest
        @CsvSource({
            "1, 1",
            "4, 4",
            "7, 7"
        })
        void testCanNotMove(int endX, int endY) throws Exception{
            assertFalse(king.canMove(board, board.getBox(1, 1), board.getBox(endX, endY)), "Should not be able to move to ("+endX+", "+endY+")");
        }
    }

    @Nested
    @DisplayName("With adjacent pieces")
    class AdjacentPieces {

        @BeforeEach
        void setUp() throws Exception{
            board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
            king = (King) board.getBox(0, 4).getPiece();
        }

        @ParameterizedTest
        @CsvSource({
            "0, 3",
            "0, 4",
            "0, 5",
            "1, 3",
            "1, 4",
            "1, 5"
        })
        void testCanNotMove(int endX, int endY) throws Exception{
            assertFalse(king.canMove(board, board.getBox(0, 4), board.getBox(endX, endY)), "Should not be able to move to ("+endX+", "+endY+") because of obstruction");
        }
    }

    @Nested
    @DisplayName("Castling")
    class Castling {

        @BeforeEach
        void setUp() throws Exception{
            board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R3K2R");
            king = (King) board.getBox(0, 4).getPiece();
            queensideRook = (Rook) board.getBox(0,0).getPiece();
            kingsideRook = (Rook) board.getBox(0,7).getPiece();
        }

        @Nested
        @DisplayName("Successful Castling Moves")
        class canCastle{

            @Test
            void canCastleKingside() throws Exception{
                assertTrue(king.canCastle(), "King canCastle() should be True");
                assertFalse(kingsideRook.hasMoved(), "Rook hasMoved() should be False");
                assertTrue(king.canMove(board, board.getBox(0, 4), board.getBox(0, 6)), "Should be able to move to (0, 6) by Castling Kingside");
            }

            @Test
            void canCastleQueenside() throws Exception{
                assertTrue(king.canCastle(), "King canCastle() should be True");
                assertFalse(queensideRook.hasMoved(), "Rook hasMoved() should be False");
                assertTrue(king.canMove(board, board.getBox(0, 4), board.getBox(0, 2)), "Should be able to move to (0, 2) by Castling Queenside");
            }    

        }

        @Nested
        @DisplayName("Unsuccessful Castling Moves")
        class canNotCastle{

            @Test
            @DisplayName("King Moved")
            void canNotCastleKingsideKingMoved() throws Exception{
                king.setCanCastle(false);
                assertFalse(king.canCastle(), "King canCastle() should be False");
                assertFalse(king.canMove(board, board.getBox(0, 4), board.getBox(0, 6)), "Should not be able to move to (0, 6) because King has moved");
            }
    
            @Test
            @DisplayName("King Moved")
            void canNotCastleQueensideKingMoved() throws Exception{
                king.setCanCastle(false);
                assertFalse(king.canCastle(), "King canCastle() should be False");
                assertFalse(king.canMove(board, board.getBox(0, 4), board.getBox(0, 2)), "Should not be able to move to (0, 2) because King has moved");
            }

            @Test
            @DisplayName("Kingside Rook Moved")
            void canNotCastleKingsideRookMoved() throws Exception{
                kingsideRook.setHasMoved(true);
                assertTrue(kingsideRook.hasMoved(), "kingside Rook hasMoved() should be True");
                assertFalse(king.canMove(board, board.getBox(0, 4), board.getBox(0, 6)), "Should not be able to move to (0, 6) because Rook has moved");
            }

            @Test
            @DisplayName("Queenside Rook Moved")
            void canNotCastleQueensideRookMoved() throws Exception{
                queensideRook.setHasMoved(true);
                assertTrue(queensideRook.hasMoved(), "queenside Rook hasMoved() should be True");
                assertFalse(king.canMove(board, board.getBox(0, 4), board.getBox(0, 2)), "Should not be able to move to (0, 2) because King has moved");
            }

        }

    }

    @Nested
    @DisplayName("Test Moving into Check")
    class TestCheck{

        @BeforeEach
        void setUp() throws Exception{
            board = new Board("8/8/8/8/7q/4K3/8/8");
            king = (King) board.getBox(2, 4).getPiece();
        }

        @ParameterizedTest
        @CsvSource({
            "2, 3",
            "2, 5",
            "1, 3",
            "1, 4",
            "1, 5"
        })
        void testCanMove(int endX, int endY) throws Exception{
            assertTrue(king.canMove(board, board.getBox(2, 4), board.getBox(endX, endY)), "Should be able to move to ("+endX+", "+endY+")");
        }

        @ParameterizedTest
        @CsvSource({
            "3, 3",
            "3, 4",
            "3, 5"
        })
        void testCanNotMove(int endX, int endY) throws Exception{
            assertFalse(king.canMove(board, board.getBox(2, 4), board.getBox(endX, endY)), "Should not be able to move to ("+endX+", "+endY+") because of Check by Queen");
        }

    }

    @Nested
    @DisplayName("Test checkmate")
    class TestCheckmate{

        @ParameterizedTest
        @CsvSource({
            "0, 3",
            "0, 5",
            "1, 3",
            "1, 4",
            "1, 5"
        })
        void testRookCheckmate(int endX, int endY) throws Exception{
            board = new Board("8/8/8/8/8/8/7r/4K2r");
            king = (King) board.getBox(0, 4).getPiece();
            assertFalse(king.canMove(board, board.getBox(0, 4), board.getBox(endX, endY)), "Should not be able to move to ("+endX+", "+endY+") because of Checkmate by Rooks");
        }

    }

}
