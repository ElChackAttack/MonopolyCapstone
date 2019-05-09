package Utility;

import java.io.*;

/**
 * Created by userhp on 07/03/2016.
 */
public class TurnLogger {
    private static File csvFile;
    private static BufferedWriter writer;

    public TurnLogger(String filename) {
        csvFile = new File(filename);
        try {
            writer = new BufferedWriter(new FileWriter(csvFile, true));
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void writeToLog(int turn) {
        if (csvFile != null) {

            try {

                writer.append(Integer.toString(turn));
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