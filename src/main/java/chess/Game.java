package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.spi.ImageWriterSpi;

import chess.Pieces.Bishop;
import chess.Pieces.King;
import chess.Pieces.Knight;
import chess.Pieces.Pawn;
import chess.Pieces.Piece;
import chess.Pieces.Queen;
import chess.Pieces.Rook;
import chess.Player.ComputerPlayer;
import chess.Player.HumanPlayer;
import chess.Player.Player;

public class Game {
    private Player[] players;
    private Board board;
    private boolean whiteMove;
    private GameStatus status;
    private List<Move> movesPlayed;
    private int halfmoveClock;
    private int fullmoveClock;

    // Default game constructor
    public Game(){
        players = new Player[2];
        players[0] = new HumanPlayer(true);
        players[1] = new ComputerPlayer(false);
        whiteMove = true;

        board = new Board();

        movesPlayed = new ArrayList<Move>();

        status = GameStatus.ACTIVE;

        halfmoveClock = 0;
        fullmoveClock = 0;
    }

    // Game constructor given players
    public Game(Player p1, Player p2){
        players = new Player[2];
        players[0] = p1;
        players[1] = p2;
        
        whiteMove = true;

        board = new Board();

        movesPlayed = new ArrayList<Move>();

        status = GameStatus.ACTIVE;

        halfmoveClock = 0;
        fullmoveClock = 0;
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
        halfmoveClock = Integer.parseInt(fenArr[4]);
        fullmoveClock = Integer.parseInt(fenArr[5]);

        players = new Player[2];
        players[0] = new HumanPlayer(true);
        players[1] = new ComputerPlayer(false);
        whiteMove = activeColor == 'w';

        board = new Board(position);

        movesPlayed = new ArrayList<Move>();

        status = GameStatus.ACTIVE;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentTurn() throws Exception {
        for(Player p:players){
            if(whiteMove == p.isWhiteSide()){
                return  p;
            }
        }
        throw new Exception("Player not found");
    }

    public boolean getwhiteMove(){
        return this.whiteMove;
    }
    public void setWhiteMove(boolean whiteMove) {
        this.whiteMove = whiteMove;
    }

    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status){
        this.status = status;
    }

    public int getHalfmoveClock(){
        return this.halfmoveClock;
    }
    public void setHalfmoveClock(int moves){
        this.halfmoveClock = moves;
    }
    public void incrementHalfmoveClock(){
        this.halfmoveClock+=1;
    }

    public int getFullmoveClock(){
        return this.fullmoveClock;
    }
    public void setFullmoveClock(int moves){
        this.fullmoveClock = moves;
    }
    public void incrementFullmoveClock(){
        this.fullmoveClock+=1;
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

        int mod = whiteMove ? 1 : -1;
        // TODO: Check for capture before Piece

        // Pawn Move
        if(a[0]=='a'||a[0]=='b'||a[0]=='c'||a[0]=='d'||a[0]=='e'||a[0]=='f'||a[0]=='g'||a[0]=='h'){
            // Pawn capture
            if(a[1]=='x'){
                dest = a[2] +""+ a[3];
                src += a[0];
                src += (Character.getNumericValue(a[3])-mod);
            }else{
                dest = a[0] +""+ a[1];
                src += findPawnSrc(dest);
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
        // Bishop Move
        if(a[0]=='B'){
            // Bishop Capture
            if(a[1] == 'x'){
                dest = a[2] +""+ a[3];
            }else{
                dest = a[1] +""+ a[2];
            }
            src = findBishopSrc(dest);
        }
        // Queen Move
        if(a[0]=='Q'){
            // Queen Capture
            if(a[1] == 'x'){
                dest = a[2] +""+ a[3];
            }else{
                dest = a[1] +""+ a[2];
            }
            src = findQueenSrc(dest);
        }
        // King Move 
        if(a[0]=='K'){
            // King Capture
            if(a[1] == 'x'){
                dest = a[2] +""+ a[3];
            }else{
                dest = a[1] +""+ a[2];
            }
            src = findKingSrc(dest);
        }

        // Add error handling

        start = sanToSpot(src);
        end = sanToSpot(dest);
        Move move = new Move(this.getCurrentTurn(), start, end);
        return move;
    }

    public String findPawnSrc(String dest) throws Exception{
        Spot destSpot = sanToSpot(dest);
        int x = destSpot.getX();
        int y = destSpot.getY();
        int mod = whiteMove ? 1 : -1;
        for(int i = 1; i <= 2; i++){
            if(board.getBox(x-(i*mod), y).hasPiece()){
                if(board.getBox(x-(i*mod), y).getPiece() instanceof Pawn && board.getBox(x-(i*mod), y).getPiece().isWhite() == whiteMove){
                    return board.getBox(x-(i*mod), y).san();
                }
            }
        }
        throw new Exception("Source Pawn not found");
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
        for(int left = y-1; left >= 0; left--){
            if(board.getBox(x, left).hasPiece()){
                if(board.getBox(x, left).getPiece() instanceof Rook && board.getBox(x, left).getPiece().isWhite() == whiteMove){
                    return board.getBox(x, left).san();
                }
                break;
            }
        }
        for(int right = y+1; right < 8; right++){
            if(board.getBox(x, right).hasPiece()){
                if(board.getBox(x, right).getPiece() instanceof Rook && board.getBox(x, right).getPiece().isWhite() == whiteMove){
                    return board.getBox(x, right).san();
                }
                break;
            }
        }
        for(int up = x+1; up < 8; up++){
            if(board.getBox(up, y).hasPiece()){
                if(board.getBox(up, y).getPiece() instanceof Rook && board.getBox(up, y).getPiece().isWhite() == whiteMove){
                    return board.getBox(up, y).san();
                }
                break;
            }
        }
        for(int down = x-1; down >= 0; down--){
            if(board.getBox(down, y).hasPiece()){
                if(board.getBox(down, y).getPiece() instanceof Rook && board.getBox(down, y).getPiece().isWhite() == whiteMove){
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
                    if(board.getBox(x+testX[i], y+testY[i]).getPiece() instanceof Knight && board.getBox(x+testX[i], y+testY[i]).getPiece().isWhite() == whiteMove){
                        return board.getBox(x+testX[i], y+testY[i]).san();
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

    public String findBishopSrc(String dest) throws Exception{
        Spot destSpot = sanToSpot(dest);
        int x = destSpot.getX();
        int y = destSpot.getY();
        // Southeast
        int seX = x-1;
        int seY = y+1;
        while(inBounds(seX) && inBounds(seY)){
            if(board.getBox(seX, seY).hasPiece()){
                if(board.getBox(seX, seY).getPiece() instanceof Bishop && board.getBox(seX, seY).getPiece().isWhite() == whiteMove){
                    return board.getBox(seX, seY).san();
                }
                break;
            }
            seX-=1;
            seY+=1;
        }
        // Southwest
        int swX = x-1;
        int swY = y-1;
        while(inBounds(swX) && inBounds(swY)){
            if(board.getBox(swX, swY).hasPiece()){
                if(board.getBox(swX, swY).getPiece() instanceof Bishop && board.getBox(swX, swY).getPiece().isWhite() == whiteMove){
                    return board.getBox(swX, swY).san();
                }
                break;
            }
            swX-=1;
            swY-=1;
        }
        // Northwest
        int nwX = x+1;
        int nwY = y-1;
        while(inBounds(nwX) && inBounds(nwY)){
            if(board.getBox(nwX, nwY).hasPiece()){
                if(board.getBox(nwX, nwY).getPiece() instanceof Bishop && board.getBox(nwX, nwY).getPiece().isWhite() == whiteMove){
                    return board.getBox(nwX, nwY).san();
                }
                break;
            }
            nwX+=1;
            nwY-=1;
        }
        // Northeast
        int neX = x+1;
        int neY = y+1;
        while(inBounds(neX) && inBounds(neY)){
            if(board.getBox(neX, neY).hasPiece()){
                if(board.getBox(neX, neY).getPiece() instanceof Bishop && board.getBox(neX, neY).getPiece().isWhite() == whiteMove){
                    return board.getBox(neX, neY).san();
                }
                break;
            }
            neX+=1;
            neY+=1;
        }
        throw new Exception("Bishop not found");
    }

    // TODO: Generalize findRookSrc, findBishopSrc, and findQueenSrc to findOnHorizontal and findOnVertical or similar
    public String findQueenSrc(String dest) throws Exception{
        Spot destSpot = sanToSpot(dest);
        int x = destSpot.getX();
        int y = destSpot.getY();
        // Left
        for(int left = y-1; left >= 0; left--){
            if(board.getBox(x, left).hasPiece()){
                if(board.getBox(x, left).getPiece() instanceof Queen && board.getBox(x, left).getPiece().isWhite() == whiteMove){
                    return board.getBox(x, left).san();
                }
                break;
            }
        } 
        // Right
        for(int right = y+1; right < 8; right++){
            if(board.getBox(x, right).hasPiece()){
                if(board.getBox(x, right).getPiece() instanceof Queen && board.getBox(x, right).getPiece().isWhite() == whiteMove){
                    return board.getBox(x, right).san();
                }
                break;
            }
        }
        // Up
        for(int up = x+1; up < 8; up++){
            if(board.getBox(up, y).hasPiece()){
                if(board.getBox(up, y).getPiece() instanceof Queen && board.getBox(up, y).getPiece().isWhite() == whiteMove){
                    return board.getBox(up, y).san();
                }
                break;
            }
        }
        // Down
        for(int down = x-1; down >= 0; down--){
            if(board.getBox(down, y).hasPiece()){
                if(board.getBox(down, y).getPiece() instanceof Queen && board.getBox(down, y).getPiece().isWhite() == whiteMove){
                    return board.getBox(down, y).san();
                }
                break;
            }
        }
        // Southeast
        int seX = x-1;
        int seY = y+1;
        while(inBounds(seX) && inBounds(seY)){
            if(board.getBox(seX, seY).hasPiece()){
                if(board.getBox(seX, seY).getPiece() instanceof Queen && board.getBox(seX, seY).getPiece().isWhite() == whiteMove){
                    return board.getBox(seX, seY).san();
                }
                break;
            }
            seX-=1;
            seY+=1;
        }
        // Southwest
        int swX = x-1;
        int swY = y-1;
        while(inBounds(swX) && inBounds(swY)){
            if(board.getBox(swX, swY).hasPiece()){
                if(board.getBox(swX, swY).getPiece() instanceof Queen && board.getBox(swX, swY).getPiece().isWhite() == whiteMove){
                    return board.getBox(swX, swY).san();
                }
                break;
            }
            swX-=1;
            swY-=1;
        }
        // Northwest
        int nwX = x+1;
        int nwY = y-1;
        while(inBounds(nwX) && inBounds(nwY)){
            if(board.getBox(nwX, nwY).hasPiece()){
                if(board.getBox(nwX, nwY).getPiece() instanceof Queen && board.getBox(nwX, nwY).getPiece().isWhite() == whiteMove){
                    return board.getBox(nwX, nwY).san();
                }
                break;
            }
            nwX+=1;
            nwY-=1;
        }
        // Northeast
        int neX = x+1;
        int neY = y+1;
        while(inBounds(neX) && inBounds(neY)){
            if(board.getBox(neX, neY).hasPiece()){
                if(board.getBox(neX, neY).getPiece() instanceof Queen && board.getBox(neX, neY).getPiece().isWhite() == whiteMove){
                    return board.getBox(neX, neY).san();
                }
                break;
            }
            neX+=1;
            neY+=1;
        }

        throw new Exception("Queen not found");
    }

    public String findKingSrc(String dest) throws Exception{
        Spot destSpot = sanToSpot(dest);
        int x = destSpot.getX();
        int y = destSpot.getY();
        int[] testX = new int[]{+1, +1, +1, +0, -1, -1, -1, +0};
        int[] testY = new int[]{-1, +0, +1, +1, +1, +0, -1, -1};
        for(int i = 0; i < 8; i++){
            if(inBounds(x+testX[i]) && inBounds(y+testY[i])){
                if(board.getBox(x+testX[i], y+testY[i]).hasPiece()){
                    if(board.getBox(x+testX[i], y+testY[i]).getPiece() instanceof King && board.getBox(x+testX[i], y+testY[i]).getPiece().isWhite() == whiteMove){
                        return board.getBox(x+testX[i], y+testY[i]).san();
                    }
                }
            }
        }
        throw new Exception("King not found");
    }

    public Spot sanToSpot(String SAN) throws Exception{
        Spot spot;
        int x = Character.getNumericValue(SAN.charAt(1)-1);
        int y = (int) SAN.charAt(0)-97;
        spot = board.getBox(x, y);
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
        if(player.whiteSide != whiteMove) return false;
        // piece color matches player color?
        if(sourcePiece.isWhite() != player.isWhiteSide()) return false;
        // valid move?
        // if(!sourcePiece.canMove(board, move.getStart(), move.getEnd())) return false;
        // kill?
        if(move.getEnd().hasPiece()){
            move.getEnd().setPiece(null);
        }

        // castling?
        
        // store the move
        movesPlayed.add(move);

        board.update(move);

        // Set the current turn to the other player
        whiteMove = !whiteMove;

        return true;
    }

    public void play() throws Exception{
        Scanner sc = new Scanner(System.in);
        board.print();
        String cmd = sc.next();
        while(!isEnd()){
            if(cmd.equals("print")) board.print();
            else if(cmd.equals("exit")) status = GameStatus.FORFEIT;
            else{
                Move move = sanToMove(cmd);
                makeMove(move, getCurrentTurn());
            }
            cmd = sc.next();
        }
        sc.close();
    }

}
