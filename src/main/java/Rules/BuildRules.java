package Rules;

import Board.*;
import Players.Player;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Stack;

/**
 * //TODO logic needs to be added to incorporate rules
 * Created by userhp on 29/01/2016.
 */
public class BuildRules {

    LuaValue _G;

    public BuildRules(String luaFileLocation) {
        _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf(luaFileLocation));
    }

    public boolean canBuildHouse(Property property, Player player) {
        boolean canBuildHouse = false;
        int propertiesInGroup = BoardHelper.getInstance().amountOfSpacesInGroup(property.getGroup());
        int propertiesOfGroupOwnedByPlayer = player.ownsSpacesOfGroup(property.getGroup());

        if (property.getHotels() == 1 || propertiesInGroup != propertiesOfGroupOwnedByPlayer) {
            canBuildHouse = false;
        } else if (allPropertiesInGroupHaveEqualHouses(player, property)) {
            canBuildHouse = true;
        }
        return canBuildHouse;
    }

    private boolean allPropertiesInGroupHaveEqualHouses(Player player, Property property) {
        Stack<Ownable> properties = player.getOwnedPropertiesOfGroup(property.getGroup());
        int amountOfHouses = property.getHouses();
        boolean equalHouses = true;
        for (Ownable p : properties) {
            Property prop = (Property) p;
            if (amountOfHouses != prop.getHouses() && amountOfHouses != prop.getHouses() - 1) {
                equalHouses = false;
                break;
            }
        }
        return equalHouses;
    }

    private boolean canBuildHouseLua(Property property, Player player) {
        BoardHelper boardHelper = BoardHelper.getInstance();
        LuaValue luaBoard = CoerceJavaToLua.coerce(boardHelper);
        LuaValue luaProperty = CoerceJavaToLua.coerce(property);
        LuaValue luaPlayer = CoerceJavaToLua.coerce(player);

        Stack<Ownable> playerOwnedPropertiesOfGroup = player.getOwnedPropertiesOfGroup(property.getGroup());

        LuaTable luaPlayerOwnedProperties = LuaTable.tableOf();


        for (int i = 0; i < playerOwnedPropertiesOfGroup.size(); i++) {
            luaPlayerOwnedProperties.insert(i, CoerceJavaToLua.coerce(playerOwnedPropertiesOfGroup.pop()));
        }


        LuaValue methodCanBuildHouse = _G.get("canBuildHouse");
        LuaValue[] luaArgs = {luaProperty, luaPlayer, luaBoard, luaPlayerOwnedProperties.toLuaValue()};
        Varargs canBuildHouse = methodCanBuildHouse.invoke(luaArgs);
        return canBuildHouse.arg1().toboolean();
    }

    public boolean canBuildHotel(Property property, Player player) {
        boolean canBuildHotel = true;
        int propertiesInGroup = BoardHelper.getInstance().amountOfSpacesInGroup(property.getGroup());
        int propertiesOfGroupOwnedByPlayer = player.ownsSpacesOfGroup(property.getGroup());

        if (property.getHotels() == 1 || propertiesInGroup != propertiesOfGroupOwnedByPlayer) {
            canBuildHotel = false;
        } else if (!allPropertiesInGroupMatchTheHouseRequirement(property, player)) {
            canBuildHotel = false;
        }
        return canBuildHotel;
    }

    private boolean allPropertiesInGroupMatchTheHouseRequirement(Property property, Player player) {
        Stack<Ownable> properties = player.getOwnedPropertiesOfGroup(property.getGroup());
        int amountOfHousesNeeded = 4;
        boolean equalHouses = true;
        for (Ownable p : properties) {
            Property prop = (Property) p;
            if (amountOfHousesNeeded != prop.getHouses() && prop.getHotels() != 1) {
                equalHouses = false;
                break;
            }
        }
        return equalHouses;
    }

    private boolean canBuildHotelLuaFunction(Property property, Player player) {
        BoardHelper boardHelper = BoardHelper.getInstance();
        LuaValue luaBoard = CoerceJavaToLua.coerce(boardHelper);
        LuaValue luaProperty = CoerceJavaToLua.coerce(property);
        LuaValue luaPlayer = CoerceJavaToLua.coerce(player);

        Stack<Ownable> playerOwnedPropertiesOfGroup = player.getOwnedPropertiesOfGroup(property.getGroup());

        LuaTable luaPlayerOwnedProperties = LuaTable.tableOf();


        for (int i = 0; i < playerOwnedPropertiesOfGroup.size(); i++) {
            luaPlayerOwnedProperties.insert(i, CoerceJavaToLua.coerce(playerOwnedPropertiesOfGroup.pop()));
        }


        LuaValue methodCanBuildHotel = _G.get("canBuildHotel");
        LuaValue[] luaArgs = {luaProperty, luaPlayer, luaBoard, luaPlayerOwnedProperties.toLuaValue()};
        Varargs canBuildHotel = methodCanBuildHotel.invoke(luaArgs);
        return canBuildHotel.arg1().toboolean();
    }

    public int amountOfHousesNeededForHotel() {
        return _G.get("getAmountOfHousesNeededForHotel").call().toint();
    }

    //Todo Implemenent Method
    public boolean canSellHouse(Property property, Player player) {
        return true;
    }



}
