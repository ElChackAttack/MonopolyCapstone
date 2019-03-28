package Players;


import java.util.Vector;

/**
 * Created by userhp on 02/02/2016.
 */
public class AllPlayers {
    private static AllPlayers ourInstance = new AllPlayers();

    public static AllPlayers getInstance() {
        return ourInstance;
    }
    private static Vector<Player> allPlayers;

    private AllPlayers() {
    }
    public static void init(Vector<Player>  players){
        allPlayers = players;
    }
    public void removePlayer(Player player){
        allPlayers.remove(player);
    }
    public Vector<Player> getAllPlayers(){
        return allPlayers;
    }

}
