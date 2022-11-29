//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class CardDeck{
    ArrayList <Card> deck;
    private int deckSize;

    //Creates ArrayList of cards, 54 w/ 2 jokers if hasJoker is true, 52 w/o if hasJoker is false.
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
                
                deck.add(deckPos, new Card(card, suit));
            }
        }
    }
    
    //Creates empty deck
    public CardDeck(){
        deck = new ArrayList <Card>();
        deckSize = 0;
    }
    
    //Getters
    public Card getCard(int pos){
        return deck.get(pos);
    }
    public int getDeckSize()
    {
        return deck.size();
    }

    public String getDeck()
    {
        String output = "";
        for(int card = 0; card < deckSize; card++)
        {
            output += deck.get(card).getValue() + " " + deck.get(card).getSuit();
        }
        return output;
    }

    public String toString()
    {
        String output = "";
        for(int card = 0; card < deckSize; card++)
        {
            output += " | "  + deck.get(card).toString() + " | ";
        }
        return output;
    }
    
    //Shuffles deck
    public void shuffle(){
        Collections.shuffle(deck);
        deckSize = deckSize + 0;
    }

    public void sort(){
        Collections.sort(deck, new deckSort());
    }
    
    //If random, returns random card from the deck and removes it from the arraylist.
    //Else returns top card from deck and removes it from arraylist
    public Card drawCard(boolean random){
        int pos = 0;
        if(random)
            pos = (int)(Math.random() * deckSize);
        Card temp = deck.get(pos);
        deck.remove(pos);
        deckSize--;
        return temp;
    }

    //If bottom, puts a new card at the bottom of the deck, else puts one at the top
    public void putCard(Card newCard, boolean bottom){
        if(bottom)
            deck.add(deck.size() - 1, newCard);
        else
            deck.add(0, newCard);
    }
    
    //Puts a card at a random position in the deck
    public void putCard(Card newCard){
        int randomPos = (int)(Math.random()*deckSize);
        deck.add(randomPos, newCard);
    }
    
}

