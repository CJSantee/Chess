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
}
