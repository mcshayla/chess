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
        ChessPiece start = chessboard.getPiece(startPosition);
        if (start == null) {
            return null;
        }
        ChessGame.TeamColor color = start.getTeamColor();
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessMove> valMoves = new ArrayList<>();
        boolean foundInOtherPlayers = false;

        moves.addAll(start.pieceMoves(chessboard, new ChessPosition(startPosition.getRow(), startPosition.getColumn())));


        for (ChessMove move : moves) {
            ChessBoard copiedboard = new ChessBoard(chessboard);
            if (movePiece(move, copiedboard)) {
                valMoves.add(move);
            }

        }



//        if (start.getPieceType() == ChessPiece.PieceType.KING) {
//            Collection<ChessMove> otherPlayers = Players(chessboard, color);
//
//
//            for (ChessMove move : moves) {
//                foundInOtherPlayers = false;
//                ChessPosition end = move.getEndPosition();
//                for (ChessMove other : otherPlayers) {
//                    if (other.getEndPosition().equals(end)) {
//                        foundInOtherPlayers = true;
//                        break;
//                    }
//                }
//                if (!foundInOtherPlayers) {
//                    ChessBoard copiedboard = new ChessBoard(chessboard);
//                    if (movePiece(move, copiedboard)) {
//                        valMoves.add(move);
//                    }
//                }
//            }
//
//        }else {
//
//            for (ChessMove move : moves) {
//                ChessBoard copiedboard = new ChessBoard(chessboard);
//                if (movePiece(move, copiedboard)) {
//                    valMoves.add(move);
//                }
//
//            }
//
//        }

//        if (isInCheck(color)) {
//            for (ChessMove move : moves) {
//                ChessBoard copiedboard = new ChessBoard(chessboard);
//                if (movePiece(move, copiedboard)) {
//                    valMoves.add(move);
//                }
//
//            }
//        }  else {
//
//            for (ChessMove move : moves) {
//                ChessBoard copiedboard = new ChessBoard(chessboard);
//                if (movePiece(move, copiedboard)) {
//                    valMoves.add(move);
//                }
//
//            }
//        }

//        if (start.getPieceType() == ChessPiece.PieceType.KING) {
//            Collection<ChessMove> otherPlayers = Players(chessboard, color);
//
//
//            for (ChessMove move: moves) {
//                foundInOtherPlayers = false;
//                ChessPosition end = move.getEndPosition();
//                for (ChessMove other : otherPlayers) {
//                    if (other.getEndPosition().equals(end)) {
//                        foundInOtherPlayers = true;
//                        break;
//                    }
//                }
//                if (!foundInOtherPlayers) {
//                    ChessBoard copiedboard = new ChessBoard(chessboard);
//                    if (movePiece(move, copiedboard)) {
//                        valMoves.add(move);
//                    }
//                }
//            }
//
//
//        } else {
//
//            for (ChessMove move : moves) {
//                ChessBoard copiedboard = new ChessBoard(chessboard);
//                if (movePiece(move, copiedboard)) {
//                    valMoves.add(move);
//                }
//
//            }
//        }
//        if (valMoves.isEmpty()) {
//            return null;
//        } else {
//            return valMoves;
//        }
        return valMoves;

    }

    private boolean movePiece(ChessMove move, ChessBoard board) {
        ChessPosition piecePosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(piecePosition);
        ChessGame.TeamColor teamColor = piece.getTeamColor();


        board.addPiece(piecePosition, null);


//        if (promo != null) {+
        if (move.getPromotionPiece() != null) {
            ChessPiece promo = new ChessPiece (piece.getTeamColor(), move.getPromotionPiece());
            board.addPiece(endPosition, promo);
        } else {
            board.addPiece(endPosition, piece);
        }
//        ChessPosition kingPosition = findKing(board, piece.getTeamColor());

//        if (move.getEndPosition().equals(kingPosition)){
//            return false;
//        }
//        return true;

        if(!isInCheck(teamColor, board)){ /////////board didn't update, so it didn't properly check:((((
            return true;
        }
        return false;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPosition piecePosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = chessboard.getPiece(piecePosition);
        TeamColor color = piece.getTeamColor();


        Collection<ChessMove> valid = validMoves(piecePosition);
        boolean canMove = false;
        for (ChessMove val: valid) {
            if (val.getEndPosition().equals(endPosition)){
                canMove = true;
            }
        }

        if (!canMove) {
            throw new InvalidMoveException("Cannot move there");
        }

        ////is king in danger

        if (color == getTeamTurn()) {
            movePiece(move, chessboard);

            if (color == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }

        } else {
            throw new InvalidMoveException("Not your turn");
        }


    }




    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    private ChessPosition findKing(ChessBoard board, TeamColor teamColor) {
        for (int i = 1; i <=8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                ChessPiece testPiece = board.getPiece(new ChessPosition(i, j));
                if (testPiece != null && testPiece.getPieceType() == ChessPiece.PieceType.KING && testPiece.getTeamColor() == teamColor){
                    return new ChessPosition(i,j);
                }
            }
        }
        return null;
    }


    private Collection<ChessMove> Players(ChessBoard board, TeamColor teamcolor) {
        Collection<ChessMove> otherPlayers = new ArrayList<>();
        for (int i = 1; i <=8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                ChessPiece testPiece = board.getPiece(new ChessPosition(i, j));
                if (testPiece != null && testPiece.getTeamColor() != teamcolor) {
                    otherPlayers.addAll(testPiece.pieceMoves(board, new ChessPosition(i,j)));
                }
            }
        }
        return otherPlayers;

    }
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(chessboard, teamColor);

        Collection<ChessMove> otherPlayers = Players(chessboard, teamColor);
        for (ChessMove move: otherPlayers) {
            if (move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }
        return false;

    }

    public boolean isInCheck(TeamColor teamColor, ChessBoard board) {
        ChessPosition kingPosition = findKing(board, teamColor);

        Collection<ChessMove> otherPlayers = Players(board, teamColor);
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

//        ChessBoard copied = new ChessBoard(chessboard);
        Collection<ChessMove> players = new ArrayList<>();
        if (isInCheck(teamColor)) {
            if (teamColor == TeamColor.WHITE) {
                players = Players(chessboard, TeamColor.BLACK);
            } else {
                players = Players(chessboard, TeamColor.WHITE);
            }
            for (ChessMove player: players) {
                ChessBoard copied = new ChessBoard(chessboard);
                if (movePiece(player,copied)) {
                    return false;
                }
            }
            return true;
        }

        return false;

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        Collection<ChessMove> players = new ArrayList<>();
        boolean stale = false;
        if (!isInCheckmate(teamColor)){
            if (teamColor == TeamColor.WHITE) {
                players = Players(chessboard, TeamColor.BLACK);
            } else {
                players = Players(chessboard, TeamColor.WHITE);
            }
            stale = true;
            for (ChessMove player: players) {
                if (!validMoves(player.getStartPosition()).isEmpty()) {
                    stale = false;
                    break;
                }
            }

        }
        return stale;
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
