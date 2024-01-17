package chess;

import java.util.ArrayList;

public class MoveQueen {
    public ArrayList<ChessPosition> queenRules(ChessBoard chessBoard, ChessPiece chessPiece, ChessPosition position) {
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

        row = position.getRow();
        col = position.getColumn();
        while (col+1 <= 8) { //right
            ChessPosition coordinate = new ChessPosition(row, col+1);

            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
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
        while (row-1 >= 1) { //down
            ChessPosition coordinate = new ChessPosition(row-1, col);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                row--;

            } else if (chessBoard.getPiece(coordinate).getTeamColor() == color) {
                break;

            } else {
                moves.add(coordinate);
                break;
            }
        }
        row = position.getRow();
        col = position.getColumn();
        while (col - 1 >= 1) { //left
            ChessPosition coordinate = new ChessPosition(row, col-1);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
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

        while (row+1 <= 8) { //up
            ChessPosition coordinate = new ChessPosition(row+1, col);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                row++;

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
