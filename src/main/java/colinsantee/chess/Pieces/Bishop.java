package colinsantee.chess.Pieces;

public class Bishop extends Piece {
    public Bishop(boolean white){
        super(white);
    }

    @Override
    public boolean canMove(){
        return true;
    }

    @Override
    public String print() {
        return "B";
    }
}

