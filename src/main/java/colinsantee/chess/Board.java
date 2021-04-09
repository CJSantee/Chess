package colinsantee.chess;
import colinsantee.chess.Pieces.*;

public class Board {
    Spot[][] boxes;

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
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(boxes[r][c].getPiece() != null)
                    System.out.print(boxes[r][c].getPiece().print());
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
