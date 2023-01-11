import java.io.*;
import java.net.*;
import java.util.*;

public class PlayerListener implements Runnable {
    private Socket socket;
    private ServerSocket server;
    private PlayerConnectionCheck checkConnectedPlayers;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public PlayerListener(int port) {
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
        }
        checkConnectedPlayers = new PlayerConnectionCheck();
    }

    public void run() {
        checkConnectedPlayers.run();
        while (true) {
            try {
                checkNewPlayers();
                sendMessage("Welcome to the Casino!\nWhat is your name?\n");
                String name = getMessage();
                sendMessage("How much money would you like to withdraw?\n$");
                double balance = Double.parseDouble(getMessage());
                CasinoV2.players.add(new Gambler(balance, name, socket));
            } catch (Exception e) {
            }
        }
    }

    private void checkNewPlayers() {
        try {
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
        }
    }

    private void sendMessage(String message) {
        try {
            output.writeBytes(message);
        } catch (IOException e) {
            if (socket.isConnected())
                sendMessage(message);
        }
    }

    private String getMessage() {
        try {
            return input.readUTF();
        } catch (IOException e) {
            if (socket.isConnected())
                getMessage();
        }
        return "";
    }
}

class PlayerConnectionCheck implements Runnable {

    public void run() {
        while (true) {
            for (int player = 0; player < CasinoV2.players.size(); player++) {
                try {
                    Casino.players.get(player).getInputStream().read();
                } catch (Exception e) {
                    Casino.players.remove(player);
                    player--;
                }
            }
        }
    }

}