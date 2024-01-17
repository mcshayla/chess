package chess;

import java.util.ArrayList;

public class MoveRook {
    public ArrayList<ChessPosition> rookRules(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();

        while (col+1 <= 8) { //right
            ChessPosition coordinate = new ChessPosition(row, col+1);
            moves.add(coordinate);
            col++;

        }
        row = position.getRow();
        col = position.getColumn();
        while (row-1 >= 1) { //down
            ChessPosition coordinate = new ChessPosition(row-1, col);
            moves.add(coordinate);
            row--;
        }
        row = position.getRow();
        col = position.getColumn();
        while (col - 1 >= 1) { //left
            ChessPosition coordinate = new ChessPosition(row, col-1);
            moves.add(coordinate);
            col--;
        }

        row = position.getRow();
        col = position.getColumn();

        while (row+1 <= 8) { //up
            ChessPosition coordinate = new ChessPosition(row+1, col);
            moves.add(coordinate);
            row++;
        }



        return moves;
    }
}
