package dataAccess;

import model.AuthData;

import javax.xml.crypto.Data;

public interface AuthDAO {

    AuthData createAuth(String username) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    String getUName(String authToken) throws DataAccessException;

    //create auth

    //delete auth

    //get auth


    void clear() throws DataAccessException;
}
