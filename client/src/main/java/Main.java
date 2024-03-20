import chess.*;
import ui.chessBoardImg;
import ui.creatingUI;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        client.main(args);
//        creatingUI.main(args);
        chessBoardImg.main(args);
    }
}