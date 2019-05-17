package Utility;

import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import Board.BoardHelper;
import Cards.Deck;
import Players.*;
import Rules.*;


public class Simulation {
    private int simulationsToRun;
    private int endlessTurn;
    private int max;
    private int[] playerRange;
    private int[] endowmentRange;
    private int[] valueRange;
    private double[] percentageRange;
    private int[] turnRange;
    private int count;
    // private Option taxOption;
    // private Option salaryOption;

    public Simulation() {
        // Todo: change this block to user input
        count = 0;
        // simulationsToRun = ParameterFetch.getSimulationsToRun();
        // endlessTurn = ParameterFetch.getEndlessTurn(); 
        max = 2000;
        simulationsToRun = 100;
        endlessTurn = 2000;
        playerRange = new int[] {4, 4};
        endowmentRange = new int[] {1300, 2500, 100};
        valueRange = new int[] {1500, 2500, 100 };
        percentageRange = new double[] {0.1, 0.1, 0.1};
        turnRange = new int[] {1, 10, 1};
        setValues();
    }

    public void setValues() {
        ParameterFetch.setNumOfDice(2);

        new TurnLogger(Paths.get("").toAbsolutePath().toString() + "/logs/turnLogFixFix.csv");
        setSalary(Option.Fix, Option.Fix);
        TurnLogger.closeFiles();

        new TurnLogger(Paths.get("").toAbsolutePath().toString() + "/logs/turnLogFixPct.csv");
        setSalary(Option.Fix, Option.Percentage);
        TurnLogger.closeFiles();

        new TurnLogger(Paths.get("").toAbsolutePath().toString() + "/logs/turnLogPctFix.csv");
        setSalary(Option.Percentage, Option.Fix);
        TurnLogger.closeFiles();

        new TurnLogger(Paths.get("").toAbsolutePath().toString() + "/logs/turnLogPctPct.csv");
        setSalary(Option.Percentage, Option.Percentage);
        TurnLogger.closeFiles();
    }


    public void setSalary(Option salaryOption, Option taxOption) {
        int value = 0;
        double percentage = 0;
        if (salaryOption == Option.Fix) {
            for (value = valueRange[0]; value <= valueRange[1]; value += valueRange[2]) {
                ParameterFetch.setSalary(value);
                ParameterFetch.setSalaryPercentage(0.0);
                if (value == 0) {
                    continue;
                }
                for (int turn = turnRange[0]; turn <= turnRange[1]; turn += turnRange[2]) {
                    ParameterFetch.setSalaryPerNumOfTurn(turn);
                    ParameterFetch.setSalaryOption(salaryOption);
                    setTax(salaryOption, taxOption);
                }
            }
        } else {
            for (percentage = percentageRange[0]; percentage <= percentageRange[1]; percentage += percentageRange[2]) {
                ParameterFetch.setSalary(0);
                ParameterFetch.setSalaryPercentage(percentage);
                if (percentage == 0) {
                    continue;
                }
                for (int turn = turnRange[0]; turn <= turnRange[1]; turn += turnRange[2]) {
                    ParameterFetch.setSalaryPerNumOfTurn(turn);
                    ParameterFetch.setSalaryOption(salaryOption);
                    setTax(salaryOption, taxOption);
                }
            }
        }
    }

    // salary, incomeTax, luxuryTax, numOfPlayers, endowment
    private void setTax(Option salaryOption, Option taxOption) {
        int value = 0;
        double percentage = 0;
        if (taxOption == Option.Fix) {
            for (value = valueRange[0]; value <= valueRange[1]; value += valueRange[2]) {
                ParameterFetch.setTax(value);
                ParameterFetch.setTaxPercentage(0.0);
                if (value == 0) {
                    continue;
                }
                for (int turn = turnRange[0]; turn <= turnRange[1]; turn += turnRange[2]) {
                    ParameterFetch.setTaxPerNumOfTurn(turn);
                    ParameterFetch.setTaxOption(taxOption);
                    buildPlayers(salaryOption, taxOption);
                }
            }
        } else {
            for (percentage = percentageRange[0]; percentage <= percentageRange[1]; percentage += percentageRange[2]) {
                ParameterFetch.setTax(0);
                ParameterFetch.setTaxPercentage(percentage);
                if (percentage == 0) {
                    continue;
                }
                for (int turn = turnRange[0]; turn <= turnRange[1]; turn += turnRange[2]) {
                    ParameterFetch.setTaxPerNumOfTurn(turn);
                    ParameterFetch.setTaxOption(taxOption);
                    buildPlayers(salaryOption, taxOption);
                }
            }
        }
    }

    // salary, incomeTax, , , 
    public void buildPlayers(Option salaryOption, Option taxOption) {
        for (int numOfPlayers = playerRange[0]; numOfPlayers <= playerRange[1]; numOfPlayers++) {
            ParameterFetch.setNumOfPlayers(numOfPlayers);
            for (int endowment = endowmentRange[0]; endowment <= endowmentRange[1] ; endowment += endowmentRange[2]) {
                ParameterFetch.setEndowment(endowment);

                String s = numOfPlayers + "," + endowment + "," + ParameterFetch.getSalary() + "," + ParameterFetch.getSalaryPercentage() + "," + ParameterFetch.getSalaryPerNumOfTurn() + "," + salaryOption.name() + "," + ParameterFetch.getTax() + "," + ParameterFetch.getTaxPercentage() + "," + ParameterFetch.getTaxPerNumOfTurn() + "," + taxOption.name();

                TurnLogger.writeToLog(s);
                System.out.println(count + ":       " +  s);

                runSimulation();
                count += 1;
            }
        }
    }



    public void runSimulation() {
        int salaryPerNumOfTurn = ParameterFetch.getSalaryPerNumOfTurn();
        int taxPerNumOfTurn = ParameterFetch.getTaxPerNumOfTurn();
        int[] winners = new int[ParameterFetch.getNumOfPlayers()];
        Arrays.fill(winners, 0);

        int endlessGames = 0;
        List<Double> turns = new ArrayList<>();

        for (int i = 0; i < simulationsToRun; i++) {
            // DataLogger dl = new DataLogger(Paths.get("").toAbsolutePath().toString() + "/logs/dataLog" + i + ".csv");

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

            // Init Players and add players to game
            Vector<Player> playersInGame = ParameterFetch.getPlayersInGame();
            Collections.sort(playersInGame, new OrderStartingPlayers());
            AllPlayers.init(playersInGame);

            TurnCounter.resetCounter();
            Vector<Player> allPlayers;
            // Long StartingTime = System.nanoTime();

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
                            player.receiveMoney(AllRules.getGoRules().calculateSalary(player));
                        }
                        if (taxPerNumOfTurn != 0 && turn % taxPerNumOfTurn == 0) {
                            player.spendMoney(AllRules.getTaxRules().calculateTax(player));
                        }
                    }
                } catch (ConcurrentModificationException e) {

                }
                TurnCounter.newTurn();
            }

            if (TurnCounter.getTurn() > endlessTurn) {
                endlessGames++;
                // System.out.println("Game reach the limitation");
                Player player = AllPlayers.getInstance().getAllPlayers().firstElement();
                for (Player p : AllPlayers.getInstance().getAllPlayers()) {
                    if (p.getMoney() > player.getMoney()) {
                        player = p;
                    }
                }
                // System.out.println("Winner is " + player.getName());
                int playerIndex = Integer.parseInt(player.getName().split(" ")[1]);
                winners[playerIndex - 1]++;
            } else {
                if (AllPlayers.getInstance().getAllPlayers().size() == 1) {
                    Player player = AllPlayers.getInstance().getAllPlayers().firstElement();
                    int playerIndex = Integer.parseInt(player.getName().split(" ")[1]);
                    winners[playerIndex - 1]++;
                    // System.out.println("Winner is " + player.getName());
                } else {
                    System.out.println("All players are out of the game");
                }
                turns.add((double) TurnCounter.getTurn());
            }
            // System.out.println("Game Finished in " + (System.nanoTime() - StartingTime) / 1000000000.0 + "s");
            // System.out.println("Turns taken = " + TurnCounter.getTurn());
            // System.out.println("-----------------------------");
            // DataLogger.closeFiles();
            TurnCounter.resetCounter();
        }
        // System.out.println("Endless games = " + endlessGames + " out of games played = " + simulationsToRun);
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Double turn : turns) {
            stats.addValue(turn);
        }
        TurnLogger.writeToLog(endlessGames);
        TurnLogger.writeToLog(stats.getMean());
        TurnLogger.writeToLog(stats.getGeometricMean());
        TurnLogger.writeToLog(stats.getStandardDeviation());
        TurnLogger.endOfLine();
    }
}