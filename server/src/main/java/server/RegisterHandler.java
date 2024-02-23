package server;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    MemoryUserDAO userInstance = new MemoryUserDAO();
    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var reg = new Gson().fromJson(request.body(), UserData.class);
        RegisterService registerService = new RegisterService(userInstance, authInstance);
        RegisterResponse registerResponse = registerService.register(reg); //returns register response
//        System.out.println(reg);
//        System.out.println(registerResponse);
        if (registerResponse.message() == null) { ////look at specific cases and add if statements
            response.status(200);
        } else if (registerResponse.message().equals("Error: already taken")) {
            response.status(403);
        } else if (registerResponse.message().equals("Error: bad request") ){
            response.status(400);
        } else {
            response.status(500);
        }
        return new Gson().toJson(registerResponse);


        //deseralize the request body into an java object
        //call Registerservice call with object(register request)
        //set the response status code
        //return response body
//        return null;
    }
}
