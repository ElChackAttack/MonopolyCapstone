package Rules;

import junit.framework.TestCase;

import java.nio.file.Paths;

/**
 * Created by userhp on 23/02/2016.
 */
public class AuctionRulesTest extends TestCase {

    public void testGetStartingPriceMultiplier() throws Exception {
        AuctionRules rules = new AuctionRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/AuctionRules.lua");
        assertEquals(0.1, rules.getStartingPriceMultiplier());
    }

    public void testGetIncrementMultiplier() throws Exception {
        AuctionRules rules = new AuctionRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/AuctionRules.lua");
        assertEquals(0.05, rules.getIncrementMultiplier());
    }
}