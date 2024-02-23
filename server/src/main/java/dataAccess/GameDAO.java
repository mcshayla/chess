package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public interface GameDAO {

    Integer createGame(GameData gameName, String authToken);

    List<GameData> createList();


    void clear();
}
