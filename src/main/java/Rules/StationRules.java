package Rules;

import Board.Group;
import Players.Player;

/**
 * Created by Lucy on 2018/04/02.
 */
public class StationRules {
    private static int oneStationRent;
    private static int twoStationRent;
    private static int threeStationRent;
    private static int fourStationRent;

    public StationRules() {
        oneStationRent = 25;
        twoStationRent = 50;
        threeStationRent = 100;
        fourStationRent = 200;
    }
    public int calculateRent(Player owner, Player visitor) {
        int rentOwed = 0;
        int stationsOwned = owner.ownsSpacesOfGroup(Group.Station);
        if (stationsOwned == 4) {
            rentOwed = fourStationRent;
        } else if (stationsOwned == 3) {
            rentOwed = threeStationRent;
        } else if (stationsOwned == 2) {
            rentOwed = twoStationRent;
        } else {
            rentOwed = oneStationRent;
        }
        if (visitor.getMoveTaken().equals(MoveType.Card)) {
            rentOwed = rentOwed * 2;
        }
        return rentOwed;
    }
}
