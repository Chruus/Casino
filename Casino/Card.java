//Christopher Petty

public class Card{
    private String numeral;
    private String suit;
    private double cardValue;

    //Allows cards to be made directly w/ strings
    Card(String _card, String _suit)
    {
        numeral = _card;
        suit = _suit;
    }
    
    //Allows cards to be set up w/ for loop
    Card(int _card, int _suit)
    {

        //Assigns numeral to card
        if(_card <= 10 && _card != 1)
            numeral = Integer.toString(_card);
        else if(_card == 1)
            numeral="ace";
        else if(_card == 11)
            numeral = "jack";
        else if(_card == 12)
            numeral = "queen";
        else if(_card == 13)
            numeral = "king";
        else if(_card == 14)
        {
            numeral = "joker";
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
        {//Assigns cardValue of the numeral
            cardValue = Integer.parseInt(numeral);
        }
        catch(Exception e)
        {
            if(numeral.equals("ace"))
                cardValue = 1;
            else if(numeral.equals("jack"))
                cardValue = 11;
            else if(numeral.equals("queen"))
                cardValue = 12;
            else if(numeral.equals("king"))
                cardValue = 13;
        }

        //Assigns cardValue of the suit
        if(suit.equals("spades"))
            cardValue += .4;
        else if(suit.equals("hearts"))
            cardValue += .3;
        else if(suit.equals("diamonds"))
            cardValue += .2;
        else if(suit.equals("clubs"))
            cardValue += .1;
    }
    
    //Getters
    public String getSuit(){
        return suit;
    }
    public String getNumeral(){
        return numeral;
    }
    public double getCardValue(boolean aceHigh){
        if(aceHigh && numeral.equals("ace"))
            cardValue += 13;
        return cardValue;
    }
    public int getNumeralValue(boolean aceHigh){
        if(aceHigh && numeral.equals("ace"))
            cardValue += 13;
        return (int)cardValue;
    }
    public double getSuitValue(){
        return cardValue % 1;
    }
    public String toString()
    {
        if(numeral.equals("joker"))
            return "joker";
        return getNumeral() + " of " + getSuit();
    }
}

