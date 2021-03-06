package chess.Pieces;

import chess.Board;
import chess.Spot;

public class Queen extends Piece {
    public Queen (boolean white){
        super(white);
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end){
        return true;
    }

    @Override
    public String printLine(int line) {
        switch(line){
            case 0: return "               ";
            case 1: return "    o  o  o    ";
            case 2: return "  o/ \\/ \\/ \\o  ";
            case 3: return "   \\  v v  /   ";
            case 4: return "    \\ ___ /    ";
            case 5: return "   /_______\\   ";
            case 6: return "               ";
            default:
                return "";
        }
    }

    @Override
    public char printChar(){
        if(isWhite()) return 'Q';
        else return 'q';
    }
}
