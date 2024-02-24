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
