package Board;

import Players.Player;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by userhp on 26/01/2016.
 */
public class BoardHelperTest extends TestCase {

    public void testPopulateBoard() throws Exception {
        BoardHelper boardHelper = BoardHelper.getInstance();
        boardHelper.populateBoard("Monopoly Map.csv");
    }

    public void testRetrieveSpaceFromIntLocation() throws Exception {
        BoardHelper boardHelper = BoardHelper.getInstance();
        boardHelper.populateBoard("Monopoly Map.csv");
        Space go = boardHelper.getSpaceOnBoard(0);
        assertTrue(go.getName().equalsIgnoreCase("GO"));
    }

    public void testRetrieveSpaceFromName() throws Exception {
        BoardHelper boardHelper = BoardHelper.getInstance();
        boardHelper.populateBoard("Monopoly Map.csv");
        Space go = boardHelper.getSpaceOnBoard("go");
        assertEquals(go.getLocation(), 0);
    }

    public void testMoveAroundBoard() throws Exception {
        BoardHelper boardHelper = BoardHelper.getInstance();
        boardHelper.populateBoard("Monopoly Map.csv");
        Space go = boardHelper.getSpaceOnBoard(0);
        int spacesToMove = 4;
        Space expectedSpace = boardHelper.getSpaceOnBoard(spacesToMove);
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getCurrentLocation()).thenReturn(go);
        assertEquals(expectedSpace, boardHelper.moveToSpace(mockPlayer, spacesToMove));
    }

}