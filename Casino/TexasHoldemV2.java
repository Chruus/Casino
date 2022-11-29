import java.util.*;

public class TexasHoldemV2{
    TexasHoldemV2(){
        players = new ArrayList <Gambler>();
    }
    private ArrayList <Gambler> players; //List of players in Texas Holdem
    private ArrayList <Gambler> activePlayers; //List of players actively playing Texas Holdem
    private CardDeck deck; //Deck cards are drawn from
    private CardDeck spread;
    private boolean minimumBetHasIncreased;
    private int playerWhoIncreasedMinimumBet;
    private int minimumBet;
    private int pool;

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
            whoWins();
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
        System.out.println(" | " + players.get(player).getHand().get(0).toString() + 
        " |  | " + players.get(player).getHand().get(1).toString() + " | \n");
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
        for(int card = 0; card < spread.getDeckSize(); card++)
            System.out.print(" | " + spread.get(card).getValue() + " | ");
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

    private void whoWins()
    {
        //Variable declaration
        HashMap <String, String> resultsLo = new HashMap <String, String>();
        HashMap <String, String> resultsHi = new HashMap <String, String>();
        HashMap <String, String> results = new HashMap <String, String>();
        
        for(int player = 0; player < activePlayers.size(); player++)
        {//Goes through every active player and adds their highest combination to results when ace is high and low
            String name = activePlayers.get(player).getName();
            resultsLo.put(name, getHighestCombination(player, false));
            resultsHi.put(name, getHighestCombination(player, true));
        }
        for(HashMap.Entry<String, String> entry : resultsHi.entrySet())
        {//Determines whether the player has a higher combination
            String key = entry.getKey();
            int combinationHi = getCombinationValue(resultsHi.get(key));
            int combinationLo = getCombinationValue(resultsLo.get(key));
            if(combinationHi >= combinationLo)
                results.put(key, resultsHi.get(key));
            if(combinationHi < combinationLo)
                results.put(key, resultsLo.get(key));
        }
        

    }



    private int getCombinationValue(String combination)
    {
        if(combination.equals("royalFlush"))
            return 8;
        if(combination.equals("straightFlush"))
            return 7;
        if(combination.equals("fourOAK"))
            return 6;
        if(combination.equals("fullHouse"))
            return 5;
        if(combination.equals("flush"))
            return 4;
        if(combination.equals("straight"))
            return 3;
        if(combination.equals("threeOAK"))
            return 2;
        if(combination.equals("pair"))
            return 1;
        
        return 0;
    }

    private String getHighestCombination(int player, boolean aceHigh)
    {//Finds highest possible combination of cards for a player and returns it
        
        //Variable declaration & setup
        String flush, straight, four, fullHouse, three, twoPair, pair;
        flush = straight = four = fullHouse = three = twoPair = pair = "";
        CardDeck playerSpreadDeck = spread;
        playerSpreadDeck.putCard(players.get(player).getHand().get(0));
        playerSpreadDeck.putCard(players.get(player).getHand().get(1));
        playerSpreadDeck.sort(aceHigh);
        CardDeck tempDeck;
        HashMap <String, Integer> numberOfCardsAtValues = new HashMap <String, Integer>();
        int counter, spades, hearts, diamonds, clubs;
        counter = spades = hearts = diamonds = clubs = 0;

        //Checks for straight
        tempDeck = playerSpreadDeck;
        for(int card = 0; card < playerSpreadDeck.getDeckSize() - 1; player ++)
        {   
            if(tempDeck.get(card).getIntValue(aceHigh) + 1 == tempDeck.get(card + 1).getIntValue(aceHigh))
                counter++;
            else
                counter = 0;
            if(counter >= 5)
                straight = tempDeck.get(card).getValue();
        }

        //Checks for flush
        tempDeck = playerSpreadDeck;
        String suit = "";
        for(int card = 0; card < tempDeck.getDeckSize(); card++)
        {
            suit = tempDeck.get(card).getSuit();
            if(suit.equals("spades"))
                spades++;
            if(suit.equals("hearts"))
                hearts++;
            if(suit.equals("diamonds"))
                diamonds++;
            if(suit.equals("clubs"))
                clubs++;
            
            if(spades >= 5 || hearts >= 5 || diamonds >= 5 || clubs >= 5)
                flush = suit;
        }
        for(int card = 0; card < tempDeck.getDeckSize() && !flush.equals(""); card++)
        {
            if(deck.get(card).getSuit().equals(suit))
                flush += " " + deck.get(card).getValue();
        }

        //Checks full house & four, three, and two of a kind
        tempDeck = playerSpreadDeck;
        for(int card = 0; card < tempDeck.getDeckSize(); card++)
        {
            String cardValue = tempDeck.get(card).getValue();
            if(numberOfCardsAtValues.containsKey(cardValue))
                numberOfCardsAtValues.put(cardValue, numberOfCardsAtValues.get(cardValue) + 1);
            else
                numberOfCardsAtValues.put(cardValue, 1);
        }
        for(Map.Entry <String, Integer> entry : numberOfCardsAtValues.entrySet())
        {
            if(entry.getValue() == 4)
                four = entry.getKey();
            if(entry.getValue() == 3 && numberOfCardsAtValues.containsValue(2))
                fullHouse += "3: " + entry.getKey() + " ";
            if(entry.getValue() == 2 && numberOfCardsAtValues.containsValue(3))
                fullHouse += "2: " + entry.getKey() + " ";
            if(entry.getValue() == 3)
                three = entry.getKey();
            if(entry.getValue() == 2)
                pair = entry.getKey();
        }

        //Assigns highest combination to player
        if(flush.indexOf("10 jack queen king ace") >= 0 && straight.equals("10 jack queen king ace"))
            return "royalFlush";
        else if(!flush.equals("") && !straight.equals(""))
            return "straightFlush";
        else if(!four.equals(""))
            return "fourOAK";
        else if(!fullHouse.equals(""))
            return "fullHouse";
        else if(!flush.equals(""))
            return "flush";
        else if(!straight.equals(""))
            return "straight";
        else if(!three.equals(""))
            return "threeOAK";
        else if(!pair.equals(""))
            return "pair";

        return "";
    } 

}