package server;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {

    MemoryGameDAO gameInstance = new MemoryGameDAO();
    MemoryAuthDAO authInstance = new MemoryAuthDAO();

    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
