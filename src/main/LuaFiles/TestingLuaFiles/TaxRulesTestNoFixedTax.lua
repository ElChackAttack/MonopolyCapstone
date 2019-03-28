--
-- Created by IntelliJ IDEA.
-- User: userhp
-- Date: 23/02/2016
-- Time: 12:02
-- To change this template use File | Settings | File Templates.
--

local fixedTaxOption = false
local incomeTaxPercentage = 0.1

function calculateIncomeTax(visitor, location)
    tax = location:getFee()
    taxablePlayerNetWorth = (visitor:calculateNetWorth() * incomeTaxPercentage)

    if (taxablePlayerNetWorth < tax or not fixedTaxOption) then
        tax = taxablePlayerNetWorth;
    end
    return tax
end

