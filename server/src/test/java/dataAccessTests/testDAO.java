package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import dataAccess.SQLUserDAO;
import model.AuthData;
import model.GameData;
import model.JoinData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.*;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

public class testDAO {

    static SQLGameDAO memoryGameDAO;

    static {
        try {
            memoryGameDAO = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static SQLAuthDAO memoryAuthDAO;

    static {
        try {
            memoryAuthDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static SQLUserDAO memoryUserDAO;

    static {
        try {
            memoryUserDAO = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void userServerTest() throws DataAccessException {
    }

    @BeforeEach
    public void clearAll() {
        DataBaseService clearHandler;
        clearHandler = new DataBaseService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
        RegisterResponse dbResponse = clearHandler.database();
    }

    @Test
    public void posGetUser() throws DataAccessException {
        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        UserData user = new UserData("username1", "password1", "email1");
        UserData user1 = memoryUserDAO.getUser(user.username());
        assertEquals(user1.email(), user.email());

    }

    @Test
    public void negGetUser() throws DataAccessException {
        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        UserData user = new UserData("username2", "password1", "email1");
        UserData user1 = memoryUserDAO.getUser(user.username());
        assertNull(user1);

    }

    @Test
    public void posCreateUser() throws DataAccessException {
//        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        UserData user = new UserData("username1", "password1", "email1");
//        RegisterResponse registerResponse = registerService.register(user);
        UserData newUser1 = memoryUserDAO.createUser(user.username(), user.password(), user.email());

        assertNotNull(newUser1);

    }

    @Test
    public void negCreateUser() throws DataAccessException {
        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        UserData user = new UserData("username1", "password1", "email1");
        RegisterResponse registerResponse = registerService.register(user);
//        UserData newUser1 = memoryUserDAO.createUser(user.username(), user.password(), user.email());
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            memoryUserDAO.createUser(user.username(), user.password(), user.email());
        });
        assertEquals(exception.getMessage(), "error createUser function");
    }

    @Test
    public void posUserClear() throws DataAccessException {
        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        UserData user = new UserData("username1", "password1", "email1");
        RegisterResponse registerResponse = registerService.register(user);
        UserData newUser1 = memoryUserDAO.clear();
        assertNull(newUser1);
    }

    @Test void posCreateAuth() throws DataAccessException {
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        assertNotNull(newAuth);
    }

    @Test void negCreateAuth() throws DataAccessException {

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            AuthData newAuth = memoryAuthDAO.createAuth(null);
        });
        assertEquals(exception.getMessage(), "error createAuth function");

    }

    @Test
    public void posDeleteAuth() throws DataAccessException {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            memoryAuthDAO.deleteAuth(null);
        });

        assertEquals(exception.getMessage(), "error deleteAuth function");


    }
    @Test
    public void negDeleteAuth() throws DataAccessException {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            memoryAuthDAO.deleteAuth(null);
        });

        assertEquals(exception.getMessage(), "error deleteAuth function");


    }

    @Test
    public void posGetAuth() throws DataAccessException {
        AuthData authData = memoryAuthDAO.getAuth("Ilikepie");
        assertNull(authData);
    }

    @Test
    public void negGetAuth() throws DataAccessException {
        AuthData authData = memoryAuthDAO.getAuth(null);
        assertNotNull(authData);
    }






//    @Test
//    public void negRegister() throws DataAccessException {
//        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
//        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
//        RegisterResponse registerResponse2 = registerService.register(new UserData("username1", "password1", "email1"));
////        assertEquals(1, userList.size());
//        assertEquals("Error: already taken", registerResponse2.message());
//
//
//    }

    public LoginService registerLogin() {

        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        return loginService;
    }

    @Test
    public void posLogin() {
        LoginService loginService = registerLogin();
        RegisterResponse registerResponse = loginService.login(new UserData("username1", "password1", "email1"));

//        assertEquals(2, MemoryAuthDAO.authList.size());
        assertNull(registerResponse.message());


    }

    @Test
    public void negLogin() {
        LoginService loginService = registerLogin();
        RegisterResponse registerResponse = loginService.login(new UserData(null, "password1", "email1"));
        assertEquals("Error: unauthorized", registerResponse.message());
    }

    @Test
    public void posLogout() {

        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        LogoutService logoutService = new LogoutService(memoryUserDAO, memoryAuthDAO);
        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));
        String authToken = registerResponse.authToken();
        LogoutResponse logoutResponse = logoutService.logout(authToken);
//        assertEquals(1, MemoryAuthDAO.authList.size());
        assertNull(logoutResponse.message());


    }

    @Test
    public void negLogout() {

        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        LogoutService logoutService = new LogoutService(memoryUserDAO, memoryAuthDAO);
        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));
        String authToken = registerResponse.authToken();
        LogoutResponse logoutResponse = logoutService.logout(null);
        assertEquals("Error: unauthorized", logoutResponse.message());

    }

    public CreateGameService createGame() {
        return null;
    }


    @Test
    public void posCreateGame() {


        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));

        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));

        CreateGameService createGameService = new CreateGameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
        String authToken = registerResponse.authToken();

        GameData gameName = new GameData(null, null,null, null, null);
        GameResponse gameResponse = createGameService.createGame(gameName, authToken);
        assertNull(gameResponse.message());

    }

    @Test
    public void negCreateGame() { // no auth token

        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));

        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));

        CreateGameService createGameService = new CreateGameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);

        GameData gameName = new GameData(null, null,null, null, null);
        GameResponse gameResponse = createGameService.createGame(gameName, null);

        assertEquals("Error: unauthorized", gameResponse.message());
    }

    @Test
    public void posListGame() {


        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));

        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));

        CreateGameService createGameService = new CreateGameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);

        ListGamesService listGamesService = new ListGamesService(memoryGameDAO,memoryAuthDAO);
        String authToken = registerResponse.authToken();
        ListGamesResponse response =  listGamesService.listGames(authToken);

        assertEquals(null, response.message());

    }

    @Test
    void negListGames() {


        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));

        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));

        CreateGameService createGameService = new CreateGameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);

        ListGamesService listGamesService = new ListGamesService(memoryGameDAO,memoryAuthDAO);
        String authToken = registerResponse.authToken();
        ListGamesResponse response =  listGamesService.listGames("fake auth");

        assertEquals("Error: unauthorized", response.message());

    }

    @Test void negJoinGame() { // no game
        JoinData joinData = new JoinData("WHITE",  1);
        JoinGameService joinGameService = new JoinGameService(memoryGameDAO, memoryAuthDAO, memoryUserDAO);
        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        String authToken = registerResponse.authToken();

        JoinResponse response =  joinGameService.joinGame(joinData, authToken);

        assertEquals("Error: bad request", response.message());
    }

    @Test void posJoinGame() { // no game
        JoinData joinData = new JoinData("WHITE",  1);
        JoinGameService joinGameService = new JoinGameService(memoryGameDAO, memoryAuthDAO, memoryUserDAO);
        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        String authToken = registerResponse.authToken();

        CreateGameService createGameService = new CreateGameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
        GameData gameName = new GameData(null, null,null, null, null);
        createGameService.createGame(gameName, authToken);

        JoinResponse response =  joinGameService.joinGame(joinData, authToken);
        assertEquals(null, response.message());

    }

    @Test void posClear() {
        LoginService loginService = registerLogin();
        DataBaseService dataBaseService = new DataBaseService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
        RegisterResponse response = dataBaseService.database();

        assertEquals(null, response.message());
    }


}
