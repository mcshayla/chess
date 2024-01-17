package chess;

import java.util.ArrayList;

public class MovePawn {
    public ArrayList<ChessPosition> pawnRules(ChessBoard chessBoard, ChessPiece chessPiece, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();
        ChessGame.TeamColor color = chessPiece.getTeamColor();


        if (color == ChessGame.TeamColor.WHITE) {
            ChessPosition coordinate = new ChessPosition(row + 1, col);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                if (row == 2) {
                    coordinate = new ChessPosition(row + 2, col);
                    if (chessBoard.getPiece(coordinate) == null) {
                        moves.add(coordinate);
                    }
                }
            }
            coordinate = new ChessPosition(row + 1, col+1);
            if (chessBoard.getPiece(coordinate) != null && chessBoard.getPiece(coordinate).getTeamColor() != color ) {
                moves.add(coordinate);
            }
            coordinate = new ChessPosition(row + 1, col-1);
            if (chessBoard.getPiece(coordinate) != null && chessBoard.getPiece(coordinate).getTeamColor() != color) {
                    moves.add(coordinate);
            }
        } else {
            ChessPosition coordinate = new ChessPosition(row - 1, col);
            if (chessBoard.getPiece(coordinate) == null) {
                moves.add(coordinate);
                if (row == 7) {
                    coordinate = new ChessPosition(row - 2, col);
                    if (chessBoard.getPiece(coordinate) == null) {
                        moves.add(coordinate);
                    }
                }
            }
            coordinate = new ChessPosition(row - 1, col+1);
            if (chessBoard.getPiece(coordinate) != null && chessBoard.getPiece(coordinate).getTeamColor() != color ) {
                moves.add(coordinate);
            }
            coordinate = new ChessPosition(row - 1, col-1);
            if (chessBoard.getPiece(coordinate) != null && chessBoard.getPiece(coordinate).getTeamColor() != color) {
                moves.add(coordinate);
            }
        }
        return moves;
    }

}
