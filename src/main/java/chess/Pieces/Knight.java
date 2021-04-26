package chess.Pieces;

public class Knight extends Piece {
    public Knight(boolean white){
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
            case 1: return "    ,^---.     ";
            case 2: return "   /      \\    ";
            case 3: return "  (_/)    )    ";
            case 4: return "    _\\   /_    ";
            case 5: return "   (_______)   ";
            case 6: return "               ";
            default:
                return "";
        }
    }
}
