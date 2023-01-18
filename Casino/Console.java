import java.util.*;

public class Console implements Runnable {
    private Scanner input;
    private static String line;
    private static String oldLine = "Old Line";

    public Console() {
        input = new Scanner(System.in);
        Thread thread = new Thread(this);
        line = "";
        thread.run();
    }

    public void run() {
        while (true) {
            line = input.nextLine();
        }
    }

    public static String getInput() {
        if(line.equals(oldLine)){
            getInput();
        }
        oldLine = line;
        return line;
    }
}
