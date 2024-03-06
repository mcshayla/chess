package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.AuthData;
import model.UserData;
import service.LogoutService;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
//    MemoryUserDAO userInstance = new MemoryUserDAO();
//    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    SQLUserDAO userInstance = new SQLUserDAO();
    SQLAuthDAO authInstance = new SQLAuthDAO();

    public LogoutHandler() throws DataAccessException {
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
//        var out = new Gson().fromJson(request.h, AuthData.class);
        var out = request.headers("Authorization");


        LogoutService logoutService = new LogoutService(userInstance, authInstance);
        LogoutResponse logoutResponse = logoutService.logout(out); //returns register response
        System.out.println(out);
        System.out.println(logoutResponse);
        if (logoutResponse.message() == null) { ////look at specific cases and add if statements
            response.status(200);
        } else if (logoutResponse.message().equals("Error: unauthorized")) {
            response.status(401);
        } else {
            response.status(500);
        }
        return new Gson().toJson(logoutResponse);
    }
}
