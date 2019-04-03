package Rules;

/**
 * Created by Lucy on 2018/04/02.
 */
public class SellingRules {
    private static double priceReductionForSellingHouse;
    private static double priceReductionForSellingHotel;

    public SellingRules() {
        priceReductionForSellingHouse = 0.5;
        priceReductionForSellingHotel = 0.5;
    }

    public double priceReductionForSellingOfHouse() {
        return priceReductionForSellingHouse;
    }

    public double priceReductionForSellingOfHotel() {
        return priceReductionForSellingHotel;
    }
}