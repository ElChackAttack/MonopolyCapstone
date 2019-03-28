package Rules;

import Board.Group;
import Players.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by userhp on 30/01/2016.
 */
public class StationRules {

    private LuaValue _G;

    public StationRules(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
    }
    public int calculateRent(Player owner, Player visitor) {
        LuaValue luaOwner = CoerceJavaToLua.coerce(owner);
        LuaValue luaVisitor = CoerceJavaToLua.coerce(visitor);
        LuaValue luaStation = CoerceJavaToLua.coerce(Group.Station);
        LuaValue luaCardMove = CoerceJavaToLua.coerce(MoveType.Card);
        LuaValue luaCalculateRentMethod = _G.get("calculateRent");
        LuaValue[] luaMethodArgs = {luaOwner, luaVisitor, luaStation, luaCardMove};
        int rentOwed = luaCalculateRentMethod.invoke(luaMethodArgs).arg1().toint();
        return rentOwed;
    }
}
