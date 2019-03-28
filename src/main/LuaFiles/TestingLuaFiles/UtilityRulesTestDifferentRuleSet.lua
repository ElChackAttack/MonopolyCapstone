--
-- Created by IntelliJ IDEA.
-- User: userhp
-- Date: 23/02/2016
-- Time: 12:55
-- To change this template use File | Settings | File Templates.
--


local multiplierForOneUtility = 20
local multiplierForBothUtilities = 50

function calculateRent(owner, visitor, moveTypeCard, groupUtility)
    rentOwed = 0
    if (visitor:getMoveTaken():equals(moveTypeCard)) then
        rentOwed = visitor:rollDice():getSumOfDiceRolls() * multiplierForBothUtilities;

    elseif (owner:ownsSpacesOfGroup(groupUtility) == 2) then
        rentOwed = visitor:getLastDiceRoll():getSumOfDiceRolls() * multiplierForBothUtilities;

    else
        rentOwed = visitor:getLastDiceRoll():getSumOfDiceRolls() * multiplierForOneUtility;
    end

    return rentOwed;
end