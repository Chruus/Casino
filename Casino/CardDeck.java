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
    public Card get(int pos){
        return deck.get(pos);
    }
    public int getSize()
    {
        return deck.size();
    }

    public String getDeck()
    {
        String output = "";
        for(int card = 0; card < deckSize; card++)
        {
            output += deck.get(card).getNumeral() + " " + deck.get(card).getSuit();
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

    public void sort(boolean byNumeral, boolean bySuit, boolean aceHigh){
        Collections.sort(deck, new CompareCards(byNumeral, bySuit, aceHigh));
    }
    
    //If random, returns random card from the deck and removes it from the arraylist.
    //Else returns top card from deck and removes it from arraylist
    public Card drawCard(){
        int pos = (int)(Math.random() * deckSize);
        Card temp = deck.get(pos);
        deck.remove(pos);
        deckSize--;
        return temp;
    }

    public Card drawCard(int index){
        Card temp = deck.get(index);
        deck.remove(index);
        deckSize--;
        return temp;
    }

    //If bottom, puts a new card at the bottom of the deck, else puts one at the top
    public void putCard(Card newCard, int position){
        deck.add(position, newCard);
    }
    
    //Puts a card at a random position in the deck
    public void putCard(Card newCard){
        int randomPos = (int)(Math.random()*deckSize);
        deck.add(randomPos, newCard);
    }
    
}

