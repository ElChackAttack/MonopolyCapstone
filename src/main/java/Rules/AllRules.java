package Rules;

/**
 * Created by Lucy on 2018/04/05.
 */
public class AllRules {
    private static AuctionRules auctionRules;
    private static Bank bankRules;
    private static BankruptcyRules bankruptcyRules;
    private static BuildRules buildRules;
    private static GoRules goRules;
    private static JailRules jailRules;
    private static MoveRules moveRules;
    private static SellingRules sellingRules;
    private static StationRules stationRules;
    private static TaxRules taxRules;
    private static UtilityRules utilityRules;

    public static AuctionRules getAuctionRules() {
        return auctionRules;
    }

    public static void setAuctionRules(AuctionRules auctionRules) {
        AllRules.auctionRules = auctionRules;
    }

    public static Bank getBankRules() {
        return bankRules;
    }

    public static void setBankRules(Bank bank) {
        AllRules.bankRules = bank;
    }

    public static BankruptcyRules getBankruptcyRules() {
        return bankruptcyRules;
    }

    public static void setBankruptcyRules(BankruptcyRules bankruptcyRules) {
        AllRules.bankruptcyRules = bankruptcyRules;
    }

    public static BuildRules getBuildRules() {
        return buildRules;
    }

    public static void setBuildRules(BuildRules buildRules) {
        AllRules.buildRules = buildRules;
    }

    public static GoRules getGoRules() {
        return goRules;
    }

    public static void setGoRules(GoRules goRules) {
        AllRules.goRules = goRules;
    }

    public static JailRules getJailRules() {
        return jailRules;
    }

    public static void setJailRules(JailRules jailRules) {
        AllRules.jailRules = jailRules;
    }

    public static MoveRules getMoveRules() {
        return moveRules;
    }

    public static void setMoveRules(MoveRules moveRules) {
        AllRules.moveRules = moveRules;
    }

    public static SellingRules getSellingRules() {
        return sellingRules;
    }

    public static void setSellingRules(SellingRules sellingRules) {
        AllRules.sellingRules = sellingRules;
    }

    public static StationRules getStationRules() {
        return stationRules;
    }

    public static void setStationRules(StationRules stationRules) {
        AllRules.stationRules = stationRules;
    }

    public static TaxRules getTaxRules() {
        return taxRules;
    }

    public static void setTaxRules(TaxRules taxRules) {
        AllRules.taxRules = taxRules;
    }

    public static UtilityRules getUtilityRules() {
        return utilityRules;
    }

    public static void setUtilityRules(UtilityRules utilityRules) {
        AllRules.utilityRules = utilityRules;
    }
}
