//Bugs
//Can pay more than gambler has

import java.util.*;
import static java.lang.System.out;

public class TexasHoldem{
    private ArrayList <DynamicHand> hands;
    private ArrayList <Gambler> players;
    private CardDeck deck;
    private CardDeck spread;
    private int bettingRound = 0;
    private int numberOfPlayers;
    private double pool = 0;

    //Creates a new game of texas holdem, sets up each player with the same starting amount of money
    public TexasHoldem(int _numberOfPlayers,int startingBalance){
        deck = new CardDeck(false);
        spread = new CardDeck();
        hands = new ArrayList <DynamicHand>();
        players = new ArrayList <Gambler>();
        numberOfPlayers = _numberOfPlayers;
        for(int i = 0; i < numberOfPlayers; i++){
            hands.add(new DynamicHand());
            players.add(new Gambler(hands.get(i), startingBalance));
        }
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
                }
                if(bettingRound == 1){
                    out.println("The Flop:");
                    showSpread();
                    showHand(0);
                    placeBets(input);
                }
                if(bettingRound == 2){
                    out.println("The River:");
                    showSpread();
                    showHand(0);
                    placeBets(input);
                }
                if(bettingRound == 3){
                    out.println("The Showdown:");
                    showSpread();
                    showHand(0);
                    placeBets(input);
                }
                showResults();
                resetGame();
            }
        }
        else if(line.indexOf("no") >= 0){
            
        }
    }

    private void showResults(){
        HashMap <DynamicHand, String> handsMap = new HashMap <DynamicHand, String>();

        for(int i = 0; i < hands.size(); i++)
            handsMap.put(hands.get(i), "");

        for(Map.Entry <DynamicHand, String> entry: handsMap.entrySet()){
            //Hands in order:
            //Royal flush - straight & flush, 10-A
            //Straight Flush - straight & flush
            //Four of a kind - four of the same type of card
            //Full house - a Pair and a Three of a Kind
            //Flush - five cards with the same suit
            //Straight - five cards in a row (A is 1 or 14)
            //Three of a Kind - three of the same type of card
            //Two pair - two pairs
            //Pair - two of one type of card
        }
        
        


        //If there's a tie between any of the hands, check which has the highest card
        //If still tied, check for suit of the highest card
        //If still tied, repeat first check w/ 2nd highest card
    }

    private void resetGame(){
        deck = new CardDeck(false);
        spread = new CardDeck();
        hands = new ArrayList <DynamicHand>();
        bettingRound = 0;
        pool = 0;
    }



    //Gives each player two cards
    private void dealHand(){
        for(int i  = 0; i < 2; i++){
            for(int j = 0; j < hands.size(); j++)
                hands.get(j).addCard(deck.drawCard(false));
        }
    }

    private void showHand(int player){
        out.println("You have:");
        for(int  i = 0; i < hands.get(player).getHandSize(); i++){
            out.print(" | " + hands.get(player).getCard(i).getCard() + " of " + hands.get(player).getCard(i).getSuit() + " | ");
        }
        out.println("\n");
    }

    private void showSpread(){
        if(bettingRound == 1){
            for(int i = 0; i < 3; i++)
                spread.putCard((deck.drawCard(false)));
        }
        else if(bettingRound == 2 || bettingRound == 3){
            spread.putCard((deck.drawCard(false)));
        }

        for(int i = 0; i < spread.getDeckSize(); i++)
            out.print(" | " + spread.getCard(i).getCard() + " of " + spread.getCard(i).getSuit() + " | ");
        out.println("\n");  
    }


    private void placeBets(Scanner in){
        String line;
        double[] bets = new double[players.size()];
        double highest = 0;
        boolean hasPlacedBet = false;
        double minimumBet = 0;
        for(int i = 0; i < players.size(); i++){
            out.println("What would you like to bet? The current bid is $" + highest);
            line = in.nextLine();
            Scanner lineScan = new Scanner(line);
            
            //Checks for amount added to pool
            outerwhile:
            while(!hasPlacedBet){
                while(lineScan.hasNext()){
                    String word = lineScan.next();
                    try{
                        double bet = Integer.parseInt(word);
                        if(word.indexOf("check") >= 0){
                            bets[i] = 0;
                            hasPlacedBet = true;
                            break;
                        }
                        
                        else if(word.indexOf("fold") >= 0){
                            bets[i] = 0;
                            hasPlacedBet = true;
                            players.remove(i);
                            i--;
                            break outerwhile;
                        }

                        else if(Integer.parseInt(word) > 0){
                            if(bet > players.get(i).getBalance()){
                                out.println("You only have $" + players.get(i).getBalance());
                                line = in.nextLine();
                                lineScan = new Scanner(line);
                                break;
                            }
                            else if(bet < minimumBet){
                                out.println("You have to bet at least $" + minimumBet);
                                line = in.nextLine();
                                lineScan = new Scanner(line);
                                break;
                            }
                            bets[i] = bet;
                            players.get(i).takeMoney(bet);
                            hasPlacedBet = true;
                            pool += bet;
                            if(bet > minimumBet){
                                minimumBet = bet;
                            }
                            break;
                        }
                    }
                    catch(Exception e){}
                }

                if(hasPlacedBet){
                    hasPlacedBet = false;
                    break;
                }
                else{
                    out.println("Please bet, check, or fold");
                    line = in.nextLine();
                    lineScan = new Scanner(line);
                }
            }
        }

        for(int i = 0; i < bets.length; i++)
            pool += bets[i];
        
        bettingRound ++;
            
    }

    


}