//Christopher Petty

import java.io.*;
import java.util.*;
import java.net.*;

public class CasinoServer {
    private static Scanner input;

    public static void main(String[] args) throws IOException, InterruptedException {
        input = new Scanner(System.in);
        gameSetup();

        System.out.println("Thanks for playing at the casino!");
    }

    private static void gameSetup() {
        String line = "";
        Game game;
        while (!line.equals("exit")) {
            line = input.nextLine();
            if (line.equals("texas holdem")) {
                game = new TexasHoldem();
                game.play();
            }
        }
    }

    public void server() throws IOException {
        int portNum = 7777;
        Socket connectionSocket;
        ServerSocket welcomeSocket = new ServerSocket(portNum);

        while (true) {
            connectionSocket = welcomeSocket.accept();

            Scanner inFromClient = new Scanner(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            outToClient.writeBytes(reply + '\n');
        }
    }
}