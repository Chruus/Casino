import java.util.Comparator;

public class CompareGamblers implements Comparator <Gambler>{
    String ruleset;
    public CompareGamblers(String _ruleset){
        ruleset = _ruleset;
    }
    
    public int compare(Gambler thisPlayer, Gambler thatPlayer){
        if(ruleset.equals("texas holdem")){
            CompareCardDecks compareCardDecks = new CompareCardDecks("texas holdem");
            CardDeck thisDeck, thatDeck;
            thisDeck = thatDeck = new CardDeck();
            thisDeck.putCard(thisPlayer.getHand().get(0));
            thisDeck.putCard(thisPlayer.getHand().get(1));
            thatDeck.putCard(thisPlayer.getHand().get(0));
            thatDeck.putCard(thisPlayer.getHand().get(1));
            
            return compareCardDecks.compare(thisDeck, thatDeck);
        }
        return 0;
    }
}
