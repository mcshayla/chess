package dataAccess;

import model.AuthData;

public interface AuthDAO {

    AuthData createAuth(String username) throws DataAccessException;

    //create auth

    //delete auth

    //get auth


    void clear();
}
