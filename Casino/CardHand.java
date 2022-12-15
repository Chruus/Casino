import java.util.*;

public class CardHand {
    ArrayList<Card> cards;

    // Creates hand with an array of cards
    public CardHand() {
        cards = new ArrayList<Card>();
    }

    // Accessors
    public int getSize() {
        return cards.size();
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public CardDeck toCardDeck() {
        CardDeck output = new CardDeck();
        for (Card card : cards) {
            output.putCard(card);
        }
        return output;
    }

    public String toString() {
        String output = "";
        for (Card card : cards) {
            output += " | " + card + " | ";
        }
        return output;
    }

    public boolean has(Card cardCheck) {
        for (Card c : cards) {
            if (c.getNumeral() == cardCheck.getNumeral() && c.getSuit() == cardCheck.getSuit()) {
                return true;
            }
        }
        return false;
    }

    // Mutators
    public String add(Card cardIn) {
        cards.add(cardIn);
        return "You added the " + cardIn + " to your hand. You now have " + cards.size() + " cards in your hand!";
    }

    public Card replace(Card cardIn, Card cardOut) {
        for (Card c : cards) {
            if (c.getNumeral() == cardOut.getNumeral() && c.getSuit() == cardOut.getSuit()) {
                cards.remove(c);
                cards.add(cardIn);
            }
        }
        return cardOut;
    }

    public Card remove(Card cardOut) {
        Card output = new Card("", "");

        for (Card c : cards) {
            if (c.getNumeral() == cardOut.getNumeral() && c.getSuit() == cardOut.getSuit()) {
                output = c;
                cards.remove(c);
            }
        }
        return output;
    }

    public void clear() {
        cards.clear();
    }

}
