package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import server.GameResponse;
import server.RegisterResponse;

public class CreateGameService {

    private final MemoryUserDAO userInstance;
    private final MemoryAuthDAO authInstance;

    private final MemoryGameDAO gameInstance;

    public CreateGameService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO, MemoryGameDAO memoryGameDAO) {
        this.userInstance = memoryUserDAO;
        this.authInstance = memoryAuthDAO;
        this.gameInstance = memoryGameDAO;
    }

//    MemoryUserDAO userInstance = new MemoryUserDAO();
//    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    public GameResponse createGame(GameData gameName, String authToken) {

        try {

            AuthData getAuth = authInstance.getAuth(authToken);
            if (getAuth == null) {
                return new GameResponse(null, "Error: unauthorized");

            }

            Integer gameID = gameInstance.createGame(gameName, authToken);
            return new GameResponse(gameID, null);

        } catch (DataAccessException e) {
            return new GameResponse(null, "Error: description");
        }

    }
}




