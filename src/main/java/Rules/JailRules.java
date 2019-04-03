package Rules;

/**
 * Created by Lucy on 2018/04/02.
 */
public class JailRules {
    private static int amountOfDoublesToBeSentToJail;
    private static int amountOfRollsToGetOutOfJail;
    private static int feetToPayToGetOutOfJail;
    private static boolean canEarnRent;

    public JailRules() {
        amountOfDoublesToBeSentToJail = 3;
        amountOfRollsToGetOutOfJail = 3;
        feetToPayToGetOutOfJail = 50;
        canEarnRent = true;
    }

    public int amountOfRollsToGetOutOfJail() {
        return amountOfRollsToGetOutOfJail;
    }

    public int feeToPayToGetOutOfJail() {
        return feetToPayToGetOutOfJail;
    }

    public boolean canEarnRent() {
        return canEarnRent;
    }

    public int amountOfDoublesToBeSentToJail() {
        return amountOfDoublesToBeSentToJail;
    }
}
