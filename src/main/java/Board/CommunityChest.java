package Board;

import Cards.Card;
import Cards.Deck;
import Players.Player;

/**
 * Basis of the Community Chest space
 * Created by userhp on 26/01/2016.
 */
public class CommunityChest extends Space {


    public CommunityChest(String name,int loc, Group group){
        super.setGroup(group);
        super.setLocation(loc);
        super.setName(name);
    }

    @Override
    public void onVisit(Player player) {
        Deck deck = Deck.getInstance();
        Card c =deck.drawCommunityChestCard();
        c.onDraw(player);
    }
}
