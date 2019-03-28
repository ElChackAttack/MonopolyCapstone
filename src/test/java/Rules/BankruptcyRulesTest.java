package Rules;

import Board.Ownable;
import Board.Property;
import Board.Space;
import Board.Station;
import Players.AllPlayers;
import Players.Player;
import junit.framework.TestCase;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Vector;

import static org.mockito.Mockito.*;

/**
 * Created by userhp on 02/02/2016.
 */
public class BankruptcyRulesTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        AllRules.setBankruptcyRules(new BankruptcyRules());
    }

    public void testCheckForBankruptcyIfPlayerHasEnoughMoney() throws Exception {
        Player player = Mockito.mock(Player.class);
        when(player.calculateSaleableItems()).thenReturn(500);
        assertFalse(AllRules.getBankruptcyRules().checkForBankruptcy(player, 400));
    }

    public void testBankruptByPlayer() throws Exception {
        Station station = mock(Station.class);
        Property property = mock(Property.class);
        Vector<Ownable> ownables=spy(new Vector<Ownable>());
        ownables.add(station);
        ownables.add(property);

        Player owedPlayer = Mockito.mock(Player.class);
        Player bankruptPlayer = Mockito.mock(Player.class);

        when(bankruptPlayer.getMoney()).thenReturn(500);
        doNothing().when(owedPlayer).addProperty(any(Ownable.class));
        when(bankruptPlayer.getOwnedSpaces()).thenReturn(ownables);
        Vector<Player> players = new Vector<Player>();
        players.add(bankruptPlayer);
        players.add(owedPlayer);
        AllPlayers.init(players);
        AllRules.getBankruptcyRules().bankruptByPlayer(owedPlayer, bankruptPlayer);
        AllPlayers spy = spy(AllPlayers.getInstance());
        verify(owedPlayer,times(1)).receiveMoney(500);
        verify(owedPlayer,times(2)).addProperty(any(Ownable.class));
        verify(station,times(1)).setOwner(owedPlayer);
        verify(property,times(1)).setOwner(owedPlayer);
        assertEquals(1,spy.getAllPlayers().size());


    }

    public void testBankruptByBank() throws Exception {

            Station station = mock(Station.class);
            Property property = mock(Property.class);
            when(station.getCost()).thenReturn(200);
            when(property.getCost()).thenReturn(350);
            Vector<Ownable> ownables=spy(new Vector<Ownable>());
            ownables.add(station);
            ownables.add(property);
            Player bankruptPlayer = Mockito.mock(Player.class);
            Player playerToBuyProperty = Mockito.mock(Player.class);
            when(bankruptPlayer.getMoney()).thenReturn(500);
            doNothing().when(playerToBuyProperty).addProperty(any(Ownable.class));
            when(bankruptPlayer.getOwnedSpaces()).thenReturn(ownables);
            Vector<Player> players = new Vector<Player>();
            players.add(bankruptPlayer);
            players.add(playerToBuyProperty);
        when(playerToBuyProperty.wantsToBuyPropertyForPrice(any(Ownable.class),anyInt())).thenReturn(true);
            AllPlayers.init(players);
            AuctionRules rules = Mockito.mock(AuctionRules.class);
            when(rules.getStartingPriceMultiplier()).thenReturn(0.1);
            when(rules.getIncrementMultiplier()).thenReturn(0.05);
        Bank bank = Mockito.mock(Bank.class);
        AllRules.setBankRules(bank);

        AllRules.getBankruptcyRules().bankruptByBank(bankruptPlayer);
            AllPlayers spy = spy(AllPlayers.getInstance());

            assertEquals(1,spy.getAllPlayers().size());



    }
}