package Rules;

import Board.Ownable;
import Board.Property;
import Players.AllPlayers;
import Players.Player;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import java.util.logging.*;
import java.util.Vector;

/**
 * Created by userhp on 28/01/2016.
 */
public class Bank {
    private GoRules goRules;
    private BuildRules buildRules;
    private AuctionRules auctionRules;
    private SellingRules sellingRules;
    private static int hotelsInBank;
    private static int housesInBank;

    private BankruptcyRules bankruptcyRules;


    private LuaValue _G;

    public Bank(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
        LuaValue getStartingHousesInBankMethod = _G.get("getStartingHousesInBank");
        LuaValue getStartingHotelsInBankMethod = _G.get("getStartingHotelsInBank");
        housesInBank = getStartingHousesInBankMethod.call().toint();
        hotelsInBank = getStartingHotelsInBankMethod.call().toint();
        goRules = AllRules.getGoRules();
        buildRules = AllRules.getBuildRules();
        auctionRules = AllRules.getAuctionRules();
        sellingRules = AllRules.getSellingRules();
        bankruptcyRules = AllRules.getBankruptcyRules();
    }


    public void payPlayer(Player playerToSend, Player playerToReceive, int amount) {
        LuaValue luaPlayerToSend = CoerceJavaToLua.coerce(playerToSend);
        LuaValue luaPlayerToReceive = CoerceJavaToLua.coerce(playerToReceive);
        LuaValue luaPaymentAmount = CoerceJavaToLua.coerce(amount);
        LuaValue luaBankruptcyRules = CoerceJavaToLua.coerce(bankruptcyRules);

        LuaValue[] luaMethodArgs = {luaPlayerToSend, luaPlayerToReceive, luaPaymentAmount, luaBankruptcyRules};
        LuaValue luaPayPlayerMethod = _G.get("payPlayer");
        luaPayPlayerMethod.invoke(luaMethodArgs);

    }
    public  void passGo(Player player){
        player.receiveMoney(goRules.getSalary());
    }

    public  boolean buyHouse(Property property,Player player) {
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

    public  boolean buyHotel(Property property,Player player) {
        boolean hotelBuilt;
        if (player.equals(property.getOwner()) && buildRules.canBuildHotel(property, player)) {
            if (getHotelsInBank() == 0) {
                hotelBuilt = false;
            } else {
                if (player.spendMoney(property.getHouseCost())) {

                    housesInBank += buildRules.amountOfHousesNeededForHotel();
                    hotelsInBank--;
                    property.addHotel();
                    for(int i =0; i < buildRules.amountOfHousesNeededForHotel();i++){
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
    public  void sellHouse(Property property,Player player){
        if (player.equals(property.getOwner()) && buildRules.canSellHouse(property, player)) {

            player.receiveMoney((int) (property.getHouseCost()*sellingRules.priceReductionForSellingOfHouse()));
            property.removeHouse();
            housesInBank++;
        }
    }
    public  void sellHotel(Property property,Player player){
        if (player.equals(property.getOwner()) && buildRules.canSellHouse(property, player)) {

            player.receiveMoney((int) (property.getHouseCost()*sellingRules.priceReductionForSellingOfHotel()));
            property.removeHotel();
            hotelsInBank++;
            for(int i =0; i < buildRules.amountOfHousesNeededForHotel();i++){
                if(housesInBank>0){
                    property.addHouse();
                    housesInBank--;
                }
                else{
                    break;
                }

            }

        }
    }
    public  void auctionProperty(Ownable property){
        Vector<Player> players = AllPlayers.getInstance().getAllPlayers();
        int baseCostOfProperty = property.getCost();
        int startingPriceOfProperty = (int)(baseCostOfProperty * auctionRules.getStartingPriceMultiplier());
        int currentPriceOfProperty = startingPriceOfProperty;
        int askingPriceOfProperty = startingPriceOfProperty;
        int incrementOfAuction = (int)(baseCostOfProperty* auctionRules.getIncrementMultiplier());
        boolean auctionRunning = true;
        Player topBidder = null;
        try{
            while(auctionRunning){

                auctionRunning = false;
                for(Player player : players){
                    if(player.wantsToBuyPropertyForPrice(property,askingPriceOfProperty) && !player.equals(topBidder)){
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
        catch (NullPointerException e){
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("No one can afford property");
            property.setOwner(null);
        }
    }


    public  int getHotelsInBank() {
        return hotelsInBank;
    }


    public  int getHousesInBank() {
        return housesInBank;
    }

    public void mortgageProperty(Ownable space, Player player) {
        space.setMortgaged(true);
        player.gainMoney(space.getMortgagePrice());
    }

    public void unmortgageProperty(Ownable space, Player player) {
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
