package dataAccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{
    
    public SQLAuthDAO()  throws DataAccessException {
        configureDatabase();
    }

    //conn.setCatalog("pet_store");     this is like saying USE database "pet_store"
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authTable (
              `authString` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authString`),
            )
           """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("connection in configure database:(");
        }
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException{
        String authToken = UUID.randomUUID().toString();
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO authTable (authString, username) VALUES(?, ?)")){
                preparedStatement.setString(1, authToken );
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("error createAuth function");
        }

        return new AuthData(authToken, username);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try(var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM authTable WHERE authString=?")) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("error deleteAuth function");
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT authString, username FROM authTable WHERE authString = ?")) {
                preparedStatement.setString(1, authToken);
                try (var rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        var authString = rs.getString("authString");
                        var user = rs.getString("username");
                        return new AuthData(authString, user);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("getAuth failed");
        }
    }

        @Override
    public String getUName(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM authTable WHERE authString = ?")) {
                preparedStatement.setString(1, authToken);
                try (var rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("username");
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("getAuth failed");
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE authTable")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("truncating authTable error");
        }
    }
}
