package Rules;

import Players.Player;
import Utility.Option;
import Utility.ParameterFetch;;

/**
 * Created by Lucy on 2018/04/02.
 */
public class TaxRules {

    public TaxRules() {
    }

    /**
     * Calculate tax for a given player
     * @param player the player to calculate tax for
     * @return minimum tax to be paid
     */
    public int calculateTax(Player player) {
        Option option = ParameterFetch.getTaxOption();
        int taxable = (int) (player.calculateNetWorth() *  ParameterFetch.getTaxPercentage());
        int tax = ParameterFetch.getTax();
        if (option == Option.MAX) {
            return Math.max(taxable, tax);
        } else if (option == Option.MIN) {
            return Math.min(taxable, tax);
        } else if (option == Option.Fix) {
            return tax;
        }
        return taxable;
    }
}