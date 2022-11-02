//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class CardDeck{
    //Card[] deck;
    ArrayList <Card> deck;
    private int deckSize;
    public CardDeck(boolean hasJoker){
        deck = new ArrayList <Card>();
        
        if(hasJoker){
            deckSize = 54;
            for(int deckPos = 0; deckPos < deckSize; deckPos++){
                if(deckPos < 52){
                    int suit = deckPos/13;
                    int card = deckPos - 13*suit + 1;
                    suit ++;
                    //out.println(suit + " " + card);
                    deck.add(deckPos, new Card(card, suit));
                }
                else
                    deck.add(deckPos, new Card("joker", "NA"));
            }
        }
        else{
            deckSize = 52;
            for(int deckPos = 0; deckPos < deckSize; deckPos++){
                int suit = deckPos/13;
                int card = deckPos - 13*suit + 1;
                suit++;
                
                deck.add(deckPos - 1, new Card(card, suit));
            }
        }
    }
    
    public CardDeck(){
        deck = new ArrayList <Card>();
        deckSize = 0;
    }
    
    
    public Card getCard(int pos){
        return deck.get(pos);
    }
    
    public String getCardValue(int pos){
        return deck.get(pos).getCard() + " " + deck.get(pos).getSuit();
    }
    
    public int getDeckSize(){
        return deckSize;
    }
    
    public void shuffle(){
        Collections.shuffle(deck);
        deckSize = deckSize + 0;
    }
    
    public Card drawCard(boolean random){
        int pos = 0;
        if(random)
            pos = (int)(Math.random() * deckSize);
        Card temp = deck.get(pos);
        deck.remove(pos);
        deckSize--;
        return temp;
    }
    
    public void putCard(Card newCard, boolean bottom){
        if(bottom)
            deck.add(deck.size() - 1, newCard);
        else
            deck.add(0, newCard);
    }
    
    public void putCard(Card newCard){
        int randomPos = (int)(Math.random()*deckSize);
        deck.add(randomPos, newCard);
    }
    
}

