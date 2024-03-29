package dataAccess;


import model.UserData;

public interface UserDAO {

    UserData getUser(String username) throws DataAccessException;

    UserData createUser(String username, String password, String email) throws DataAccessException;

    UserData clear() throws DataAccessException;
}
