package dataAccess;


import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDAO implements UserDAO{

    private static List<UserData> userList;
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

        UserData user = new UserData(username, password, email);
        userList.add(user);
        System.out.println(userList);

        return user;

    }

    @Override
    public void clear() throws DataAccessException {
        userList.clear();

    }


    //create a list of objects

    //methods:

    // getUser: pass in user, see if it's in the list, if it is, throw error

    //create user: pass in user. create an object of UserData. append it to list

    //create authToken

    //
}
