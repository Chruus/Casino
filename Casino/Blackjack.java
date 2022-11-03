import java.util.*;
import java.io.*;
import java.security.cert.TrustAnchor;


public class Blackjack {
    CardDeck deck;
    int bet=0;
    String choice;
    Scanner input = new Scanner(System.in);
    String promptAnswer;
    private boolean open;
    
    public Blackjack()
    {
        deck = new CardDeck(false);
        
        open = true;
    }
    public void play()
    {
        //setup();
        prompt();
    }
    public void setup()
    {

        do
        {
            System.out.print("Place your bet: ");
            String temp = input.nextLine();

            if(temp.matches("^[0-9]*[1-9][0-9]*$"))
            {
                bet=Integer.parseInt(temp);
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

    public void end()
    {
        //input.close();
        open = false;
    }
}
