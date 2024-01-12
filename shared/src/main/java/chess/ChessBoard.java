package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    int rows = 8;
    int cols = 8;
    ChessPiece[][] board = new ChessPiece[rows][cols];
    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     * indexing in the two dimentional array,
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     *
     * two rows of blacks and two rows of whites in a specific spot.
     * use a two dementional array as instance variable of pieces
     * when you call addPiece, you are passed position as a row and a column
     * everytime add piece is called, put the piece in the row
     *
     * there are 32 pieces and you have to set all 32 pieces in board
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }
}
