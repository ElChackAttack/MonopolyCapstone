package GUI; /**
 * Created by userhp on 28/03/2016.
 */

import Board.BoardHelper;
import Cards.Deck;
import Dice.Dice;
import Players.Player;
import Players.StartingPlayers;
import Rules.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.LogManager;

public class Gui extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        LogManager.getLogManager().reset();
        AuctionRules auctionRules = new AuctionRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/AuctionRules.lua");
        AllRules.setAuctionRules(auctionRules);
        BankruptcyRules bankruptcyRules = new BankruptcyRules();
        AllRules.setBankruptcyRules(bankruptcyRules);
        BuildRules buildRules = new BuildRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/BuildRules.lua");
        AllRules.setBuildRules(buildRules);
        GoRules goRules = new GoRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/GoRules.lua");
        AllRules.setGoRules(goRules);
        JailRules jailRules = new JailRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/JailRules.lua");
        AllRules.setJailRules(jailRules);
        SellingRules sellingRules = new SellingRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/SellingRules.lua");
        AllRules.setSellingRules(sellingRules);
        StationRules stationRules = new StationRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/StationRules.lua");
        AllRules.setStationRules(stationRules);
        TaxRules taxRules = new TaxRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/TaxRules.lua");
        AllRules.setTaxRules(taxRules);
        UtilityRules utilityRules = new UtilityRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/UtilityRules.lua");
        AllRules.setUtilityRules(utilityRules);
        Bank bank = new Bank(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/Bank.lua");
        AllRules.setBankRules(bank);


        //init BoardGui
        BoardHelper.getInstance().populateBoard("Monopoly Map.csv");

        //Init Deck
        Deck.getInstance().initializeDeck("ExampleOfCards.csv");
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        Dice[] diceForGame = {dice1, dice2};
        Player player1 = new Player("Player 1", 1500, diceForGame);
        Player player2 = new Player("Player 2", 1500, diceForGame);
        Player player3 = new Player("Player 3", 1500, diceForGame);
        Player player4 = new Player("Player 4", 1500, diceForGame);
        Player player5 = new Player("Player 5", 1500, diceForGame);
        Player player6 = new Player("Player 6", 1500, diceForGame);
        Player player7 = new Player("Player 7", 1500, diceForGame);
        Player player8 = new Player("Player 8", 1500, diceForGame);
        Vector<Player> playersInGame = new Vector<Player>();
        playersInGame.add(player1);
        playersInGame.add(player2);
        StartingPlayers.getInstance().init(playersInGame);


        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("guiDesign.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle("Monopoly Simulator by Marc Cork");
        primaryStage.setScene(scene);
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();


    }

}