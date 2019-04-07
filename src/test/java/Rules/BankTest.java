package Rules;

import Board.Property;
import Dice.Dice;
import Players.AllPlayers;
import Players.Player;
import Rules.RulesTest.TestBank;
import junit.framework.TestCase;
import org.mockito.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;

import static org.mockito.Mockito.*;
/**
 * Created by userhp on 29/01/2016.
 */
public class BankTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        AuctionRules auctionRules = Mockito.mock(AuctionRules.class);
        when(auctionRules.getStartingPriceMultiplier()).thenReturn(0.1);
        when(auctionRules.getIncrementMultiplier()).thenReturn(0.05);
        AllRules.setAuctionRules(auctionRules);

        BuildRules buildRules = Mockito.mock(BuildRules.class);
        when(buildRules.canSellHouse(any(Property.class), any(Player.class))).thenReturn(true);
        when(buildRules.canBuildHotel(any(Property.class), any(Player.class))).thenReturn(true);
        when(buildRules.canBuildHouse(any(Property.class), any(Player.class))).thenReturn(true);
        when(buildRules.amountOfHousesNeededForHotel()).thenReturn(4);
        AllRules.setBuildRules(buildRules);

        SellingRules sellingRules = Mockito.mock(SellingRules.class);
        when(sellingRules.priceReductionForSellingOfHouse()).thenReturn(0.5);
        when(sellingRules.priceReductionForSellingOfHotel()).thenReturn(0.5);
        AllRules.setSellingRules(sellingRules);

        GoRules goRules = Mockito.mock(GoRules.class);
        when(goRules.getSalary()).thenReturn(200);

        //Should Mock This class
        AllRules.setBankruptcyRules(new BankruptcyRules());

    }

    public void testAuctionProperty() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getCost()).thenReturn(500);
        AuctionRules rules = Mockito.mock(AuctionRules.class);
        when(rules.getStartingPriceMultiplier()).thenReturn(0.1);
        when(rules.getIncrementMultiplier()).thenReturn(0.05);
        Bank bank = new Bank();
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);
        Player player3 = Mockito.mock(Player.class);
        Player player4 = Mockito.mock(Player.class);

        when(player1.wantsToBuyPropertyForPrice(Matchers.eq(property),anyInt())).thenReturn(true).thenReturn(true).thenReturn(false);
        when(player2.wantsToBuyPropertyForPrice(Matchers.eq(property),anyInt())).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(player3.wantsToBuyPropertyForPrice(Matchers.eq(property),anyInt())).thenReturn(true).thenReturn(false);
        when(player4.wantsToBuyPropertyForPrice(Matchers.eq(property),anyInt())).thenReturn(true);

        Player[] p = {player1,player2,player3,player4};
        Vector<Player> players = new Vector<Player>(Arrays.asList(p));
        AllPlayers.init(players);
        bank.auctionProperty(property);

        verify(player4,times(1)).addProperty(property);
        verify(property,times(1)).setOwner(player4);
        verify(player1,never()).addProperty(property);
        verify(player2,never()).addProperty(property);
        verify(player3,never()).addProperty(property);
    }


    public void testBuyingHouse() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getHouseCost()).thenReturn(50);
        BuildRules rules = Mockito.mock(BuildRules.class);
        Player player = Mockito.mock(Player.class);
        when(property.getOwner()).thenReturn(player);
        when(player.spendMoney(50)).thenReturn(true);
        when(rules.canBuildHouse(property,player)).thenReturn(true);
        Bank bank = new Bank();
        assertTrue(bank.buyHouse(property,player));
        verify(player, times(1)).spendMoney(50);
        verify(property, times(1)).addHouse();

    }
    public void testBuyingAHouseWithNoHousesInBank() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getHouseCost()).thenReturn(50);
        BuildRules rules = Mockito.mock(BuildRules.class);
        Player player = Mockito.mock(Player.class);
        when(property.getOwner()).thenReturn(player);
        when(player.spendMoney(50)).thenReturn(true);
        when(rules.canBuildHouse(property,player)).thenReturn(true);
        TestBank bank = new TestBank(Paths.get("").toAbsolutePath().toString() +
                "/src/main/LuaFiles/TestingLuaFiles/BankTestWithNoHousesOrHotels.lua");
        assertFalse(bank.buyHouse(property, player));
        verify(player, never()).spendMoney(50);
        verify(property, never()).addHouse();

    }
    public void testBuyingAHotel() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getHouseCost()).thenReturn(50);
        BuildRules rules = Mockito.mock(BuildRules.class);
        Player player = Mockito.mock(Player.class);
        when(property.getOwner()).thenReturn(player);
        when(player.spendMoney(50)).thenReturn(true);
        when(rules.canBuildHotel(property,player)).thenReturn(true);
        when(rules.amountOfHousesNeededForHotel()).thenReturn(4);
        Bank bank = new Bank();
        assertTrue(bank.buyHotel(property,player));
        verify(player, times(1)).spendMoney(50);
        verify(property, times(1)).addHotel();
        assertEquals(36, bank.getHousesInBank());
    }
    public void testBuyingAHotelWhenNoHotelsInBank() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getHouseCost()).thenReturn(50);
        BuildRules rules = Mockito.mock(BuildRules.class);
        Player player = Mockito.mock(Player.class);
        when(property.getOwner()).thenReturn(player);
        when(player.spendMoney(50)).thenReturn(true);
        when(rules.canBuildHotel(property,player)).thenReturn(true);
        when(rules.amountOfHousesNeededForHotel()).thenReturn(4);
        TestBank bank = new TestBank(Paths.get("").toAbsolutePath().toString() +
                "/src/main/LuaFiles/TestingLuaFiles/BankTestWithNoHousesOrHotels.lua");
        assertFalse(bank.buyHotel(property,player));
        verify(player, never()).spendMoney(50);
        verify(property, never()).addHotel();
        assertEquals(bank.getHousesInBank(),0);
    }

    public void testSellingAHouse() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getHouseCost()).thenReturn(50);
        BuildRules rules = Mockito.mock(BuildRules.class);
        SellingRules sellingRules = Mockito.mock(SellingRules.class);
        when(sellingRules.priceReductionForSellingOfHouse()).thenReturn(0.5);
        Player player = Mockito.mock(Player.class);
        when(property.getOwner()).thenReturn(player);
        when(rules.canSellHouse(property,player)).thenReturn(true);
        Bank bank = new Bank();
        bank.sellHouse(property,player);
        verify(player, times(1)).receiveMoney(25);
        verify(property, times(1)).removeHouse();
        assertEquals(33, bank.getHousesInBank());
    }

    public void testSellingAHotel() throws Exception {
        Property property = Mockito.mock(Property.class);
        when(property.getHouseCost()).thenReturn(50);
        BuildRules rules = Mockito.mock(BuildRules.class);
        SellingRules sellingRules = Mockito.mock(SellingRules.class);
        when(sellingRules.priceReductionForSellingOfHotel()).thenReturn(0.1);
        Player player = Mockito.mock(Player.class);
        when(property.getOwner()).thenReturn(player);
        when(rules.canSellHouse(property,player)).thenReturn(true);
        when(rules.amountOfHousesNeededForHotel()).thenReturn(4);
        AllRules.setSellingRules(sellingRules);
        Bank bank = new Bank();
        bank.sellHotel(property,player);
        verify(player, times(1)).receiveMoney(5);
        verify(property, times(1)).removeHotel();
        verify(property, times(4)).addHouse();
        assertEquals(13, bank.getHotelsInBank());
        assertEquals(28, bank.getHousesInBank());

    }

    public void testPayPlayer() throws Exception {
        Player playerSpending = Mockito.mock(Player.class);
        Player playerOwed = Mockito.mock(Player.class);
        when(playerSpending.spendMoney(anyInt())).thenReturn(true);
        Bank bank = new Bank();
        bank.payPlayer(playerSpending, playerOwed, 200);
        verify(playerOwed, times(1)).receiveMoney(200);
    }

    public void testPayPlayerWhenPlayerDoesNotHaveEnoughMoneyAndNotEnoughSalableItems() throws Exception {
        Player playerSpending = Mockito.mock(Player.class);
        Player playerOwed = Mockito.mock(Player.class);
        when(playerSpending.spendMoney(anyInt())).thenReturn(false);
        BankruptcyRules mockRules = Mockito.mock(BankruptcyRules.class);
        when(mockRules.checkForBankruptcy(playerSpending, 200)).thenReturn(true);
        AllRules.setBankruptcyRules(mockRules);
        Bank bank = new Bank();
        bank.payPlayer(playerSpending, playerOwed, 200);
        verify(playerOwed, times(0)).receiveMoney(200);
        verify(mockRules, times(1)).bankruptByPlayer(playerOwed, playerSpending);
    }

    public void testPayPlayerWhenPlayerDoesNotHaveEnoughMoneyAndEnoughSalableItems() throws Exception {
        Player playerSpending = Mockito.mock(Player.class);
        Player playerOwed = Mockito.mock(Player.class);
        when(playerSpending.spendMoney(anyInt())).thenReturn(false).thenReturn(true);
        BankruptcyRules mockRules = Mockito.mock(BankruptcyRules.class);
        when(mockRules.checkForBankruptcy(playerSpending, 200)).thenReturn(false);
        AllRules.setBankruptcyRules(mockRules);
        Bank bank = new Bank();
        bank.payPlayer(playerSpending, playerOwed, 200);
        verify(playerOwed, times(1)).receiveMoney(200);
        verify(mockRules, times(0)).bankruptByPlayer(playerOwed, playerSpending);
    }
}