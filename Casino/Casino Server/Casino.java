import java.util.*;

public class Casino {
    public static ArrayList<Player> players;
    private static Game game;

    private static void setup() {
        players = new ArrayList<Player>();
        new Console();
        setupServer();
        setupGame();
    }

    public static void main(String[] args) {
        setup();
        while (true) {
            game.play();
        }
    }

    private static void setupServer() {
        System.out.print("Enter Port Number: ");
        int port = -1;
        port = Console.getIntInput();
        new PlayerJoinCheck(port);
        new PlayerConnectionCheck();
    }

    private static void setupGame() {
        System.out.print("Enter Game: ");
        String line = Console.getInput();
        if (line.equals("texas holdem")) {
            game = new TexasHoldem();
            return;
        }
        System.out.println(line + " is not a supported game");
        setupGame();
    }
}
