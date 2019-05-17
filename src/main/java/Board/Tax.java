package Board;

import Players.Player;
import Rules.AllRules;
import Utility.ParameterFetch;

/**
 * Contains the information for tax spaces.
 *
 * Created by Lucy on 2018/04/05.
 */
public class Tax extends Space {
    // private static int fee;

    public Tax(String name, int location, int fee){
        super.setGroup(Group.Tax);
        super.setName(name);
        super.setLocation(location);
        // this.fee = fee;
    }

    @Override
    /**
     * Default: fixed luxury tax, non-fixed income tax
     */
    public void onVisit(Player player) {
        if (ParameterFetch.getTaxPerNumOfTurn() == 0) {
            player.spendMoney(AllRules.getTaxRules().calculateTax(player));
        }
    }
}
