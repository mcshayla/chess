package server;

import model.GameData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public record ListGamesResponse(List<GameData> games, String message) {

}
