package Rules;

import Board.Group;
import Board.Tax;
import Players.Player;
import Rules.RulesTest.TestTaxRules;
import junit.framework.TestCase;
import org.mockito.Mockito;

import java.nio.file.Paths;

import static org.mockito.Mockito.when;

/**
 * Created by userhp on 01/02/2016.
 */
public class TaxRulesTest extends TestCase {

    public void testCalculateIncomeTax() throws Exception {
        Player player = Mockito.mock(Player.class);
        Tax tax = Mockito.mock(Tax.class);
        when(tax.getFee()).thenReturn(200);
        when(player.getCurrentLocation()).thenReturn(tax);
        when(player.calculateNetWorth()).thenReturn(100);

        TaxRules rules = new TaxRules();
        assertEquals(10,rules.calculateTax(player, null));
    }
    public void testCalculateIncomeTaxWhereNetWorthIsMoreThanSetTax() throws Exception {
        Player player = Mockito.mock(Player.class);
        Tax tax = Mockito.mock(Tax.class);
        when(tax.getFee()).thenReturn(200);
        when(player.getCurrentLocation()).thenReturn(tax);
        when(player.calculateNetWorth()).thenReturn(4000);
        TaxRules rules = new TaxRules();
        assertEquals(200,rules.calculateTax(player, null));
    }
    public void testCalculateIncomeTaxWhenNoFixedTaxIsAllowed() throws Exception {
        Player player = Mockito.mock(Player.class);
        Tax tax = Mockito.mock(Tax.class);
        when(tax.getFee()).thenReturn(200);
        when(player.getCurrentLocation()).thenReturn(tax);
        when(player.calculateNetWorth()).thenReturn(4000);
        TestTaxRules rules = new TestTaxRules(Paths.get("").toAbsolutePath().toString() +
                "/src/main/LuaFiles/TestingLuaFiles/TaxRulesTestNoFixedTax.lua");
        assertEquals(400,rules.calculateIncomeTax(player));
    }
    public void testCalculateIncomeTaxWhenNoFixedTaxIsAllowedAndDifferentTaxPercentage() throws Exception {
        Player player = Mockito.mock(Player.class);
        Tax tax = Mockito.mock(Tax.class);
        when(tax.getFee()).thenReturn(200);
        when(player.getCurrentLocation()).thenReturn(tax);
        when(player.calculateNetWorth()).thenReturn(4000);
        TestTaxRules rules = new TestTaxRules(Paths.get("").toAbsolutePath().toString() +
                "/src/main/LuaFiles/TestingLuaFiles/TaxRulesTestNoFixedTaxAndDifferentIncomeTaxPercentage.lua");
        assertEquals(1000,rules.calculateIncomeTax(player));
    }

}