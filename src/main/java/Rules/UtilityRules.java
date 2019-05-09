package Rules;

import Board.Group;
import Players.Player;

/**
 * Created by Lucy on 2018/04/02.
 */
public class UtilityRules {
    private static int multiplierForOneUtility;
    private static int multiplierForBothUtilities;

    public UtilityRules() {
        multiplierForOneUtility = 4;
        multiplierForBothUtilities = 10;
    }
    
    public int calculateRent(Player owner, Player visitor) {
        int rentOwed = 0;
        if (visitor.getMoveTaken().equals(MoveType.Card)) {
            rentOwed = visitor.rollDice().getSumOfDiceRolls() * multiplierForBothUtilities;
        } else {
            if (owner.ownsSpacesOfGroup(Group.Utility) == 2) {
                rentOwed = visitor.getLastDiceRoll().getSumOfDiceRolls() * multiplierForBothUtilities;
            } else {
                rentOwed = visitor.getLastDiceRoll().getSumOfDiceRolls() * multiplierForOneUtility;
            }
        }
        return rentOwed;
    }
}
