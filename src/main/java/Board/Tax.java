package Board;

import Players.Player;
import Rules.AllRules;
import Rules.TaxRules;

/**
 * Contains the information for tax spaces
 *
 * Created by marc on 20/11/2015.
 */
public class Tax extends Space {
    private int fee;

    public Tax(String name, int location, int fee){
        super.setGroup(Group.Tax);
        super.setName(name);
        super.setLocation(location);
        this.fee = fee;
    }
    public int getFee(){
        return fee;
    }



    @Override
    public void onVisit(Player player) {
        if(super.getName().equalsIgnoreCase("Income Tax")){
            player.spendMoney(AllRules.getTaxRules().calculateIncomeTax(player));
        }
        else{
            player.spendMoney(fee);
        }
    }
}
