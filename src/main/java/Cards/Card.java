package Cards;

import Board.BoardHelper;
import Board.Space;
import Players.Player;

import java.util.logging.Logger;

/**
 *
 * Created by userhp on 26/01/2016.
 */
public abstract class Card {
    private String name;
    private CardAction action;
    private int feeToPlayer;
    private int spacesToMove;
    private int feePerHouse;
    private int feePerHotel;
    private Space location;


    protected void setAction(CardAction action) {
        this.action = action;
    }

    protected void setFee(int fee) {
        feeToPlayer = fee;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setLocation(Space location) {
        this.location = location;
    }

    protected void setFeePerHouse(int feePerHouse) {
        this.feePerHouse = feePerHouse;
    }

    protected void setFeePerHotel(int feePerHotel) {
        this.feePerHotel = feePerHotel;
    }

    protected void setSpacesToMove(int spacesToMove) {
        this.spacesToMove = spacesToMove;
    }

    public CardAction getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public int getFeeToPlayer() {
        return feeToPlayer;
    }

    public int getSpacesToMove() {
        return spacesToMove;
    }

    public int getFeePerHouse() {
        return feePerHouse;
    }

    public int getFeePerHotel() {
        return feePerHotel;
    }

    public Space getLocation() {
        return location;
    }
    public void onDraw(Player player){
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(player.getName() + " Picked up card " + name + " " + action);
        Deck deck = Deck.getInstance();
        BoardHelper boardHelper = BoardHelper.getInstance();
        Space currentLocation = player.getCurrentLocation();
        Space newLocation = null;
        switch (action){

            case AdvanceToLocation:
                player.moveToLocation(location);
                deck.addCard(this);
                break;
            case CollectMoneyFromBank:
                player.receiveMoney(feeToPlayer);
                deck.addCard(this);
                break;
            case GetOutOfJail:
                player.keepCard(this);
                break;
            case GoToJail:
                player.goToJail();
                deck.addCard(this);
                break;
            case PayBank:
                player.giveMoneyToBank(feeToPlayer);
                deck.addCard(this);
                break;
            case CollectFromPlayers:
                player.receiveMoneyFromPlayers(feeToPlayer);
                deck.addCard(this);
                break;
            case PayBankDependingOnHousesAndHotelsOwned:
                int houses = player.calculateHousesOwned();
                int hotels = player.calculateHotelsOwned();
                int payment = (houses*feePerHouse)+(hotels*feePerHotel);
                player.giveMoneyToBank(payment);
                deck.addCard(this);
                break;
            case GoBackSpaces:
                newLocation = boardHelper.moveToSpace(player, -spacesToMove);
                player.moveToLocation(newLocation);
                deck.addCard(this);
                break;
            case AdvanceToNearestUtility:
                newLocation = boardHelper.moveToNearestUtility(currentLocation);
                player.moveToLocation(newLocation);
                deck.addCard(this);
                break;
            case AdvanceToNearestStation:
                newLocation = boardHelper.moveToNearestStation(currentLocation);
                player.moveToLocation(newLocation);
                deck.addCard(this);
                break;
            case PayPlayers:
                player.payOtherPlayers(feeToPlayer);
                deck.addCard(this);
                break;
        }
    }



}
