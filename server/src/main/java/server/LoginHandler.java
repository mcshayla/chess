package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.UserData;
import service.LoginService;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
//    MemoryUserDAO userInstance = new MemoryUserDAO();
//    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    SQLAuthDAO authInstance = new SQLAuthDAO();
    SQLUserDAO userInstance = new SQLUserDAO();

    public LoginHandler() throws DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var log = new Gson().fromJson(request.body(), UserData.class);
        LoginService loginService = new LoginService(userInstance, authInstance);
        RegisterResponse registerResponse = loginService.login(log); //returns response
        System.out.println(log);
        System.out.println(registerResponse);
        if (registerResponse.message() == null) { ////look at specific cases and add if statements
            response.status(200);
        } else if (registerResponse.message().equals("Error: unauthorized")) {
            response.status(401);
        } else {
            response.status(500);
        }
        return new Gson().toJson(registerResponse);
    }

}
