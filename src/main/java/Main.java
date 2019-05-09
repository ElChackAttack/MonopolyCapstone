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
    private static int incomeTaxPerNumOfTurn;
    private static int luxuryTaxPerNumOfTurn;
    private static int salaryPerNumOfTurn;
    public static void main(String args[]) {
        try {
            LogManager.getLogManager().reset();
//            FileHandler fh = new FileHandler(Paths.get("").toAbsolutePath().toString() + "/logs/debugLog.log");
//            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(fh);
//            SimpleFormatter formatter = new SimpleFormatter();
//            fh.setFormatter(formatter);
//            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setUseParentHandlers(false);
            //Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.WARNING);

        } catch (Exception e) {
            e.printStackTrace();
        }

        TurnLogger tl = new TurnLogger(Paths.get("").toAbsolutePath().toString() + "/logs/turnLogPlayers8.csv");

        ParameterFetch parameter = new ParameterFetch(Paths.get("").toAbsolutePath().toString() + "/UserInput.csv");
        incomeTaxPerNumOfTurn = ParameterFetch.getIncomeTaxPerNumOfTurn();
        luxuryTaxPerNumOfTurn = ParameterFetch.getLuxuryTaxPerNumOfTurn();
        salaryPerNumOfTurn = ParameterFetch.getSalaryPerNumOfTurn();
        int simulationsToRun = ParameterFetch.getSimulationsToRun();
        int endlessTurn = ParameterFetch.getEndlessTurn();
        int[] winners = new int[ParameterFetch.getNumOfPlayers()];
        Arrays.fill(winners, 0);

        int endlessGames = 0;

        for (int i = 0; i < simulationsToRun; i++) {
            DataLogger dl = new DataLogger(Paths.get("").toAbsolutePath().toString() + "/logs/dataLog" + i + ".csv");

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

            // Init Board
            BoardHelper.getInstance().populateBoard("Monopoly Map.csv");

            // Init Deck
            Deck.getInstance().initializeDeck("ExampleOfCards.csv");

            // Init Players and add players to game
            Vector<Player> playersInGame = ParameterFetch.getPlayersInGame();
            Collections.sort(playersInGame, new OrderStartingPlayers());
            AllPlayers.init(playersInGame);

            TurnCounter.resetCounter();
            Vector<Player> allPlayers;
            Long StartingTime = System.nanoTime();

            while (AllPlayers.getInstance().getAllPlayers().size() > 1 && TurnCounter.getTurn() <= endlessTurn) {
                allPlayers = AllPlayers.getInstance().getAllPlayers();
                try {
                    for (Player player : allPlayers) {
                        player.onTurn();
                        for (Player p : allPlayers) {
                            p.betweenTurns();
                        }
                    }
                    for (Player player : allPlayers) {
                        int turn = TurnCounter.getTurn();
                        if (salaryPerNumOfTurn != 0 && turn % salaryPerNumOfTurn == 0) {
                            player.receiveMoney(AllRules.getGoRules().getSalary(player));
                        }
                        if (incomeTaxPerNumOfTurn != 0 && turn % incomeTaxPerNumOfTurn == 0) {
                            player.spendMoney(AllRules.getTaxRules().calculateTax(player, "Income Tax"));
                        }
                        if (luxuryTaxPerNumOfTurn != 0 && turn % luxuryTaxPerNumOfTurn == 0) {
                            player.spendMoney(AllRules.getTaxRules().calculateTax(player, "Luxury Tax"));
                        }
                    }
                } catch (ConcurrentModificationException e) {

                }
                TurnCounter.newTurn();
            }

            if (TurnCounter.getTurn() > endlessTurn) {
                endlessGames++;
                System.out.println("Game ended at 500 turns");
                Player player = AllPlayers.getInstance().getAllPlayers().firstElement();
                for (Player p : AllPlayers.getInstance().getAllPlayers()) {
                    if (p.getMoney() > player.getMoney()) {
                        player = p;
                    }
                }
                System.out.println("Winner is " + player.getName());
                int playerIndex = Integer.parseInt(player.getName().split(" ")[1]);
                winners[playerIndex - 1]++;
            } else {
                Player player = AllPlayers.getInstance().getAllPlayers().firstElement();
                System.out.println("Winner is " + player.getName());
                int playerIndex = Integer.parseInt(player.getName().split(" ")[1]);
                winners[playerIndex - 1]++;
            }
            System.out.println("Game Finished in " + (System.nanoTime() - StartingTime) / 1000000000.0 + "s");
            System.out.println("Turns taken = " + TurnCounter.getTurn());
            System.out.println("-----------------------------");
            DataLogger.closeFiles();
            TurnLogger.writeToLog(TurnCounter.getTurn());
            TurnCounter.resetCounter();

        }
        TurnLogger.closeFiles();
        System.out.println("Endless games = " + endlessGames + " out of games played = " + simulationsToRun);
        for (int i = 0; i < winners.length; i++) {
            System.out.println("Player " + (i + 1) + " won : " + winners[i] + " games");   
        }
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
