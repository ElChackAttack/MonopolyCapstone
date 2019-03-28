package Board;

import Players.Player;

/**
 * Object for the jail space
 *
 * Created by marc on 20/11/2015.
 */
public class Jail extends Space {
    private boolean justVisiting = true;


    public Jail(String name, int loc, Group group) {
        super.setGroup(group);
        super.setName(name);
        super.setLocation(loc);
    }


    @Override
    public void onVisit(Player player) {
        //Jail Rules need to be implented here and on the player
    }
}
