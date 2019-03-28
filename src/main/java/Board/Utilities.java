package Board;

import Players.Player;
import Rules.AllRules;

/**
 * Created by userhp on 26/01/2016.
 */
public class Utilities extends Ownable {

    public Utilities(String name, int loc, Group group, int cost,int mtg){
        super.setGroup(group);
        super.setLocation(loc);
        super.setName(name);
        super.setCost(cost);
        super.setMortgagePrice(mtg);
        super.setMortgaged(false);
    }

    @Override
    public void onVisit(Player player) {
        if(super.getOwner() == null){
            if(player.wantsToBuyPropertyForPrice(this, super.getCost())){
                player.spendMoney(super.getCost());
                player.addProperty(this);
                super.setOwner(player);
            }
            else{
                AllRules.getBankRules().auctionProperty(this);
            }
        }
        else{
            if(!getOwner().equals(player)){
                int rentOwed = AllRules.getUtilityRules().calculateRent(getOwner(), player);
                AllRules.getBankRules().payPlayer(player, super.getOwner(), rentOwed);
                moneyEarned+=rentOwed;
            }
        }

    }


}
