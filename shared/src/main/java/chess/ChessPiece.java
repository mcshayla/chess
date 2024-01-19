package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
     *
     *
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        PieceType pieceType = piece.getPieceType();
        PieceType promType = piece.type;



        Collection<ChessMove> chessMoves = new ArrayList<>();

        switch (pieceType) {

            case BISHOP -> {
                MovementRules movementRules = new MovementRules();
                ArrayList<ChessPosition> coordinates = movementRules.bishopRules(board, piece, myPosition);
//
//
                for (ChessPosition coordinate : coordinates) {
                    chessMoves.add(new ChessMove(myPosition, coordinate, null));

                }
                return chessMoves;

            }
            case ROOK -> {
                MoveRook moveRook = new MoveRook();
                ArrayList<ChessPosition> coordinates = moveRook.rookRules(board, piece, myPosition);
                for (ChessPosition coordinate : coordinates) {
                    chessMoves.add(new ChessMove(myPosition, coordinate, null));

                }
                return chessMoves;
            }
            case QUEEN -> {
                MoveQueen moveQueen = new MoveQueen();
                ArrayList<ChessPosition> coordinates = moveQueen.queenRules(board, piece, myPosition);
                for (ChessPosition coordinate : coordinates) {
                    chessMoves.add(new ChessMove(myPosition, coordinate, null));
                }
                return chessMoves;
            }
            case KING -> {
                MoveKing moveKing = new MoveKing();
                ArrayList<ChessPosition> coordinates = moveKing.kingRules(board, piece, myPosition);
                for (ChessPosition coordinate : coordinates) {
                    chessMoves.add(new ChessMove(myPosition, coordinate, null));
                }
                return chessMoves;
            }
            case KNIGHT -> {
                MoveKnight moveKing = new MoveKnight();
                ArrayList<ChessPosition> coordinates = moveKing.knightRules(board, piece,myPosition);
                for (ChessPosition coordinate : coordinates) {
                    chessMoves.add(new ChessMove(myPosition, coordinate, null));
                }
                return chessMoves;
            }
            case PAWN -> {
                MovePawn movePawn = new MovePawn();
                HashSet<ChessMove> hashchessMoves = new HashSet<>(chessMoves);
                ArrayList<ChessPosition> coordinates = movePawn.pawnRules(board, piece, myPosition);
                for (ChessPosition coordinate : coordinates) {
//                    if (piece.getTeamColor() == ChessGame.TeamColor.WHITE && coordinate.getRow() == 8) {
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.ROOK));
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.QUEEN));
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.BISHOP));
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.KNIGHT));
//                    } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK && coordinate.getRow() == 1) {
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.ROOK));
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.QUEEN));
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.BISHOP));
//                        chessMoves.add(new ChessMove(myPosition, coordinate, PieceType.KNIGHT));
//                    } else {
//                        chessMoves.add(new ChessMove(myPosition, coordinate, null));
//                    }
                    if (piece.getTeamColor() == ChessGame.TeamColor.WHITE && coordinate.getRow() == 8) {
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.ROOK));
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.QUEEN));
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.BISHOP));
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.KNIGHT));
                    } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK && coordinate.getRow() == 1) {
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.ROOK));
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.QUEEN));
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.BISHOP));
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, PieceType.KNIGHT));
                    } else {
                        hashchessMoves.add(new ChessMove(myPosition, coordinate, null));
                    }
                }
                return hashchessMoves;
            }
        }
        return null;
        }
}
