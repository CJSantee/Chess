package chess;
import chess.Pieces.*;

public class Spot {
    private Piece piece;
    private int x;
    private int y;

    public Spot(int x, int y, Piece piece){
        this.setPiece(piece);
        this.setX(x);
        this.setY(y);
    }

    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return this.piece != null;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite(){
        return (x%2!=y%2);
    }

    public char printChar(){
        if(hasPiece()){
            return getPiece().printChar();
        }else{
            return ' ';
        }
    }

    public String san(){
        String ret;
        switch (this.getY()) {
            case 0: 
                ret = "a";
                break;
            case 1:
                ret = "b";
                break;
            case 2:
                ret = "c";
                break;
            case 3:
                ret = "d";
                break;
            case 4:
                ret = "e";
                break;
            case 5:
                ret = "f";
                break;
            case 6:
                ret = "g";
                break;
            case 7:
                ret = "h";
                break;
            default:
                return "";
        }
        ret += getX()+1;
        return ret;
    }

}
