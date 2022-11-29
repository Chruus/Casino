import java.util.Comparator;

public class deckSort implements Comparator <Card> {
    private boolean aceHigh;
    public deckSort (boolean isAceHigh)
    {
        aceHigh = isAceHigh;
    }
    public int compare(Card card1, Card card2)
    {
        return (int)(card1.getDoubleValue(aceHigh) - card2.getDoubleValue(aceHigh));
    }
}
