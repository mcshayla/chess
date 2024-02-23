package server;

import chess.ChessGame;
import model.AuthData;

public record RegisterResponse(String username, String authToken, String message) {

}
