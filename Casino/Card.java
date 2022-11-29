//Christopher Petty

public class Card{
    private String value;
    private String suit;
    private double numberValue;

    //Allows cards to be made directly w/ strings
    Card(String _card, String _suit)
    {
        value = _card;
        suit = _suit;
    }
    
    //Allows cards to be set up w/ for loop
    Card(int _card, int _suit)
    {

        //Assigns value to card
        if(_card <= 10 && _card != 1)
            value = Integer.toString(_card);
        else if(_card == 1)
            value="ace";
        else if(_card == 11)
            value = "jack";
        else if(_card == 12)
            value = "queen";
        else if(_card == 13)
            value = "king";
        else if(_card == 14)
        {
            value = "joker";
            suit = "NA";
        }
        
        //Assigns suit to card
        if(_suit == 1)
            suit = "diamonds";
        if(_suit == 2)
            suit = "hearts";
        if(_suit == 3)
            suit = "clubs";
        if(_suit == 4)
            suit = "spades";

        try
        {//Assigns numberValue of the value
            numberValue = Integer.parseInt(value);
        }
        catch(Exception e)
        {
            if(value.equals("ace"))
                numberValue = 1;
            else if(value.equals("jack"))
                numberValue = 11;
            else if(value.equals("queen"))
                numberValue = 12;
            else if(value.equals("king"))
                numberValue = 13;
        }

        //Assigns numberValue of the suit
        if(suit.equals("spades"))
            numberValue += .4;
        else if(suit.equals("hearts"))
            numberValue += .3;
        else if(suit.equals("diamonds"))
            numberValue += .2;
        else if(suit.equals("clubs"))
            numberValue += .1;
    }

    public double compareTo(Card inCard)
    {   
        return numberValue - inCard.getDoubleValue();
    }
    
    //Getters
    public String getSuit()
    {
        return suit;
    }
    public String getValue()
    {
        return value;
    }
    public double getDoubleValue()
    {
        return (double)numberValue;
    }
    public int getIntValue()
    {
        return (int)numberValue;
    }
    public String toString()
    {
        if(value.equals("joker"))
            return "joker";
        return getValue() + " of " + getSuit();
    }
}

