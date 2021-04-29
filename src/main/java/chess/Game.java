package chess;

import java.util.ArrayList;
import java.util.List;

import chess.Pieces.Knight;
import chess.Pieces.Piece;
import chess.Pieces.Rook;
import chess.Player.ComputerPlayer;
import chess.Player.HumanPlayer;
import chess.Player.Player;

public class Game {
    private Player[] players;
    private Board board;
    private Player currentTurn;
    private GameStatus status;
    private List<Move> movesPlayed;

    // Default game constructor
    public Game(){
        players = new Player[2];
        players[0] = new HumanPlayer(true);
        players[1] = new ComputerPlayer(false);
        currentTurn = players[0];

        board = new Board();

        movesPlayed = new ArrayList<Move>();

        status = GameStatus.ACTIVE;
    }

    // Game constructor given players
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

        status = GameStatus.ACTIVE;
    }

    // Game constructor with Forsyth-Edwards Notation
    public Game(String FEN) throws Exception{
        String[] fenArr = FEN.split(" ");
        if(fenArr.length != 6){
            throw new Exception("Invalid FEN input");
        }
        String position = fenArr[0];
        char activeColor = fenArr[1].charAt(0);
        String castling = fenArr[2];
        String enPassant = fenArr[3];
        int halfmoveClock = Integer.parseInt(fenArr[4]);
        int fullmoveClock = Integer.parseInt(fenArr[5]);

        players = new Player[2];
        players[0] = new HumanPlayer(true);
        players[1] = new ComputerPlayer(false);
        currentTurn = activeColor == 'w' ? players[0] : players[1];

        board = new Board(position);

        movesPlayed = new ArrayList<Move>();

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
        char[] a = SAN.toCharArray();
        // Pawn Move
        if(a[0]=='a'||a[0]=='b'||a[0]=='c'||a[0]=='d'||a[0]=='e'||a[0]=='f'||a[0]=='g'||a[0]=='h'){
            src += a[0];
            // Pawn capture
            if(a[1]=='x'){
                dest = a[2] +""+ a[3];
                src += (Character.getNumericValue(a[3])-1);
            }else{
                dest = a[0] +""+ a[1];
                src += findPawnSrcRank(dest, currentTurn.whiteSide);
            }
        }
        // Rook Move
        if(a[0]=='R'){
            // Rook Capture
            if(a[1] == 'x'){
                dest = a[2] +""+ a[3];
            }else{
                dest = a[1] +""+ a[2];
            }
            src = findRookSrc(dest);
        }
        // Knight Move
        if(a[0]=='N'){
            // Knight Capture
            if(a[1] == 'x'){
                dest = a[2] +""+ a[3];
            }else{
                dest = a[1] +""+ a[2];
            }
            src = findKnightSrc(dest);
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

    public String findRookSrc(String dest) throws Exception{
        Spot destSpot = sanToSpot(dest);
        int x = destSpot.getX();
        int y = destSpot.getY();
        for(int left = destSpot.getY()-1; left >= 0; left--){
            if(board.getBox(x, left).hasPiece()){
                if(board.getBox(x, left).getPiece() instanceof Rook && board.getBox(x, left).getPiece().isWhite() == currentTurn.whiteSide){
                    return board.getBox(x, left).san();
                }
                break;
            }
        }
        for(int right = destSpot.getY()+1; right < 8; right++){
            if(board.getBox(x, right).hasPiece()){
                if(board.getBox(x, right).getPiece() instanceof Rook && board.getBox(x, right).getPiece().isWhite() == currentTurn.whiteSide){
                    return board.getBox(x, right).san();
                }
                break;
            }
        }
        for(int up = destSpot.getX()+1; up < 8; up++){
            if(board.getBox(up, y).hasPiece()){
                if(board.getBox(up, y).getPiece() instanceof Rook && board.getBox(up, y).getPiece().isWhite() == currentTurn.whiteSide){
                    return board.getBox(up, y).san();
                }
                break;
            }
        }
        for(int down = destSpot.getX()-1; down >= 0; down--){
            if(board.getBox(down, y).hasPiece()){
                if(board.getBox(down, y).getPiece() instanceof Rook && board.getBox(down, y).getPiece().isWhite() == currentTurn.whiteSide){
                    return board.getBox(down, y).san();
                }
                break;
            }
        }
        throw new Exception("Rook not found");
    }

    public String findKnightSrc(String dest) throws Exception{
        Spot destSpot = sanToSpot(dest);
        int x = destSpot.getX();
        int y = destSpot.getY();
        int[] testX = new int[]{+2, +2, +1, -1, -2, -2, -1, +1};
        int[] testY = new int[]{-1, +1, +2, +2, +1, -1, -2, -2};
        for(int i = 0; i < 8; i++){
            if(inBounds(x+testX[i])&&inBounds(y+testY[i])){
                if(board.getBox(x+testX[i], y+testY[i]).hasPiece()){
                    if(board.getBox(x+testX[i], y+testY[i]).getPiece() instanceof Knight && board.getBox(x+testX[i], y+testY[i]).getPiece().isWhite() == currentTurn.whiteSide){
                        String ret = board.getBox(x+testX[i], y+testY[i]).san();
                        System.out.println(ret);
                        return ret;
                    }
                }
            }
        }
        throw new Exception("Knight not found");
    }

    public boolean inBounds(int n){
        if(n >= 0 && n < 8) return true;
        return false;
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
