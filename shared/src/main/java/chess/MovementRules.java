package chess;

//import jdk.internal.icu.text.UnicodeSet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class MovementRules {



    public ArrayList<ChessPosition> bishopRules(ChessBoard chessBoard, ChessPiece chessPiece, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();
        ChessGame.TeamColor color = chessPiece.getTeamColor();



        while (row+1 <= 8 && col+1 <= 8) { //up and right

            ChessPosition coordinate = new ChessPosition(row+1, col+1);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                row++;
                col++;

            } else if (chessBoard.getPiece(coordinate).getTeamColor() == color) {
                break;

            } else {
                moves.add(coordinate);
                break;
            }
        }
        row = position.getRow();
        col = position.getColumn();
        while (row-1 >= 1 && col + 1 <= 8) { //down and right
            ChessPosition coordinate = new ChessPosition(row-1, col+1);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                row--;
                col++;

            } else if (chessBoard.getPiece(coordinate).getTeamColor() == color) {
                break;

            } else {
                moves.add(coordinate);
                break;
            }
        }
        row = position.getRow();
        col = position.getColumn();
        while (row-1 >= 1 && col - 1 >= 1) { //down and left
            ChessPosition coordinate = new ChessPosition(row-1, col-1);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                row--;
                col--;

            } else if (chessBoard.getPiece(coordinate).getTeamColor() == color) {
                break;

            } else {
                moves.add(coordinate);
                break;
            }
        }

        row = position.getRow();
        col = position.getColumn();

        while (row+1 <= 8 && col - 1 >= 1) { //up and left
            ChessPosition coordinate = new ChessPosition(row+1, col-1);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                row++;
                col--;

            } else if (chessBoard.getPiece(coordinate).getTeamColor() == color) {
                break;

            } else {
                moves.add(coordinate);
                break;
            }
        }



       return moves;
    }
}
