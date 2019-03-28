package Players;

import java.util.Comparator;

/**
 * Created by userhp on 18/03/2016.
 */
public class OrderStartingPlayers implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        int o1Dice = o1.rollDice().getSumOfDiceRolls();
        int o2Dice = o2.rollDice().getSumOfDiceRolls();
        int result = 0;
        if (o1Dice > o2Dice) {
            result = 1;
        } else if (o1Dice < o2Dice) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }
}
