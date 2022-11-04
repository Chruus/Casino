import java.util.*;
import static java.lang.System.out;

public class TexasHoldem{
    private ArrayList <DynamicHand> hands;
    private ArrayList <Gambler> players;
    private CardDeck deck;
    private CardDeck spread;
    private int bettingRound;
    private double pool;

    public TexasHoldem(int numberOfPlayers){
        deck = new CardDeck(false);
        deck.shuffle();
        spread = new CardDeck();
        hands = new ArrayList <DynamicHand>();
        players = new ArrayList <Gambler>();
        for(int i = 0; i < numberOfPlayers; i++){
            hands.add(new DynamicHand());
            players.add(new Gambler(hands.get(i), 1000));
        }
        int pool = 0;
        int bettingRound = 0;
    }

    public void main(){
        Scanner input =  new Scanner(System.in);
        out.println("Welcome to Texas Holdem");
        out.println("Do you know the rules?");
        String line = input.nextLine();
        if(line.indexOf("yes") >= 0){

        }
        else if(line.indexOf("no") >= 0){
            out.println("Then let's start");
            while(line.indexOf("exit") != -1){
                if(bettingRound == 0){
                    dealHand();
                    placeBets(input);
                    showSpread();
                }
            }
        }
    }

    private void dealHand(){
        for(int i  = 0; i < 2; i++){
            for(Gambler player: players)
                players.add(player);
        }
    }
    
    private void showSpread(){
        if(bettingRound == 1){
            for(int i = 0; i < 3; i++)
                spread.putCard((deck.drawCard(false)));
        }
        else if(bettingRound == 2 || bettingRound == 3){
            spread.putCard((deck.drawCard(false)));
        }
    }

    private void placeBets(Scanner in){
        String line;
        double[] bets = new double[players.size()];
        double highest = 0;
        for(int i = 0; i < players.size(); i++){
            out.println("What would you like to bet?");
            out.println("(You can bet a number between one and your current balance, 'check', or 'fold'.");
            line = in.nextLine();
            Scanner lineScan = new Scanner(line);

            //Checks for amount added to pool
            while(lineScan.hasNext()){
                String word = lineScan.next();
                if(word.equals("check")){
                    bets[i] = 0;
                }
                else if(word.equals("fold")){
                    bets[i] = null;
                }
                try{
                    bets[i] = Integer.parseInt(word);
                }
                catch(Exception e){}
            }
        }
    }
}