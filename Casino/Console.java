import java.util.*;

public class Console implements Runnable {
    private Scanner input;
    private static String line;

    public Console() {
        input = new Scanner(System.in);
        Thread thread = new Thread(this);
        thread.run();
    }

    public void run() {
        while (true) {
            line = input.nextLine();
        }
    }

    public static String getInput() {
        return line;
    }
}
