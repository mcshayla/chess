package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.JoinData;
import model.UserData;
import org.junit.jupiter.api.Test;
import server.JoinResponse;
import server.ListGamesResponse;
import server.LogoutResponse;
import server.RegisterResponse;
import service.*;

import static dataAccess.MemoryUserDAO.userList;
import static org.junit.jupiter.api.Assertions.*;

public class userServerTest {


//    MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
//    MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    MemoryGameDAO memoryGameDAO = new MemoryGameDAO();

    SQLAuthDAO memoryAuthDAO = new SQLAuthDAO();
    SQLUserDAO memoryUserDAO = new SQLUserDAO();

    public userServerTest() throws DataAccessException {
    }


    @Test
    public void posRegister() throws DataAccessException {
        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        assertNotNull(MemoryUserDAO.userList);
    }
    @Test
    public void negRegister() throws DataAccessException {
        RegisterService registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        RegisterResponse registerResponse2 = registerService.register(new UserData("username1", "password1", "email1"));
        assertEquals(1, userList.size());

    }

    public LoginService registerLogin() {

        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        return loginService;
    }

    @Test
    public void posLogin() {
        LoginService loginService = registerLogin();
        loginService.login(new UserData("username1", "password1", "email1"));
        assertEquals(2, MemoryAuthDAO.authList.size());

    }

    @Test
    public void negLogin() {
        LoginService loginService = registerLogin();
        loginService.login(new UserData(null, "password1", "email1"));
        assertEquals(1, MemoryAuthDAO.authList.size());
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
        assertEquals(1, MemoryAuthDAO.authList.size());

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
        assertEquals(2, MemoryAuthDAO.authList.size());

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
        createGameService.createGame(gameName, authToken);
        assertEquals(1, MemoryGameDAO.gameList.size());

    }

    @Test
    public void negCreateGame() { // no auth token

        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));

        LoginService loginService = new LoginService(memoryUserDAO, memoryAuthDAO);
        loginService.login(new UserData("username1", "password1", "email1"));

        CreateGameService createGameService = new CreateGameService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);

        GameData gameName = new GameData(null, null,null, null, null);
        createGameService.createGame(gameName, null);
        System.out.println(MemoryGameDAO.gameList);
        assertEquals(0, MemoryGameDAO.gameList.size());
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
        JoinData joinData = new JoinData("WHITE",  7);
        JoinGameService joinGameService = new JoinGameService(memoryGameDAO, memoryAuthDAO, memoryUserDAO);
        RegisterService registerService = new RegisterService(memoryUserDAO, memoryAuthDAO);
        RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));
        String authToken = registerResponse.authToken();

        JoinResponse response =  joinGameService.joinGame(joinData, authToken);

        assertEquals("Error: bad request", response.message());
    }

    @Test void posJoinGame() { // no game
        JoinData joinData = new JoinData("WHITE",  7);
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
        dataBaseService.database();

        assertEquals(0,userList.size());
    }


}


