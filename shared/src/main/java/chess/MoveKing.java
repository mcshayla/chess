package chess;

import java.util.ArrayList;
import java.util.List;

public class MoveKing {
    public ArrayList<ChessPosition> kingRules(ChessBoard chessBoard, ChessPiece chessPiece, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();
        ChessGame.TeamColor color = chessPiece.getTeamColor();


        List<ChessPosition> coordinates = new ArrayList<>();
        coordinates.add(new ChessPosition(row, col+1)); //RIGHT
        coordinates.add(new ChessPosition(row + 1, col + 1)); //RIGHT UP
        coordinates.add(new ChessPosition(row + 1, col)); // UP
        coordinates.add(new ChessPosition(row + 1, col - 1)); //UP LEFT
        coordinates.add(new ChessPosition(row, col - 1));//LEFT
        coordinates.add(new ChessPosition(row - 1, col - 1));//LEFT DOWN
        coordinates.add(new ChessPosition(row - 1, col )); //DOWN
        coordinates.add(new ChessPosition(row -1, col + 1)); //DOWN RIGHT

        for (ChessPosition coordinate: coordinates) {
            if (coordinate.getRow() <=8 && coordinate.getRow() >=1 && coordinate.getColumn() >=1 && coordinate.getColumn() <=8 && (chessBoard.getPiece(coordinate) == null || color != chessBoard.getPiece(coordinate).getTeamColor())) {
                moves.add(coordinate);
            }
        }




        return moves;
    }
}
