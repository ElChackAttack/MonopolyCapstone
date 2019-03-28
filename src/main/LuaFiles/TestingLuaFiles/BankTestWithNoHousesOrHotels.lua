--
-- Created by IntelliJ IDEA.
-- User: userhp
-- Date: 23/02/2016
-- Time: 14:09
-- To change this template use File | Settings | File Templates.
--
local startingHousesInBank = 0
local startingHotelsInBank = 0

function getStartingHousesInBank()
    return startingHousesInBank
end

function getStartingHotelsInBank()
    return startingHotelsInBank
end

function payPlayer(playerToSend, playerToReceive, amount, bankruptcyRules)
    if (playerToSend:spendMoney(amount)) then
        playerToReceive:gainMoney(amount)

    else
        if bankruptcyRules:checkForBankruptcy(playerToSend, amount) then
            bankruptcyRules:bankruptByPlayer(playerToReceive, playerToSend);

        else
            playerToSend:sellItemsToMakeMoney(amount);
            playerToSend:spendMoney(amount)
            playerToReceive:gainMoney(amount)
        end
    end
end