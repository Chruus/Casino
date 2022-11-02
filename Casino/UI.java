//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class UI{
    Dice die;
    CardDeck deck;
    
    public UI(Dice _die, CardDeck _deck){
        die = _die;
        deck = _deck;
    }
    
    public void dice(Scanner input, String line){
        //Rolling dice
        if(line.indexOf("roll") >= 0){
            int loopLength = 0;
            boolean rollMoreThanOnce = false;
            Scanner lineScan = new Scanner(line);
            
            //Checks if any word has a value, stores it if it does
            while(lineScan.hasNext()){
                String word = lineScan.next();
                
                try{
                    loopLength = Integer.parseInt(word);
                    rollMoreThanOnce = true;
                    break;
                }
                catch (Exception e){}
            }
            //Rolls and prints however many times specified or once if not specified
            if(rollMoreThanOnce){
                if(loopLength > 0){
                    out.print("You rolled a ");
                    for(int i = 1; i < loopLength; i++){
                        die.roll();
                        out.print(die.getFaceValue() + ", ");
                    }
                    die.roll();
                    out.println(die.getFaceValue() + ".");
                }
                else
                    out.println("You can't roll " + loopLength + " times!");
            }
            else{
                die.roll();
                out.println(die.getFaceValueToString());
            }
        }
    }
    
    public void cardDeck(Scanner input, String line){
        //Removing cards
        if(line.indexOf("draw") >= 0 && line.indexOf("card") >= 0){
            Card card;
            if(line.indexOf("random") >= 0){
                card = deck.drawCard(true);
            }
            else{
                card = deck.drawCard(false);
            }
            out.println(card.getCardValueToString());
        }
        
        //Shuffling Cards
        if(line.indexOf("shuffle") >= 0){
            deck.shuffle();
            out.println("Your deck has been shuffled.");
        }
        
        //Adding cards
        if(line.indexOf("put") >= 0 && line.indexOf("card") >= 0){
            out.println("Which card?");
            line = input.nextLine();
            Scanner lineScan = new Scanner(line);
            String card = "";
            String suit = "";
            
            //Assigns card/suit a value
            while(lineScan.hasNext()){
                String word = lineScan.next();
                word = word.toLowerCase();
                
                if(word.equals("joker")){
                    card = "joker";
                    break;
                }
                if(word.equals("diamonds") || word.equals("diamonds") || word.equals("diamonds") || word.equals("diamonds")){
                    suit = word;
                }
                if(word.equals("jack") || word.equals("queen") || word.equals("king")){
                    card = word;
                }
                try{
                    if(Integer.parseInt(word) > 0)
                        card = word;
                }
                catch (Exception e){
                }
                if(!card.equals("") && !suit.equals(""))
                    break;
            }
            
            //Checks where to put card
            out.println("Do you want it on the bottom or somewhere in the middle?");
            line = input.nextLine();
            line = line.toLowerCase();
            if(line.indexOf("bottom") >= 0){
                deck.putCard(new Card(card, suit),true);
            }
            else if(line.indexOf("middle") >= 0){
                deck.putCard(new Card(card, suit));
            }
            out.println("The " + card + " of " + suit + " has been added to the deck");
        }
    }
}

