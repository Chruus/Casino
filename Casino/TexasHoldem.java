import java.util.*;
import static java.lang.System.out;

public class TexasHoldem{
    private ArrayList <DynamicHand> hands;
    private ArrayList <Gambler> players;
    private CardDeck deck;
    private CardDeck spread;
    private int bettingRound;
    private double pool;

    //Creates a new game of texas holdem, sets up each player with the same starting amount of money
    public TexasHoldem(int numberOfPlayers,int startingBalance){
        deck = new CardDeck(false);
        spread = new CardDeck();
        hands = new ArrayList <DynamicHand>();
        players = new ArrayList <Gambler>();
        for(int i = 0; i < numberOfPlayers; i++){
            hands.add(new DynamicHand());
            players.add(new Gambler(hands.get(i), startingBalance));
        }
        int pool = 0;
        int bettingRound = 0;
    }

    //Creates a new game of texas holdem, you give them an arraylist of players and it uses their balances to play
    public TexasHoldem(ArrayList <Gambler> listOfPlayers){
        deck = new CardDeck(false);
        spread = new CardDeck();
        hands = new ArrayList <DynamicHand>();
        players = listOfPlayers;
        for(int i = 0; i < players.size(); i++){
            hands.add(new DynamicHand());
            players.add(new Gambler(hands.get(i), 1000));
        }
        int pool = 0;
        int bettingRound = 0;
    }

    //Call for each game of Holdem
    public void main(){
        out.println("Welcome to Texas Holdem");
        out.println("Do you know the rules?");
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        deck.shuffle();
        if(line.indexOf("yes") >= 0){
            out.println("Then let's start");
            while(line.indexOf("exit") == -1){
                if(bettingRound == 0){
                    dealHand();
                    showHand(0);
                    placeBets(input);
                    showSpread();
                }
            }
        }
        else if(line.indexOf("no") >= 0){
            
        }
    }

    

    //Gives each player two cards
    private void dealHand(){
        for(int i  = 0; i < 2; i++){
            for(int j = 0; j < hands.size(); j++)
                hands.get(j).addCard(deck.drawCard(false));
        }
    }

    private void showHand(int player){
        out.print("You have:");
        for(int  i = 0; i < hands.get(player).getHandSize(); i++){
            out.print(" | " + hands.get(player).getCard(i).getCard() + " of " + hands.get(player).getCard(i).getSuit() + " | ");
        }
        out.println();
    }

    private void showSpread(){
        if(bettingRound == 1){
            for(int i = 0; i < 3; i++)
                spread.putCard((deck.drawCard(false)));

            out.print("The flop:");
            for(int i = 0; i < spread.getDeckSize(); i++)
                out.print(" | " + spread.getCard(i).getCard() + " of " + spread.getCard(i).getSuit() + " | ");
            out.println();
        }
        else if(bettingRound == 2 || bettingRound == 3){
            spread.putCard((deck.drawCard(false)));
        }        
    }


    private void placeBets(Scanner in){
        String line;
        double[] bets = new double[players.size()];
        double highest = 0;
        boolean placedBet = false;
        for(int i = 0; i < players.size(); i++){
            out.println("What would you like to bet? The current bid is $" + highest);
            out.println("(You can bet a number between one and your current balance, 'check', or 'fold'.");
            
            line = in.nextLine();
            Scanner lineScan = new Scanner(line);
            
            //Checks for amount added to pool
            outerwhile:
            while(!placedBet){
                while(lineScan.hasNext()){
                    String word = lineScan.next();
                    try{
                        if(word.indexOf("check") >= 0){
                            bets[i] = 0;
                            placedBet = true;
                            break outerwhile;
                        }
        
                        else if(word.indexOf("fold") >= 0){
                            bets[i] = -1;
                            placedBet = true;
                            break outerwhile;
                        }
                        
                        //else if(word.indexOf("all in") >= 0){
                        //    bets[i] = players[i].takeMoney(players[i].getBalance);
                        //}

                        else if(Integer.parseInt(word) > 0){
                            double bet = Integer.parseInt(word);
                            if(bet > players.get(i).getBalance()){
                                out.println("You only have $" + players.get(i).getBalance() + ". Please bet a lower amount, check, or fold.");
                                line = in.nextLine();
                                lineScan = new Scanner(line);
                                break;
                            }
                            bets[i] = bet;
                            players.get(i).takeMoney(bet);
                            placedBet = true;
                            break;
                        }
                    }
                    catch(Exception e){}
                }

                if(!placedBet){
                    i--;
                    out.println("Please bet, check, or fold");
                    line = in.nextLine();
                }
                else
                    out.println("You added $" + bets[i] + " to the pool.");
            }
            placedBet = false;
        }

        for(int i = 0; i < bets.length; i++){
            if(bets[i] > 0)
                pool += bets[i];
        }
        bettingRound ++;
            
    }
}