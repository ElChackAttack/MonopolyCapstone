package Rules;

import Utility.Option;
import Utility.ParameterFetch;
import Players.Player;

/**
 * Created by Lucy on 2018/04/02.
 */
public class GoRules {

    public GoRules() {
    }
    
    /**
     * Calculate minimun salary for a given player to earn
     * (maximum would result in a rich gets richer situation)
     * @param player the player to calculate salary for
     * @return minimum salary to be earned
     */
    public int calculateSalary(Player player) {
        Option option = ParameterFetch.getTaxOption();
        int profitable = (int) (player.calculateNetWorth() * ParameterFetch.getSalaryPercentage());
        int salary = ParameterFetch.getSalary();
        if (option == Option.MAX) {
            return Math.max(profitable, salary);
        } else if (option == Option.MIN) {
            return Math.min(profitable, salary);
        } else if (option == Option.Fix) {
            return salary;
        }
        return profitable;
    }
}
