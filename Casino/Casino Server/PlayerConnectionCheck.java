public class PlayerConnectionCheck implements Runnable {
    private Thread thread;

    public PlayerConnectionCheck() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        checkConnectedPlayers();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
    }

    private void checkConnectedPlayers() {
        for (int player = 0; player < Casino.players.size(); player++) {
            try {
                Casino.players.get(player).getInputStream().read();
            } catch (Exception e) {
                Casino.players.remove(player);
                player--;
            }
        }
    }
}