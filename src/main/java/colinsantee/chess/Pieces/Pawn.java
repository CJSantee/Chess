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
    public String printLine(int line){
        switch(line){
            case 0: return "       _       ";
            case 1: return "      ( )      ";
            case 2: return "      )-(      ";
            case 3: return "     /   \\     ";
            case 4: return "    |     |    ";
            case 5: return "    =======    ";
            case 6: return "               ";
            default:
                return "";
        }
    }
}
