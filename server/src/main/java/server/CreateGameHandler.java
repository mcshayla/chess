package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.GameData;
import model.UserData;
import service.CreateGameService;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {


//    MemoryUserDAO userInstance = new MemoryUserDAO();
//    MemoryAuthDAO authInstance = new MemoryAuthDAO();
    SQLUserDAO userInstance = new SQLUserDAO();

    SQLAuthDAO authInstance = new SQLAuthDAO();
    MemoryGameDAO gameInstance = new MemoryGameDAO();

    public CreateGameHandler() throws DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gameName = new Gson().fromJson(request.body(), GameData.class);
//        System.out.println(gameName);
//        System.out.println(gameName.blackUsername());
        var authToken = request.headers("Authorization");
        CreateGameService createGameService = new CreateGameService(userInstance, authInstance, gameInstance);
        GameResponse registerResponse = createGameService.createGame(gameName, authToken); //returns register response
//        System.out.println(reg);
//        System.out.println(registerResponse);
        if (registerResponse.message() == null) { ////look at specific cases and add if statements
            response.status(200);
        } else if (registerResponse.message().equals("Error: unauthorized")) {
            response.status(401);
        } else if (registerResponse.message().equals("Error: bad request")) {
            response.status(400);
        } else {
            response.status(500);
        }
        return new Gson().toJson(registerResponse);
    }

}
