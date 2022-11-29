//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class StaticHand{
    Card[] cards;

    //Creates hand with an array of cards
    public StaticHand(int size, Card[] _cards){
        cards = new Card[size];
        try{
            cards = _cards;
        }
        catch(Exception e){
            out.println("You can't hold that many cards.");
        }
    }

    //Creates empty hand
    public StaticHand(int size){
        cards = new Card[size];
    }
    
    //Adds card into hand
    public String addCard(Card cardIn){
        boolean space = false;
        
        for(int i = 0; i < cards.length; i++){
            if(cards[i] == null){
                cards[i] = cardIn;
                space = true;
                break;
            }
        }
        if(space)
            return "You added the " + cardIn.getValue() + " of " + cardIn.getSuit() + " to your hand.";    
        return "You don't have space for the " + cardIn.getValue() + " of " + cardIn.getSuit() + " in your hand.";
    }
    
    //Takes card from hand and replaces it with cardIn, then returns cardOut
    public Card replaceCard(Card cardIn, Card cardOut){
        for(int i = 0; i < cards.length; i++){
            if(cards[i].getValue() == cardOut.getValue() && cards[i].getSuit() == cardOut.getSuit())
                cards[i] = cardIn;
        }
        return cardOut;
    }
    
    //Removes card from hand and returns it
    public Card removeCard(Card cardOut){
        Card output = new Card("", "");
        for(int i = 0; i < cards.length; i++){
            if(cards[i].getValue() == cardOut.getValue() && cards[i].getSuit() == cardOut.getSuit()){
                output = cards[i];
                cards[i] = null;
            }
        }
        return output;
    }
}

