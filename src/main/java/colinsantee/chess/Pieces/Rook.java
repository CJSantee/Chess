package colinsantee.chess.Pieces;

public class Rook extends Piece {

    public Rook(boolean white){
        super(white);
    }

    @Override
    public boolean canMove(){
        // we can't move the piece to a spot that has a piece has
        // a piece of the same color
        return true;
    }

    @Override
    public String print() {
        return "R";
    }
}
