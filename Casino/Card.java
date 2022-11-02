//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class Card{
    private String card;
    private String suit;
    Card(String _card, String _suit){
        card = _card;
        suit = _suit;
    }
    
    Card(int _card, int _suit){
        if(_card <= 10)
            card = Integer.toString(_card);
        else if(_card == 11)
            card = "jack";
        else if(_card == 12)
            card = "queen";
        else if(_card == 13)
            card = "king";
        else if(_card == 14){
            card = "joker";
            suit = "NA";
        }
        
        if(_suit == 1)
            suit = "diamonds";
        if(_suit == 2)
            suit = "hearts";
        if(_suit == 3)
            suit = "clubs";
        if(_suit == 4)
            suit = "spades";
    }
    
    public String getSuit(){
        return suit;
    }
    
    public String getCard(){
        return card;
    }
    
    public String getCardValueToString(){
        if(card.equals("joker"))
            return "The card is a joker.";
        return "The card is a " + getCard() + " of " + getSuit() + ".";
    }
}

