import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import chess.Board;

public class SpotTest {

    private Board board;

    @BeforeEach
    void setUp(){
        board = new Board();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/sanCords.csv", numLinesToSkip = 1)
    void testSAN(String SAN, int x, int y) throws Exception{
        assertEquals(SAN, board.getBox(x, y).san(), "("+x+", "+y+") should equal "+SAN);
    }
}
