package GUI;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by userhp on 05/04/2016.
 */
public class RulesController {

    public GridPane Rules;
    public TextField AuctionRulesLocation;
    public TextField BankRulesLocation;
    public TextField BankruptcyRulesLocation;
    public TextField BuildRulesLocation;
    public TextField GoRulesLocation;
    public TextField JailRulesLocation;
    public TextField MoveRulesLocation;
    public TextField SellingRulesLocation;
    public TextField StationRulesLocation;
    public TextField TaxRulesLocation;
    public TextField UtilityRulesLocation;

    public void initialize() {
        String auctionRulesLoc = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/AuctionRules.lua";
        AuctionRulesLocation.setAccessibleText(auctionRulesLoc);
        AuctionRulesLocation.setText("AuctionRules.lua");

        String buildRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/BuildRules.lua";
        BuildRulesLocation.setAccessibleText(buildRulesLocation);
        BuildRulesLocation.setText("BuildRules.lua");

        String bankruptcyRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/BankruptcyRules.lua";
        BankruptcyRulesLocation.setAccessibleText(bankruptcyRulesLocation);
        BankruptcyRulesLocation.setText("BankruptcyRules.lua");

        String goRuleLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/GoRules.lua";
        GoRulesLocation.setAccessibleText(goRuleLocation);
        GoRulesLocation.setText("GoRules.lua");

        String jailRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/JailRules.lua";
        JailRulesLocation.setAccessibleText(jailRulesLocation);
        JailRulesLocation.setText("JailRules.lua");


        String sellingRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/SellingRules.lua";
        SellingRulesLocation.setAccessibleText(sellingRulesLocation);
        SellingRulesLocation.setText("SellingRules.lua");

        String stationRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua";
        StationRulesLocation.setAccessibleText(stationRulesLocation);
        StationRulesLocation.setText("StationRules.lua");

        String taxRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/TaxRules.lua";
        TaxRulesLocation.setAccessibleText(taxRulesLocation);
        TaxRulesLocation.setText("TaxRules.lua");

        String utilityRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/UtilityRules.lua";
        UtilityRulesLocation.setAccessibleText(utilityRulesLocation);
        UtilityRulesLocation.setText("UtilityRules.lua");

        String bankRulesLocation = Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/Bank.lua";
        BankRulesLocation.setAccessibleText(bankRulesLocation);
        BankRulesLocation.setText("Bank.lua");

    }

    private void browseForRules(TextField rulesLocation) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/"));
        File fileChosen = fileChooser.showOpenDialog(Rules.getScene().getWindow());
        if (fileChosen != null) {
            rulesLocation.setText(fileChosen.getName());
            rulesLocation.setAccessibleText(fileChosen.getPath());
        }
    }

    public void browseForAuctionRules(Event event) {
        browseForRules(AuctionRulesLocation);
    }

    public void browseForBankRules(Event event) {
        browseForRules(BankRulesLocation);
    }

    public void browseForBankruptcyRules(Event event) {
        browseForRules(BankruptcyRulesLocation);
    }

    public void browseForBuildRules(Event event) {
        browseForRules(BuildRulesLocation);
    }

    public void browseForGoRules(Event event) {
        browseForRules(GoRulesLocation);
    }

    public void browseForJailRules(Event event) {
        browseForRules(JailRulesLocation);
    }

    public void browseForMoveRules(Event event) {
        browseForRules(MoveRulesLocation);
    }

    public void browseForSellingRules(Event event) {
        browseForRules(SellingRulesLocation);
    }

    public void browseForStationRules(Event event) {
        browseForRules(StationRulesLocation);
    }

    public void browseForTaxRules(Event event) {
        browseForRules(TaxRulesLocation);
    }

    public void browseForUtilityRules(Event event) {
        browseForRules(UtilityRulesLocation);
    }
}
