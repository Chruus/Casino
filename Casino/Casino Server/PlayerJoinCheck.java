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
    }

    public void run() {
        while (true) {
            setupNewPlayer();
        }
    }

    private void setupNewPlayer() {
        checkNewPlayers();
        sendMessage("Welcome to the Casino!\nWhat is your name?\n");
        String name = getMessage();
        sendMessage("How much money would you like to withdraw?\n$");
        double balance = Double.parseDouble(getMessage());
        OldCasino.players.add(new Player(balance, name, socket));
    }

    private void wait(double ms) {
        double startTime = System.currentTimeMillis();
        double endTime = System.currentTimeMillis();
        while (endTime - startTime < ms) {
            endTime = System.currentTimeMillis();
        }
    }

    private void checkNewPlayers() {
        try {
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            System.out.println(
                    socket.getPort() + " " + socket.isBound() + " " + socket.isConnected() + " " + socket.isClosed());

        } catch (IOException e) {
        }
    }

    private void sendMessage(String message) {
        System.out.println("sending message");
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            if (socket.isBound())
                sendMessage(message);
        }
    }

    private String getMessage() {
        System.out.println("reading message");
        try {
            String out = input.readUTF();
            System.out.println("reading message");
            return out;
        } catch (IOException e) {
            if (socket.isBound())
                getMessage();
        }
        return "";
    }
}