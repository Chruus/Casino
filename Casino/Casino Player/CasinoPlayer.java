import java.io.*;
import java.net.*;

public class CasinoPlayer {
    private static Socket socket;
    private static DataInputStream input;
    private static DataOutputStream output;

    private static void setup() {
        new Console();
        System.out.print("Server's IP: ");
        String ip = Console.getInput();
        System.out.print("Server's Port: ");
        int port = Console.getIntInput();
        try {
            socket = new Socket(ip, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            new ServerListener();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        setup();
        String line;
        do {
            System.out.print(ServerListener.getInput());
            line = Console.getInput();
            sendOutput(line);
        } while (!line.equals("exit"));
    }

    private static void sendOutput(String out) {
        try {
            output.writeUTF(out);
        } catch (Exception e) {
            pause(1000);
            sendOutput(out);
        }
    }

    private static void pause(double pauseLength) {
        double startTime = System.currentTimeMillis();
        double endTime = System.currentTimeMillis();
        while (endTime - startTime < pauseLength) {
            endTime = System.currentTimeMillis();
        }
    }

    public static Socket getSocket() {
        return socket;
    }

    public static DataInputStream getDataInputStream() {
        return input;
    }

    public static DataOutputStream getDataOutputStream() {
        return output;
    }
}
