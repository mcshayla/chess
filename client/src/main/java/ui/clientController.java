package ui;

import chess.ChessGame;
import model.GameData;
import model.JoinData;
import model.UserData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static ui.EscapeSequences.ERASE_SCREEN;

public class clientController {


    private static client.State state = client.State.SIGNEDOUT;
    static Scanner scanner = new Scanner(System.in);
    private static String username;

    private static boolean exit = false;

    private ServerFacade serverFacade;

    private String keepAuthToken = null;

    private List<GameData> listToJoinFrom = null;

    public clientController() {
        this.serverFacade = new ServerFacade(null);
    }

    public void run(String[] args) throws ResponseException {

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        out.println("Welcome to 240 chess. Type help to get started!");
        while (!exit){
            if (state == client.State.SIGNEDOUT) {
                signedOut();
            } else {
                out.println(String.format("Logged in as %s", username));
                out.println("Type help for options");
                signedIn();
            }
        }
    }
    private void signedIn() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//        String input = scanner.nextLine().toLowerCase();

//        out.println("Type help for options");
        String input = scanner.nextLine().toLowerCase();
        switch (input) {
            case "help":
            case "7":
                out.println("type a number: \n 1. Create Game \n 2. List Games  \n 3. Join Game \n 4. Observe \n 5. Logout \n 6. Quit \n 7. help ");
                break;
            case "1":
                createGame();
                break;
            case "2":
                listGames();
                break;
            case "3":
                joinGame(true);
                break;
            case "4":
                joinGame(false);
                break;
            case "5":
                logout();
                break;
            case "6":
            case "quit":
                out.println("quitting...");
                exit = true;
                break;
            default:
                out.println("invalid. Type help");
        }

    }
    private void signedOut() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String input = scanner.nextLine().toLowerCase();

        switch (input) {
            case "help":
                out.println("type a number: \n 1. Register \n 2. Login  \n 3. Quit \n 4. Help ");
                break;
            case "1":
                this.register();
                break;
            case "2":
                login();
                break;
            case "3":
            case "quit":
                out.println("quitting...");
                exit = true;
                break;
            default:
                out.println("invalid. Type help");
        }
    }
    private void register() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println("please enter a username");
        username = scanner.nextLine();
        out.println("please enter a password");
        String password = scanner.nextLine();
        out.println("please enter an email");
        String email = scanner.nextLine();

        UserData userData = this.serverFacade.register(username, password, email);

        if (userData != null) { ///depends on what I am returning. I need to figure out how to see the reponse when it returns....
            state = client.State.SIGNEDIN;
        } else {
            out.println("Register failed");
            exit = true;
        }
    }

    private void createGame() throws ResponseException {

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println("please enter game name");
        String givenGameName = scanner.nextLine();

        GameData gameData = new GameData(null, null, null, givenGameName, new ChessGame());

        ServerFacade.GameResponse gameResponse = this.serverFacade.createGamesServer(keepAuthToken, gameData);
        System.out.println(gameResponse.gameID());
        if (gameResponse.gameID() != null) { ///depends on what I am returning. I need to figure out how to see the reponse when it returns....
            out.println("new game has been created. YAY");
        } else {
            out.println("Creation of game failed, sad.");
            exit = true;
        }

    }

    private void joinGame(boolean joinTrue) throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String playerColor = null;
        if (joinTrue) {
            out.println("please WHITE or BLACK");
            playerColor = scanner.nextLine();
        } else {
            playerColor = null;
        }
        out.println("enter a game number for which game you want to join. you can list games to see the numbers:)");
        String gameNumString = scanner.nextLine();
        int gameNum = Integer.parseInt(gameNumString);
        int counter = 1;
        Integer gameID = null;
        listGames();
        for(GameData game: listToJoinFrom) {
            if (counter == gameNum) {
                gameID = game.gameID();
            }
        }
        JoinData join = new JoinData(playerColor, gameID);
        ServerFacade.JoinResponse joined = this.serverFacade.joinGameServer(keepAuthToken, join);

        if (joined.message() == null) { ///depends on what I am returning. I need to figure out how to see the reponse when it returns....
            chessBoardImg.main(null);
        } else {
            out.println("Register failed");
            exit = true;
        }
    }
//    private void observeGame(){
//        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//        String playerColor = null;
//        out.println("enter a game number for which game you want to join. you can list games to see the numbers:)");
//        String gameNumString = scanner.nextLine();
//        int gameNum = Integer.parseInt(gameNumString);
//        int counter = 1;
//        Integer gameID = null;
//        listGames();
//        for(GameData game: listToJoinFrom) {
//            if (counter == gameNum) {
//                gameID = game.gameID();
//            }
//        }
//        JoinData join = new JoinData(playerColor, gameID);
//        ServerFacade.JoinResponse joined = this.serverFacade.joinGameServer(keepAuthToken, join);
//
//        if (joined.message() == null) { ///depends on what I am returning. I need to figure out how to see the reponse when it returns....
//            chessBoardImg.main(null);
//        } else {
//            out.println("Register failed");
//            exit = true;
//        }
//    }
    private void logout() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        ServerFacade.LogoutResponse logoutResponse = this.serverFacade.logout(keepAuthToken);

        if (logoutResponse.message() == null) { ///depends on what I am returning. I need to figure out how to see the reponse when it returns....
            state = client.State.SIGNEDOUT;
        } else {
            out.println("Logout failed");
            exit = true;
        }
    }
    private void login() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println("please enter a username");
        username = scanner.nextLine();
        out.println("please enter a password");
        String password = scanner.nextLine();

        ServerFacade.RegisterResponse authToken = this.serverFacade.login(username, password, null);
        keepAuthToken = authToken.authToken();
        if (keepAuthToken != null) { ///depends on what I am returning. I need to figure out how to see the reponse when it returns....
            state = client.State.SIGNEDIN;
        } else {
            out.println("Login failed");
            exit = true;
        }
    }
    private void listGames() throws ResponseException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        ServerFacade.ListGamesResponse listGamesResponse = this.serverFacade.listGamesServer(keepAuthToken);
        int counter = 1;
        listToJoinFrom = listGamesResponse.games();
        for (GameData game : listGamesResponse.games()) {
            out.println(String.format("%d: gamename: %s, whiteUser: %s, blackUser: %s", counter, game.gameName(), game.whiteUsername(), game.blackUsername()));
            counter++;
        }
    }
}
