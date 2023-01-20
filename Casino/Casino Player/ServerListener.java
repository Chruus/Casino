public class ServerListener implements Runnable {

    private static String line;

    public ServerListener() {
        Thread thread = new Thread(this);
        thread.run();
        line = "";
    }

    public void run() {
        while (true) {
            try {
                line = CasinoPlayer.getDataInputStream().readUTF();
            } catch (Exception e) {
            }
        }
    }

    public static String getInput() {
        return line;
    }

}
