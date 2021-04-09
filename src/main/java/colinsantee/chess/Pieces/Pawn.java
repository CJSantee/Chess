package colinsantee.chess.Pieces;

public class Pawn extends Piece {
    public Pawn(boolean white){
        super(white);
    }

    @Override
    public boolean canMove(){
        return true;
    }

    @Override
    public String print(){
        return "P";
    }
}
