package chess;

import java.util.ArrayList;
import java.util.List;

public class MoveKnight {
    public ArrayList<ChessPosition> knightRules(ChessBoard chessBoard, ChessPiece chessPiece, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();
        ChessGame.TeamColor color = chessPiece.getTeamColor();


        List<ChessPosition> coordinates = new ArrayList<>();
        coordinates.add(new ChessPosition(row+2, col+1));
        coordinates.add(new ChessPosition(row+2, col-1));
        coordinates.add(new ChessPosition(row-2, col-1));
        coordinates.add(new ChessPosition(row-2, col+1));
        coordinates.add(new ChessPosition(row+1, col+2));
        coordinates.add(new ChessPosition(row-1, col+2));
        coordinates.add(new ChessPosition(row+1, col-2));
        coordinates.add(new ChessPosition(row-1, col-2));

        for (ChessPosition coordinate: coordinates) {
            if (coordinate.getRow() <=8 && coordinate.getRow() >=1 && coordinate.getColumn() >=1 && coordinate.getColumn() <=8 && (chessBoard.getPiece(coordinate) == null || color != chessBoard.getPiece(coordinate).getTeamColor())) {
                moves.add(coordinate);
            }
        }

        return moves;
    }
}
