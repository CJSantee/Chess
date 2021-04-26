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
    public String printLine(int line) {
        switch(line){
            case 0: return "               ";
            case 1: return "       o       ";
            case 2: return "      / \\      ";
            case 3: return "     ( + )     ";
            case 4: return "      \\_/      ";
            case 5: return "   \\__/_\\__/   ";
            case 6: return "               ";
            default:
                return "";
        }
    }
}

