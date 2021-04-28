package chess;

import java.util.ArrayList;
import java.util.List;

import chess.Pieces.Piece;
import chess.Player.Player;

public class Game {
    private Player[] players;
    private Board board;
    private Player currentTurn;
    private GameStatus status;
    private List<Move> movesPlayed;

    public Game(Player p1, Player p2){
        players = new Player[2];
        players[0] = p1;
        players[1] = p2;

        if(p1.isWhiteSide()){
            currentTurn = p1;
        } else {
            currentTurn = p2;
        }

        board = new Board();

        movesPlayed = new ArrayList<Move>();
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

    public boolean playerMove(Player player, String AN) throws Exception{
        return false;
    }

    public Move sanToMove(String SAN) throws Exception{
        Spot start, end;
        String src = "";
        String dest = "";
        // Queenside Castle
        if(SAN == "0-0-0"){
            if(currentTurn.isWhiteSide()){
                start = board.getBox(0, 4);
                end = board.getBox(0, 2);
            }else{
                start = board.getBox(7, 4);
                end = board.getBox(7, 2);
            }
        }else if(SAN == "0-0"){
            // Kingside castle
            if(currentTurn.isWhiteSide()){
                start = board.getBox(0, 4);
                end = board.getBox(0, 6);
            }else{
                start = board.getBox(7, 4);
                end = board.getBox(7, 6);
            }
        }
        char[] a = SAN.toCharArray();
        // Pawn Move
        if(a[0]=='a'||a[0]=='b'||a[0]=='c'||a[0]=='d'||a[0]=='e'||a[0]=='f'||a[0]=='g'||a[0]=='h'){
            src += a[0];
            // Pawn capture
            if(a[1]=='x'){
                dest += a[2] + a[3];
                src += (Character.getNumericValue(a[3])-2);
                System.out.println(src+" "+dest);
            }else{
                dest += a[0];
                dest += (Character.getNumericValue(a[1]));
                src += findPawnSrcRank(dest, currentTurn.whiteSide);
                System.out.println(src+" "+dest);
            }
        }
        start = sanToSpot(src);
        end = sanToSpot(dest);
        Move move = new Move(currentTurn, start, end);
        return move;
    }

    public char findPawnSrcRank(String dest, boolean isWhite) throws Exception{
        char file = dest.charAt(0);
        int fCord = fileToCord(file);
        int rCord = Character.getNumericValue(dest.charAt(1)-1);
        int ret;
        // If the rank behind the Pawn destination has a piece, return that rank
        int isBlack = isWhite ? 1 : -1;
        if(board.getBox(rCord+(-1*isBlack), fCord).hasPiece()){
            ret = isWhite ? rCord : rCord + 2;
            return Character.forDigit(ret, 10);
        }else if (board.getBox(rCord+(-2*isBlack), fCord).hasPiece()){
            ret = isWhite ? rCord-1 : rCord + 3;
            return Character.forDigit(ret, 10);
        }else{
            throw new Exception("Source Pawn not found");
        }
    }

    public Spot sanToSpot(String SAN){
        Spot spot;
        int x = Character.getNumericValue(SAN.charAt(1)-1);
        int y = (int) SAN.charAt(0)-97;
        spot = new Spot(x, y, null);
        return spot;
    }

    public int fileToCord(char file) throws Exception{
        switch (file) {
            case 'a': return 0;
            case 'b': return 1;
            case 'c': return 2;
            case 'd': return 3;
            case 'e': return 4;
            case 'f': return 5;
            case 'g': return 6;
            case 'h': return 7;
            default:
                throw new Exception("Invalid file");
        }
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
