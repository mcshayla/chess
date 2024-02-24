package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import server.RegisterResponse;

public class DataBaseService {

    private final MemoryUserDAO userInstance;
    private final MemoryAuthDAO authInstance;

    private final MemoryGameDAO gameInstance;

    public DataBaseService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO, MemoryGameDAO memoryGameDAO) {
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
