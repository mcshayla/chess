package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.JoinData;
import model.UserData;
import server.JoinResponse;
import server.RegisterResponse;

public class JoinGameService {

    private final MemoryGameDAO gameInstance;
    private final MemoryAuthDAO authInstance;

    private final MemoryUserDAO userInstance;

    public JoinGameService(MemoryGameDAO memoryGameDAO, MemoryAuthDAO memoryAuthDAO, MemoryUserDAO memoryUserDAO) {
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

