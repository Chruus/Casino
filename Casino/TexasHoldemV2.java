import java.util.*;

public class TexasHoldemV2{
    TexasHoldemV2(){
        players = new ArrayList <Gambler>();
    }
    ArrayList <Gambler> players; //List of players in Texas Holdem
    ArrayList <Gambler> activePlayers; //List of players actively playing Texas Holdem
    CardDeck deck; //Deck cards are drawn from
    CardDeck spread;
    boolean minimumBetHasIncreased;
    int playerWhoIncreasedMinimumBet;
    int minimumBet;
    int pool;

    public void addPlayer(Gambler player)
    {
        players.add(player);
    }

    public void play()
    {//Plays one game each time it's called
        activePlayers = players;
        if(players.size() > 1)
        {//If there are enough players to play a round
            spread = new CardDeck();
            deck = new CardDeck(false);
            deck.shuffle();
            minimumBet = 0;
            pool = 0;
            minimumBetHasIncreased = false;
            playerWhoIncreasedMinimumBet = 0;

            //Giving players cards
            dealHands();

            //Pre-flop bets
            for(int player = 0; player < players.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showHand(player);
                placeBets(player);
            }
            minimumBet = playerWhoIncreasedMinimumBet = 0;
            minimumBetHasIncreased = false;


            //Flop
            dealSpread("flop");
            for(int player = 0; player < players.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("Flop");
                showHand(player);
                placeBets(player);
            }
            minimumBet = playerWhoIncreasedMinimumBet = 0;
            minimumBetHasIncreased = false;

            //River
            dealSpread("river");
            for(int player = 0; player < players.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("River");
                showHand(player);
                placeBets(player);
            }
            minimumBet = playerWhoIncreasedMinimumBet = 0;
            minimumBetHasIncreased = false;

            //Showdown
            dealSpread("showdown");
            for(int player = 0; player < players.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("Showdown");
                showHand(player);
                placeBets(player);
            }
            minimumBet = playerWhoIncreasedMinimumBet = 0;
            minimumBetHasIncreased = false;
            
            //Check who wins
            for(int player = 0; player < players.size(); player++)
            {

            }
        }
        else
        {//Handles case where there's less than 2 players
            System.out.println("Waiting for players...");
        }
    }
    
    private void dealHands()
    {//Gives each player 2 cards
        for(int player = 0; player < activePlayers.size(); player++)
        {
            DynamicHand newHand = new DynamicHand();
            newHand.addCard(deck.drawCard(false));
            newHand.addCard(deck.drawCard(false));
            players.get(player).setHand(newHand);
        }
    }

    private void showHand(int player)
    {//Prints hand
        System.out.println("Your Hand:");
        System.out.println(" | " + players.get(player).getHand().getCard(0).toString() + 
        " |  | " + players.get(player).getHand().getCard(1).toString() + " | \n");
    }

    private void dealSpread(String position)
    {//Adds cards to spread
        if(position.equals("flop"))
        {
            for(int i = 0; i < 3; i++)
                spread.putCard(deck.drawCard(false));
        }
        else if(position.equals("river") || position.equals("showdown")){
            spread.putCard(deck.drawCard(false), true);
        }
    }

    private void showSpread(String position)
    {//Prints out spread
        System.out.println("The " + position + ":");
        for(int i = 0; i < spread.getDeckSize(); i++)
            System.out.print(" | " + spread.getCardValue(i) + " | ");
        System.out.println("\n");
    }

    private void placeBets(int player)
    {//Takes bet of a certain player & adds it to the pool
        System.out.println("The current bet is $" + minimumBet + ". Please bet, check, or fold.");
        String line = checkLine(player);//What the current player has typed
        int bet = numberIn(line);//Checks if number was in line, sets bet to that number

        
        if(line.indexOf("fold") >= 0)
        {//Handles you folding (quitting)
            activePlayers.remove(player);
        }

        else if(line.indexOf("check") >= 0 || bet == 0)
        {//Handles you checking (betting 0)
            if(minimumBet > 0)
            {//If anyone has bet before you, asks for you to bet again
                System.out.println("You need to bet at least $" + minimumBet);
                placeBets(player);
            }
        }
        
        else if(bet == -1)
        {//Handles you not inputing anything useful
            System.out.println("Please bet, check, or fold");
            placeBets(player);
        }

        else if(bet == minimumBet)
        {//Handles you matching the minimum bet
            if(activePlayers.get(player).getBalance() >= bet)
            {//If you have enough money, adds it to pool
                pool += bet;
                activePlayers.get(player).takeMoney(bet);
            }
            else
            {//Asks for you to bet again because you do not have enough money
                System.out.println("That exceedes your balance of $" + activePlayers.get(player).getBalance());
                placeBets(player);
            }

        }

        else if(bet > minimumBet)
        {//Handles you raising the previous bet
            if(activePlayers.get(player).getBalance() >= bet)
            {//If you have enough money, adds it to pool + raises minimum bet
                pool += bet; 
                minimumBet = bet;
                minimumBetHasIncreased = true;
                playerWhoIncreasedMinimumBet = player;
                activePlayers.get(player).takeMoney(bet);
            }
            else
            {//Asks for you to bet again because you do not have enough money
                System.out.println("That exceedes your balance of $" + activePlayers.get(player).getBalance());
                placeBets(player);
            }
        }
        else if(bet < minimumBet)
        {//Handles you betting less than minimum
            System.out.println("You need to bet at least $" + minimumBet);
            placeBets(player);
        }
    }

    
    private void placeRaisedBets()
    {
        for(int player = 0; player < playerWhoIncreasedMinimumBet; player++)
        {
            System.out.println("The bet has been raised to $" + minimumBet + ". Would you like to match it?");
            String line = checkLine(player);//What the current player has typed
            
        }
    }

    
    private String checkLine(int player)
    {//Takes line from player and checks it for commands, then returns line
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        if(line.indexOf("exit") > 0)
        {
            players.remove(player);
        }
        return line;
    }

    private int numberIn(String line)
    {//Checks string to see if it has a number in it
        Scanner lineScan = new Scanner(line);
        int output;
        while(lineScan.hasNext())
        {
            try
            {
                output = Integer.parseInt(lineScan.next());
                return output;
            }
            catch(Exception e){}
        }
        return -1;
    }
}