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
        
        UI ui = new UI(new Dice(6), new CardDeck(false));
        //Hand hand1 = new Hand(7);
        
        while(!line.equals("exit")){
            ui.dice(input, line);
            ui.cardDeck(input, line);
                
            line = input.nextLine();
        }
        
        out.println("Thanks for playing at the casino!");
    }
}


