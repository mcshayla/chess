package ui;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.client.HttpResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {


    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }


    public UserData register(String username, String password, String email) throws ResponseException {
        Object user = new UserData(username, password, email);
        var path = "/user";
        System.out.println("before make request");
        UserData data = this.makeRequest("POST", path, user, UserData.class);
        System.out.println(data);
        return data;////figure out what to return
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws HttpResponseException, ResponseException {
        try {

            URL url = (new URI("http://localhost:8080"+ path)).toURL();
//            "http://localhost:8080"
//            serverURL
            System.out.println(url);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
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
