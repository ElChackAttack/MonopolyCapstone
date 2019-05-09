package Rules;

import Utility.ParameterFetch;
import Players.Player;

/**
 * Created by Lucy on 2018/04/02.
 */
public class GoRules {
    private static int salary;
    private static double salaryPercentage;
    private static boolean fixedSalaryOption;

    public GoRules() {
        salary = ParameterFetch.getSalary();
        salaryPercentage = ParameterFetch.getSalaryPercentage();
        fixedSalaryOption = getFixedSalaryOption();
    }

    public int getSalary() {
        return calculateSalary();
    }
    
    public int getSalary(Player player) {
        return calculateSalary(player);
    }
    
    private int calculateSalary() {
        return salary;
    }

    private boolean getFixedSalaryOption() {
        if (salaryPercentage != 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Calculate minimun salary for a given player to earn
     * (maximum would result in a rich gets richer situation)
     * @param player the player to calculate salary for
     * @return minimum salary to be earned
     */
    private int calculateSalary(Player player) {
        int profitablePlayerNetWorth = (int) (player.calculateNetWorth() * salaryPercentage);
        if (profitablePlayerNetWorth < salary && !fixedSalaryOption) {
            return profitablePlayerNetWorth;
        }
        return salary;
    }
}
