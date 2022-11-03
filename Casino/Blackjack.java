import java.util.*;
import java.io.*;


public class Blackjack {
    CardDeck deck;
    int bet;
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
        prompt();
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
        return promptAnswer;
    }

    public void end()
    {
        //input.close();
        open = false;
    }
}
