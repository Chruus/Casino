import java.util.*;

public class CompareCardDecks implements Comparator<CardDeck> {

    String ruleset;

    public CompareCardDecks(String _ruleset) {
        ruleset = _ruleset;
    }

    public int compare(CardDeck deck1, CardDeck deck2) {
        int result = 0;
        if (ruleset.equals("texas holdem"))
            result = texasHoldem(deck1, deck2);
        return result;
    }

    private int texasHoldem(CardDeck thisDeck, CardDeck thatDeck) {

        // Gets the name (ie twoOAK or royalFlush) of each deck's value
        String thisDeckStr = getHighestValueHand(thisDeck);
        String thatDeckStr = getHighestValueHand(thatDeck);
        Scanner deckStrScan = new Scanner(thisDeckStr);
        System.out.println(thisDeckStr + "\n" + thatDeckStr);
        String thisDeckStrName = deckStrScan.next();
        // deckStrScan.close();
        deckStrScan = new Scanner(thatDeckStr);
        String thatDeckStrName = deckStrScan.next();
        deckStrScan.close();

        if (getValueOfName(thisDeckStrName) > getValueOfName(thatDeckStrName))
            return 1;
        else if (getValueOfName(thisDeckStrName) < getValueOfName(thatDeckStrName))
            return -1;
        return 0;
    }

    private int getValueOfName(String name) {
        if (name.equals("royalFlush"))
            return 9;
        if (name.equals("straightFlush"))
            return 8;
        if (name.equals("fourOAK"))
            return 7;
        if (name.equals("fullHouse"))
            return 6;
        if (name.equals("flush"))
            return 5;
        if (name.equals("straight"))
            return 4;
        if (name.equals("threeOAK"))
            return 3;
        if (name.equals("twoPair"))
            return 2;
        if (name.equals("pair"))
            return 1;
        return 0;
    }

    private String getHighestValueHand(CardDeck deck) {
        String flushHi, flushLo, straight, fullHouse, four, three, twoPair, pair;

        // Stores basic types of combinations
        four = hasFour(deck);
        fullHouse = hasFullHouse(deck);
        flushHi = checkFlush(deck, true);
        flushLo = checkFlush(deck, false);
        straight = checkStraight(deck);
        three = hasThree(deck);
        twoPair = hasTwoPair(deck);
        pair = hasPair(deck);

        // Royal and Straight Flush
        // If there is a flush and a straight
        if (!(flushHi.equals("") && flushLo.equals("") && straight.equals(""))) {

            // If the straight is 10-A
            if (straight.indexOf("king") >= 0 && straight.indexOf("ace") >= 0) {
                // If the straight is a flush
                if (flushHi.indexOf(straight) >= 0 || flushLo.indexOf(straight) >= 0)
                    return "royalFlush" + straight;
                // If the flush is a straight (High and Low ace)
                if (straight.indexOf(flushHi) >= 0)
                    return "royalFlush" + flushHi;
                if (straight.indexOf(flushLo) >= 0)
                    return "royalFlush" + flushLo;
            }

            // If the straight is a flush
            if (flushHi.indexOf(straight) >= 0 || flushLo.indexOf(straight) >= 0)
                return "straightFlush" + straight;
            // If the flush is a straight (High and Low ace)
            if (straight.indexOf(flushHi) >= 0)
                return "straightFlush" + flushHi;
            if (straight.indexOf(flushLo) >= 0)
                return "straightFlush" + flushLo;
        }

        // Four of a Kind
        if (!four.equals(""))
            return "fourOAK " + four;

        // Full House
        if (!fullHouse.equals(""))
            return "fullHouse " + fullHouse;

        // Flush
        if (!flushHi.equals(""))
            return "flush " + flushHi;

        // Straight
        if (!straight.equals(""))
            return "straight " + four;

        // Three of a Kind
        if (!three.equals(""))
            return "threeOAK " + three;

        // Two Pair
        if (!twoPair.equals(""))
            return "twoPair " + twoPair;

        // Pair
        if (!pair.equals(""))
            return "pair " + pair;

        return "";
    }

    private String hasFullHouse(CardDeck deck) {
        deck.sort(true, false, false);
        String three = hasThree(deck);

        if (three.equals(""))
            return "";

        Scanner outputScan = new Scanner(three);
        String threeNumeral = outputScan.next();
        // outputScan.close();

        for (int card = 0; card < deck.getSize(); card++) {
            if (deck.get(card).getNumeral().equals(threeNumeral))
                deck.drawCard(card);
        }

        String pair = hasPair(deck);
        if (!pair.equals(""))
            return "";

        return three + pair;
    }

    private String hasTwoPair(CardDeck deck) {

        deck.sort(true, false, false);
        String output = hasPair(deck);

        if (output.equals(""))
            return "";

        Scanner outputScan = new Scanner(output);
        String firstPair = outputScan.next();
        // outputScan.close();

        for (int card = 0; card < deck.getSize(); card++) {
            if (deck.get(card).getNumeral().equals(firstPair))
                deck.drawCard(card);
        }

        if (hasPair(deck).equals(""))
            return "";
        else
            output += hasPair(deck);

        return output;
    }

    private String hasPair(CardDeck deck) {
        int counter = 0;
        String output = "";

        if (!hasFour(deck).equals("") || !hasThree(deck).equals(""))
            return "";

        deck.sort(true, false, false);
        for (int card = 1; card < deck.getSize(); card++) {
            if (counter == 3)
                return output;
            else if (deck.get(card).getNumeralValue(false) == deck.get(card - 1).getNumeralValue(false)) {
                counter++;
                output += deck.get(card).toString();
            } else {
                counter = 0;
                output = "";
            }
        }

        return "";
    }

    private String hasThree(CardDeck deck) {
        int counter = 0;
        String output = "";

        if (!hasFour(deck).equals(""))
            return "";

        deck.sort(true, false, false);
        for (int card = 1; card < deck.getSize(); card++) {
            if (counter == 3)
                return output;
            else if (deck.get(card).getNumeralValue(false) == deck.get(card - 1).getNumeralValue(false)) {
                counter++;
                output += deck.get(card).toString();
            } else {
                counter = 0;
                output = "";
            }
        }

        return "";
    }

    private String hasFour(CardDeck deck) {
        int counter = 0;
        String output = "";

        deck.sort(true, false, false);
        for (int card = 1; card < deck.getSize(); card++) {
            if (counter == 4)
                return output;
            else if (deck.get(card).getNumeralValue(false) == deck.get(card - 1).getNumeralValue(false)) {
                counter++;
                output += deck.get(card).toString();
            } else {
                counter = 0;
                output = "";
            }
        }

        return "";
    }

    private String checkStraight(CardDeck deck) {
        int counterHi = 0;
        int counterLo = 0;
        String outputHi = "";
        String outputLo = "";

        for (int card = 1; card < deck.getSize(); card++) {
            // Checks for straight if ace is high
            deck.sort(true, false, true);
            if (deck.get(card).getNumeralValue(true) == deck.get(card - 1).getNumeralValue(true) + 1) {
                counterHi++;
                outputHi += deck.get(card).toString();
            } else if (counterHi >= 5)
                return outputHi;
            else {
                counterHi = 0;
                outputHi = "";
            }
            // Checks for straight if ace is low
            deck.sort(true, false, false);
            if (deck.get(card).getNumeralValue(false) == deck.get(card - 1).getNumeralValue(false) + 1) {
                counterLo++;
                outputLo += deck.get(card).toString();
            } else if (counterLo >= 5)
                return outputLo;
            else {
                counterLo = 0;
                outputLo = "";
            }
        }

        return "";
    }

    private String checkFlush(CardDeck deck, boolean aceHigh) {
        int counter = 0;
        String output = "";

        deck.sort(true, true, aceHigh);
        for (int card = 1; card < deck.getSize(); card++) {
            if (deck.get(card).getSuitValue() == deck.get(card - 1).getSuitValue()) {
                counter++;
                output += deck.get(card).toString() + " ";
            } else if (counter >= 5)
                return output;
            else {
                output = "";
                counter = 0;
            }
        }

        return "";
    }

}
