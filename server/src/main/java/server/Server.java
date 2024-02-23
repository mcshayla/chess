package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.UserData;
import service.RegisterService;
import spark.*;

public class Server {

//    RegisterService regService;


//    public void RegisterServer(UserData username, UserData password, UserData email) {
//        this.regService = new RegisterService();
//    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        Spark.post("/user", new RegisterHandler());
        Spark.delete("/db", new ClearHandler());
        Spark.post("/session", new LoginHandler());

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
