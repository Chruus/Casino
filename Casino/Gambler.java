import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Gambler {
    double balance;
    int wins;
    int losses;
    CardDeck hand;
    String name;
    Socket socket;
    DataInputStream input;
    DataOutputStream output;

    public Gambler(double startingMoney, String _name, Socket _socket) throws IOException {
        hand = new CardDeck();
        balance = startingMoney;
        wins = 0;
        losses = 0;
        name = _name;
        socket = _socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }

    public Gambler(CardDeck _hand, double startingMoney, String _name) {
        hand = _hand;
        balance = startingMoney;
        wins = 0;
        losses = 0;
        name = _name;
    }

    public Gambler(double startingMoney, String _name) {
        hand = new CardDeck();
        balance = startingMoney;
        wins = 0;
        losses = 0;
        name = _name;
    }

    public Gambler(String _name) {
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

    // Setters
    public void giveMoney(int give) {
        balance += give;
    }

    public void setMoney(double money) {
        balance = money;
    }

    public String takeMoney(double taken) {
        if (balance >= taken) {
            balance -= taken;
            return "You now have $" + balance + " left";
        }
        return "You do not have enough money";
    }

    public void win(int moneyWon) {
        wins++;
        balance += moneyWon;
    }

    public void win(int moneyWon, int numberOfWins) {
        wins += numberOfWins;
        balance += moneyWon;
    }

    public void lose(int moneyLost) {
        losses++;
        balance -= moneyLost;
    }

    public void lose(int moneyLost, int numberOfLosses) {
        losses += numberOfLosses;
        balance -= moneyLost;
    }

    public void setHand(CardDeck newHand) {
        hand = newHand;
    }

}