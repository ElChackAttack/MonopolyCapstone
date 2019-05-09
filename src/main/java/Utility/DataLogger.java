package Utility;

import java.io.*;

import Board.Space;
import Players.Player;

/**
 * Created by userhp on 07/03/2016.
 */
public class DataLogger {
    private static File csvFile;
    private static BufferedWriter writer;

    public DataLogger(String filename) {
        csvFile = new File(filename);
        try{
            writer = new BufferedWriter(new FileWriter(csvFile, true));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToLog(int turn, Player player, Space location) {
        if (csvFile != null) {
            try {
                writer.append(turn + "," + player.getName() + "," + player.calculateNetWorth() + "," + location.getName() + "," + player.getOwnedSpaces().size() + "," + player.getMoney() + ",");
                // for (Ownable ownable : player.getOwnedSpaces()) {
                //     writer.append(ownable.getName() + ",");
                // }
                writer.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeFiles() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
