package Cards;

import Board.Space;

/**
 * Created by userhp on 26/01/2016.
 */
public class ChanceCard extends Card {

    public ChanceCard(String name, CardAction action) {
        super.setAction(action);
        super.setName(name);
    }

    public ChanceCard(String name, CardAction action, int feeOrSpaces) {
        super.setAction(action);
        super.setName(name);
        if(action.equals(CardAction.GoBackSpaces)) {
            super.setSpacesToMove(feeOrSpaces);
        }
        else{
            super.setFee(feeOrSpaces);
        }
    }

    public ChanceCard(String name, CardAction action, Space location){
        super.setAction(action);
        super.setName(name);
        super.setLocation(location);

    }
    public ChanceCard(String name, CardAction action, int feePerHouse, int feePerHotel){
        super.setAction(action);
        super.setName(name);
        super.setFeePerHouse(feePerHouse);
        super.setFeePerHotel(feePerHotel);

    }

}

