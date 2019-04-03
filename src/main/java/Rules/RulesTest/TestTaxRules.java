package Rules.RulesTest;

import Board.Tax;
import Players.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by Lucy on 2018/04/02.
 */
public class TestTaxRules {

    private LuaValue _G;

    public TestTaxRules(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
    }

    public int calculateIncomeTax(Player player){
        Tax location = (Tax) player.getCurrentLocation();
        LuaValue luaLocation = CoerceJavaToLua.coerce(location);
        LuaValue luaPlayer = CoerceJavaToLua.coerce(player);
        LuaValue calculateIncomeTaxMethod = _G.get("calculateIncomeTax");
        int tax = calculateIncomeTaxMethod.call(luaPlayer, luaLocation).toint();
        return tax;
    }
}