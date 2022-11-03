//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;

public class Casino{
    public static void main(String[] args) throws IOException{
        out.println("Hi, welcome to the casino! You can either roll dice or play with a deck of cards");
        out.println("Current commands:\n- Dice: roll, roll + number of times\n- Cards: draw card, draw random card, shuffle, put card\n- Misc: exit\n");
        
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        
        UI ui = new UI(new Dice(6), new CardDeck(true));
        //Hand hand1 = new Hand(7);
        //DynamicHand hand = new DynamicHand();
        //Card one = new Card("9", "spades");
        //hand.addCard(one);
        //System.out.println(hand.showHand());
        while(!line.equals("exit")){
            //ui.blackjack(input, line);
            if(line.equals("blackjack"))
            {
                Blackjack game = new Blackjack();
                game.setup();
                do
                {
    
                    game.play();
                }while(!game.isBye());
               
                
            }
            else
            {
                ui.dice(input, line);
                ui.cardDeck(input, line);
            }
            
                
            line = input.nextLine();
        }
        
        out.println("Thanks for playing at the casino!");
    }
}


