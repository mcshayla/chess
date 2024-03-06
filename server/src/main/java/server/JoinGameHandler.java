package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.GameData;
import model.JoinData;
import model.UserData;
import service.JoinGameService;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {

    MemoryGameDAO gameInstance = new MemoryGameDAO();
//    MemoryAuthDAO authInstance = new MemoryAuthDAO();
//    MemoryUserDAO userInstance = new MemoryUserDAO();
    SQLAuthDAO authInstance = new SQLAuthDAO();
    SQLUserDAO userInstance = new SQLUserDAO();

    public JoinGameHandler() throws DataAccessException {
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gamePlayerData = new Gson().fromJson(request.body(), JoinData.class);
        var authToken = request.headers("Authorization");

        JoinGameService joinGameService = new JoinGameService(gameInstance, authInstance, userInstance);
        JoinResponse joinResponse = joinGameService.joinGame(gamePlayerData, authToken);

        if (joinResponse.message() == null) {
            response.status(200);
        } else if (joinResponse.message().equals("Error: unauthorized") ) {
            response.status(401);
        } else if (joinResponse.message().equals("Error: bad request")) {
            response.status(400);
        } else if (joinResponse.message().equals("Error: already taken")) {
            response.status(403);
        } else {
            response.status(500);
        }

        return new Gson().toJson(joinResponse);
    }

}
