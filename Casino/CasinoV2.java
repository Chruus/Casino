import java.util.*;

public class CasinoV2 {
    public static ArrayList<Player> players;
    private static Game game;

    private static void setup() {
        players = new ArrayList<Player>();
        setupServer();
        setupGame();
    }

    public static void main(String[] args) {
        setup();
        while (Console.getInput() == null || !Console.getInput().equals("stop")) {
            game.play();
        }
    }

    private static void setupServer() {
        System.out.print("Enter Port Number: ");
        int port;
        do {
            port = Integer.parseInt(Console.getInput());
        } while (Console.getInput().matches("^[0-9]+"));
        new PlayerJoinCheck(port);
    }

    private static void setupGame() {
        System.out.print("Enter Game: ");
        String line;
        do {
            line = Console.getInput();
            line.toLowerCase();
        } while (!Console.getInput().equals("texas holdem"));
        game = new TexasHoldem();
    }
}
