package Board;

import Players.Player;

/**
 * Basis of the GoToJail space
 * Created by userhp on 26/01/2016.
 */
public class GoToJail extends Space {


    public GoToJail(String name, int loc, Group group){
        super.setGroup(group);
        super.setLocation(loc);
        super.setName(name);
    }

    @Override
    public void onVisit(Player player) {
        player.goToJail();
    }
}
