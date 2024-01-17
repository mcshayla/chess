package chess;

import jdk.internal.icu.text.UnicodeSet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class MovementRules {



    public ArrayList<ChessPosition> bishopRules(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        ArrayList<ChessPosition> moves = new ArrayList<>();

        while (row+1 <= 8 && col+1 <= 8) { //up and right
            ChessPosition coordinate = new ChessPosition(row+1, col+1);
            moves.add(coordinate);
            row++;
            col++;

        }
        row = position.getRow();
        col = position.getColumn();
        while (row-1 >= 1 && col + 1 <= 8) { //down and right
            ChessPosition coordinate = new ChessPosition(row-1, col+1);
            moves.add(coordinate);
            row--;
            col++;
        }
        row = position.getRow();
        col = position.getColumn();
        while (row-1 >= 1 && col - 1 >= 1) { //down and left
            ChessPosition coordinate = new ChessPosition(row-1, col-1);
            moves.add(coordinate);
            row--;
            col--;
        }

        row = position.getRow();
        col = position.getColumn();

        while (row+1 <= 8 && col - 1 >= 1) { //up and left
            ChessPosition coordinate = new ChessPosition(row+1, col-1);
            moves.add(coordinate);
            row++;
            col--;
        }



       return moves;
    }
}
