package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import server.ListGamesResponse;

import java.util.List;

public class ListGamesService {

    private final MemoryGameDAO gameInstance;
    private final SQLAuthDAO authInstance;

    public ListGamesService(MemoryGameDAO gameInstance, SQLAuthDAO authInstance) {
        this.gameInstance = gameInstance;
        this.authInstance = authInstance;
    }

    public ListGamesResponse listGames(String authToken) {
        try {
            AuthData auth = authInstance.getAuth(authToken);
            if (auth == null) {
                return new ListGamesResponse(null, "Error: unauthorized");
            }

            List<GameData> games = gameInstance.createList();
            return new ListGamesResponse(games, null );
        } catch (DataAccessException e) {
            return new ListGamesResponse(null, "Error: description");
        }
    }

//    private final MemoryUserDAO userInstance;
//    private final MemoryAuthDAO authInstance;
//
//    public RegisterService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO) {
//        this.userInstance = memoryUserDAO;
//        this.authInstance = memoryAuthDAO;
//    }
//
//    public RegisterResponse register(UserData user) {
//
//
//
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
}
