public class PlayerConnectionCheck implements Runnable {
    private Thread thread;

    public PlayerConnectionCheck() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        System.out.println("Remove");
        checkConnectedPlayers();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
    }

    private void checkConnectedPlayers() {
        for (int player = 0; player < CasinoV2.players.size(); player++) {
            try {
                CasinoV2.players.get(player).getInputStream().read();
            } catch (Exception e) {
                CasinoV2.players.remove(player);
                player--;
            }
        }
    }
}
