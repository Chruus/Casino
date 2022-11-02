//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class Hand{
    Card[] cards;
    public Hand(int size, Card[] _cards){
        cards = new Card[size];
        try{
            cards = _cards;
        }
        catch(Exception e){
            out.println("You can't hold that many cards.");
        }
    }
    public Hand(int size){
        cards = new Card[size];
    }
    
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
            return "You added the " + cardIn.getCard() + " of " + cardIn.getSuit() + " to your hand.";    
        return "You don't have space for the " + cardIn.getCard() + " of " + cardIn.getSuit() + " in your hand.";
    }
    
    public Card replaceCard(Card cardIn, Card cardOut){
        for(int i = 0; i < cards.length; i++){
            if(cards[i].getCard() == cardOut.getCard() && cards[i].getSuit() == cardOut.getSuit())
                cards[i] = cardIn;
        }
        return cardOut;
    }
    
    public Card removeCard(Card cardOut){
        for(int i = 0; i < cards.length; i++){
            if(cards[i].getCard() == cardOut.getCard() && cards[i].getSuit() == cardOut.getSuit())
                
        }
    }
}

