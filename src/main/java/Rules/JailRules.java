package Rules;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by userhp on 01/02/2016.
 */
public class JailRules {

    LuaValue _G;

    public JailRules(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
    }


    public int amountOfRollsToGetOutOfJail(){
        LuaValue methodAmountOfRollsToGetOutOfJail = _G.get("amountOfRollsToGetOutOfJailFunc");
        LuaValue amountOfRolls = methodAmountOfRollsToGetOutOfJail.call();
        return amountOfRolls.toint();
    }
    public int feeToPayToGetOutOfJail(){
        LuaValue methodGetSalary = _G.get("feetToPayToGetOutOfJailFunc");
        LuaValue salary = methodGetSalary.call();
        return salary.toint();
    }
    public boolean canEarnRent(){
        LuaValue methodGetSalary = _G.get("canEarnRentFunc");
        LuaValue salary = methodGetSalary.call();
        return salary.toboolean();
    }

    public int amountOfDoublesToBeSentToJail() {
        LuaValue methodGetSalary = _G.get("amountOfDoublesToBeSentToJailFunc");
        LuaValue salary = methodGetSalary.call();
        return salary.toint();
    }


//    private static JailRules instance = new JailRules();
//    private static int amountOfRollsToGetOutOfJail;
//    private static int amountOfDoublesToBeSentToJail;
//    private static int feeToPayToGetOutOfJail;
//    private static boolean canEarnRent;
//
//    private JailRules(){};
//
//    public static void init(int rollsToGetOutOfJail, int fee, boolean earnRent, int doublesToBeSentJail) {
//        amountOfRollsToGetOutOfJail = rollsToGetOutOfJail;
//        feeToPayToGetOutOfJail = fee;
//        canEarnRent = earnRent;
//        amountOfDoublesToBeSentToJail = doublesToBeSentJail;
//    }
//
//    public static JailRules getInstance(){
//        return instance;
//    }
//    public int amountOfRollsToGetOutOfJail(){
//        return amountOfRollsToGetOutOfJail;
//    }
//    public int feeToPayToGetOutOfJail(){
//        return feeToPayToGetOutOfJail;
//    }
//    public boolean canEarnRent(){
//        return canEarnRent;
//    }
//
//    public int amountOfDoublesToBeSentToJail() {
//        return amountOfDoublesToBeSentToJail;
//    }
}
