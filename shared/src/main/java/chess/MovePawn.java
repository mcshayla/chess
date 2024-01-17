package chess;

import java.util.ArrayList;

public class MovePawn {
    public ArrayList<ChessPosition> pawnRules(ChessBoard chessBoard, ChessPiece chessPiece, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();
        ChessGame.TeamColor color = chessPiece.getTeamColor();

        if (row == 2) {
            ChessPosition coordinate = new ChessPosition(row+1, col);
            moves.add(coordinate);
            coordinate = new ChessPosition(row+2, col);
            moves.add(coordinate);
        } else {
            ChessPosition coordinate = new ChessPosition(row+1, col);
            moves.add(coordinate);
        }
        return moves;
    }

}
