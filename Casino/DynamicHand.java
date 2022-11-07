//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class DynamicHand{
    ArrayList<Card> cards;
    

    //Creates hand with an array of cards
    public DynamicHand(){
        cards = new ArrayList<Card>();
        
        
    }

    
    
    //Checks if hand has a card in it
    public boolean hasCard(Card cardCheck){
        for(Card c : cards)
        {
            if(c.getCard() == cardCheck.getCard() && c.getSuit() == cardCheck.getSuit())
            {
                return true;
            }
        }
        return false;
    }

    public int getHandSize(){
        return cards.size();
    }
    
    public Card getCard(int index){
        return cards.get(index);
    }

    //Adds card into hand
    public String addCard(Card cardIn){
       
        
        cards.add(cardIn);
        
        return "You added the " + cardIn.getCard() + " of " + cardIn.getSuit() + " to your hand. \n You now have " + cards.size() + " cards in your hand!";    
        
    }
    
    //Takes card from hand and replaces it with cardIn, then returns cardOut
    public Card replaceCard(Card cardIn, Card cardOut){
        for(Card c : cards)
        {
            if(c.getCard() == cardOut.getCard() && c.getSuit() == cardOut.getSuit())
            {
                cards.remove(c);
                cards.add(cardIn);
            }
        }
        
        return cardOut;
    }
    
    //Removes card from hand and returns it
    public Card removeCard(Card cardOut){
        Card output = new Card("", "");
      
        for(Card c : cards)
        {
            if(c.getCard() == cardOut.getCard() && c.getSuit() == cardOut.getSuit())
            {
                output=c;
                cards.remove(c);
            }
        }
        return output;
    }

    public String showHand(boolean suit)
    {
        String output = "";
        for(int i = 0; i<cards.size(); i++)
        {
            Card temp = cards.get(i);
            output += "(";
            output += Integer.toString(i+1);
            output += ")\t";
            if(suit)
            {
                output += "Suit: ";
                output += temp.getSuit();
                output += "\t";
            }
            
            output += "Card: ";
            output += temp.getCard();
            output += "\n";
            

        }
        return output;
    }
}

