package chess;

import chess.Player.HumanPlayer;

/**
 * Hello world!
 */
public final class App {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.print();
    }
}
