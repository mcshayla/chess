package ui;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static chess.ChessPiece.PieceType.*;
import static ui.EscapeSequences.*;
public class chessBoardImg {

    private static final int BOARD_SIZE_IN_SQUARES = 10;

    private static final int SQUARE_SIZE_IN_CHARS = 1;

    private static final String EMPTY = "   ";

    private static final int LINE_WIDTH_IN_CHARS = 1;

    private static final ChessBoard board = new ChessBoard();

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        board.resetBoard();
//        out.print(board);
//        out.println();
        drawChessBoard(out, true);
        drawVerticalLine(out);
        drawChessBoard(out, false);

    }

    private static void drawChessBoard(PrintStream out, boolean first) {
        int startRow;
        int endRow;
        int increment;
        if (first) {
            startRow = 0;
            endRow = BOARD_SIZE_IN_SQUARES;
            increment = 1;
        } else {
            startRow = BOARD_SIZE_IN_SQUARES-1;
            endRow = -1;
            increment = -1;
        }

        for (int boardRow = startRow; boardRow != endRow; boardRow += increment) {
            if (boardRow == 0 || boardRow == 9) { //10?
                drawHeaders(out);
            } else {
                drawInsideBoard(out, boardRow, first);
            }
        }
    }

    private static void drawInsideBoard(PrintStream out, int boardRow, boolean first) {
        setBlack(out);
        ArrayList<String> inside = new ArrayList<>();
        String boarder = String.format(" %d ", boardRow);
        inside.add(boarder);

        for (int boardCol = 1; boardCol < BOARD_SIZE_IN_SQUARES-1; ++boardCol) {
            ChessPosition position = new ChessPosition(boardRow, boardCol);
            ChessPiece piece = board.getPiece(position);
            if (piece == null) {
                inside.add("   ");
            }
            else if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (piece.getPieceType() == KING) {
                    inside.add(" K ");
                } else if (piece.getPieceType() == QUEEN) {
                    inside.add(" Q ");
                } else if (piece.getPieceType() == BISHOP) {
                    inside.add(" B ");
                } else if (piece.getPieceType() == KNIGHT) {
                    inside.add(" N ");
                } else if (piece.getPieceType() == ROOK) {
                    inside.add(" R ");
                } else if (piece.getPieceType() == PAWN) {
                    inside.add(" P ");
                }
            } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (piece.getPieceType() == KING) {
                    inside.add(" k ");
                } else if (piece.getPieceType() == QUEEN) {
                    inside.add(" q ");
                } else if (piece.getPieceType() == BISHOP) {
                    inside.add(" b ");
                } else if (piece.getPieceType() == KNIGHT) {
                    inside.add(" n ");
                } else if (piece.getPieceType() == ROOK) {
                    inside.add(" r ");
                } else if (piece.getPieceType() == PAWN) {
                    inside.add(" p ");
                }
            }
        }
        inside.add(boarder);
        if (first) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                drawInside(out, inside.get(boardCol), boardRow, boardCol);
            }
        } else {
            for (int boardCol = BOARD_SIZE_IN_SQUARES-1; boardCol >= 0; --boardCol) {
                drawInside(out, inside.get(boardCol), boardRow, boardCol);

            }
        }

        out.println();
    }

    private static void drawInside(PrintStream out, String insideText, Integer boardRow, Integer boardCol) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        if (boardCol == 0 || boardCol == 9) {
            out.print(EMPTY.repeat(prefixLength));
            printHeaderText(out, insideText);
            out.print(EMPTY.repeat(suffixLength));
        } else if (boardCol%2 == 0 && boardRow%2 == 0) {
            out.print(EMPTY.repeat(prefixLength));
            printInsideText(out, insideText);
            out.print(EMPTY.repeat(suffixLength));
        } else if (boardCol%2 != 0 && boardRow% 2 != 0) {
            out.print(EMPTY.repeat(prefixLength));
            printInsideText(out, insideText);
            out.print(EMPTY.repeat(suffixLength));
        } else {
            out.print(EMPTY.repeat(prefixLength));
            printInsideTextTwo(out, insideText);
            out.print(EMPTY.repeat(suffixLength));
        }
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = { "   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", " " };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printInsideText(PrintStream out, String piece) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(piece);
        setBlack(out);
    }
    private static void printInsideTextTwo(PrintStream out, String piece) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print(piece);
        setBlack(out);
    }
    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_MAGENTA);
        out.print(player);
        setBlack(out);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private static void drawVerticalLine(PrintStream out) {

        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES;
//        * SQUARE_SIZE_IN_CHARS +
//                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;

        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
            setMagenta(out);
            out.print(EMPTY.repeat(boardSizeInSpaces));

            setBlack(out);
            out.println();
        }
    }

    private static void setMagenta(PrintStream out) {
        out.print(SET_BG_COLOR_MAGENTA);
        out.print(SET_TEXT_COLOR_MAGENTA);
    }

}
