package Players;

import Board.*;
import Board.Space;
import Cards.Card;
import Cards.CardAction;
import Cards.Deck;
import Dice.*;
import Rules.*;
import Logger.*;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by marc on 20/11/2015.
 */
public class Player implements Serializable {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String loggingName;
    private Space currentLocation;
    private int money = 0;
    private MoveType moveTaken;   
    private int turnInJail;    
    private Vector<Ownable> ownedSpaces;
    private Dice[] dices;
    private Vector<Card> cards;
    private boolean inJail = false;
    private BoardHelper boardHelper = BoardHelper.getInstance();
    private DiceRoll lastDiceRoll;

    private BankruptcyRules bankruptcyRules = AllRules.getBankruptcyRules();
    private GoRules goRules = AllRules.getGoRules();
    private JailRules jailRules = AllRules.getJailRules();
    private Bank bankRules = AllRules.getBankRules();

    public Player(String name, int initialMoney, Dice[] dices) {
        cards = new Vector<Card>();
        moveTaken = MoveType.DiceRoll;
        money = initialMoney;
        ownedSpaces = new Vector<Ownable>();
        this.dices = dices;
        currentLocation = boardHelper.getSpaceOnBoard("Go");

        loggingName = name;
        //DataLogger.writeToLog(0, this, currentLocation);
    }

    public void onTurn() {
        if (inJail) {
            this.playTurnInJail();
        } else {
            DiceRoll roll = rollDice();
            LOGGER.info(loggingName + " rolled dice of " + roll.getSumOfDiceRolls());
            int rolls = 1;
            while (roll.isReRoll()) {
                LOGGER.info(loggingName + " Got a double roll, has another roll of dice");
                if (rolls >= jailRules.amountOfDoublesToBeSentToJail()) {
                    this.goToJail();
                    turnInJail = 0;
                    break;
                }
                this.moveToLocation(BoardHelper.getInstance().moveToSpace(this, roll.getSumOfDiceRolls()));
                roll = rollDice();
                rolls++;
                LOGGER.info(loggingName + " rolled dice of " + roll.getSumOfDiceRolls());
            }
            if (!inJail) {
                this.moveToLocation(BoardHelper.getInstance().moveToSpace(this, roll.getSumOfDiceRolls()));
                moveTaken = MoveType.DiceRoll;
            }
        }
    }

    //To Be Expanded
    public void betweenTurns() {
        anyPropertiesToUnmortgage();
        askOtherPlayersForPropertiesWanted();
        anyHousesOrHotelsToBuild();
    }

    //Implementing this method may allow for the simulation to run more smoothly.
    private void askOtherPlayersForPropertiesWanted() {
        Group[] ownableGroups = {Group.Station, Group.Utility,
                Group.Brown, Group.DarkBlue, Group.Green, Group.LightBlue,
                Group.Orange, Group.Red, Group.Violet, Group.Yellow};
        for (Group g : ownableGroups) {
            Stack<Ownable> ownedPropertiesOfGroup = getOwnedPropertiesOfGroup(g);
            Vector<Space> allSpacesOfGroup = boardHelper.getAllSpacesOfGroup(g);
            if ((allSpacesOfGroup.size() - ownedPropertiesOfGroup.size()) == 1) {
                allSpacesOfGroup.removeAll(ownedPropertiesOfGroup);
                Ownable space = (Ownable) allSpacesOfGroup.get(0);
                LOGGER.info(loggingName + " wants space " + space.getName());

                if (space.getOwner() != (null)) {
                    if (space.getOwner().askToBuyPropertyFor(space, space.getCost() * 10)) {
                        bankRules.tradeProperty(space, this, space.getOwner(), space.getCost() * 10);
                    }
                }
            }
        }
        // Need to work out a suitable way of describing what properties are wanted and and algorithm for selling them.
        // This may take a while 
    }

    public boolean askToBuyPropertyFor(Ownable space, int askingPrice) {
        double rand = new Random().nextDouble();
        boolean tradeProperty = false;
        if (rand > 0.25) {
            tradeProperty = true;
        }
        LOGGER.info(loggingName + " wants to trade space " + space.getName() + ": " + tradeProperty);
        return tradeProperty;
    }

    private void anyPropertiesToUnmortgage() {
        List<Ownable> properties = (List) ownedSpaces.clone();
        Collections.sort(properties, new OwnableComparator());
        for (Ownable space : properties) {
            if (space.isMortgaged() && wantToUnmortgageProperty(space)) {
                bankRules.unmortgageProperty(space, this);
            }
        }
    }

    private boolean wantToUnmortgageProperty(Ownable space) {
        boolean wantToUnmortgage = false;
        if (money * 0.3 > (space.getMortgagePrice() + (space.getMortgagePrice() * 0.1))) {
            wantToUnmortgage = true;
        }
        return wantToUnmortgage;
    }

    private void anyHousesOrHotelsToBuild() {
        List<Ownable> properties = (List) ownedSpaces.clone();
        Collections.sort(properties, new OwnableComparator());
        Collections.reverse(properties);
        boolean buildingProperties = true;
        while (buildingProperties && properties.size() > 0) {
            Vector<Ownable> removeFromSearch = new Vector<Ownable>();
            for (Ownable space : properties) {
                if (space instanceof Property) {
                    if (money * 0.5 < ((Property) space).getHouseCost()) {
                        buildingProperties = false;
                        break;
                    }
                    if (AllRules.getBuildRules().canBuildHotel((Property) space, this)) {
                        if (!bankRules.buyHotel((Property) space, this)) {
                            removeFromSearch.add(space);
                        }

                    } else if (AllRules.getBuildRules().canBuildHouse((Property) space, this)) {
                        if (!bankRules.buyHouse((Property) space, this)) {
                            removeFromSearch.add(space);
                        }
                    } else {
                        removeFromSearch.add(space);
                    }
                } else {
                    removeFromSearch.add(space);
                }
            }
            properties.removeAll(removeFromSearch);
        }
    }

    private void playTurnInJail() {
        turnInJail++;
        DataLogger.writeToLog(TurnCounter.getTurn(), this, currentLocation);
        if (cards.size() > 0) {
            for (Card card : cards) {
                if (card.getAction().equals(CardAction.GetOutOfJail)) {
                    inJail = false;
                    turnInJail = 0;
                    cards.remove(card);
                    Deck.getInstance().addCard(card);
                    break;
                }
            }
        } else if (turnInJail > jailRules.amountOfRollsToGetOutOfJail()) {
            inJail = false;
            turnInJail = 0;
        } else if (this.wantsToPayJailFine()) {
            spendMoney(jailRules.feeToPayToGetOutOfJail());
            inJail = false;
            turnInJail = 0;
        } else {
            DiceRoll roll = rollDice();

            if (roll.isReRoll()) {
                moveToLocation(BoardHelper.getInstance().moveToSpace(this, roll.getSumOfDiceRolls()));
                inJail = false;
                turnInJail = 0;
            }
        }
    }

    private boolean wantsToPayJailFine() {
        boolean payFine = false;
        if (money > AllRules.getJailRules().feeToPayToGetOutOfJail()) {
            payFine = true;
        }
        return payFine;
    }

    public DiceRoll rollDice(){        
        Vector<Integer> diceResults = new Vector<Integer>();
        for (Dice d : dices) {
            diceResults.add(d.rollTheDice());
        }
        int sumOfDiceRolls = 0;
        int firstResult = diceResults.get(0);
        boolean allTheSame = true;
        for (int result: diceResults) {
            sumOfDiceRolls += result;
            if (result!=firstResult) {
                allTheSame=false;                
            }
        }        
        DiceRoll roll = new DiceRoll(sumOfDiceRolls,allTheSame);
        lastDiceRoll = roll;
        return roll;
    }

    public Space getCurrentLocation() {
        return currentLocation;
    }

   public void receiveMoney(int amount){
       money += amount;
   }

    public boolean spendMoney(int amount){
        boolean enoughMoney;
        if(money-amount<0){
            enoughMoney= false;
        }
        else{
            money-=amount;
            enoughMoney=true;
            LOGGER.info(loggingName + " spent " + amount + "\nThey now have: " + money);
        }
        return enoughMoney;
    }

    public void moveToLocation(Space location) {
        DataLogger.writeToLog(TurnCounter.getTurn(), this, location);
        LOGGER.info(loggingName + " moved from location " + currentLocation.getName() + " to location " + location.getName());
        currentLocation = location;
        location.onVisit(this);
    }



    public void keepCard(Card card) {
        cards.add(card);
    }

    public void goToJail() {
        moveTaken = MoveType.GoToJail;
        this.inJail = true;
        currentLocation = boardHelper.getSpaceOnBoard("Jail");
        DataLogger.writeToLog(TurnCounter.getTurn(), this, currentLocation);
    }

    public void giveMoneyToBank(int feeToPlayer) {
        if(!this.spendMoney(feeToPlayer)){
            if (bankruptcyRules.checkForBankruptcy(this, feeToPlayer)) {
                bankruptcyRules.bankruptByBank(this);
            }
            else {
                this.sellItemsToMakeMoney(feeToPlayer);
                this.spendMoney(feeToPlayer);
            }
        }
    }

    public void sellItemsToMakeMoney(int moneyNeeded) {
        //For now best way to do this is to sell the minimum amount of houses and mortgage as little as possible.
        List<Ownable> sortedProperties = (List) ownedSpaces.clone();
        Collections.sort(sortedProperties, new OwnableComparator());
        while (money < moneyNeeded) {
            for (Ownable space : ownedSpaces) {
                if (space instanceof Property) {
                    if (((Property) space).getHotels() > 0) {
                        bankRules.sellHotel((Property) space, this);
                    } else if (((Property) space).getHouses() > 0) {
                        bankRules.sellHouse((Property) space, this);
                    } else {
                        bankRules.mortgageProperty(space, this);
                    }
                } else {
                    bankRules.mortgageProperty(space, this);
                }
                if (money > moneyNeeded) {
                    break;
                }
            }
        }
    }

    public void receiveMoneyFromPlayers(int feeToPlayer) {
        for(Player player : AllPlayers.getInstance().getAllPlayers()){
            if(!player.spendMoney(feeToPlayer)){
                if (bankruptcyRules.checkForBankruptcy(player, feeToPlayer)) {
                    bankruptcyRules.bankruptByPlayer(this, player);
                }
                else{
                    player.sellItemsToMakeMoney(feeToPlayer);
                    player.spendMoney(feeToPlayer);
                    this.receiveMoney(feeToPlayer);
                }
            }
            else{
                this.receiveMoney(feeToPlayer);
            }
        }
    }

    public int calculateHotelsOwned() {
        int numberOfHotels = 0;
        for(Ownable property: ownedSpaces){
            if(property instanceof Property){
                numberOfHotels += ((Property) property).getHotels();
            }
        }
        return numberOfHotels;
    }

    public int calculateHousesOwned() {
        int numberOfHouses = 0;
        for(Ownable property: ownedSpaces){
            if(property instanceof Property){
                numberOfHouses += ((Property) property).getHouses();
            }
        }
        return numberOfHouses;
    }

    public void payOtherPlayers(int feeToPlayer) {
        for(Player player : AllPlayers.getInstance().getAllPlayers()){
            if(!this.spendMoney(feeToPlayer)){
                if (bankruptcyRules.checkForBankruptcy(this, feeToPlayer)) {
                    bankruptcyRules.bankruptByPlayer(player, this);
                }
                else{
                    this.sellItemsToMakeMoney(feeToPlayer);
                    this.spendMoney(feeToPlayer);
                    player.receiveMoney(feeToPlayer);
                }
            }
            else{
                player.receiveMoney(feeToPlayer);
            }
        }
    }

    public boolean wantsToBuyPropertyForPrice(Space property, int askingPriceOfProperty) {
        boolean willingToBuyProperty = false;
        int amountOfSpacesOwnedOfGroup = ownsSpacesOfGroup(property.getGroup());
        int amountOfSpacesOnBoardOfGroup = boardHelper.amountOfSpacesInGroup(property.getGroup());
        int amountOfMoneyWillingToSpend = 0;

        //VERY BASIC HEURISTIC
        switch (amountOfSpacesOnBoardOfGroup - amountOfSpacesOwnedOfGroup) {
            case 1:
                amountOfMoneyWillingToSpend = (int) (money * 0.7);
                break;
            case 2:
                amountOfMoneyWillingToSpend = (int) (money * 0.6);
                break;
            default:
                amountOfMoneyWillingToSpend = (int) (money * 0.5);
                break;
        }
        if (amountOfMoneyWillingToSpend > askingPriceOfProperty) {
            willingToBuyProperty = true;
        }
        return willingToBuyProperty;
    }

    public void addProperty(Ownable space) {
        LOGGER.info(loggingName + " now owns " + space.getName());
        ownedSpaces.add(space);
    }

    public void removeProperty(Ownable space) {
        LOGGER.info(loggingName + " no longer owns " + space.getName());
        ownedSpaces.remove(space);
    }

    public MoveType getMoveTaken() {
        return moveTaken;
    }

    public int ownsSpacesOfGroup(Group group){
        int amountOfSpacesOwned =0;
        for (Space space : ownedSpaces){
            if(space.getGroup().equals(group)){
                amountOfSpacesOwned++;
            }
        }
        return amountOfSpacesOwned;
    }
    

    public int calculateNetWorth() {
        // Todo: test method
        int netWorth = money;
        for(Ownable space : ownedSpaces){
            netWorth += space.getCost();
            if(space instanceof Property){
                netWorth += ((Property) space).getHotels()* ((Property) space).getHouseCost();
                netWorth += ((Property) space).getHouses()* ((Property) space).getHouseCost();
            }
        }
        return netWorth;
    }

    public int calculateSaleableItems() {
        // Todo: test method
        int saleableMoney = money;
        for(Ownable space : ownedSpaces){
            saleableMoney += space.getMortgagePrice();
            if(space instanceof Property){
                saleableMoney += ((Property) space).getHotels() * ((Property) space).getHouseCost() * AllRules.getSellingRules().priceReductionForSellingOfHotel();
                saleableMoney += ((Property) space).getHouses() * ((Property) space).getHouseCost() * AllRules.getSellingRules().priceReductionForSellingOfHouse();
            }
        }
        return saleableMoney;
    }

    public int getMoney(){
        return money;
    }
    
    public Vector<Ownable> getOwnedSpaces(){
        return ownedSpaces;
    }

    public DiceRoll getLastDiceRoll() {
        return lastDiceRoll;
    }

    public Stack<Ownable> getOwnedPropertiesOfGroup(Group group) {
        Stack<Ownable> propertyStack = new Stack<Ownable>();
        for (Ownable ownable : ownedSpaces) {
            if (ownable.getGroup().equals(group)) {
                propertyStack.add(ownable);
            }
        }
        return propertyStack;
    }

    public String getName() {
        return loggingName;
    }

    public Dice[] getAllDice() {
        return dices;
    }

    public String getLuaFileLocation() {
        return "";
    }

    public void setCurrentLocation(Space location) {
        currentLocation = location;
    }
}
