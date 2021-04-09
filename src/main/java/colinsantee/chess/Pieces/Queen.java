package colinsantee.chess.Pieces;

public class Queen extends Piece {
    public Queen (boolean white){
        super(white);
    }

    @Override
    public boolean canMove(){
        return true;
    }

    @Override
    public String print() {
        return "Q";
    }
}
