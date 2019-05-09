package Rules;

import Board.Tax;
import Players.Player;
import Utility.ParameterFetch;;

/**
 * Created by Lucy on 2018/04/02.
 */
public class TaxRules {
    private static boolean fixedIncomeTaxOption;
    private static double incomeTaxPercentage;
    private static boolean fixedLuxuryTaxOption;
    private static double luxuryTaxPercentage;

    public TaxRules() {
        luxuryTaxPercentage = 0;
        incomeTaxPercentage = 0.1;
        fixedLuxuryTaxOption = true;
        fixedIncomeTaxOption = false;
    }

    private boolean getFixedTaxOption(double taxPercentage) {
        if (taxPercentage != 0) {
            return false;
        }
        return true;
    }

    /**
     * Calculate tax for a given player
     * @param player the player to calculate tax for
     * @return minimum tax to be paid
     */
    public int calculateTax(Player player, String taxName) {
        String name = null;
        int tax = 0;
        if (taxName == null) {
            Tax location = (Tax) player.getCurrentLocation();
            tax = location.getFee();
            name = location.getName();
        } else {
            tax = ParameterFetch.getTaxFee(taxName);
            name = taxName;
        }
        double taxPercentage = ParameterFetch.getTaxPercentage(name);
        int taxablePlayerNetWorth = (int) (player.calculateNetWorth() * taxPercentage);
        if (taxablePlayerNetWorth < tax && !getFixedTaxOption(taxPercentage)) {
            tax = taxablePlayerNetWorth;
        }
        return tax;
    }
}