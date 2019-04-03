package Rules;

/**
 * Created by Lucy on 2018/04/02.
 */
public class AuctionRules {
    private static double startingPriceMulitplier;
    private static double incrementMultiplier;

    public AuctionRules() {
        startingPriceMulitplier = 0.1;
        incrementMultiplier = 0.05;
    }

    public double getStartingPriceMultiplier() {
        return startingPriceMulitplier;
    }

    public double getIncrementMultiplier() {
        return incrementMultiplier;
    }
}
