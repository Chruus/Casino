import java.util.*;

public class TexasHoldem extends Game {
    TexasHoldem() {
        pool = 0;
        minimumNumberOfPlayers = 2;
        minimumBet = 0;
        hasRaisedBet = false;
        bets = new HashMap<Player, Integer>();
    }

    private HashMap<Player, Integer> bets;
    private ArrayList<Player> activePlayers;
    private CardDeck deck;
    private CardDeck spread;
    private boolean hasRaisedBet;
    private int minimumBet;
    private int pool;
    private int minimumNumberOfPlayers;

    public void setup() {
        if (!enoughPlayers())
            waitForPlayers();
        for (Player player : activePlayers)
            bets.put(player, 0);
        activePlayers = Casino.players;
        spread = new CardDeck();
        deck = new CardDeck(false);
        deck.shuffle();
        dealHands();
    }

    public void play() {
        setup();

        bettingRound("preflop");
        bettingRound("flop");
        bettingRound("river");
        bettingRound("showdown");

        String winner = whoWins();
        for (Player player : activePlayers) {
            player.sendOutput(winner + " wins $" + pool + "!");
            activePlayers.remove(player);
        }
        pool = 0;
    }

    private void bettingRound(String bettingRound) {
        if (!bettingRound.equals("preflop")) {
            dealSpread(bettingRound);
            showSpread(bettingRound);
        }
        for (Player player : activePlayers) {
            player.sendOutput("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            showHand(player);
            placeBet(player);
        }
        placeRaisedBet();
    }

    private void waitForPlayers() {
        double startTime = System.currentTimeMillis();
        double currentTime = 0;
        while (currentTime - startTime < 10000)
            currentTime = System.currentTimeMillis();
    }

    private boolean enoughPlayers() {
        if (CasinoV2.players.size() < minimumNumberOfPlayers)
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

    private void dealSpread(String bettingRoung) {// Adds cards to spread
        if (bettingRoung.equals("flop")) {
            for (int i = 0; i < 3; i++)
                spread.add(deck.draw());
        } else if (bettingRoung.equals("river") || bettingRoung.equals("showdown")) {
            spread.add(deck.draw(), spread.getSize() - 1);
        }
    }

    private void showHand(Player player) {// Prints hand
        String output = "Your Hand:\n" + player.getHand() + "\n";
        player.sendOutput(output);
    }

    private void showSpread(String position) {// Prints out spread
        String output = "\nThe " + position + ": \n" + spread + "\n";
        for (Player player : activePlayers)
            player.sendOutput(output);
    }

    private void placeBet(Player player) {// Takes bet of a certain player & adds it to the pool
        player.sendOutput("\nThe current bet is $" + minimumBet + ". Please bet, check, or fold.\n");
        String line = getInput(player);
        int bet = 0;

        if (line.indexOf("fold") >= 0) {// Handles you folding (quitting)
            fold(player);
            return;
        }

        if (line.indexOf("check") >= 0 || bet == 0) {// Handles you checking (betting 0)
            if (minimumBet > 0) {// If anyone has bet before you, asks for you to bet again
                player.sendOutput("\nYou need to bet at least $" + minimumBet + "\n");
                placeBet(player);
            }
            return;
        }

        try {
            bet = Integer.parseInt(line);
        } catch (Exception e) {
            player.sendOutput("Please bet, check, or fold.");
            placeBet(player);
        }

        if (bet >= minimumBet) {
            if (player.getBalance() >= bet) {
                pool += bet;
                player.takeMoney(bet);
            } else {
                player.sendOutput("\nThat exceedes your balance of $" + player.getBalance() + "\n");
                placeBet(player);
            }
            if (bet > minimumBet)
                raiseBet(bet);
        }
    }

    private void placeRaisedBet() {
        if (!hasRaisedBet)
            return;
        for (Player player : activePlayers) {
            player.sendOutput("The bet has been raised to " + minimumBet + ". Would you like to match it?");
            String reply = player.getInput();
            if (reply.equals("yes") || reply.equals("y"))
                
        }
    }

    private void fold(Player player) {
        player.hand.clear();
        activePlayers.remove(player);
    }

    private void raiseBet(int bet) {
        hasRaisedBet = true;
        minimumBet = bet;
    }

    private String getInput(Player player) {// Takes line from player and checks it for commands, then returns line
        String line = player.getInput();
        if (line.toLowerCase().equals("exit") || line.equals("Player Has Disconnected")) {
            fold(player);
        }
        return line;
    }

    private String whoWins() {
        ArrayList<CardDeck> playerDecks = new ArrayList<CardDeck>();
        HashMap<CardDeck, String> deckToName = new HashMap<CardDeck, String>();

        // Puts cards and spread into one deck
        for (int player = 0; player < activePlayers.size(); player++) {// Incorrectly sets up playerDecks
            playerDecks.add(player, new CardDeck());
            playerDecks.get(player).addCardDeck(activePlayers.get(player).getHand());
        }

        // System.out.println(playerDecks);
        Collections.sort(playerDecks, new CompareCardDecks("texas holdem"));

        return deckToName.get(playerDecks.get(0));
    }
}