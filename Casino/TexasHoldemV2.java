import java.util.*;

//Always have this called in the main class, add players to game, minimum of 2 players in game to start
public class TexasHoldemV2{
    TexasHoldemV2(){
        players = new ArrayList <Gambler>();
    }
    ArrayList <Gambler> players; //List of players in Texas Holdem
    ArrayList <Gambler> activePlayers; //List of players actively playing Texas Holdem
    CardDeck deck; //Deck cards are drawn from

    public void play(){
        if(players.size() > 1){//If there are enough players to play a round
            deck = new CardDeck(false);
            deck.shuffle();
            
            int pool = 0;

            //Giving players cards
            dealHands();

            //Pre-flop bets
            for(int player = 0; player < players.size(); player++){
                showHand(player);
            }
            pool += placeBets(pool);


            //Flop
            showDeck("Flop");
            showHand();

            //River
            showDeck("River");
            showHand();

            //Showdown
            showDeck("Showdown");
            showHand();
            
            //Check who wins
            for(int player = 0; player < players.size(); player++){

            }
        }
    }
    private void dealHands()
    {
        for(int player = 0; player < activePlayers.size(); player++){
            DynamicHand newHand = new DynamicHand();
            newHand.addCard(deck.drawCard(false));
            newHand.addCard(deck.drawCard(false));
            players.get(player).setHand(newHand);
        }
    }

    private void showHand()
    {
        
    }

    //Bug - cannot check for minimumBet, need to look into how to retrieve the data.
    private int placeBets(int pool)
    {
        int minimumBet = 0;
        for(int player = 0; player < players.size(); player++){
            pool += checkPlayerForBet(minimumBet, player);
        }
        //if(raised)
        //    pool += placeRaisedBets(playerThatRaised);
        
        return pool;
    }

    private int checkPlayerForBet(int minimumBet, int player){
        String line = checkLine(player);//What the current player has typed
        int bet = numberInLine(line);//Checks if number was in line, sets bet to that number
        int pool = 0;

        if(line.indexOf("fold") >= 0)
        {
            activePlayers.remove(player);
        }

        else if(line.indexOf("check") >= 0 || bet == 0)
        {
            if(minimumBet > 0)
            {//If anyone has bet before you, asks for you to bet again
                System.out.println("You need to bet at least $" + minimumBet);
                checkPlayerForBet(minimumBet, player);
            }
        }

        else if(bet == minimumBet)
        {
            if(activePlayers.get(player).getBalance() >= bet)
            {//If you have enough money, adds it to pool
                pool += bet;
            }
            else
            {//Asks for you to bet again because you do not have enough money
                System.out.println("That exceedes your balance of $" + activePlayers.get(player).getBalance());
                checkPlayerForBet(minimumBet, player);
            }

        }

        else if(bet > minimumBet)
        {
            if(activePlayers.get(player).getBalance() >= bet)
            {//If you have enough money, adds it to pool + raises minimum bet
                pool += bet; 
                minimumBet = bet;
            }
            else
            {//Asks for you to bet again because you do not have enough money
                System.out.println("That exceedes your balance of $" + activePlayers.get(player).getBalance());
                checkPlayerForBet(minimumBet, player);
            }
        }

        else if(bet < minimumBet)
        {//Because someone has bet higher than you, asks for you to bet again
            System.out.println("You need to bet at least $" + minimumBet);
            checkPlayerForBet(minimumBet, player);
        }

        return pool;
    }

    /*
    private int placeRaisedBets(int playerThatRaised)
    {
        int pool = 0;
        for(int player = 0; player < playerThatRaised; player++)
        {
            pool += checkPlayerForRaise(player);
        }
        return pool;
    }

    private int checkPlayerForRaise(int player)
    {
        String line = checkLine(player);//What the current player has typed
        int bet = numberInLine(line);//Checks if number was in line, sets bet to that number
        int pool = 0;

        //
        return pool;
    }
    */
    private String checkLine(int player)
    {
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        if(line.indexOf("exit") > 0)
        {
            players.remove(player);
        }
        return line;
    }

    private int numberInLine(String line)
    {
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