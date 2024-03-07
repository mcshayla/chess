package service;

import dataAccess.*;
import server.RegisterResponse;

public class DataBaseService {

    private final SQLUserDAO userInstance;
    private final SQLAuthDAO authInstance;

    private final SQLGameDAO gameInstance;

    public DataBaseService(SQLUserDAO memoryUserDAO, SQLAuthDAO memoryAuthDAO, SQLGameDAO memoryGameDAO) {
        this.userInstance = memoryUserDAO;
        this.authInstance = memoryAuthDAO;
        this.gameInstance = memoryGameDAO;
    }

    public RegisterResponse database() {
        try {
            userInstance.clear();
            authInstance.clear();
            gameInstance.clear();
            return new RegisterResponse(null, null, null);
        } catch (DataAccessException e) {
            return new RegisterResponse(null, null, "Error: description");
        }
//        } catch (DataAccessException e) {
//            return new RegisterResponse(null, null, "Error: description");
//        }

//            userInstance.clear();
//            authInstance.clear();
//            gameInstance.clear();



//


    }
}
