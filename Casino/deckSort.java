import java.util.Comparator;

public class deckSort implements Comparator <Card> {

    public int compare(Card card1, Card card2)
    {
        double thisCard, thisSuit, thatCard, thatSuit;
        thisCard = thisSuit = thatCard = thatSuit = 0;
        
        try
        {//Assigns number values for each card
            thisCard = Integer.parseInt(card1.getCard());
            thatCard = Integer.parseInt(card2.getCard());
        }
        catch(Exception e)
        {
            if(card1.getCard().equals("ace"))
                thisCard = 1;
            else if(card1.getCard().equals("jack"))
                thisCard = 11;
            else if(card1.getCard().equals("queen"))
                thisCard = 12;
            else if(card1.getCard().equals("king"))
                thisCard = 13;
            
            if(card2.getCard().equals("ace"))
                thatCard = 1;
            else if(card2.getCard().equals("jack"))
                thatCard = 11;
            else if(card2.getCard().equals("queen"))
                thatCard = 12;
            else if(card2.getCard().equals("king"))
                thatCard = 13;
        }

        //Assigns number values for each suit
        if(card1.getSuit().equals("spades"))
            thisSuit = .4;
        else if(card1.getSuit().equals("hearts"))
            thisSuit = .3;
        else if(card1.getSuit().equals("diamonds"))
            thisSuit = .2;
        else if(card1.getSuit().equals("clubs"))
            thisSuit = .1;

        if(card2.getSuit().equals("spades"))
            thatSuit = .4;
        else if(card2.getSuit().equals("hearts"))
            thatSuit = .3;
        else if(card2.getSuit().equals("diamonds"))
            thatSuit = .2;
        else if(card2.getSuit().equals("clubs"))
            thatSuit = .1;

        //Compares the two and returns
        return (int)((thisCard + thisSuit) - (thatCard + thatSuit));
    }
}
