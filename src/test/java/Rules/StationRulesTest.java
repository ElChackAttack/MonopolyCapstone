package Rules;

import Board.Group;
import Board.Station;
import Players.Player;
import junit.framework.TestCase;
import org.mockito.Mockito;

import java.nio.file.Paths;

import static org.mockito.Mockito.when;

/**
 * Created by userhp on 01/02/2016.
 */
public class StationRulesTest extends TestCase {

    public void testCalculateRentWithOneStation() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(1);

        when(visitor.getMoveTaken()).thenReturn(MoveType.DiceRoll);
        assertEquals(25,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithTwoStations() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(2);

        when(visitor.getMoveTaken()).thenReturn(MoveType.DiceRoll);
        assertEquals(50,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithThreeStations() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(3);

        when(visitor.getMoveTaken()).thenReturn(MoveType.DiceRoll);
        assertEquals(100,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithFourStations() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(4);
        when(visitor.getMoveTaken()).thenReturn(MoveType.DiceRoll);
        assertEquals(200,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithOneStationAndArriveByCard() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(1);

        when(visitor.getMoveTaken()).thenReturn(MoveType.Card);
        assertEquals(50,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithTwoStationsAndArriveByCard() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
       
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(2);

        when(visitor.getMoveTaken()).thenReturn(MoveType.Card);
        assertEquals(100,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithThreeStationsAndArriveByCard() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(3);

        when(visitor.getMoveTaken()).thenReturn(MoveType.Card);
        assertEquals(200,rules.calculateRent(owner,visitor));
    }
    public void testCalculateRentWithFourStationsAndArriveByCard() throws Exception {

        StationRules rules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        Player owner = Mockito.mock(Player.class);
        Player visitor = Mockito.mock(Player.class);
        when(owner.ownsSpacesOfGroup(Group.Station)).thenReturn(4);
        when(visitor.getMoveTaken()).thenReturn(MoveType.Card);
        assertEquals(400,rules.calculateRent(owner,visitor));
    }

    public void testCalculateRent() throws Exception {

    }
}