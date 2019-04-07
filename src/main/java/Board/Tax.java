package Board;

import Players.Player;
import Rules.AllRules;
import Board.ParameterFetch;

/**
 * Contains the information for tax spaces.
 *
 * Created by Lucy on 2018/04/05.
 */
public class Tax extends Space {
    private static int fee;

    public Tax(String name, int location, int fee){
        super.setGroup(Group.Tax);
        super.setName(name);
        super.setLocation(location);
        this.fee = fee;
    }

    public int getFee(){
        return ParameterFetch.getTaxFee(super.getName());
    }
    

    @Override
    /**
     * Default: fixed luxury tax, non-fixed income tax
     */
    public void onVisit(Player player) {
        if ((super.getName().equalsIgnoreCase("Income Tax") && ParameterFetch.getIncomeTaxPerNumOfTurn() == 0) || (super.getName().equalsIgnoreCase("Luxury Tax") && ParameterFetch.getLuxuryTaxPerNumOfTurn() == 0)) {
            player.spendMoney(AllRules.getTaxRules().calculateTax(player, null));
        }
    }
}
