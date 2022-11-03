public class Gambler{
    double totalMoney;
    int wins;
    int losses;
    DynamicHand hand;

    public Gambler(DynamicHand _hand, double startingMoney){
        hand = _hand;
        totalMoney = startingMoney;
        wins = 0;
        losses = 0;
    }

    //Getters
    public DynamicHand getHand(){
        return hand;
    }
    public int getWins(){
        return wins;
    }
    public int getLosses(){
        return losses;
    }
    public double getMoney(){
        return totalMoney;
    }
    
    //Setters
    public void giveMoney(int give)
    {
        totalMoney+=give;
    }
    public void win(int moneyWon){
        wins++;
        totalMoney += moneyWon;
    }
    public void win(int moneyWon, int numberOfWins){
        wins += numberOfWins;
        totalMoney += moneyWon;
    }
    public void lose(int moneyLost){
        losses ++;
        totalMoney -= moneyLost;
    }
    public void lose(int moneyLost, int numberOfLosses){
        losses += numberOfLosses;
        totalMoney -= moneyLost;
    }

}