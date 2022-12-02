//Christopher Petty

import static java.lang.System.out;
import java.io.*;
import java.util.*;
//import java.net.*;

public class Casino{
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        out.println("Hi, welcome to the casino! Please enter:");
        out.print(" your name: ");
        String name = input.nextLine();
        System.out.print(" starting balance:");
        String line = input.nextLine();
        double startingMoney = Double.parseDouble(line);

        out.print("Enter which game you would like to play : ");
        Gambler player = new Gambler(new DynamicHand(), startingMoney, name);
        UI ui = new UI(new Dice(6), new CardDeck(true));
        TexasHoldemV2 texasHoldem = new TexasHoldemV2();
        texasHoldem.addPlayer(player);
        texasHoldem.addPlayer(new Gambler(new DynamicHand(), 1500, "Bot 1"));


        while(!line.equals("exit")){
            if(line.equals("blackjack"))
            {
                Blackjack game = new Blackjack(player);
                
                do
                {
                    game.play();
                }
                while(game.isOpen());
                player.setMoney(game.getPlayerBalance());
                game=null;
            }

            else if(line.toLowerCase().equals("texas holdem"))
            {
                do
                {
                    texasHoldem.play();
                }
                while(true);
            }

            else{
                ui.dice(input, line);
                ui.cardDeck(input, line);
            }
            
            line = input.nextLine();
        }
        
        out.println("Thanks for playing at the casino!");





        
        
        /* 
		String sentence = "";
		String IP;
		int portNum = 7777;
		String answer;
		Scanner scan = new Scanner(System.in);
		Socket clientSocket;
		
		Socket connectionSocket;

		System.out.println("Welcome to the Casino!  You are at Casino #" + portNum);
		System.out.println("Are you a player? (Y or N)");
		//System.out.println("Do you want to send a message right now? (Y or N)?");

		answer = scan.nextLine();

		if(answer.equalsIgnoreCase("y"))
		{
			System.out.println("What is the IP address of Casino you want to connect to? (ask your dealer for help if needed)");
			IP = scan.nextLine();

			while(true)
            {

                clientSocket = new Socket(IP, portNum);
                DataOutputStream outToServer =
                                    new DataOutputStream(clientSocket.getOutputStream());
                Scanner inFromServer =
                                    new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Enter Your Message: ");
                sentence = scan.nextLine();

                System.out.println("Waiting for reply...");

                outToServer.writeBytes(sentence + '\n');

                sentence = inFromServer.readLine();

                System.out.println("Message from " + clientSocket.getInetAddress() +
                       ": " + sentence);
            }
		}

		else
		{
			String clientSentence;
			String reply;

			ServerSocket welcomeSocket = new ServerSocket(portNum);
			System.out.println("Ok, you are online and waiting...");

			while(true)
			{
				connectionSocket = welcomeSocket.accept();

				BufferedReader inFromClient =
					new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

				DataOutputStream outToClient =
					new DataOutputStream(connectionSocket.getOutputStream());

				clientSentence = inFromClient.readLine();

				System.out.println("Message from " + connectionSocket.getInetAddress() +
									": " + clientSentence);
				reply = maggie.getResponse(clientSentence);
			    System.out.println("Your Reply: " + reply);
			    

			    outToClient.writeBytes(reply + '\n');
			}


		}



	
    }*/
    }
}


