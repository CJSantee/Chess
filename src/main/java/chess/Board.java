package chess;
import chess.Pieces.*;

/*
    Board Class:
    0, 0 is a1
    "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    is standard setup, a8 -> h1, in FEN
*/
public class Board {
    private Spot[][] boxes;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // Default board constructor
    public Board(){
        boxes = new Spot[8][8];
        this.resetBoard();
    }

    // Setup new board from FEN position
    public Board(String input) throws Exception{
        boxes = new Spot[8][8];
        String[] rows = input.split("/");
        if(rows.length != 8){
            System.err.println("Invalid num ranks: expected 8");
            throw new Exception("Invalid input String");
        }
        int i, c;
        char[] row;
        i = 0;
        for(int r = 7; r >= 0; r--){
            c = 0;
            row = rows[i].toCharArray();
            if(row.length > 8){
                System.err.println("Invalid num elements in rank: expected <= 8");
                throw new Exception("Invalid input String");
            }
            for (char ch : row){
                switch (ch) {
                    case 'R': 
                        boxes[r][c] = new Spot(r, c, new Rook(true));
                        c++;
                        break;
                    case 'r': 
                        boxes[r][c] = new Spot(r, c, new Rook(false));
                        c++;
                        break;
                    case 'N': 
                        boxes[r][c] = new Spot(r, c, new Knight(true));
                        c++;
                        break;
                    case 'n': 
                        boxes[r][c] = new Spot(r, c, new Knight(false));
                        c++;
                        break;
                    case 'B': 
                        boxes[r][c] = new Spot(r, c, new Bishop(true));
                        c++;
                        break;
                    case 'b': 
                        boxes[r][c] = new Spot(r, c, new Bishop(false));
                        c++;
                        break;
                    case 'Q': 
                        boxes[r][c]  = new Spot(r, c, new Queen(true));
                        c++;
                        break;
                    case 'q': 
                        boxes[r][c] = new Spot(r, c, new Queen(false));
                        c++;
                        break;
                    case 'K': 
                        boxes[r][c] = new Spot(r, c, new King(true));
                        c++;
                        break;
                    case 'k': 
                        boxes[r][c] = new Spot(r, c, new King(false));
                        c++;
                        break;
                    case 'P': 
                        boxes[r][c] = new Spot(r, c, new Pawn(true));
                        c++;
                        break;
                    case 'p': 
                        boxes[r][c] = new Spot(r, c, new Pawn(false));
                        c++;
                        break;
                    default:
                        if(ch == '1' || ch == '2' || ch == '3' || ch == '4'|| ch == '5' || ch == '6' || ch == '7' || ch == '8'){
                            int num = Character.getNumericValue(ch);
                            if(c+num > 8){
                                System.err.println("Invalid num empty squares");
                                throw new Exception("Invalid input String");
                            }
                            for(int j = 0; j < num; j++){
                                boxes[r][c+j] = new Spot(r, c+j, null);
                            }
                            c+=num;
                            break;
                        }
                        System.err.println("Invalid input field: expected <RrNnBbQqKkPp12345678>");
                        throw new Exception("Invalid input String");
                }
                if(c > 8){
                    System.err.println("Invalid num elements on rank");
                    throw new Exception("Invalid input String");
                }
            }
            i++;
        }

    }

    // Get spot on the board
    public Spot getBox(int x, int y) throws Exception{
        if(x < 0 || x > 7 || y < 0 || y > 7){
            throw new Exception("Index out of bound");
        }
        return boxes[x][y];
    }

    // TODO: Add public Spot getBox(Piece piece) method

    // Reset the board to standard starting position
    public void resetBoard(){
        // initialize white pieces
        boxes[0][0] = new Spot(0, 0, new Rook(true));   // Rook
        boxes[0][1] = new Spot(0, 1, new Knight(true)); // Knight
        boxes[0][2] = new Spot(0, 2, new Bishop(true)); // Bishop
        boxes[0][3] = new Spot(0, 3, new Queen(true));   // King
        boxes[0][4] = new Spot(0, 4, new King(true));  // Queen
        boxes[0][5] = new Spot(0, 5, new Bishop(true)); // Bishop
        boxes[0][6] = new Spot(0, 6, new Knight(true)); // Knight
        boxes[0][7] = new Spot(0, 7, new Rook(true));   // Rook
        for(int i = 0; i < 8; i++){
            boxes[1][i] = new Spot(1, i, new Pawn(true));   // Pawns
        }

        // initialize black pieces
        boxes[7][0] = new Spot(7, 0, new Rook(false));   // Rook
        boxes[7][1] = new Spot(7, 1, new Knight(false)); // Knight
        boxes[7][2] = new Spot(7, 2, new Bishop(false)); // Bishop
        boxes[7][3] = new Spot(7, 3, new Queen(false));   // King
        boxes[7][4] = new Spot(7, 4, new King(false));  // Queen
        boxes[7][5] = new Spot(7, 5, new Bishop(false)); // Bishop
        boxes[7][6] = new Spot(7, 6, new Knight(false)); // Knight
        boxes[7][7] = new Spot(7, 7, new Rook(false));   // Rook
        for(int i = 0; i < 8; i++){
            boxes[6][i] = new Spot(6, i, new Pawn(false));   // Pawns
        }

        // intitialize remaining boxes without any piece
        for(int r = 2; r < 6; r++){
            for(int c = 0; c < 8; c++){
                boxes[r][c] = new Spot(r, c, null);
            }
        }
    }

    // Print the board to the console
    public void print(){
        String blankLine = "               ";
        for(int r = 0; r < 8; r++){
            for(int i = 0; i < 7; i++){
                for(int c = 0; c < 8; c++){
                    if(boxes[r][c].isWhite()){
                        System.out.print(ANSI_WHITE_BACKGROUND);
                        if(boxes[r][c].hasPiece()){
                            System.out.print(ANSI_BLACK+boxes[r][c].getPiece().printLine(i));
                        }else{
                            System.out.print(blankLine);
                        }
                    }else{
                        System.out.print(ANSI_BLACK_BACKGROUND);
                        if(boxes[r][c].hasPiece()){
                            System.out.print(ANSI_WHITE+boxes[r][c].getPiece().printLine(i));
                        }else{
                            System.out.print(blankLine);
                        }
                    } 
                }
                System.out.println(ANSI_RESET);
            }
        }
    }

    // Export the board position in FEN
    public String fen(){
        String ret = "";
        int numEmpty;
        for(int r = 7; r >= 0; r--){
            numEmpty = 0;
            for(int c = 0; c < 8; c++){
                if(boxes[r][c].hasPiece()){
                    if(numEmpty != 0){
                        ret += Character.forDigit(numEmpty, 10);
                    }
                    ret += boxes[r][c].printChar();
                    numEmpty = 0;
                }else{
                    numEmpty++;
                }
            }
            if(numEmpty != 0){
                ret += Character.forDigit(numEmpty, 10);
            }
            if(r != 0) ret+='/';
        }
        return ret;
    }

    // TODO: Add clear() Method 
}
