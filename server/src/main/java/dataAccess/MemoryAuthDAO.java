package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.*;

public class MemoryAuthDAO implements AuthDAO{

//    public static List<AuthData> authList;


//    public MemoryAuthDAO() {
//        authList = new ArrayList<>();
//    }

    public static Map<String, AuthData> authList;

    public MemoryAuthDAO() {authList = new HashMap<>();}

    @Override
    public AuthData createAuth(String username) throws DataAccessException {


        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);

//        authList.add(newAuth);
        authList.put(authToken, newAuth);

        return newAuth;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData findDelete = getAuth(authToken);
        authList.remove(authToken);

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {

//            for (AuthData auth : authList) {
//                if (auth.authToken().equals(authToken)) {
//                    return auth;
//                }
//            }
//            return null;
            AuthData auth = authList.get(authToken);
            if (auth != null) {
                return auth;
            }
            return null;

        }

    @Override
    public String getUName(String authToken) throws DataAccessException {
//        for(AuthData auth: authList) {
//            if (auth.authToken().equals(authToken)) {
//                return auth.username();
//            }
//        }
        AuthData auth = authList.get(authToken);
        if (auth != null) {
            return auth.username();
        }
        return "didn't match";
    }


    @Override
    public void clear() throws DataAccessException{
        authList.clear();
    }
}



