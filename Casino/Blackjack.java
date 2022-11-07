import java.util.*;

import javax.lang.model.util.ElementScanner6;

import java.lang.*;
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
    String promptAnswer = "";
    private boolean open;
    private boolean setupDone=false;;
    
    public Blackjack(Gambler player_)
    {
        
        deck = new CardDeck(false);
        player = player_;
        dealer = new Gambler(new DynamicHand(), 0);
        playerHand=player.getHand();
        dealerHand=dealer.getHand();
        open = true;
    }
    public double getPlayerBalance()
    {
        return player.getBalance();
    }
    public void play()
    {
        if(!setupDone)
        {
            setup();
            knowRules();
            giveCards();
        }
        else
        {
            
            main();
            isBye();
        }
       

        
    }
    public void main()
    {
        
        

        
         
        
        hitStand();

    
        //System.out.print("choice");

        
    }
    private void giveCards() {
        deck.shuffle();
        //System.out.println(deck.getDeckSize());
        playerHand.addCard(deck.drawCard(false));
        playerHand.addCard(deck.drawCard(false));
        dealerHand.addCard(deck.drawCard(false));
        dealerHand.addCard(deck.drawCard(false));
        System.out.println("Here is your hand!: \n");
        System.out.println(playerHand.showHand(false));
        int val1;
        int val2;
        if(playerHand.getCard(0).getCard().equals("jack") 
        || playerHand.getCard(0).getCard().equals("queen")
        || playerHand.getCard(0).getCard().equals("king"))
        {
            val1=10;
        }
        else
        {
            val1 = Integer.parseInt(playerHand.getCard(0).getCard());
        }

        if(playerHand.getCard(1).getCard().equals("jack") 
        || playerHand.getCard(1).getCard().equals("queen")
        || playerHand.getCard(1).getCard().equals("king"))
        {
            val2=10;
        }
        else
        {
            val2 = Integer.parseInt(playerHand.getCard(1).getCard());
        }

        int dval1;
        int dval2;
        if(dealerHand.getCard(0).getCard().equals("jack") 
        || dealerHand.getCard(0).getCard().equals("queen")
        || dealerHand.getCard(0).getCard().equals("king"))
        {
            dval1=10;
        }
        else
        {
            dval1 = Integer.parseInt(dealerHand.getCard(0).getCard());
        }
        if(dealerHand.getCard(1).getCard().equals("jack") 
        || dealerHand.getCard(1).getCard().equals("queen")
        || dealerHand.getCard(1).getCard().equals("king"))
        {
            dval2=10;
        }
        else
        {
            dval2 = Integer.parseInt(dealerHand.getCard(1).getCard());
        }

        if((val1 + val2 == 21) && (dval1 + dval2==21))
        {
            System.out.println("Dealer hand: \n");
            System.out.println(dealerHand.showHand(false));
            player.giveMoney(bet);
            System.out.println("You and the dealer both got a natural! To play again, simply enter \"blackjack\" again on the game select promp!");
            end();
        }
    }
    private void hitStand() {
        String temp  = prompt("Make your choice (hit/stand): ");
        if(temp.equals("hit"))
        {
            playerHand.addCard(deck.drawCard(false));
            System.out.println("Here is your hand!: \n");
            System.out.println(playerHand.showHand(false));

        }
        else if(temp.equals("stand"))
        {
           
        }
        else
        {
           
            hitStand();
        }
    }
    private void knowRules() {
        String answer;
        answer =prompt("Do you know how to play Blackjack? (y/n): ");
        if(answer.equals("y"))
        {
            System.out.println("Great! Let us begin.");
        
            
        }
        else if(answer.equals("n"))
        {
            System.out.println("RULES ARE A WIP");
            prompt("Press any key to continue:");
        }
        else
        {
            knowRules();
        }
        
        
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
                if(bet>player.getBalance())
                {
                    bet=0;
                    System.out.println("You cannot bet more than you have deposited in your account. \nTo deposit more money, type M. \nTo enter a lower bet, press B");
                    String temp3 = input.nextLine();
                    if(temp3.equals("M"))
                    {
                        System.out.print("Add: ");
                        player.giveMoney(Integer.parseInt(input.nextLine()));
                    }
                    else if(!temp3.equals("B"))
                    {
                        //input.nextLine();
                        String temp2 = "bruhh";
                        
                        do
                        {   
                            System.out.println("You must enter either M or B");
                            System.out.print("Decision: ");
                            temp2 = input.nextLine();

                        }while(!temp2.equals("M") && !temp2.equals("B"));
                        if(temp2.equals("M"))
                        {
                            System.out.print("Add: ");
                            player.giveMoney(Integer.parseInt(input.nextLine()));
                        }
                        
                    }
                   
                   
                }
            }
            else
            {
                System.out.println("Enter only an integer > 0");
            }
        }while(bet==0);
        double returner = player.getBalance()-(double)bet;
        player.takeMoney((double)bet);
        System.out.println("Remaining balance: " + returner);
        setupDone=true;

        

        
    }
    public boolean isOpen()
    {
        return open;
    }
    public void isBye()
    {
        if(promptAnswer.equals("bye"))
        {
            open=false;
            System.out.print("Goodbye! Please select what game you would like to play next: ");
        }
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


