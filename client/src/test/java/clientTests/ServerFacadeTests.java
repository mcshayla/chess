package clientTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import dataAccess.SQLUserDAO;
import model.GameData;
import model.JoinData;
import org.junit.jupiter.api.*;
import server.RegisterResponse;
import server.Server;
import service.DataBaseService;
import ui.ResponseException;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    static SQLAuthDAO memoryAuthDAO;

    static {
        try {
            memoryGameDAO = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            memoryAuthDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            memoryUserDAO = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    static SQLUserDAO memoryUserDAO;
    static SQLGameDAO memoryGameDAO;



    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);

    }

    @BeforeEach
    public void clearAll() {
        DataBaseService clearHandler;
        clearHandler = new DataBaseService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
        RegisterResponse dbResponse = clearHandler.database();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void posRegister() throws Exception, ResponseException {
//        Assertions.assertTrue(true);
        var authData = facade.register("player3", "password3", "p2@email.com");
        System.out.println(authData.authToken());
        Assertions.assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void negRegister() throws ResponseException, RuntimeException {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facade.register("player3", null, "p2@email.com");
        });
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void posLogin() throws ResponseException {
        var authData = facade.register("player3", "password3", "p2@email.com");
        var registerResponse = facade.login("player3", "password3", null);
        Assertions.assertTrue(registerResponse.authToken().length() > 10);
    }

    @Test
    void negLogin() throws ResponseException, RuntimeException {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facade.login("player3", null, "p2@email.com");
        });
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void posLogout() throws ResponseException {
        var loginResponse = facade.register("player3", "password3", "p2@email.com");
        var registerResponse = facade.login("player3", "password3", null);
        var logoutResponse = facade.logout(registerResponse.authToken());
        Assertions.assertTrue(logoutResponse.message() == null);
    }

    @Test
    void negLogout() throws ResponseException, RuntimeException {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facade.logout(null);
        });
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void posListGamesServer() throws ResponseException {
        var authData = facade.register("player3", "password3", "p2@email.com");
        var registerResponse = facade.login("player3", "password3", null);
        var listGamesResponse = facade.listGamesServer(registerResponse.authToken());
        Assertions.assertTrue(listGamesResponse.message() == null);
    }

    @Test
    void negListGamesServer() throws ResponseException, RuntimeException {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facade.listGamesServer(null);
        });
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void posCreateGamesServer() throws ResponseException {
        var authData = facade.register("player3", "password3", "p2@email.com");
        var registerResponse = facade.login("player3", "password3", null);
        var gameResponse = facade.createGamesServer(registerResponse.authToken(), new GameData(null, null, null, registerResponse.authToken(), new ChessGame()));
        Assertions.assertTrue(gameResponse.message() == null);
    }

    @Test
    void negCreateGamesServer() throws ResponseException, RuntimeException {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facade.createGamesServer(null, new GameData(null, null, null, "fakeAuthToken", new ChessGame()) );
        });
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void posJoinGameServer() throws ResponseException {
        var authData = facade.register("player3", "password3", "p2@email.com");
        var registerResponse = facade.login("player3", "password3", null);
        var gameResponse = facade.createGamesServer(registerResponse.authToken(), new GameData(null, null, null, registerResponse.authToken(), new ChessGame()));
        var joinResponse = facade.joinGameServer(registerResponse.authToken(), new JoinData("WHITE", 1));
        Assertions.assertTrue(joinResponse.message()== null);
    }

    @Test
    void negJoinGameServer() throws ResponseException {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facade.joinGameServer(null, new JoinData("BLACK", 3) );
        });
        Assertions.assertNotNull(exception.getMessage());
    }

}
