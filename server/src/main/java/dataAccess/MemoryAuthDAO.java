package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//import UUID
public class MemoryAuthDAO implements AuthDAO{

    private static List<AuthData> authList;

    public MemoryAuthDAO() {
        authList = new ArrayList<>();
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {


        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);

        authList.add(newAuth);

        return newAuth; // possibly return newAuth instead??
    }

    @Override
    public void clear() {

    }

    //delete auth

    //get auth

}
