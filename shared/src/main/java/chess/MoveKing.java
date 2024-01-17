package chess;

import java.util.ArrayList;

public class MoveKing {
    public ArrayList<ChessPosition> kingRules(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();

        ChessPosition coordinate = new ChessPosition(row, col+1); //RIGHT
        moves.add(coordinate);
        coordinate = new ChessPosition(row + 1, col + 1); //RIGHT UP
        moves.add(coordinate);
        coordinate = new ChessPosition(row + 1, col); // UP
        moves.add(coordinate);
        coordinate = new ChessPosition(row + 1, col - 1); //UP LEFT
        moves.add(coordinate);
        coordinate = new ChessPosition(row, col - 1);//LEFT
        moves.add(coordinate);
        coordinate = new ChessPosition(row - 1, col - 1);//LEFT DOWN
        moves.add(coordinate);
        coordinate = new ChessPosition(row - 1, col ); //DOWN
        moves.add(coordinate);
        coordinate = new ChessPosition(row -1, col + 1); //DOWN RIGHT
        moves.add(coordinate);


        return moves;
    }
}
