import java.util.*;

public class TexasHoldem extends Game {
    TexasHoldem() {
        minimumNumberOfPlayers = 2;
    }

    private ArrayList<Gambler> activePlayers; // List of players actively playing Texas Holdem
    private CardDeck deck; // Deck cards are drawn from
    private CardDeck spread; // Deck of up to 5 cards on the table
    private int minimumBet;
    private int pool;
    private int minimumNumberOfPlayers;

    public void play() {// Plays Texas Holdem until there aren't enough players
        while (enoughPlayers()) {
            activePlayers = Casino.players;
            spread = new CardDeck();
            deck = new CardDeck(false);
            deck.shuffle();
            minimumBet = 0;
            pool = 0;

            // Giving players cards
            dealHands();

            // Pre-flop bets
            for (int player = 0; player < activePlayers.size(); player++) {
                System.out.println("\n\n\n\n\n\n\n\n");
                showHand(player);
                placeBets(player);
            }

            // Flop
            dealSpread("flop");
            for (int player = 0; player < activePlayers.size(); player++) {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("Flop");
                showHand(player);
                placeBets(player);
            }

            // River
            dealSpread("river");
            for (int player = 0; player < activePlayers.size(); player++) {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("River");
                showHand(player);
                placeBets(player);
            }

            // Showdown
            dealSpread("showdown");
            for (int player = 0; player < activePlayers.size(); player++) {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("Showdown");
                showHand(player);
                placeBets(player);
            }

            // Check who wins and gives them the money
            String winner = whoWins();
            System.out.println(winner + " wins $" + pool + "!");
            for (int player = 0; activePlayers.size() > 0; player++) {
                if (activePlayers.get(player).getName().equals(winner)) {
                    activePlayers.get(player).win(pool);
                    pool = 0;
                }
                activePlayers.remove(player);
                player--;
            }
        }
    }

    private boolean enoughPlayers() {
        if (Casino.players.size() < minimumNumberOfPlayers)
            return false;
        return true;
    }

    private void dealHands() {// Gives each player 2 cards
        for (int player = 0; player < activePlayers.size(); player++) {
            CardDeck hand = new CardDeck();
            hand.add(deck.draw());
            hand.add(deck.draw());
            activePlayers.get(player).setHand(hand);
        }
    }

    private void showHand(int player) {// Prints hand
        System.out.println("Your Hand:");
        System.out.println(" | " + activePlayers.get(player).getHand().get(0) +
                " |  | " + activePlayers.get(player).getHand().get(1) + " | \n");
    }

    private void dealSpread(String position) {// Adds cards to spread
        if (position.equals("flop")) {
            for (int i = 0; i < 3; i++)
                spread.add(deck.draw());
        } else if (position.equals("river") || position.equals("showdown")) {
            spread.add(deck.draw(), spread.getSize() - 1);
        }
    }

    private void showSpread(String position) {// Prints out spread
        System.out.println("The " + position + ":");
        for (int card = 0; card < spread.getSize(); card++)
            System.out.print(" | " + spread.get(card).toString() + " | ");
        System.out.println("\n");
    }

    private void placeBets(int player) {// Takes bet of a certain player & adds it to the pool
        System.out.println("The current bet is $" + minimumBet + ". Please bet, check, or fold.");
        String line = checkLine(player);
        int bet = numberIn(line);

        if (line.indexOf("fold") >= 0) {// Handles you folding (quitting)
            activePlayers.get(player).getHand().clear();
        }

        else if (line.indexOf("check") >= 0 || bet == 0) {// Handles you checking (betting 0)
            if (minimumBet > 0) {// If anyone has bet before you, asks for you to bet again
                System.out.println("You need to bet at least $" + minimumBet);
                placeBets(player);
            }
        }

        else if (bet == -1) {// Handles you not inputing anything useful
            System.out.println("Please bet, check, or fold");
            placeBets(player);
        }

        else if (bet == minimumBet) {// Handles you matching the minimum bet
            if (activePlayers.get(player).getBalance() >= bet) {// If you have enough money, adds it to pool
                pool += bet;
                activePlayers.get(player).takeMoney(bet);
            } else {// Asks for you to bet again because you do not have enough money
                System.out.println("That exceedes your balance of $" + activePlayers.get(player).getBalance());
                placeBets(player);
            }

        }

        else if (bet > minimumBet) {// Handles you raising the previous bet
            if (activePlayers.get(player).getBalance() >= bet) {// If you have enough money, adds it to pool + raises
                                                                // minimum bet
                pool += bet;
                activePlayers.get(player).takeMoney(bet);
            } else {// Asks for you to bet again because you do not have enough money
                System.out.println("That exceedes your balance of $" + activePlayers.get(player).getBalance());
                placeBets(player);
            }
        } else if (bet < minimumBet) {// Handles you betting less than minimum
            System.out.println("You need to bet at least $" + minimumBet);
            placeBets(player);
        }
    }

    private String checkLine(int player) {// Takes line from player and checks it for commands, then returns line
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        if (line.indexOf("exit") > 0) {
            activePlayers.remove(player);
        }
        return line;
    }

    private int numberIn(String line) {// Checks string to see if it has a number in it
        Scanner lineScan = new Scanner(line);
        int output;
        while (lineScan.hasNext()) {
            try {
                output = Integer.parseInt(lineScan.next());
                // lineScan.close();
                return output;
            } catch (Exception e) {
            }
        }
        // lineScan.close();
        return -1;
    }

    private String whoWins() {
        ArrayList<CardDeck> playerDecks = new ArrayList<CardDeck>();
        HashMap<CardDeck, String> deckToName = new HashMap<CardDeck, String>();

        // Puts cards and spread into one deck
        for (int player = 0; player < activePlayers.size(); player++) {// Incorrectly sets up playerDecks
            CardDeck temp = spread;
            playerDecks.add(player, new CardDeck());
            playerDecks.get(player).addCardDeck(activePlayers.get(player).getHand());
        }

        // System.out.println(playerDecks);
        Collections.sort(playerDecks, new CompareCardDecks("texas holdem"));

        System.out.println("a " + new CompareCardDecks("texas holdem").getHighestValueHand(playerDecks.get(0)));

        return deckToName.get(playerDecks.get(0));
    }
}