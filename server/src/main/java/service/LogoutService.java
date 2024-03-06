package service;

import dataAccess.*;
import model.AuthData;
import server.LogoutResponse;

public class LogoutService {

    private final SQLUserDAO userInstance;
    private final SQLAuthDAO authInstance;

    public LogoutService(SQLUserDAO memoryUserDAO, SQLAuthDAO memoryAuthDAO) {
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
