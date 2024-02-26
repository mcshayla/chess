package dataAccess;


import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDAO implements UserDAO{

    public static List<UserData> userList;
    public MemoryUserDAO() {
        userList = new ArrayList<>();
    }


    @Override
    public UserData getUser(String username) throws DataAccessException {
        for (UserData user : userList) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

        @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {

//        for (UserData existingUser : userList) {
//            if (existingUser.username().equals(username)) {
//                throw new DataAccessException("User with the same username already exists.");
//            }
//        }

        UserData user = new UserData(username, password, email);
        userList.add(user);
        System.out.println(userList);

        return user;

    }

    @Override
    public void clear() throws DataAccessException {
        userList.clear();

    }

}
