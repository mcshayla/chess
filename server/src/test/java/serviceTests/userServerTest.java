package serviceTests;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import server.RegisterResponse;
import service.RegisterService;

public class userServerTest {


    RegisterService registerService = new RegisterService(new MemoryUserDAO(), new MemoryAuthDAO());
    RegisterResponse registerResponse = registerService.register(new UserData("username1", "password1", "email1"));





}
