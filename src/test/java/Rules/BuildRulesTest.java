package Rules;

import Board.*;
import Players.Player;
import junit.framework.TestCase;
import org.mockito.Mockito;

import java.nio.file.Paths;
import java.util.Stack;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by userhp on 15/02/2016.
 */
public class BuildRulesTest extends TestCase {

    public void testCanBuildHouse() throws Exception {
        BuildRules rules = new BuildRules();
        Player player = Mockito.mock(Player.class);
        when(player.ownsSpacesOfGroup(any(Group.class))).thenReturn(3);
        BoardHelper boardHelperTest = BoardHelper.getInstance();
        boardHelperTest.populateBoard("Monopoly Map.csv");
        Property mockProperty = mock(Property.class);
        when(mockProperty.getHouses()).thenReturn(1);
        when(mockProperty.getGroup()).thenReturn(Group.Green);
        Property mockProperty2 = mock(Property.class);
        when(mockProperty2.getHouses()).thenReturn(2);
        when(mockProperty2.getGroup()).thenReturn(Group.Green);
        Stack<Ownable> mockStack = new Stack<Ownable>();
        mockStack.add(mockProperty);
        mockStack.add(mockProperty2);
        when(player.getOwnedPropertiesOfGroup(any(Group.class))).thenReturn(mockStack);
        assertTrue(rules.canBuildHouse(mockProperty, player));

    }

    public void testCanBuildHotel() throws Exception {
        BuildRules rules = new BuildRules();
        Player player = Mockito.mock(Player.class);
        when(player.ownsSpacesOfGroup(any(Group.class))).thenReturn(3);
        BoardHelper boardHelperTest = BoardHelper.getInstance();
        boardHelperTest.populateBoard("Monopoly Map.csv");
        Property mockProperty = mock(Property.class);
        when(mockProperty.getHouses()).thenReturn(4);
        when(mockProperty.getGroup()).thenReturn(Group.Green);
        Property mockProperty2 = mock(Property.class);
        when(mockProperty2.getHouses()).thenReturn(4);
        when(mockProperty2.getGroup()).thenReturn(Group.Green);
        Stack<Ownable> mockStack = new Stack<Ownable>();
        mockStack.add(mockProperty);
        mockStack.add(mockProperty2);
        when(player.getOwnedPropertiesOfGroup(any(Group.class))).thenReturn(mockStack);
        assertTrue(rules.canBuildHotel(mockProperty, player));

    }

    public void testCannotBuildHotel() throws Exception {
        BuildRules rules = new BuildRules();
        Player player = Mockito.mock(Player.class);
        when(player.ownsSpacesOfGroup(any(Group.class))).thenReturn(3);
        BoardHelper boardHelperTest = BoardHelper.getInstance();
        boardHelperTest.populateBoard("Monopoly Map.csv");
        Property mockProperty = mock(Property.class);
        when(mockProperty.getHouses()).thenReturn(3);
        when(mockProperty.getGroup()).thenReturn(Group.Green);
        Property mockProperty2 = mock(Property.class);
        when(mockProperty2.getHouses()).thenReturn(1);
        when(mockProperty2.getGroup()).thenReturn(Group.Green);

        Stack<Ownable> mockStack = new Stack<Ownable>();
        mockStack.add(mockProperty);
        mockStack.add(mockProperty2);
        when(player.getOwnedPropertiesOfGroup(any(Group.class))).thenReturn(mockStack);
        assertFalse(rules.canBuildHotel(mockProperty, player));

    }

    public void testCannotBuildHouse() throws Exception {
        BuildRules rules = new BuildRules();
        Player player = Mockito.mock(Player.class);
        when(player.ownsSpacesOfGroup(any(Group.class))).thenReturn(2);
        BoardHelper boardHelperTest = BoardHelper.getInstance();
        boardHelperTest.populateBoard("Monopoly Map.csv");
        Property mockProperty = mock(Property.class);
        when(mockProperty.getGroup()).thenReturn(Group.Green);
        Stack<Ownable> mockStack = new Stack<Ownable>();
        mockStack.add(mockProperty);
        when(player.getOwnedPropertiesOfGroup(any(Group.class))).thenReturn(mockStack);
        assertFalse(rules.canBuildHouse(mockProperty, player));
    }
}