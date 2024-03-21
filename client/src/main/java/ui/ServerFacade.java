package ui;

import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import org.eclipse.jetty.client.HttpResponseException;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.util.log.Log;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class ServerFacade {

    public record RegisterResponse(String username, String authToken, String message) {
    }

    public record LogoutResponse(String authToken, String message) {
    }

    public record ListGamesResponse(List<GameData> games, String message) {
    }
    public record GameResponse(Integer gameID, String message) {
    }

    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }


    public UserData register(String username, String password, String email) throws ResponseException {
        Object user = new UserData(username, password, email);
        var path = "/user";
        System.out.println("before make request");
        UserData data = this.makeRequest("POST", path, user,null, null,  UserData.class);
        System.out.println(data);
        return data;////figure out what to return
    }

    public RegisterResponse login(String username, String password, String email) throws ResponseException { //returns a new authToken
        Object user = new UserData(username, password, email);
        var path = "/session";
        System.out.println("before make request");
       RegisterResponse newAuth = this.makeRequest("POST", path, user,null, null, RegisterResponse.class);
        System.out.println(newAuth);
        return newAuth;////figure out what to return
    }

    public LogoutResponse logout(String authToken) throws ResponseException { //returns a new authToken
        var path = "/session";
        System.out.println("before make request");
        LogoutResponse authNull = this.makeRequest("DELETE", path, null, "Authorization", authToken, LogoutResponse.class);
        System.out.println(authNull);
        return authNull;////figure out what to return
    }

    public ListGamesResponse listGamesServer(String authToken) throws ResponseException {
        var path = "/game";
        System.out.println("before make request");
        ListGamesResponse gamesResponse = this.makeRequest("GET", path, null, "Authorization", authToken, ListGamesResponse.class);
        System.out.println(gamesResponse.games());
        return gamesResponse;////figure out what to return
    }

    public GameResponse createGamesServer(String authToken, GameData gameName) throws ResponseException {
        var path = "/game";
        System.out.println("before make request");
        GameResponse createGameResponse = this.makeRequest("POST", path, gameName, "Authorization", authToken, GameResponse.class);

        return createGameResponse;
    }


    private <T> T makeRequest(String method, String path, Object request, String headerKey, String headerValue, Class<T> responseClass) throws HttpResponseException, ResponseException {
        try {

            URL url = (new URI("http://localhost:8080"+ path)).toURL();
//            "http://localhost:8080"
//            serverURL
            System.out.println(url);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (headerValue != null) {
                http.setRequestProperty(headerKey, headerValue);
            }
            writeBody(request, http);
            http.connect();
            System.out.println("i connected");
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
