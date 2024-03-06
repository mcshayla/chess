package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.UserData;
import service.ListGamesService;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {

//    MemoryAuthDAO authInstance = new MemoryAuthDAO();
    SQLAuthDAO authInstance = new SQLAuthDAO();
    MemoryGameDAO gameInstance = new MemoryGameDAO();

    public ListGamesHandler() throws DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
//        var reg = new Gson().fromJson(request.body(), UserData.class);
        var authToken = request.headers("Authorization");
        ListGamesService listGamesService = new ListGamesService(gameInstance, authInstance);
        ListGamesResponse listGamesResponse = listGamesService.listGames(authToken); //returns register response
//        System.out.println(reg);
//        System.out.println(registerResponse);
        if (listGamesResponse.message() == null) { ////look at specific cases and add if statements
            response.status(200);
        } else if (listGamesResponse.message().equals("Error: unauthorized")) {
            response.status(401);
        } else {
            response.status(500);
        }
        return new Gson().toJson(listGamesResponse);
    }
}
