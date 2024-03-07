package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDAO{

    public SQLGameDAO()  throws DataAccessException {
        configureDatabase();
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  gameTable (
              `gameID` int AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `chessGame` blob,
              PRIMARY KEY (`gameID`)
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
    public Integer createGame(GameData gameName, String authToken) {
        Integer gameId = null;
        try (var conn = DatabaseManager.getConnection()) {
            String blackUser = gameName.blackUsername();
            String whiteUser = gameName.whiteUsername();
            String gName = gameName.gameName();
            ChessGame chessGame = gameName.game();


            try (var preparedStatement = conn.prepareStatement("INSERT INTO gameTable (whiteUsername, blackUsername, gameName, chessGame) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, whiteUser);
                preparedStatement.setString(2, blackUser);
                preparedStatement.setString(3, gName);
                var json = new Gson().toJson(chessGame);
                preparedStatement.setString(4, json);
                preparedStatement.executeUpdate();
                ResultSet id = preparedStatement.getGeneratedKeys();
                if (id.next()) {
                    gameId = id.getInt(1);
                }
            }
            } catch (SQLException | DataAccessException e) {
                throw new RuntimeException(e);
        }
        return gameId;
    }


    @Override
    public List<GameData> createList() throws DataAccessException {
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, chessGame FROM gameTable";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        var id = rs.getInt("gameID");
                        var white = rs.getString("whiteUsername");
                        var black = rs.getString("blackUsername");
                        var name = rs.getString("gameName");
                        var game = rs.getString("chessGame");
                        ChessGame chessGame = new Gson().fromJson(game, ChessGame.class);
                        GameData newGameData = new GameData(id, white, black, name, chessGame);
                        result.add(newGameData);
                    }

                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("createList");
        }
        return result;
    }

    @Override
    public GameData getGame(Integer gameId) {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, chessGame FROM gameTable WHERE GameID = ?")) {
                preparedStatement.setInt(1, gameId);
                try (var rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        var id = rs.getInt("gameID");
                        var white = rs.getString("whiteUsername");
                        var black = rs.getString("blackUsername");
                        var name = rs.getString("gameName");
                        var game = rs.getString("chessGame");
                        ChessGame chessGame = new Gson().fromJson(game, ChessGame.class);
                        return new GameData(id, white, black, name, chessGame);
                    }
                    return null;
                }
            }

        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GameData updateGame(String playerColor, GameData game, String username) {
        Integer gameId = game.gameID();
        try (var conn = DatabaseManager.getConnection()) {
            if (playerColor == null) {
                return game;
            }
            if (playerColor.equals("WHITE")) {
                try (var preparedStatement = conn.prepareStatement("UPDATE gameTable SET whiteUsername=? WHERE gameId=?")) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setInt(2, gameId);
                    preparedStatement.executeUpdate();
                }
            } else {
                try (var preparedStatement = conn.prepareStatement("UPDATE gameTable SET blackUsername=? WHERE gameId=?")) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setInt(2, gameId);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE gameTable")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("truncating authTable error");
        }
    }
}
