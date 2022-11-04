public class Gambler{
    double balance;
    int wins;
    int losses;
    DynamicHand hand;

    public Gambler(DynamicHand _hand, double startingMoney){
        hand = _hand;
        balance = startingMoney;
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
    public double getBalance(){
        return balance;
    }
    
    //Setters
    public void giveMoney(int give)
    {
        balance += give;
    }
    public String takeMoney(double taken){
        if(balance >= taken){
            balance -= taken;
            return "You now have $" + balance + " left";
        }
        return "You do not have enough money";
    }
    public void win(int moneyWon){
        wins++;
        balance += moneyWon;
    }
    public void win(int moneyWon, int numberOfWins){
        wins += numberOfWins;
        balance += moneyWon;
    }
    public void lose(int moneyLost){
        losses ++;
        balance -= moneyLost;
    }
    public void lose(int moneyLost, int numberOfLosses){
        losses += numberOfLosses;
        balance -= moneyLost;
    }

}