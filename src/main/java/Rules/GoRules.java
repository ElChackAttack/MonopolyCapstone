package Rules;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by userhp on 28/01/2016.
 */
public class GoRules {
    private LuaValue _G;

    public GoRules(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
    }
    public int getSalary(){
        LuaValue methodGetSalary = _G.get("getSalary");
        LuaValue salary = methodGetSalary.call();
        return salary.toint();
    }

}
