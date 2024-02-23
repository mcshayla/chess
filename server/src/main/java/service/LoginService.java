package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import server.RegisterResponse;

public class LoginService {

    private final MemoryUserDAO userInstance;
    private final MemoryAuthDAO authInstance;

    public LoginService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO) {
        this.userInstance = memoryUserDAO;
        this.authInstance = memoryAuthDAO;
    }

public RegisterResponse login(UserData user) {


    try {

//        if (user.username() == null || user.password() == null || user.email() == null) {
//            return new RegisterResponse(null, null, "Error: unauthorized");
//        }

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
