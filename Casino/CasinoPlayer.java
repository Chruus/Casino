import java.io.*;
import java.net.*;
import java.util.*;

public class CasinoPlayer {
    private static Scanner input;
    private static Gambler thisPlayer;

    public static void main(String[] args) throws UnknownHostException, IOException {
        input = new Scanner(System.in);
        playerSetup();
        findGame();
    }

    private static void playerSetup() {
        System.out.println("Hi, welcome to the casino! Please enter: ");
        System.out.print(" your name: ");
        String name = input.nextLine();
        System.out.print(" starting balance: ");
        String line = input.nextLine();
        double startingMoney = Double.parseDouble(line);
        thisPlayer = new Gambler(startingMoney, name);
    }

    public static void findGame() throws UnknownHostException, IOException {
        Socket clientSocket;
        Socket connectionSocket;
        String playerInput;

        System.out.println("What game would you like to connect to?");
        playerInput = input.nextLine();
        if (playerInput.equals("texas holdem")) {
            clientSocket = new Socket("IP", 7777);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            Scanner inFromServer = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connecting...");
            outToServer.writeBytes(thisPlayer.getName() + " " + thisPlayer.getBalance());
        }

    }

    // public static void {

    // Essentially: Create a "player" class that doesn't actually hold any of the
    // code that deals with the game.
    // "player" class asks for input w/ a normal scanner, but then sends that data
    // to server which processes data and replys

    // clientSocket = new Socket(IP, portNum);
    // DataOutputStream outToServer = new
    // DataOutputStream(clientSocket.getOutputStream());
    // Scanner inFromServer = new Scanner(new
    // InputStreamReader(clientSocket.getInputStream()));

    // System.out.println("Enter Your Message: ");
    // sentence = scan.nextLine();

    // System.out.println("Waiting for reply...");
    // outToServer.writeBytes(sentence + '\n');

    // inFromServer.nextLine();

    // System.out.println("Message from " + clientSocket.getInetAddress() + ": " +
    // sentence);
    // }
}
