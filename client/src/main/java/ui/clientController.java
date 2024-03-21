package ui;

import chess.ChessBoard;
import model.UserData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.ERASE_SCREEN;

public class clientController {


    private static client.State state = client.State.SIGNEDOUT;
    static Scanner scanner = new Scanner(System.in);
    private static String username;

    private static boolean exit = false;

    private ServerFacade serverFacade;

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
    private static void signedIn() {
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
                joinGame();
                break;
            case "4":
                observeGame();
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
            return;
//            displayPostLogin();
        } else {
            out.println("Register failed");
            exit = true;
        }
    }

    private static void createGame() {
        System.out.println("made it to createGame function");
        exit = true;
    }
    private static void listGames() {
        System.out.println("made it to listGame function");
        exit = true;
    }

    private static void joinGame(){
        System.out.println("made it to joinGame function");
        exit = true;
    }
    private static void observeGame(){
        System.out.println("made it to observeGame function");
        exit = true;
    }
    private static void logout(){
        System.out.println("made it to logout function");
        exit = true;
    }
    private static void login() {
        System.out.println("made it to login function");
        exit = true;
    }
}
