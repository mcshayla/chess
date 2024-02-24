package dataAccess;

import model.AuthData;
import model.UserData;

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
    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData findDelete = getAuth(authToken);
        authList.remove(findDelete);
        System.out.println(authList);

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {

            for (AuthData auth : authList) {
                if (auth.authToken().equals(authToken)) {
                    return auth;
                }
            }
            return null;
        }

    @Override
    public String getUName(String authToken) throws DataAccessException {
        for(AuthData auth: authList) {
            if (auth.authToken().equals(authToken)) {
                return auth.username();
            }
        }
        return "didn't match";
    }


    @Override
    public void clear() {
        authList.clear();
    }
}




    //delete auth

    //get auth


