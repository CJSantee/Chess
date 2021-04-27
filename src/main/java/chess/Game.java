package chess;

import java.util.List;

import chess.Pieces.King;
import chess.Pieces.Piece;
import chess.Player.Player;

public class Game {
    private Player[] players;
    private Board board;
    private Player currentTurn;
    private GameStatus status;
    private List<Move> movesPlayed;

    public Game(Player p1, Player p2){
        players[0] = p1;
        players[1] = p2;

        if(p1.isWhiteSide()){
            currentTurn = p1;
        } else {
            currentTurn = p2;
        }

        board = new Board();

        movesPlayed.clear();

        status = GameStatus.ACTIVE;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }
    public void setCurrentTurn(Player player){
        this.currentTurn = player;
    } 

    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status){
        this.status = status;
    }

    public boolean isEnd(){
        return this.getStatus() != GameStatus.ACTIVE;
    }

    public boolean playerMove(Player player, int startX, int startY, int endX, int endY) throws Exception{
        Spot startBox = board.getBox(startX, startY);
        Spot endBox = board.getBox(endX, endY);
        Move move = new Move(player, startBox, endBox);
        return this.makeMove(move, player);
    }

    public boolean makeMove(Move move, Player player){
        Piece sourcePiece = move.getStart().getPiece();
        
        // souce piece not null?
        if(sourcePiece == null) return false;
        // valid player?
        if(player != currentTurn) return false;
        // piece color matches player color?
        if(sourcePiece.isWhite() != player.isWhiteSide()) return false;
        // valid move?
        if(!sourcePiece.canMove(board, move.getStart(), move.getEnd())) return false;
        // kill?
        Piece destPiece = move.getEnd().getPiece();
        if(destPiece != null){
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }

        // castling?
        
        // store the move
        movesPlayed.add(move);

        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);

        // Set the current turn to the other player
        if (this.currentTurn == players[0]){
            this.currentTurn = players[1];
        } else {
            this.currentTurn = players[0];
        }

        return true;
    }

}
