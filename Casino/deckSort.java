import java.util.Comparator;

public class deckSort implements Comparator <Card> {

    public int compare(Card card1, Card card2)
    {
        return (int)(card1.getDoubleValue() - card2.getDoubleValue());
    }
}
