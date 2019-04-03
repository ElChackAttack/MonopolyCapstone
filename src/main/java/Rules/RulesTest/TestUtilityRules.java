package Rules.RulesTest;

import Players.Player;
import Board.Group;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by Lucy on 2018/04/02.
 */
public class TestUtilityRules {

    private LuaValue _G;

    public TestUtilityRules(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
    }
    
    public int calculateRent(Player owner, Player visitor) {
        LuaValue luaOwner = CoerceJavaToLua.coerce(owner);
        LuaValue luaVisitor = CoerceJavaToLua.coerce(visitor);
        LuaValue liaUtility = CoerceJavaToLua.coerce(Group.Utility);
        LuaValue luaCardMove = CoerceJavaToLua.coerce(Rules.MoveType.Card);
        LuaValue[] luaArgs = {luaOwner, luaVisitor, luaCardMove, liaUtility};
        LuaValue luaCalculateRentMethod = _G.get("calculateRent");
        int rentOwed = luaCalculateRentMethod.invoke(luaArgs).arg1().toint();
        return rentOwed;
    }
}
