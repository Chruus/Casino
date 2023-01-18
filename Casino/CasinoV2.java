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
        int port = -1;
        try {
            port = Integer.parseInt(Console.getInput());
        } catch (Exception e) {
            System.out.println("Invalid Port Number");
            setupServer();
        }
        new PlayerJoinCheck(port);
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
