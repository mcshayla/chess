package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private boolean isGameOver;
    private ChessGame.TeamColor turn;
    private ChessBoard chessboard;

    public ChessGame() {
        this.isGameOver = false;
        this.turn = TeamColor.WHITE;
        this.chessboard = new ChessBoard();


    }
    public boolean isGameOver() {
        return isGameOver;
    }


    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = null;
        outerLoop:
        for (int i = 1; i <=8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                ChessPiece testPiece = chessboard.getPiece(new ChessPosition(i, j));
                if (testPiece != null && testPiece.getPieceType() == ChessPiece.PieceType.KING && testPiece.getTeamColor() == teamColor){
                    kingPosition = new ChessPosition(i,j);
                    break outerLoop;
                }
            }
        }

        Collection<ChessMove> otherPlayers = new ArrayList<>();
        for (int i = 1; i <=8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                ChessPiece testPiece = chessboard.getPiece(new ChessPosition(i, j));
                if (testPiece != null && testPiece.getTeamColor() != teamColor) {
                    otherPlayers.addAll(testPiece.pieceMoves(chessboard, new ChessPosition(i,j)));
                }
            }
        }

        for (ChessMove move: otherPlayers) {
            if (move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }

        return false;




    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "isGameOver=" + isGameOver +
                ", turn=" + turn +
                ", chessboard=" + chessboard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return isGameOver == chessGame.isGameOver && turn == chessGame.turn && Objects.equals(chessboard, chessGame.chessboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isGameOver, turn, chessboard);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessboard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessboard;
    }
}
