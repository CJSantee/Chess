package colinsantee.chess;
import colinsantee.chess.Pieces.*;

public class Board {
    Spot[][] boxes;

    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public Board(){
        boxes = new Spot[8][8];
        this.resetBoard();
    }

    public void resetBoard(){
        // initialize white pieces
        boxes[0][0] = new Spot(0, 0, new Rook(true));   // Rook
        boxes[0][1] = new Spot(0, 1, new Knight(true)); // Knight
        boxes[0][2] = new Spot(0, 2, new Bishop(true)); // Bishop
        boxes[0][3] = new Spot(0, 3, new King(true));   // King
        boxes[0][4] = new Spot(0, 4, new Queen(true));  // Queen
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
        boxes[7][3] = new Spot(7, 3, new King(false));   // King
        boxes[7][4] = new Spot(7, 4, new Queen(false));  // Queen
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
}
