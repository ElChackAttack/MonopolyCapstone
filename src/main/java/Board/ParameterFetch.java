package Board;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

import Board.*;
import Cards.*;
import Dice.*;
import Logger.*;
import Players.*;

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
    
    // Percentage = 0 --> fixed taxation
    private static int incomeTax;
    private static double incomeTaxPercentage;
    private static int incomeTaxPerNumOfTurn;

    private static int luxuryTax;
    private static double luxuryTaxPercentage;
    private static int luxuryTaxPerNumOfTurn;

    private static int numOfPlayers;
    private static int[] endowments;
    private static Vector<Player> playersInGame;


    public ParameterFetch(String fileName) {
        playersInGame = new Vector<Player>();
        File file = new File(fileName);
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] input = line.split(",");
                if (input[0].equals("numOfDice")) {
                    ParameterFetch.numOfDice = Integer.parseInt(input[1]);
                } else if (input[0].equals("simulationsToRun")) {
                    ParameterFetch.simulationsToRun = Integer.parseInt(input[1]);
                } else if (input[0].equals("endlessTurn")) {
                    ParameterFetch.endlessTurn = Integer.parseInt(input[1]);
                } else if (input[0].equals("salary")) {
                    ParameterFetch.salary = Integer.parseInt(input[1]);
                    ParameterFetch.salaryPercentage = Double.parseDouble(input[2]);
                    ParameterFetch.salaryPerNumOfTurn = Integer.parseInt(input[3]);
                } else if (input[0].equals("incomeTax")) {
                    ParameterFetch.incomeTax = Integer.parseInt(input[1]);
                    ParameterFetch.incomeTaxPercentage = Double.parseDouble(input[2]);
                    ParameterFetch.incomeTaxPerNumOfTurn = Integer.parseInt(input[3]);
                } else if (input[0].equals("luxuryTax")) {
                    ParameterFetch.luxuryTax = Integer.parseInt(input[1]);
                    ParameterFetch.luxuryTaxPercentage = Double.parseDouble(input[2]);
                    ParameterFetch.luxuryTaxPerNumOfTurn = Integer.parseInt(input[3]);
                } else if (input[0].equals("numOfPlayers")) {
                    ParameterFetch.numOfPlayers = Integer.parseInt(input[1]);
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

    public static int getSimulationsToRun() {
        return simulationsToRun;
    }

    public static int getEndlessTurn() {
        return endlessTurn;
    }

    public static int getNumOfPlayers() {
        return numOfPlayers;
    }

    public static Vector<Player> getPlayersInGame() {
        Dice[] diceForGame = new Dice[numOfDice];
        Arrays.fill(diceForGame, new Dice());
        ParameterFetch.playersInGame = new Vector<Player>();
        for (int i = 1; i <= numOfPlayers; i++) {
            Player player = new Player("Player " + i, endowments[i-1], diceForGame);
            playersInGame.add(player);
        }
        return playersInGame;
    }

    public static int getTaxFee(String name) {
        if (name.equalsIgnoreCase("Luxury Tax")) {
            return luxuryTax;
        }
        return incomeTax;
    }

    public static double getTaxPercentage(String name) {
        if (name.equalsIgnoreCase("Income Tax")) {
            return incomeTaxPercentage;
        }
        return luxuryTaxPercentage;
    }

    public static int getSalary() {
        return salary;
    }

    public static double getSalaryPercentage() {
        return salaryPercentage;
    }
    
    public static int getIncomeTaxPerNumOfTurn() {
        return incomeTaxPerNumOfTurn;
    }
    
    public static int getLuxuryTaxPerNumOfTurn() {
        return luxuryTaxPerNumOfTurn;
    }

    public static int getSalaryPerNumOfTurn() {
        return salaryPerNumOfTurn;
    }
}