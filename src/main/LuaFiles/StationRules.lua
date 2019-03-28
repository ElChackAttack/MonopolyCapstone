--
-- Created by IntelliJ IDEA.
-- User: userhp
-- Date: 23/02/2016
-- Time: 11:30
-- To change this template use File | Settings | File Templates.
--
local oneStationRent = 25
local twoStationRent = 50
local threeStationRent = 100
local fourStationRent = 200

function calculateRent(owner, visitor, groupStation, moveTypeCard)
    rentOwed = 0
    stationsOwned = owner:ownsSpacesOfGroup(groupStation)

    if stationsOwned == 4 then rentOwed = fourStationRent

    elseif stationsOwned == 3 then rentOwed = threeStationRent

    elseif stationsOwned == 2 then rentOwed = twoStationRent

    else rentOwed = oneStationRent
    end -- end if

    if visitor:getMoveTaken():equals(moveTypeCard) then rentOwed = rentOwed * 2
    end

    return rentOwed
end


