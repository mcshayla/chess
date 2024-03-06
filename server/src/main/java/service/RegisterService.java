package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import server.RegisterResponse;

public class RegisterService {

    private final SQLUserDAO userInstance;
    private final SQLAuthDAO authInstance;

    public RegisterService(SQLUserDAO memoryUserDAO, SQLAuthDAO memoryAuthDAO) {
        this.userInstance = memoryUserDAO;
        this.authInstance = memoryAuthDAO;
    }

//    MemoryUserDAO userInstance = new MemoryUserDAO();
//    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    public RegisterResponse register(UserData user) {



        try {

            if (user.username() == null || user.password() == null || user.email() == null) {

                return new RegisterResponse(null, null, "Error: bad request");
            }

            UserData user1 = userInstance.getUser(user.username());
            if (user1 != null) {

                return new RegisterResponse(null, null, "Error: already taken");
            }

            UserData newUser1 = userInstance.createUser(user.username(), user.password(), user.email());


            AuthData newAuth = authInstance.createAuth(user.username());


            return new RegisterResponse(user.username(), newAuth.authToken(), null);

        } catch (DataAccessException e){

            return new RegisterResponse(null, null, "Error: description");
        }

    }

}
