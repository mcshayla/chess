package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.JoinData;
import server.JoinResponse;

public class JoinGameService {

    private final MemoryGameDAO gameInstance;
    private final SQLAuthDAO authInstance;

    private final SQLUserDAO userInstance;

    public JoinGameService(MemoryGameDAO memoryGameDAO, SQLAuthDAO memoryAuthDAO, SQLUserDAO memoryUserDAO) {
        this.gameInstance = memoryGameDAO;
        this.authInstance = memoryAuthDAO;
        this.userInstance = memoryUserDAO;
    }
    public JoinResponse joinGame(JoinData playerAndGame, String authToken) {
        try {


            AuthData auth = authInstance.getAuth(authToken);

            String playerColor = playerAndGame.playerColor();
            Integer gameId = playerAndGame.gameID();

            GameData game = gameInstance.getGame(gameId);


            String username = authInstance.getUName(authToken);

            if (auth == null) {
                return new JoinResponse("Error: unauthorized");
            }
            if (game == null) {
                return new JoinResponse("Error: bad request");
            }

            if (playerColor != null && playerColor.equals("BLACK") && game.blackUsername() != null) {
                return new JoinResponse("Error: already taken");
            }
            if (playerColor != null && playerColor.equals("WHITE") && game.whiteUsername() != null) {
                return new JoinResponse("Error: already taken");
            }


            game = gameInstance.updateGame(playerColor, game, username);



            return new JoinResponse(null);
        } catch (DataAccessException e) {
            return new JoinResponse("Error: description");
        }
    }
}

