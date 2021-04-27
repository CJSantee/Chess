package chess.Pieces;

import chess.Board;
import chess.Spot;

public class King extends Piece {
    private boolean castlingDone = false;

    public King(boolean white){
        super(white);
    }

    public boolean isCastlingDone() {
        return this.castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end){
        return true;
    }

    @Override
    public String printLine(int line) {
        switch(line){
            case 0: return "               ";
            case 1: return "       +       ";
            case 2: return "   / \\/^\\/ \\   ";
            case 3: return "  (   \\ /   )  ";
            case 4: return "    |__|__|    ";
            case 5: return "   /_______\\   ";
            case 6: return "               ";
            default:
                return "";
        }
    }

    @Override
    public char printChar(){
        if(isWhite()) return 'K';
        else return 'k';
    }

}
