package Rules;

import junit.framework.TestCase;

import java.nio.file.Paths;

/**
 * Created by userhp on 23/02/2016.
 */
public class SellingRulesTest extends TestCase {

    public void testPriceReductionForSellingOfHouse() throws Exception {
        SellingRules rules = new SellingRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/SellingRules.lua");
        assertEquals(0.5, rules.priceReductionForSellingOfHouse());
    }

    public void testPriceReductionForSellingOfHotel() throws Exception {
        SellingRules rules = new SellingRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/SellingRules.lua");
        assertEquals(0.5, rules.priceReductionForSellingOfHotel());
    }
}