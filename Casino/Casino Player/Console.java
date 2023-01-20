import java.util.*;

public class Console {
    private static Scanner input;
    public static String line;

    public Console() {
        input = new Scanner(System.in);
        line = "";
    }

    public static String getInput() {
        line = input.nextLine();
        return line;
    }

    public static int getIntInput() {
        try {
            return Integer.parseInt(getInput());
        } catch (Exception e) {
            System.out.println("Not a valid input. Enter an integer value");
            getIntInput();
        }
        return 0;
    }
}