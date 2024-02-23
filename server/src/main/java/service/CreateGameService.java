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


//        try {
//
//            if (user.username() == null || user.password() == null || user.email() == null) {
//                return new RegisterResponse(null, null, "Error: bad request");
//            }
//
//            UserData user1 = userInstance.getUser(user.username());
//            if (user1 != null) {
//
//                return new RegisterResponse(null, null, "Error: already taken");
//            }
//
//            UserData newUser1 = userInstance.createUser(user.username(), user.password(), user.email());
//
//
//            AuthData newAuth = authInstance.createAuth(user.username());
//
//            return new RegisterResponse(user.username(), newAuth.authToken(), null);
//
//        } catch (DataAccessException e){
//
//            return new RegisterResponse(null, null, "Error: description");
//        }
//
//    }



