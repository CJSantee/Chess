package colinsantee.chess.Pieces;

public class Knight extends Piece {
    public Knight(boolean white){
        super(white);
    }

    @Override 
    public boolean canMove(){
        return true;
    }

    @Override
    public String print() {
        return "N";
    }
}
