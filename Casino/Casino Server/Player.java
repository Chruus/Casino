import java.io.*;
import java.net.*;

public class Player {
    double balance;
    int wins;
    int losses;
    CardDeck hand;
    String name;
    Socket socket;
    DataInputStream input;
    DataOutputStream output;

    public Player(double startingMoney, String _name, Socket _socket) {
        hand = new CardDeck();
        balance = startingMoney;
        wins = 0;
        losses = 0;
        name = _name;
        socket = _socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
        }
    }

    public Player(CardDeck _hand, double startingMoney, String _name) {
        hand = _hand;
        balance = startingMoney;
        wins = 0;
        losses = 0;
        name = _name;
    }

    public Player(double startingMoney, String _name) {
        hand = new CardDeck();
        balance = startingMoney;
        wins = 0;
        losses = 0;
        name = _name;
    }

    public Player(String _name) {
        hand = new CardDeck();
        balance = 0;
        wins = 0;
        losses = 0;
        name = _name;
    }

    // Getters
    public CardDeck getHand() {
        return hand;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getInputStream() {
        return input;
    }

    public DataOutputStream getOutputStream() {
        return output;
    }

    public String getInput() {
        try {
            return DataInputStream.readUTF(input);
        } catch (Exception e) {
            if (socket.isConnected())
                return getInput();
        }
        return "Player Has Disconnected";
    }

    // Non-Getters
    public void sendOutput(String message) {
        try {
            output.writeBytes(message);
        } catch (Exception e) {
            if (socket.isConnected())
                sendOutput(message);
        }
    }

    public void giveMoney(int give) {
        balance += give;
    }

    public void setMoney(double money) {
        balance = money;
    }

    public double takeMoney(double taken) {
        if (balance >= taken) {
            balance -= taken;
            return balance;
        }
        return -1;
    }

    public void setHand(CardDeck newHand) {
        hand = newHand;
    }

}