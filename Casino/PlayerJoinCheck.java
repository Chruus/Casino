import java.io.*;
import java.net.*;

public class PlayerJoinCheck implements Runnable {
    private Socket socket;
    private ServerSocket server;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private Thread thread;

    public PlayerJoinCheck(int port) {
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
        }
        thread = new Thread(this);
        thread.start();
        new PlayerConnectionCheck();
    }

    public void run() {
        while (true) {
            System.out.println("Listen");
            setupNewPlayer();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }
    }

    private void setupNewPlayer() {
        checkNewPlayers();
        sendMessage("Welcome to the Casino!\nWhat is your name?\n");
        String name = getMessage();
        sendMessage("How much money would you like to withdraw?\n$");
        double balance = Double.parseDouble(getMessage());
        CasinoV2.players.add(new Player(balance, name, socket));
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