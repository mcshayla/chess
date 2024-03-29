package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.UserData;
import service.RegisterService;
import spark.*;

public class Server {

    public int run(int desiredPort){
        try {
            Spark.port(desiredPort);

            Spark.staticFiles.location("web");


            Spark.post("/user", new RegisterHandler());
            Spark.delete("/db", new ClearHandler());
            Spark.post("/session", new LoginHandler());
            Spark.delete("/session", new LogoutHandler());
            Spark.post("/game", new CreateGameHandler());
            Spark.get("/game", new ListGamesHandler());
            Spark.put("/game", new JoinGameHandler());


            // Register your endpoints and handle exceptions here.

            Spark.awaitInitialization();
            return Spark.port();
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
