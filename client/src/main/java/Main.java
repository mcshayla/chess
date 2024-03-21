import chess.*;
import ui.ResponseException;
import ui.chessBoardImg;
import ui.clientController;

public class Main {

    public static void main(String[] args) throws ResponseException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        clientController clientController = new clientController();
        clientController.run(args);

//        chessBoardImg.main(args);
    }
}