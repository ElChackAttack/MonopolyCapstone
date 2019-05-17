package Rules;

import java.util.Vector;
import java.util.logging.Logger;

import Board.Ownable;
import Board.Property;
import Players.AllPlayers;
import Players.Player;

/**
 * Created by Lucy on 2018/04/02.
 */
public class Bank {
    private GoRules goRules;
    private BuildRules buildRules;
    private AuctionRules auctionRules;
    private SellingRules sellingRules;
    private BankruptcyRules bankruptcyRules;

    private static int hotelsInBank;
    private static int housesInBank;

    public Bank() {
        housesInBank = 32;
        hotelsInBank = 12;
        
        goRules = AllRules.getGoRules();
        buildRules = AllRules.getBuildRules();
        auctionRules = AllRules.getAuctionRules();
        sellingRules = AllRules.getSellingRules();
        bankruptcyRules = AllRules.getBankruptcyRules();
    }

    public void payPlayer(Player playerToSend, Player playerToReceive, int amount) {
        if (playerToSend.spendMoney(amount)) {
            playerToReceive.receiveMoney(amount);
        } else {
            if (bankruptcyRules.checkForBankruptcy(playerToSend, amount)) {
                bankruptcyRules.bankruptByPlayer(playerToReceive, playerToSend);
            } else {
                playerToSend.sellItemsToMakeMoney(amount);
                playerToSend.spendMoney(amount);
                playerToReceive.receiveMoney(amount);
            }
        }
    }

    public void passGo(Player player) {
        player.receiveMoney(goRules.calculateSalary(player));
    }

    public boolean buyHouse(Property property,Player player) {
        boolean houseBuilt;
        if (player.equals(property.getOwner()) && buildRules.canBuildHouse(property, player)) {
            if (getHousesInBank() == 0) {
                houseBuilt = false;
            } else {
                if (player.spendMoney(property.getHouseCost())) {
                    housesInBank--;
                    property.addHouse();
                    houseBuilt = true;
                } else {
                    houseBuilt = false;
                }
            }
        }
        else{
            houseBuilt = false;
        }
        return houseBuilt;
    }

    public boolean buyHotel(Property property,Player player) {
        boolean hotelBuilt;
        if (player.equals(property.getOwner()) && buildRules.canBuildHotel(property, player)) {
            if (getHotelsInBank() == 0) {
                hotelBuilt = false;
            } else {
                if (player.spendMoney(property.getHouseCost())) {
                    housesInBank += buildRules.amountOfHousesNeededForHotel();
                    hotelsInBank--;
                    property.addHotel();
                    for(int i =0; i < buildRules.amountOfHousesNeededForHotel();i++) {
                        property.removeHouse();
                    }
                    hotelBuilt = true;
                } else {
                    hotelBuilt = false;
                }
            }
        }
        else{
            hotelBuilt = false;
        }
        return hotelBuilt;
    }

    public void sellHouse(Property property,Player player) {
        if (player.equals(property.getOwner()) && buildRules.canSellHouse(property, player)) {
            player.receiveMoney((int) (property.getHouseCost()*sellingRules.priceReductionForSellingOfHouse()));
            property.removeHouse();
            housesInBank++;
        }
    }

    public void sellHotel(Property property,Player player) {
        if (player.equals(property.getOwner()) && buildRules.canSellHouse(property, player)) {
            player.receiveMoney((int) (property.getHouseCost()*sellingRules.priceReductionForSellingOfHotel()));
            property.removeHotel();
            hotelsInBank++;
            for(int i =0; i < buildRules.amountOfHousesNeededForHotel();i++) {
                if(housesInBank>0) {
                    property.addHouse();
                    housesInBank--;
                }
                else{
                    break;
                }
            }
        }
    }

    public void auctionProperty(Ownable property) {
        Vector<Player> players = AllPlayers.getInstance().getAllPlayers();
        int baseCostOfProperty = property.getCost();
        int startingPriceOfProperty = (int)(baseCostOfProperty * auctionRules.getStartingPriceMultiplier());
        int currentPriceOfProperty = startingPriceOfProperty;
        int askingPriceOfProperty = startingPriceOfProperty;
        int incrementOfAuction = (int)(baseCostOfProperty* auctionRules.getIncrementMultiplier());
        boolean auctionRunning = true;
        Player topBidder = null;
        try {
            while(auctionRunning) {
                auctionRunning = false;
                for(Player player : players) {
                    if(player.wantsToBuyPropertyForPrice(property,askingPriceOfProperty) && !player.equals(topBidder)) {
                        topBidder = player;
                        currentPriceOfProperty= askingPriceOfProperty;
                        askingPriceOfProperty += incrementOfAuction;
                        auctionRunning = true;
                    }
                }
            }
           topBidder.spendMoney(currentPriceOfProperty);
           property.setOwner(topBidder);
           topBidder.addProperty(property);
        }
        catch (NullPointerException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("No one can afford property");
            property.setOwner(null);
        }
    }

    public int getHotelsInBank() {
        return hotelsInBank;
    }

    public int getHousesInBank() {
        return housesInBank;
    }

    public void mortgageProperty(Ownable space, Player player) {
        space.setMortgaged(true);
        player.receiveMoney(space.getMortgagePrice());
    }

    public void unmortgagedProperty(Ownable space, Player player) {
        player.spendMoney((int) (space.getMortgagePrice() + (space.getMortgagePrice() * 0.1)));
        space.setMortgaged(false);
    }

    public void tradeProperty(Ownable spaceToTrade, Player playerToReceive, Player playerToSell, int amountFor) {
        if (playerToReceive.spendMoney(amountFor)) {
            spaceToTrade.setOwner(playerToReceive);
            playerToSell.removeProperty(spaceToTrade);
            playerToReceive.addProperty(spaceToTrade);
        }
    }
}
