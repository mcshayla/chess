package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     * if I have a board and a position, I can find the piece type
     *
     * come up with possible places.
     * ex: king. any direction by 1 but not off board and not in a spot where it's occupied by the same color.
     *
     *
     * create a classmovement for each piece.
     * probably used a switch statement.
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        PieceType pieceType = piece.getPieceType();

        MovementRules movementRules = new MovementRules();

        if (pieceType == ChessPiece.PieceType.BISHOP) {
            return movementRules.bishopRules(pieceType, myPosition);
        }
        return null;
    }
//        switch (pieceType) {
//            case KING -> {
//
//            }
//
//            case PAWN -> {
//
//            }
//
//            case ROOK -> {
//
//            }
//
//            case QUEEN -> {
//
//            }
//
//            case BISHOP -> {
//                return movementRules.bishopRules(pieceType, myPosition);
//            }
//
//
//            case KNIGHT -> {
//
//            }
//
//        }
//
//    }
}
