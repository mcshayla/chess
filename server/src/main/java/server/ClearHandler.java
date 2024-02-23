package server;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import service.DataBaseService;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {

    MemoryUserDAO userInstance = new MemoryUserDAO();
    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    MemoryGameDAO gameInstance = new MemoryGameDAO();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var reg = new Gson().fromJson(request.body(), UserData.class);
        DataBaseService dbService = new DataBaseService(userInstance, authInstance, gameInstance);
        RegisterResponse dbResponse = dbService.database(); //returns register response
        System.out.println(reg);
        System.out.println(dbResponse);
        if (dbResponse.message() == null) { ////look at specific cases and add if statements
            response.status(200);
        } else {
            response.status(500);
        }
        return new Gson().toJson(dbResponse);

    }
}
