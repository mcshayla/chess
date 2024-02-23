package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import server.LogoutResponse;
import server.RegisterResponse;

public class LogoutService {

    private final MemoryUserDAO userInstance;
    private final MemoryAuthDAO authInstance;

    public LogoutService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO) {
        this.userInstance = memoryUserDAO;
        this.authInstance = memoryAuthDAO;
    }

    public LogoutResponse logout(String authToken) {

        try {

            if (authToken == null ) {
                return new LogoutResponse( null, "Error: unauthorized");
            }

            AuthData foundAuth = authInstance.getAuth(authToken);
            if (foundAuth == null) {
                return new LogoutResponse(null, "Error: unauthorized");
            }
            
            
            authInstance.deleteAuth(authToken);
            return new LogoutResponse( null, null);



        } catch (DataAccessException e) {
            return new LogoutResponse(null, "Error: description");
        }
    }
}
