package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import server.GameResponse;

public class CreateGameService {

    private final SQLUserDAO userInstance;
    private final SQLAuthDAO authInstance;




    private final SQLGameDAO gameInstance;

    public CreateGameService(SQLUserDAO memoryUserDAO, SQLAuthDAO memoryAuthDAO, SQLGameDAO memoryGameDAO) {
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




