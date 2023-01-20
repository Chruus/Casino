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
        for (int player = 0; player < OldCasino.players.size(); player++) {
            try {
                OldCasino.players.get(player).getInputStream().read();
            } catch (Exception e) {
                OldCasino.players.remove(player);
                player--;
            }
        }
    }
}
