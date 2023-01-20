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
        if (!enoughPlayers()) {
            waitForPlayers();
            setup();
        }
        for (Player player : activePlayers)
            bets.put(player, 0);
        pool = 0;
        activePlayers = OldCasino.players;
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

        checkWhoWins();
    }

    private void checkWhoWins() {
        Player winner = whoWins();
        for (Player player : activePlayers) {
            player.sendOutput(winner + " wins $" + pool + "!");
            activePlayers.remove(player);
        }
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
        System.out.println("Waiting for players...");
        while (currentTime - startTime < 10000)
            currentTime = System.currentTimeMillis();
    }

    private boolean enoughPlayers() {
        if (OldCasino.players.size() < minimumNumberOfPlayers)
            return false;
        return true;
    }

    private void dealHands() {
        for (int player = 0; player < activePlayers.size(); player++) {
            CardDeck hand = new CardDeck();
            hand.add(deck.draw());
            hand.add(deck.draw());
            activePlayers.get(player).setHand(hand);
        }
    }

    private void dealSpread(String bettingRoung) {
        if (bettingRoung.equals("flop")) {
            for (int i = 0; i < 3; i++)
                spread.add(deck.draw());
        } else if (bettingRoung.equals("river") || bettingRoung.equals("showdown")) {
            spread.add(deck.draw(), spread.getSize() - 1);
        }
        System.out.println("Dealt Spread");
    }

    private void showHand(Player player) {
        String output = "Your Hand:\n" + player.getHand() + "\n";
        player.sendOutput(output);
    }

    private void showSpread(String position) {
        String output = "The " + position + ": \n" + spread + "\n";
        for (Player player : activePlayers)
            player.sendOutput(output);
    }

    private void placeBet(Player player) {// Takes bet of a certain player & adds it to the pool
        player.sendOutput("The current bet is $" + minimumBet + ". Please bet, check, or fold.\n");
        String line = getInput(player);
        int bet = 0;

        if (line.equals("fold")) {// Handles you folding (quitting)
            fold(player);
            return;
        }

        if (line.equals("check")) {// Handles you checking (betting 0)
            if (minimumBet > 0) {// If anyone has bet before you, asks for you to bet again
                player.sendOutput("You need to bet at least $" + minimumBet + "\n");
                placeBet(player);
            }
            return;
        }

        try {
            bet = Integer.parseInt(line);
        } catch (Exception e) {
            player.sendOutput("Please bet, check, or fold.\n");
            placeBet(player);
        }

        if (bet >= minimumBet) {
            if (player.getBalance() >= bet) {
                pool += bet;
                bets.put(player, bet);
                player.takeMoney(bet);
                System.out.println("Player " + player.getName() + " bets " + bet);
            } else {
                player.sendOutput("That exceedes your balance of $" + player.getBalance() + "\n");
                placeBet(player);
            }
            if (bet > minimumBet)
                raiseBet(bet);
        } else {
            player.sendOutput("You need to bet at least $" + minimumBet + "\n");
            placeBet(player);
        }
    }

    private void placeRaisedBet() {
        if (!hasRaisedBet)
            return;
        for (Player player : activePlayers) {
            int bet = bets.get(player);
            if (bet < minimumBet) {
                askToRaiseBet(player, bet);
            }
        }
    }

    private void askToRaiseBet(Player player, int bet) {
        int raise = minimumBet - bet;
        player.sendOutput("The bet has been raised to " + minimumBet + ". Would you like to match it?");
        String reply = player.getInput().toLowerCase();
        if (reply.equals("yes") || reply.equals("y")) {
            if (player.getBalance() >= raise) {
                pool += raise;
                bets.put(player, raise);
                player.takeMoney(raise);
            } else {
                player.sendOutput("That exceedes your balance of $" + player.getBalance() + "\n");
                askToRaiseBet(player, bet);
            }
        } else if (reply.equals("no") || reply.equals("n")) {
            fold(player);
        } else {
            player.sendOutput("Invalid input. Please type yes or no\n");
        }
        bets.remove(player);
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

    private Player whoWins() {
        ArrayList<CardDeck> playerDecks = new ArrayList<CardDeck>();
        HashMap<CardDeck, Player> deckToPlayer = new HashMap<CardDeck, Player>();

        for (int player = 0; player < activePlayers.size(); player++) {
            playerDecks.add(player, new CardDeck());
            playerDecks.get(player).addCardDeck(activePlayers.get(player).getHand());
            deckToPlayer.put(playerDecks.get(player), activePlayers.get(player));
        }

        Collections.sort(playerDecks, new CompareCardDecks("texas holdem"));

        Player winner = deckToPlayer.get(playerDecks.get(0));
        return winner;
    }
}