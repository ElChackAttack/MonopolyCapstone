package Players;

import Board.BoardHelper;
import Board.Property;
import junit.framework.TestCase;
import Dice.Dice;

import static org.mockito.Mockito.*;

/**
 * Created by userhp on 04/02/2016.
 */
public class PlayerTest extends TestCase {



    public void testCalculateNetWorth() throws Exception {
        // DataLogger dl = new DataLogger(Paths.get("").toAbsolutePath().toString() + "/logs/dataLog" + i + ".csv");
        BoardHelper.getInstance().populateBoard("Monopoly Map.csv");
        Dice dice1 = mock(Dice.class);
        Dice dice2 = mock(Dice.class);
        Dice[] dices = {dice1, dice2};
        Property property1 = mock(Property.class);
        Property property2 = mock(Property.class);
        Property property3 = mock(Property.class);
        when(property1.getHouses()).thenReturn(3);
        when(property1.getHotels()).thenReturn(0);
        when(property1.getCost()).thenReturn(400);
        when(property1.getHouseCost()).thenReturn(50);
        when(property2.getHouses()).thenReturn(1);
        when(property2.getHotels()).thenReturn(0);
        when(property2.getCost()).thenReturn(100);
        when(property2.getHouseCost()).thenReturn(20);
        when(property3.getHouses()).thenReturn(0);
        when(property3.getHotels()).thenReturn(0);
        when(property3.getCost()).thenReturn(300);
        when(property3.getHouseCost()).thenReturn(50);
        Player player = spy(new Player("Player 1", 1500, dices));
        player.addProperty(property1);
        player.addProperty(property2);
        player.addProperty(property3);
        assertEquals((970 + 1500), player.calculateNetWorth());

    }

    public void testCalculateSaleableItems() throws Exception {

    }

    public void testOnTurn() throws Exception {

    }

    public void testRollDice() throws Exception {

    }

    public void testCalculateHotelsOwned() throws Exception {

    }

    public void testCalculateHousesOwned() throws Exception {

    }
}