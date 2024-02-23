package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

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


//        gameName.whiteUsername(), String blackUsername, String gameName, ChessGame
//        game);
//        userList.add(user);
//        System.out.println(userList);
//
//        return user;
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
    public void clear() {
        gameList.clear();

    }
}
