package chess;
import chess.Pieces.*;

/*
    Board Class:
    0, 0 is a1
    "rnbqkbnrpppppppp                                PPPPPPPPRNBQKBNR"
    is standard setup, a8 -> h1
*/
public class Board {
    private Spot[][] boxes;

    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public Board(){
        boxes = new Spot[8][8];
        this.resetBoard();
    }

    public Board(String input) throws Exception{
        if(input.length() != 64){
            throw new Exception("Invalid input String");
        }
        boxes = new Spot[8][8];
        char[] arrOfString = input.toCharArray();
        int n = 0;
        for(int r = 7; r >= 0; r--){
            for(int c = 0; c < 8; c++){
                switch (arrOfString[n]) {
                    case 'R':
                        boxes[r][c] = new Spot(r, c, new Rook(true));
                        break;
                    case 'r':
                        boxes[r][c] = new Spot(r, c, new Rook(false));
                        break;
                    case 'N':
                        boxes[r][c] = new Spot(r, c, new Knight(true));
                        break;
                    case 'n':
                        boxes[r][c] = new Spot(r, c, new Knight(false));
                        break;
                    case 'B':
                        boxes[r][c] = new Spot(r, c, new Bishop(true));
                        break;
                    case 'b':
                        boxes[r][c] = new Spot(r, c, new Bishop(false));
                        break;
                    case 'K':
                        boxes[r][c] = new Spot(r, c, new King(true));
                        break;
                    case 'k':
                        boxes[r][c] = new Spot(r, c, new King(false));
                        break;
                    case 'Q':
                        boxes[r][c] = new Spot(r, c, new Queen(true));
                        break;
                    case 'q':
                        boxes[r][c] = new Spot(r, c, new Queen(false));
                        break;
                    case 'P':
                        boxes[r][c] = new Spot(r, c, new Pawn(true));
                        break;
                    case 'p':
                        boxes[r][c] = new Spot(r, c, new Pawn(false));
                        break;
                    default:
                        boxes[r][c] = new Spot(r, c, null);
                        break;
                }
                n++;
            }
        }
    }

    public Spot getBox(int x, int y) throws Exception{
        if(x < 0 || x > 7 || y < 0 || y > 7){
            throw new Exception("Index out of bound");
        }
        return boxes[x][y];
    }

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

    public String export(){
        String ret = "";
        for(int r = 7; r >= 0; r--){
            for(int c = 0; c < 8; c++){
                ret += boxes[r][c].printChar();
            }
        }
        return ret;
    }
}
