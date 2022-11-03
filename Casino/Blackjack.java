import java.util.*;
import java.io.*;



public class Blackjack {
    Gambler player;
    Gambler dealer;
    DynamicHand playerHand;
    DynamicHand dealerHand;
    CardDeck deck;
    int bet=0;
    String choice;
    Scanner input = new Scanner(System.in);
    String promptAnswer;
    private boolean open;
    
    public Blackjack(Gambler player_)
    {
        
        deck = new CardDeck(false);
        player = player_;
        dealer = new Gambler(new DynamicHand(), 0);
        playerHand=player.getHand();
        dealerHand=dealer.getHand();
        
        
        open = true;
    }
    public void play()
    {
        
        prompt("sup!");
    }
    public void setup()
    {

        do
        {
            System.out.print("Place your bet: ");
            String temp = input.nextLine();

            if(temp.matches("^[0-9]*[1-9][0-9]*$")) //checks to see if input in only numbers, not equal to 0
            {
                bet=Integer.parseInt(temp);
                if(bet>player.getMoney())
                {
                    bet=0;
                    System.out.println("You cannot bet more than you have deposited in your account. \nTo deposit more money, type M. \nTo enter a lower bet, press B");
                    if(input.nextLine().equals("M"))
                    {
                        System.out.print("Add: ");
                        player.giveMoney(Integer.parseInt(input.nextLine()));
                    }
                   
                }
            }
            else
            {
                System.out.println("Enter only an integer > 0");
            }
        }while(bet==0);

        

        
    }
    public boolean isOpen()
    {
        return open;
    }
    public boolean isBye()
    {
        return (promptAnswer.equals("bye"))? true : false;
    }
    
    public String prompt()
    {

        System.out.println("Welcome to Blackjack!");
        System.out.print("Please enter a number: ");
        promptAnswer = input.nextLine();
        System.out.println(promptAnswer);
        end();
        return promptAnswer;
    }
    
    public String prompt(String prompter)
    {
        System.out.print(prompter);
        promptAnswer=input.nextLine();
        return promptAnswer;
    }
    public void end()
    {
        //input.close();
        open = false;
    }
}


