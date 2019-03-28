package Board;

import Cards.Card;
import Cards.Deck;
import Players.Player;

/**
 * Basis of the ChanceCard space
 * Created by userhp on 26/01/2016.
 */
public class Chance extends Space {


    public Chance(String name, int loc, Group group){
        super.setGroup(group);
        super.setLocation(loc);
        super.setName(name);
    }

    @Override
    public void onVisit(Player player) {
        Deck deck = Deck.getInstance();
        Card c =deck.drawChanceCard();
        c.onDraw(player);
    }
}
