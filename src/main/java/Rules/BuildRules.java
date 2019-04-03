package Rules;

import Board.*;
import Players.Player;

import java.util.Stack;

/**
 * ? differenece Lua function and not Lua ones
 * Created by Lucy on 2018/04/02.
 */
public class BuildRules {
    private static int amountOfHousesNeededForHotel;

    public BuildRules() {
        amountOfHousesNeededForHotel = 4;
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

    // ? differenece between this function and the one above
    private boolean canBuildHouseLua(Property property, Player player) {
        boolean allowedToBuildHouse = false;
        Group group = property.getGroup();
        if (player.ownsSpacesOfGroup(group) == BoardHelper.getInstance().amountOfSpacesInGroup(group)) {
            for (Ownable p : player.getOwnedPropertiesOfGroup(group)) {
                Property prop = (Property) p;
                if (property.getHouses() == prop.getHouses() || property.getHouses() == prop.getHouses() - 1) {
                    allowedToBuildHouse = true;
                }
            }
        }
        return allowedToBuildHouse;
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

    // ? differenece between this function and the one above
    private boolean canBuildHotelLuaFunction(Property property, Player player) {
        Group group = property.getGroup();
        Stack<Ownable> playerOwnedPropertiesOfGroup = player.getOwnedPropertiesOfGroup(group);
        boolean allowedToBuildHotel = false;
        if (property.getHouses() == amountOfHousesNeededForHotel 
        && allPropertiesHaveSameAmountOfHouses(property, playerOwnedPropertiesOfGroup) 
        && player.ownsSpacesOfGroup(group) == BoardHelper.getInstance().amountOfSpacesInGroup(group) 
        && property.getHotels() == 0) {
            allowedToBuildHotel = true;
        }
        return allowedToBuildHotel;
    }

    private boolean allPropertiesHaveSameAmountOfHouses(Property property, Stack<Ownable> properties) {
        boolean enoughHouses = true;
        for (Ownable p : properties) {
            Property prop = (Property) p;
            if (prop.getHouses() != property.getHouses() || prop.getHotels() != 1) {
                enoughHouses = false;
            }
        }
        return enoughHouses;
    }

    public int amountOfHousesNeededForHotel() {
        return amountOfHousesNeededForHotel;
    }

    // TODO: Implemenent Method
    public boolean canSellHouse(Property property, Player player) {
        return true;
    }
}
