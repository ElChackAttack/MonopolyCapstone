package Rules;

import Board.Ownable;
import Logger.DataLogger;
import Logger.TurnCounter;
import Players.AllPlayers;
import Players.Player;

import java.util.Vector;

/**
 * Rules for checking if the player is bankrupt
 */
public class BankruptcyRules {


    public boolean checkForBankruptcy(Player player, int moneyOwed){
        boolean bankrupt = false;
        if(player.calculateSaleableItems()<moneyOwed){
            bankrupt = true;
        }
        return bankrupt;
    }
    public void bankruptByPlayer(Player owedPlayer, Player bankruptPlayer){
        int allBankruptPlayerMoney = bankruptPlayer.getMoney();
        owedPlayer.receiveMoney(allBankruptPlayerMoney);
        bankruptPlayer.spendMoney(allBankruptPlayerMoney);
        Vector<Ownable> ownedSpaces = (Vector<Ownable>) bankruptPlayer.getOwnedSpaces().clone();
        for(Ownable space : ownedSpaces){
            owedPlayer.addProperty(space);
            bankruptPlayer.removeProperty(space);
            space.setOwner(owedPlayer);
        }
        System.out.println(bankruptPlayer.getName() + " is out of the game");
        DataLogger.writeToLog(TurnCounter.getTurn(), bankruptPlayer, bankruptPlayer.getCurrentLocation());
        AllPlayers.getInstance().removePlayer(bankruptPlayer);
    }
    public void bankruptByBank( Player bankruptPlayer){
        int allBankruptPlayerMoney = bankruptPlayer.getMoney();
        bankruptPlayer.spendMoney(allBankruptPlayerMoney);
        Vector<Ownable> ownedSpaces = bankruptPlayer.getOwnedSpaces();
        for(Ownable space : ownedSpaces){
            AllRules.getBankRules().auctionProperty(space);
            bankruptPlayer.removeProperty(space);
        }
        System.out.println("Player is out of the game");
        DataLogger.writeToLog(TurnCounter.getTurn(), bankruptPlayer, bankruptPlayer.getCurrentLocation());
        AllPlayers.getInstance().removePlayer(bankruptPlayer);
    }

    

    
}
