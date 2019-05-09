package Players;

import java.util.Vector;

/**
 * Created by userhp on 02/02/2016.
 */
public class StartingPlayers {
    private static StartingPlayers ourInstance = new StartingPlayers();

    public static StartingPlayers getInstance() {
        return ourInstance;
    }

    private Vector<Player> allPlayers;

    private StartingPlayers() {
    }

    public void init(Vector<Player> players) {
        allPlayers = players;
    }

    public void removePlayer(Player player) {
        allPlayers.remove(player);
    }

    public final Vector<Player> getAllPlayers() {
        return allPlayers;
    }

    public void addPlayer(Player player) {
        allPlayers.add(player);
    }

    public void replacePlayer(Player oldPlayer, Player newPlayer) {
        int locationOfOldPlayer = allPlayers.indexOf(oldPlayer);
        allPlayers.set(locationOfOldPlayer, newPlayer);
    }
}
