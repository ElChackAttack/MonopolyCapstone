package Board;

import Players.Player;

/**
 * Basis of the free parking space
 * Created by userhp on 26/01/2016.
 */
public class FreeParking extends Space {


    public FreeParking(String name, int loc, Group group){
        super.setGroup(group);
        super.setLocation(loc);
        super.setName(name);
    }

    @Override
    public void onVisit(Player player) {
        //Currently nothing however may want rules
    }
}
