package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import server.RegisterResponse;

public class LoginService {

    private final SQLUserDAO userInstance;
    private final SQLAuthDAO authInstance;

    public LoginService(SQLUserDAO memoryUserDAO, SQLAuthDAO memoryAuthDAO) {
        this.userInstance = memoryUserDAO;
        this.authInstance = memoryAuthDAO;
    }

public RegisterResponse login(UserData user) {


    try {

        UserData user1 = userInstance.getUser(user.username());

        if (user1 == null){

            return new RegisterResponse(null, null, "Error: unauthorized");
        }
        if (!user.password().equals(user1.password())) {
            return new RegisterResponse(null, null, "Error: unauthorized");
        }

        AuthData newAuth = authInstance.createAuth(user.username());

        return new RegisterResponse(user.username(), newAuth.authToken(), null);

    } catch (DataAccessException e){

        return new RegisterResponse(null, null, "Error: description");
    }

}
}
