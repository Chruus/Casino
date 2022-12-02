import java.util.*;

public class TexasHoldemV2{
    TexasHoldemV2(){
        players = new ArrayList <Gambler>();
        activePlayers = new ArrayList <Gambler>();
    }
    private ArrayList <Gambler> players; //List of players in Texas Holdem
    private ArrayList <Gambler> activePlayers; //List of players actively playing Texas Holdem
    private CardDeck deck; //Deck cards are drawn from
    private CardDeck spread; //Deck of up to 5 cards on the table
    private int minimumBet;
    private int pool;

    public void addPlayer(Gambler player)
    {
        players.add(player);
    }

    public void play()
    {//Plays one game each time it's called
        activePlayers.addAll(players);
        players.clear();

        if(activePlayers.size() > 1)
        {//If there are enough players to play a round
            spread = new CardDeck();
            deck = new CardDeck(false);
            deck.shuffle();
            minimumBet = 0;
            pool = 0;
            //Giving players cards
            dealHands();

            //Pre-flop bets
            for(int player = 0; player < activePlayers.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showHand(player);
                placeBets(player);
            }


            //Flop
            dealSpread("flop");
            for(int player = 0; player < activePlayers.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("Flop");
                showHand(player);
                placeBets(player);
            }

            //River
            dealSpread("river");
            for(int player = 0; player < activePlayers.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("River");
                showHand(player);
                placeBets(player);
            }

            //Showdown
            dealSpread("showdown");
            for(int player = 0; player < activePlayers.size(); player++)
            {
                System.out.println("\n\n\n\n\n\n\n\n");
                showSpread("Showdown");
                showHand(player);
                placeBets(player);
            }
            
            //Check who wins and gives them the money
            String winner = whoWins();
            System.out.println(winner + " wins $" + pool + "!");
            for(int player = 0; activePlayers.size() > 0; player++)
            {
                if(activePlayers.get(player).getName().equals(winner))
                {
                    activePlayers.get(player).win(pool);
                    pool = 0;
                }
                players.add(activePlayers.get(player));
                activePlayers.remove(player);
                player--;
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
            newHand.addCard(deck.drawCard());
            newHand.addCard(deck.drawCard());
            activePlayers.get(player).setHand(newHand);
        }
    }

    private void showHand(int player)
    {//Prints hand
        System.out.println("Your Hand:");
        System.out.println(" | " + activePlayers.get(player).getHand().get(0).toString() + 
        " |  | " + activePlayers.get(player).getHand().get(1).toString() + " | \n");
    }

    private void dealSpread(String position)
    {//Adds cards to spread
        if(position.equals("flop"))
        {
            for(int i = 0; i < 3; i++)
                spread.putCard(deck.drawCard());
        }
        else if(position.equals("river") || position.equals("showdown")){
            spread.putCard(deck.drawCard(), spread.getSize() - 1);
        }
    }

    private void showSpread(String position)
    {//Prints out spread
        System.out.println("The " + position + ":");
        for(int card = 0; card < spread.getSize(); card++)
            System.out.print(" | " + spread.get(card).toString() + " | ");
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
    
    private String checkLine(int player)
    {//Takes line from player and checks it for commands, then returns line
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        if(line.indexOf("exit") > 0)
        {
            activePlayers.remove(player);
        }
        input.close();
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
                lineScan.close();
                return output;
            }
            catch(Exception e){}
        }
        lineScan.close();
        return -1;
    }

    private String whoWins(){
        ArrayList <CardDeck> playerSpreadDecks = new ArrayList <CardDeck>();
        CompareCardDecks compareCardDecks = new CompareCardDecks("texas holdem");

        //Puts cards and spread into one deck
        for(int player = 0; player < activePlayers.size(); player++){
            playerSpreadDecks.add(spread);
            playerSpreadDecks.get(player).putCard(activePlayers.get(player).getHand().get(0));
            playerSpreadDecks.get(player).putCard(activePlayers.get(player).getHand().get(1));
        }

        Collections.sort(playerSpreadDecks, new CompareCardDecks("texas holdem"));

        if(compareCardDecks.compare(playerSpreadDecks.get(0), playerSpreadDecks.get(1)) == 0){}

        return "";
    }
}