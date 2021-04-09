package colinsantee.chess.Pieces;

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
    public boolean canMove(){
        return true;
    }

    @Override
    public String print() {
        return "K";
    }
}
