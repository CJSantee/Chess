package chess.Pieces;

import chess.Board;
import chess.Spot;

public class Rook extends Piece {

    public Rook(boolean white){
        super(white);
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end){
        // we can't move the piece to a spot that has a piece has
        // a piece of the same color
        return true;
    }

    @Override
    public String printLine(int line) {
        switch(line){
            case 0: return "               ";
            case 1: return "    H-H-H-H    ";
            case 2: return "     \\   /     ";
            case 3: return "     |   |     ";
            case 4: return "    /     \\    ";
            case 5: return "    =======    ";
            case 6: return "               ";
            default:
                return "";
        }
    }

    @Override
    public char printChar(){
        if(isWhite()) return 'R';
        else return 'r';
    }
}
