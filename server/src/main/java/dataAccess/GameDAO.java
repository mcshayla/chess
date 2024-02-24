package dataAccess;

import model.GameData;
import model.JoinData;

import java.util.ArrayList;
import java.util.List;

public interface GameDAO {

    Integer createGame(GameData gameName, String authToken);

    List<GameData> createList();

    GameData getGame(Integer gameId);

    GameData updateGame(String playerColor, GameData game, String username);


    void clear();
}
