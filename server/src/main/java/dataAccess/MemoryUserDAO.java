package dataAccess;


import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO{

//    public static List<UserData> userList;
//    public MemoryUserDAO() {
//        userList = new ArrayList<>();
//    }

    public static Map<String, UserData> userList;

    public MemoryUserDAO() {
        userList = new HashMap<>();
    }


    @Override
    public UserData getUser(String username) throws DataAccessException {
//        for (UserData user : userList) {
//            if (user.username().equals(username)) {
//                return user;
//            }
//        }
        UserData user = userList.get(username);
        if( user != null){
            return user;
        }
        return null;
    }
        @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {

        UserData user = new UserData(username, password, email);
        userList.put(username, user);
        System.out.println(userList);

        return user;

    }

    @Override
    public UserData clear() throws DataAccessException {
        userList.clear();

        return null;
    }

}
