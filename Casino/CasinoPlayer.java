import java.io.*;
import java.net.*;
import java.util.*;

public class CasinoPlayer {
    private static Socket socket;
    private static DataInputStream input;
    private static DataOutputStream output;

    public static void main(String[] args) {
        String line = "";
        System.out.print("Server's IP:");
        String ip = scanner.nextLine();
        System.out.print("Server's Port:");
        int port = Integer.parseInt(scanner.nextLine());
        try {
            socket = new Socket(ip, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
        }

        do {
            try {
                System.out.print(input.readUTF());
                line = scanner.nextLine();
                output.writeUTF(line);
            } catch (Exception e) {
            }

        } while (!line.equals("stop"));

        scanner.close();
    }
}
