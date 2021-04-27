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

@DisplayName("Testing Knight Class")
public class KingTest {

    private Board board;
    private King king;
    
    @BeforeEach
    void setUp() throws Exception{
        board = new Board("                                                K               ");
        king = new King(true);
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
        assertTrue(king.canMove(board, board.getBox(1, 1), board.getBox(endX, endY)), "canMove to ("+endX+", "+endY+")");
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1",
        "4, 4",
        "7, 7"
    })
    void testCantMove(int endX, int endY) throws Exception{
        assertFalse(king.canMove(board, board.getBox(1, 1), board.getBox(endX, endY)), "Should not be able to move to ("+endX+", "+endY+")");
    }

    @Nested
    @DisplayName("With adjacent pieces")
    class AdjacentPieces {

        @BeforeEach
        void setUp() throws Exception{
            board = new Board("                                                KP              ");
            king = new King(true);
        }

        @Test
        void testCanMove(){
            
        }
    }

}
