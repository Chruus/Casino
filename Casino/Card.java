//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class Card{
    private String card;
    private String suit;

    //Allows cards to be made directly w/ strings
    Card(String _card, String _suit){
        card = _card;
        suit = _suit;
    }
    
    //Allows cards to be set up w/ for loop
    Card(int _card, int _suit){

        if(_card <= 10 && _card != 1)
            card = Integer.toString(_card);
        else if(_card==1)
        {
            card="ace";
        }
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

    public double compareTo(Card inCard)
    {
        double thisCard, thisSuit, thatCard, thatSuit;
        thisCard = thisSuit = thatCard = thatSuit = 0;
        
        try
        {//Assigns number values for each card
            thisCard = Integer.parseInt(card);
            thatCard = Integer.parseInt(inCard.getCard());
        }
        catch(Exception e)
        {
            if(card.equals("ace"))
                thisCard = 1;
            else if(card.equals("jack"))
                thisCard = 11;
            else if(card.equals("queen"))
                thisCard = 12;
            else if(card.equals("king"))
                thisCard = 13;
            
            if(inCard.getCard().equals("ace"))
                thatCard = 1;
            else if(inCard.getCard().equals("jack"))
                thatCard = 11;
            else if(inCard.getCard().equals("queen"))
                thatCard = 12;
            else if(inCard.getCard().equals("king"))
                thatCard = 13;
        }

        //Assigns number values for each suit
        if(suit.equals("spades"))
            thisSuit = .4;
        else if(suit.equals("hearts"))
            thisSuit = .3;
        else if(suit.equals("diamonds"))
            thisSuit = .2;
        else if(suit.equals("clubs"))
            thisSuit = .1;

        if(inCard.getSuit().equals("spades"))
            thatSuit = .4;
        else if(inCard.getSuit().equals("hearts"))
            thatSuit = .3;
        else if(inCard.getSuit().equals("diamonds"))
            thatSuit = .2;
        else if(inCard.getSuit().equals("clubs"))
            thatSuit = .1;

        //Compares the two and returns
        return (thisCard + thisSuit) - (thatCard + thatSuit);

    }
    
    //Getters
    public String getSuit(){
        return suit;
    }
    public String getCard(){
        return card;
    }
    public String toString(){
        if(card.equals("joker"))
            return "joker";
        return getCard() + " of " + getSuit();
    }
}

