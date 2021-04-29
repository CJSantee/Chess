package chess;
import chess.Player.Player;

public class Move {
    private Player player;
    private Spot start;
    private Spot end;
    private boolean capture; 
    private boolean castle = false;

    public Move(Player player, Spot start, Spot end){
        this.player = player;
        this.start = start;
        this.end = end;
        capture = end.hasPiece();
    }

    public boolean isCastle() {
        return this.castle;
    }
    public void setCastle(boolean castle) {
        this.castle = castle;
    }

    public boolean isCapture() {
        return this.capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public Spot getStart() {
        return this.start;
    }
    public Spot getEnd() {
        return this.end;
    }
    

    
}
