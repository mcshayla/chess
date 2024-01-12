package chess;

import jdk.internal.icu.text.UnicodeSet;

import java.util.ArrayList;
import java.util.Collection;
public class MovementRules {

    public Collection<ChessMove> bishopRules(ChessPiece.PieceType pieceType, ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<int[]> moves = new ArrayList<>();

        while (row+1 <= 8 && col + 1 <= 8) {
            int[] coordinate = {row+1, col+1};
            moves.add(coordinate);
            row++;
            col++;
        }

       return moves;
    }
}
