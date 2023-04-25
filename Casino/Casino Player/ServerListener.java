public class ServerListener implements Runnable {

    private static String line;

    public ServerListener() {
        line = "";
        System.out.println(4);
        new Thread(this).start();
    }

    public static String getInput() {
        return line;
    }

    @Override
    public void run() {
        System.out.println(5);
        while (true) {
            System.out.println(6);
            try {
                System.out.println(7);
                line = CasinoPlayer.getDataInputStream().readUTF();
                System.out.println(line + "A");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
