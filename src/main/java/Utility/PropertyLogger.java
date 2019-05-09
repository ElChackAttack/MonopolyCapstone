package Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import Board.Ownable;

/**
 * Created by userhp on 07/03/2016.
 */
public class PropertyLogger {
    private static File csvFile;
    private static BufferedWriter writer;
    private static HashMap<String, Ownable> map = new HashMap<>();

    public PropertyLogger(String filename) {
        csvFile = new File(filename);
        try {
            writer = new BufferedWriter(new FileWriter(csvFile, true));
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void addToLog(Ownable ownable){
        String name = ownable.getName();
        int moneyEarned = ownable.getMoneyEarned();
        if(map.containsKey(name)){
            Ownable oldMoneyOwed = map.get(name);
//            final int newMoneyEarned = oldMoneyOwed.getMoneyEarned() + moneyEarned;
//            ownable.setMoneyEarned(newMoneyEarned);
            map.replace(name, oldMoneyOwed,ownable);
        }
        else {
            map.put(name,ownable);
        }
    }
    public static void writeToLog() {

        if (csvFile != null) {

            try {
                for (String key:  map.keySet()) {
                    Ownable ownable = map.get(key);
                    writer.append(ownable.getName() +"," +ownable.getGroup() +","  +Integer.toString(ownable.getMoneyEarned()));
                    writer.append("\n");

                }

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
