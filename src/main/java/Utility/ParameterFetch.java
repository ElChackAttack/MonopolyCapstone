package Utility;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

import Dice.Dice;
import Players.Player;

/**
 * Fetch user input from UserInput.csv, and assign values to corresponding variable
 * 
 * Created by Lucy on 2018/04/03.
 */
public class ParameterFetch {
    private static int numOfDice;
    private static int simulationsToRun;
    private static int endlessTurn;
    
    // PerNumOfTurn = 0 --> pay by round
    private static int salary;
    private static double salaryPercentage;
    private static int salaryPerNumOfTurn;
    private static Option salaryOption;
    
    // Percentage = 0 --> fixed taxation
    private static int tax;
    private static double taxPercentage;
    private static int taxPerNumOfTurn;
    private static Option taxOption;

    private static int numOfPlayers;
    private static int[] endowments;
    private static Vector<Player> playersInGame;


    public ParameterFetch() {
        playersInGame = new Vector<Player>();
    }

    public static void setValuesFromFile(String fileName) {
        File file = new File(fileName);
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] input = line.split(",");
                if (input[0].equals("numOfDice")) {
                    numOfDice = Integer.parseInt(input[1]);
                } else if (input[0].equals("simulationsToRun")) {
                    simulationsToRun = Integer.parseInt(input[1]);
                } else if (input[0].equals("endlessTurn")) {
                    endlessTurn = Integer.parseInt(input[1]);
                } else if (input[0].equals("salary")) {
                    salary = Integer.parseInt(input[1]);
                    salaryPercentage = Double.parseDouble(input[2]);
                    salaryPerNumOfTurn = Integer.parseInt(input[3]);
                } else if (input[0].equals("incomeTax")) {
                    taxPercentage = Double.parseDouble(input[1]);
                } else if (input[0].equals("luxuryTax")) {
                    tax = Integer.parseInt(input[1]);
                } else if (input[0].equals("luxuryTax")) {
                    taxPerNumOfTurn = Integer.parseInt(input[1]);
                } else if (input[0].equals("numOfPlayers")) {
                    numOfPlayers = Integer.parseInt(input[1]);
                    endowments = new int[numOfPlayers];
                } else {
                    if (index < numOfPlayers) {
                        endowments[index] = Integer.parseInt(input[1]);
                        index++;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNumOfDice(int num) {
        numOfDice = num;
    }

    public static void setSimulationsToRun(int num) {
        simulationsToRun = num;
    }


    public static int getSimulationsToRun() {
        return simulationsToRun;
    }


    public static void setEndlessTurn(int turn) {
        endlessTurn = turn;
    }


    public static int getEndlessTurn() {
        return endlessTurn;
    }


    public static void setNumOfPlayers(int num) {
        numOfPlayers = num;
        endowments = new int[numOfPlayers];
    }

    
    public static void setEndowment(int num) {
        Arrays.fill(endowments, num);
    }


    public static int getNumOfPlayers() {
        return numOfPlayers;
    }


    public static Vector<Player> getPlayersInGame() {
        Dice[] diceForGame = new Dice[numOfDice];
        Arrays.fill(diceForGame, new Dice());
        playersInGame = new Vector<Player>();
        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player("Player " + i, endowments[i-1], diceForGame);
            playersInGame.add(player);
        }
        return playersInGame;
    }


    public static void setTax(int fee) {
        tax = fee;
    }


    public static int getTax() {
        return tax;
    }


    public static void setTaxPercentage(double percentage) {
        taxPercentage = percentage;
    }


    public static double getTaxPercentage() {
        return taxPercentage;
    }


    public static void setTaxOption(Option option) {
        taxOption = option;
    }

    public static Option getTaxOption() {
        return taxOption;
    }


    public static void setSalary(int s) {
        salary = s;
    }


    public static int getSalary() {
        return salary;
    }


    public static void setSalaryPercentage(double percentage) {
        salaryPercentage = percentage;
    }


    public static double getSalaryPercentage() {
        return salaryPercentage;
    }
    

    public static void setTaxPerNumOfTurn(int turn) {
        taxPerNumOfTurn = turn;
    }


    public static int getTaxPerNumOfTurn() {
        return taxPerNumOfTurn;
    }


    public static void setSalaryPerNumOfTurn(int turn) {
        salaryPerNumOfTurn = turn;
    }

    
    public static int getSalaryPerNumOfTurn() {
        return salaryPerNumOfTurn;
    }

    public static void setSalaryOption(Option option) {
        salaryOption = option;
    }

    public static Option getSalaryOption() {
        return salaryOption;
    }
}