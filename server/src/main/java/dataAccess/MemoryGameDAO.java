package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.JoinData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO{

    private static List<GameData> gameList;
    public MemoryGameDAO() {
        gameList = new ArrayList<>();
    }
    @Override
    public Integer createGame(GameData gameName, String authToken) {
        int gameId = gameList.size() + 7;
        System.out.println(gameName.blackUsername());
        GameData game = new GameData(gameId, gameName.whiteUsername(), gameName.blackUsername(), gameName.gameName(), gameName.game());
        gameList.add(game);
        System.out.println(gameList);

        return gameId;
    }

    @Override
    public List<GameData> createList() {
        System.out.println(gameList);
        if (gameList.isEmpty()) {
            return null;
        }
        return gameList;
    }

    @Override
    public GameData getGame(Integer gameId) {

        for( GameData game: gameList) {
            if (game.gameID().equals(gameId)) return game;
        }
        return null;
    }

    @Override
    public GameData updateGame(String playerColor, GameData game, String username) {
        if (playerColor == null) {
            return game;
        }
        if (playerColor.equals("WHITE")) {
            GameData newGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
            gameList.add(game);
            System.out.println(newGame);
            return newGame;
        } else if (playerColor.equals("BLACK")) {
            GameData newGame =  new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
            gameList.add(game);
            System.out.println(newGame);
            return newGame;

        }
        return game;

    }

    @Override
    public void clear() {
        gameList.clear();

    }

}
