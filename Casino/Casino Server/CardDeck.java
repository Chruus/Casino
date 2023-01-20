import java.util.*;

public class CardDeck {
    private ArrayList<Card> deck;
    private int deckSize;

    // Creates ArrayList of cards, 54 w/ 2 jokers if hasJoker is true, 52 w/o if
    // hasJoker is false.
    public CardDeck(boolean hasJoker) {
        deck = new ArrayList<Card>();

        if (hasJoker) {
            deckSize = 54;
            for (int deckPos = 0; deckPos < deckSize; deckPos++) {
                if (deckPos < 52) {
                    int suit = deckPos / 13;
                    int card = deckPos - 13 * suit + 1;
                    suit++;
                    // out.println(suit + " " + card);
                    deck.add(deckPos, new Card(card, suit));
                } else
                    deck.add(deckPos, new Card("joker", "NA"));
            }
        } else {
            deckSize = 52;
            for (int deckPos = 0; deckPos < deckSize; deckPos++) {
                int suit = deckPos / 13;
                int card = deckPos - 13 * suit + 1;
                suit++;

                deck.add(deckPos, new Card(card, suit));
            }
        }
    }

    // Creates empty deck
    public CardDeck() {
        deck = new ArrayList<Card>();
        deckSize = 0;
    }

    // Getters
    public Card get(int pos) {
        return deck.get(pos);
    }

    public int getSize() {
        return deck.size();
    }

    public String getDeck() {
        String output = "";
        for (int card = 0; card < deckSize; card++) {
            output += deck.get(card).getNumeral() + " " + deck.get(card).getSuit();
        }
        return output;
    }

    public ArrayList<Card> getArrayList() {
        return deck;
    }

    public String toString() {
        String output = "";
        for (int card = 0; card < deck.size(); card++) {
            output += " | " + deck.get(card).toString() + " | ";
        }
        return output;
    }

    public void addCardDeck(CardDeck inDeck) {
        for (Card card : inDeck.getArrayList()) {
            add(card);
        }
    }

    // Setters
    public void shuffle() {
        Collections.shuffle(deck);
        deckSize = deckSize + 0;
    }

    public void clear() {
        deck.clear();
    }

    public void sort(boolean byNumeral, boolean bySuit, boolean aceHigh) {
        Collections.sort(deck, new CompareCards(byNumeral, bySuit, aceHigh));
    }

    public Card draw() {
        int pos = (int) (Math.random() * deckSize);
        Card temp = deck.get(pos);
        deck.remove(pos);
        deckSize--;
        return temp;
    }

    public Card draw(int index) {
        Card temp = deck.get(index);
        deck.remove(index);
        deckSize--;
        return temp;
    }

    public void add(Card newCard, int position) {
        deck.add(position, newCard);
    }

    public void add(Card newCard) {
        deck.add(0, newCard);
    }

}
