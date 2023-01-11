import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.net.*;

public class CasinoV2 {
    public static ArrayList<Gambler> players;
    private static Game game;

    private static void setup() {
        players = new ArrayList<Gambler>();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Port Number: ");
        int port = Integer.parseInt(input.nextLine());
        PlayerListener listener = new PlayerListener(port);
        listener.run();
        System.out.print("Enter Game: ");
        String line = input.nextLine();
        if (line.equals("texas holdem"))
            game = new TexasHoldem();
        input.close();
    }

    public static void main(String[] args) {
        setup();
        game.play();
    }
}
