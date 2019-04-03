package Rules;

import Board.Tax;
import Players.Player;

/**
 * Created by Lucy on 2018/04/02.
 */
public class TaxRules {
    private static boolean fixedTaxOption;
    private static double incomeTaxPercentage;

    public TaxRules() {
        fixedTaxOption = true;
        incomeTaxPercentage = 0.1;
    }

    public int calculateIncomeTax(Player player){
        Tax location = (Tax) player.getCurrentLocation();
        int tax = location.getFee();
        int taxablePlayerNetWorth = (int) (player.calculateNetWorth() * incomeTaxPercentage);
        if (taxablePlayerNetWorth < tax || !fixedTaxOption) {
            tax = taxablePlayerNetWorth;
        }
        return tax;
    }
}