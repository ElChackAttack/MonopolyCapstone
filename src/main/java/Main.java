import Board.*;
import Cards.Deck;
import Dice.Dice;
import Players.AllPlayers;
import Players.OrderStartingPlayers;
import Players.Player;
import Rules.*;
import Utility.*;

import java.util.logging.*;
import java.util.*;
import java.nio.file.Paths;
import java.util.ConcurrentModificationException;
import java.util.Vector;

/**
 * Created by Lucy on 2018/04/02.
 */
public class Main {
    public static void main(String args[]) {
        try {
            LogManager.getLogManager().reset();
//            FileHandler fh = new FileHandler(Paths.get("").toAbsolutePath().toString() + "/logs/debugLog.log");
//            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(fh);
//            SimpleFormatter formatter = new SimpleFormatter();
//            fh.setFormatter(formatter);
//            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setUseParentHandlers(false);
            //Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.WARNING);
            // Init Rules
            AuctionRules auctionRules = new AuctionRules();
            AllRules.setAuctionRules(auctionRules);
            BankruptcyRules bankruptcyRules = new BankruptcyRules();
            AllRules.setBankruptcyRules(bankruptcyRules);
            BuildRules buildRules = new BuildRules();
            AllRules.setBuildRules(buildRules);
            GoRules goRules = new GoRules();
            AllRules.setGoRules(goRules);
            JailRules jailRules = new JailRules();
            AllRules.setJailRules(jailRules);
            SellingRules sellingRules = new SellingRules();
            AllRules.setSellingRules(sellingRules);
            StationRules stationRules = new StationRules();
            AllRules.setStationRules(stationRules);
            TaxRules taxRules = new TaxRules();
            AllRules.setTaxRules(taxRules);
            UtilityRules utilityRules = new UtilityRules();
            AllRules.setUtilityRules(utilityRules);
            Bank bank = new Bank();
            AllRules.setBankRules(bank);

            // Init Board and Deck
            BoardHelper.getInstance().populateBoard("Monopoly Map.csv");
            Deck.getInstance().initializeDeck("ExampleOfCards.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // new ParameterFetch(Paths.get("").toAbsolutePath().toString() + "/UserInput.csv");
        new Simulation();
        TurnLogger.closeFiles();

    }

    private static void endOfTurnLog(Vector<Player> players) {
        Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        for (Player player : players) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(player.getName());
            stringBuilder.append(" Current cash = ");
            stringBuilder.append(player.getMoney());
            stringBuilder.append("\nOwned spaces:\n");
            List<Ownable> properties = (List) player.getOwnedSpaces();
            Collections.sort(properties, new OwnableComparator());

            for (Ownable owned : properties) {
                stringBuilder.append(owned.getName());
                stringBuilder.append(" Group: ");
                stringBuilder.append(owned.getGroup());
                if (owned instanceof Property) {
                    stringBuilder.append(" Houses: ");
                    stringBuilder.append(((Property) owned).getHouses());
                    stringBuilder.append(" Hotels: ");
                    stringBuilder.append(((Property) owned).getHotels());
                }
                stringBuilder.append("\n");
            }
            log.warning(stringBuilder.toString());
        }

    }
}
