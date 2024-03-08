package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.*;
import service.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTest {

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
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        assertNotNull(newAuth);
        memoryAuthDAO.deleteAuth(newAuth.authToken());
        AuthData test = memoryAuthDAO.getAuth(newAuth.authToken());
        assertNull(test);
    }
    @Test
    public void negDeleteAuth() throws DataAccessException {
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        assertNotNull(newAuth);
        memoryAuthDAO.deleteAuth("no token");
        AuthData test = memoryAuthDAO.getAuth(newAuth.authToken());
        assertNotNull(test);

    }

    @Test
    public void negGetAuth() throws DataAccessException {
        AuthData authData = memoryAuthDAO.getAuth("Ilikepie");
        assertNull(authData);
    }

    @Test
    public void posGetAuth() throws DataAccessException {
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        AuthData authData = memoryAuthDAO.getAuth(newAuth.authToken());
        assertNotNull(authData);
    }

    @Test
    public void posGetUName() throws DataAccessException {
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        String username = memoryAuthDAO.getUName(newAuth.authToken());
        assertEquals(username, "username1");

    }

    @Test
    public void negGetUName() throws DataAccessException {
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        String username = memoryAuthDAO.getUName(null);
        assertNull(username);

    }

    @Test
    public void clearAuth() throws DataAccessException {
        AuthData newAuth = memoryAuthDAO.createAuth("username1");
        assertNotNull(newAuth);
        memoryAuthDAO.clear();
        AuthData test = memoryAuthDAO.getAuth(newAuth.authToken());
        assertNull(test);
    }

    @Test
    public void posCreateGame() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        String authToken = UUID.randomUUID().toString();
        Integer id = memoryGameDAO.createGame(gameData, authToken);
        assertNotNull(id);
    }

    @Test
    public void negCreateGame() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        String authToken = UUID.randomUUID().toString();
        Integer id = memoryGameDAO.createGame(gameData, null);
        assertNull(id);
    }

    @Test
    public void posCreateList() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        Integer id = memoryGameDAO.createGame(gameData, "token");
        List<GameData> gameDataList = memoryGameDAO.createList();
        assertEquals(gameDataList.size(), 1);
    }

    @Test
    public void negCreateList() throws DataAccessException {
        List<GameData> gameDataList = memoryGameDAO.createList();
        assertEquals(gameDataList.size(), 0);
    }

    @Test
    public void posGetGame() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        Integer id = memoryGameDAO.createGame(gameData, "test token");
        GameData gameData1 = memoryGameDAO.getGame(1);
        assertNotNull(gameData1);
    }

    @Test
    public void negGetGame() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        Integer id = memoryGameDAO.createGame(gameData, "test token");
        assertNull(memoryGameDAO.getGame(2));
    }

    @Test
    public void posUpdateGame() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        GameData updated = memoryGameDAO.updateGame("WHITE", gameData, "ohhyeahh");
        assertNull(updated);
    }

    @Test void negUpdateGame() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        GameData updated = memoryGameDAO.updateGame(null, gameData, "ohhyeahh");
        assertNotNull(updated);
    }

    @Test void gameClear() throws DataAccessException {
        GameData gameData = new GameData(1, "whiteU", "blackU", "coolName", new ChessGame());
        String authToken = UUID.randomUUID().toString();
        Integer id = memoryGameDAO.createGame(gameData, authToken);
        memoryGameDAO.clear();
        GameData test = memoryGameDAO.getGame(1);
        assertNull(test);
    }

    @Test void posSQLGame() throws DataAccessException {
        new SQLGameDAO();
        try (var connection = DatabaseManager.getConnection()) {
            // Execute a query to check if the game table exists
            try (Statement statement = connection.createStatement()) {
                String query = "SHOW TABLES LIKE 'gameTable'";
                boolean tableExists = statement.executeQuery(query).next();
                // Assert that the game table exists
                assertTrue(tableExists);
//        new SQLGameDAO();
//        assertNotNull(memoryGameDAO);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void posSQLUser() throws DataAccessException {
        new SQLUserDAO();
        try (var connection = DatabaseManager.getConnection()) {
            // Execute a query to check if the game table exists
            try (Statement statement = connection.createStatement()) {
                String query = "SHOW TABLES LIKE 'userTable'";
                boolean tableExists = statement.executeQuery(query).next();
                // Assert that the game table exists
                assertTrue(tableExists);
//        new SQLGameDAO();
//        assertNotNull(memoryGameDAO);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @BeforeEach
//    public void deleteData() throws DataAccessException{
//        try (Connection connection = DatabaseManager.getConnection()) {
//            try (Statement statement = connection.createStatement()) {
//                String query = "DROP DATABASE IF EXISTS `chess`";
//                statement.executeUpdate(query);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test void posSQLAuth() throws DataAccessException {
        new SQLUserDAO();
        try (var connection = DatabaseManager.getConnection()) {
            // Execute a query to check if the game table exists
            try (Statement statement = connection.createStatement()) {
                String query = "SHOW TABLES LIKE 'authTable'";
                boolean tableExists = statement.executeQuery(query).next();
                // Assert that the game table exists
                assertTrue(tableExists);
//        new SQLGameDAO();
//        assertNotNull(memoryGameDAO);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test void negSQLGame() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {

            try (Statement statement = connection.createStatement()) {
                String query = "SHOW TABLES LIKE 'gameTable'";
                boolean tableExists = statement.executeQuery(query).next();

                assertTrue(tableExists);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void negSQLUser() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            // Execute a query to check if the game table exists
            try (Statement statement = connection.createStatement()) {
                String query = "SHOW TABLES LIKE 'userTable'";
                boolean tableExists = statement.executeQuery(query).next();
                // Assert that the game table exists
                assertTrue(tableExists);
//        new SQLGameDAO();
//        assertNotNull(memoryGameDAO);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void negSQLAuth() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            // Execute a query to check if the game table exists
            try (Statement statement = connection.createStatement()) {
                String query = "SHOW TABLES LIKE 'authTable'";
                boolean tableExists = statement.executeQuery(query).next();
                // Assert that the game table exists
                assertTrue(tableExists);
//        new SQLGameDAO();
//        assertNotNull(memoryGameDAO);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
