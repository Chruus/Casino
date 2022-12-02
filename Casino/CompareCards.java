import java.util.Comparator;
public class CompareCards implements Comparator<Card>{
    private boolean numeral, suit, aceHigh;
    public CompareCards(boolean _numeral, boolean _suit, boolean _aceHigh){
        numeral = _numeral;
        suit = _suit;
        aceHigh = _aceHigh;
    }

    public int compare(Card card1, Card card2){
        if(numeral && suit){
            if(card1.getCardValue(aceHigh) > card2.getCardValue(aceHigh))
                return 1;
            else if(card1.getCardValue(aceHigh) < card2.getCardValue(aceHigh))
                return -1;
            else
                return 0;
        }
        else if(numeral){
            if(card1.getNumeralValue(aceHigh) > card2.getNumeralValue(aceHigh))
                return 1;
            else if(card1.getNumeralValue(aceHigh) < card2.getNumeralValue(aceHigh))
                return -1;
            else
                return 0;
        }
        else if(suit){
            if(card1.getSuitValue() > card2.getSuitValue())
                return 1;
            else if(card1.getSuitValue() < card2.getSuitValue())
                return -1;
            else
                return 0;
        }

        return 0;
    }
}
