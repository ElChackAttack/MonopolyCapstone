package Utility;

/**
 * Created by userhp on 23/03/2016.
 */
public class TurnCounter {
    private static int turn;

    public static void resetCounter() {
        turn = 1;
    }

    public static void newTurn() {
        turn++;
    }

    public static int getTurn() {
        return turn;
    }
}
