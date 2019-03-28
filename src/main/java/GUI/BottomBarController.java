package GUI;

import Board.*;
import Cards.Deck;
import Logger.DataLogger;
import Logger.PropertyLogger;
import Logger.TurnCounter;
import Logger.TurnLogger;
import Players.*;
import Rules.*;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Vector;

/**
 * Created by userhp on 04/04/2016.
 */
public class BottomBarController {
    public TextField SimulationsToRun;
    public GridPane BottomBar;
    public TextField DirectoryOfDataLogs;
    public Button RunSimulationButton;
    private BoardController boardController;
    private Stage rulesStage;
    private Stage playersStage;
    private RulesController rulesController;
    private PlayerController playerController;

    public void initialize() {
        DirectoryOfDataLogs.setText("logs");
        DirectoryOfDataLogs.setAccessibleText(Paths.get("").toAbsolutePath().toString() + "/logs/");
        Parent rootRules;
        Parent rootPlayer;
        FXMLLoader fxmlLoaderRules = new FXMLLoader();
        FXMLLoader fxmlLoaderPlayers = new FXMLLoader();
        try {
            fxmlLoaderRules.setLocation(getClass().getClassLoader().getResource("guiRulesDesign.fxml"));
            rootRules = fxmlLoaderRules.load();
            rulesStage = new Stage();
            rulesStage.setTitle("Rules");
            rulesStage.setScene(new Scene(rootRules));
            rulesController = (RulesController) fxmlLoaderRules.getController();
            rulesController.initialize();
            rulesStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    rulesStage.hide();
                }
            });

            fxmlLoaderPlayers.setLocation(getClass().getClassLoader().getResource("guiPlayerDesign.fxml"));
            rootPlayer = fxmlLoaderPlayers.load();
            playersStage = new Stage();
            playersStage.setTitle("Players");
            playersStage.setScene(new Scene(rootPlayer));
            playerController = (PlayerController) fxmlLoaderPlayers.getController();
            playerController.initialize();
            playersStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    playersStage.hide();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runSimulation(Event event) {

        ProgressForm progressForm = new ProgressForm();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int endlessGames = 0;
                int simulationsToRun = Integer.valueOf(SimulationsToRun.getText());
                TurnLogger tl = new TurnLogger(DirectoryOfDataLogs.getAccessibleText() + "/turnLogRandom.csv");
                PropertyLogger pl = new PropertyLogger(DirectoryOfDataLogs.getAccessibleText() + "/propertyLog.csv");
                int[] winners = {0, 0, 0, 0, 0, 0, 0, 0};
                for (int i = 0; i < simulationsToRun; i++) {
                    updateProgress(i, simulationsToRun);
                    //Init Players
                    setupRules();

                    Vector<Player> playersInGame = new Vector<>();
                    DataLogger dl = new DataLogger(DirectoryOfDataLogs.getAccessibleText() + "/dataLog" + i + ".csv");
                    for (Space space : BoardHelper.getInstance().getAllSpaces()) {
                        if (space instanceof Ownable) {
                            ((Ownable) space).setOwner(null);
                            //((Ownable) space).setMoneyEarned(0);
                            if (space instanceof Property) {
                                ((Property) space).setHouses(0);
                                ((Property) space).setHotels(0);
                            }
                        }
                    }

                    for (Player player : StartingPlayers.getInstance().getAllPlayers()) {
                        Player newPlayer = new Player(player.getName(), player.getMoney(), player.getAllDice());
                        player.setCurrentLocation(player.getCurrentLocation());
                        playersInGame.add(newPlayer);
                    }


                    Collections.sort(playersInGame, new OrderStartingPlayers());
                    AllPlayers.init(playersInGame);
                    TurnCounter.resetCounter();
                    Long StartingTime = System.nanoTime();
                    while (AllPlayers.getInstance().getAllPlayers().size() > 1 && TurnCounter.getTurn() < 500) {
                        try {
                            for (Player player : AllPlayers.getInstance().getAllPlayers()) {
                                player.onTurn();
                                for (Player p : AllPlayers.getInstance().getAllPlayers()) {
                                    p.betweenTurns();
                                }


                            }
                        } catch (ConcurrentModificationException e) {

                        }
                        //endOfTurnLog(allPlayers);
                        TurnCounter.newTurn();
                    }
                    if (TurnCounter.getTurn() > 499) {
                        endlessGames++;
                        System.out.println("Endless Game");
                        Player player = AllPlayers.getInstance().getAllPlayers().firstElement();
                        for (Player p : AllPlayers.getInstance().getAllPlayers()) {
                            if (p.getMoney() > player.getMoney()) {
                                player = p;
                            }
                        }
                        System.out.println("Winner is " + player.getName());
                        if (player.getName().equalsIgnoreCase("Player 1")) {
                            winners[0]++;
                        } else if (player.getName().equalsIgnoreCase("Player 2")) {
                            winners[1]++;
                        } else if (player.getName().equalsIgnoreCase("Player 3")) {
                            winners[2]++;
                        } else if (player.getName().equalsIgnoreCase("Player 4")) {
                            winners[3]++;

                        } else if (player.getName().equalsIgnoreCase("Player 5")) {
                            winners[4]++;

                        } else if (player.getName().equalsIgnoreCase("Player 6")) {
                            winners[5]++;

                        } else if (player.getName().equalsIgnoreCase("Player 7")) {
                            winners[6]++;

                        } else if (player.getName().equalsIgnoreCase("Player 8")) {
                            winners[7]++;
                        }
                    } else {
                        Player player = AllPlayers.getInstance().getAllPlayers().firstElement();
                        System.out.println("Winner is " + player.getName());
                        if (player.getName().equalsIgnoreCase("Player 1")) {
                            winners[0]++;
                        } else if (player.getName().equalsIgnoreCase("Player 2")) {
                            winners[1]++;
                        } else if (player.getName().equalsIgnoreCase("Player 3")) {
                            winners[2]++;
                        } else if (player.getName().equalsIgnoreCase("Player 4")) {
                            winners[3]++;

                        } else if (player.getName().equalsIgnoreCase("Player 5")) {
                            winners[4]++;

                        } else if (player.getName().equalsIgnoreCase("Player 6")) {
                            winners[5]++;

                        } else if (player.getName().equalsIgnoreCase("Player 7")) {
                            winners[6]++;

                        } else if (player.getName().equalsIgnoreCase("Player 8")) {
                            winners[7]++;
                        }
                    }
                    System.out.println("Game Finished in " + (System.nanoTime() - StartingTime) / 1000000000.0 + "s");
                    System.out.println("Turns taken = " + TurnCounter.getTurn());
                    System.out.println("-----------------------------");
                    DataLogger.closeFiles();
                    TurnLogger.writeToLog(TurnCounter.getTurn());
                    TurnCounter.resetCounter();
                    for(Space space: BoardHelper.getInstance().getAllSpaces()){
                        if(space instanceof Ownable){
                            PropertyLogger.addToLog((Ownable) space);
                        }
                    }
                    //Run Simulation

                }
                TurnLogger.closeFiles();
                System.out.println("Endless games = " + endlessGames + " out of games played = " + simulationsToRun);
                System.out.println("Player 1 won : " + winners[0] + " games");
                System.out.println("Player 2 won : " + winners[1] + " games");
                System.out.println("Player 3 won : " + winners[2] + " games");
                System.out.println("Player 4 won : " + winners[3] + " games");
                System.out.println("Player 5 won : " + winners[4] + " games");
                System.out.println("Player 6 won : " + winners[5] + " games");
                System.out.println("Player 7 won : " + winners[6] + " games");
                System.out.println("Player 8 won : " + winners[7] + " games");
                updateProgress(simulationsToRun, simulationsToRun);
                return null;
            }

        };
        progressForm.activateProgressBar(task);
        task.setOnSucceeded(event1 -> {
            progressForm.getDialogStage().close();
            RunSimulationButton.setDisable(false);
            PropertyLogger.writeToLog();
            PropertyLogger.closeFiles();

        });
        RunSimulationButton.setDisable(true);
        progressForm.getDialogStage().show();
        Thread thread = new Thread(task);
        thread.start();


    }

    private void setupRules() {
        AuctionRules auctionRules = new AuctionRules(rulesController.AuctionRulesLocation.getAccessibleText());
        AllRules.setAuctionRules(auctionRules);
        BankruptcyRules bankruptcyRules = new BankruptcyRules();
        AllRules.setBankruptcyRules(bankruptcyRules);
        BuildRules buildRules = new BuildRules(rulesController.BuildRulesLocation.getAccessibleText());
        AllRules.setBuildRules(buildRules);
        GoRules goRules = new GoRules(rulesController.GoRulesLocation.getAccessibleText());
        AllRules.setGoRules(goRules);
        JailRules jailRules = new JailRules(rulesController.JailRulesLocation.getAccessibleText());
        AllRules.setJailRules(jailRules);
        SellingRules sellingRules = new SellingRules(rulesController.SellingRulesLocation.getAccessibleText());
        AllRules.setSellingRules(sellingRules);
        StationRules stationRules = new StationRules(rulesController.StationRulesLocation.getAccessibleText());
        AllRules.setStationRules(stationRules);
        TaxRules taxRules = new TaxRules(rulesController.TaxRulesLocation.getAccessibleText());
        AllRules.setTaxRules(taxRules);
        UtilityRules utilityRules = new UtilityRules(rulesController.UtilityRulesLocation.getAccessibleText());
        AllRules.setUtilityRules(utilityRules);
        Bank bank = new Bank(rulesController.BankRulesLocation.getAccessibleText());
        AllRules.setBankRules(bank);

    }

    public void resetToDefault(Event event) {
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
        boardController.reloadButtons();
    }

    public void accessToBoardController(BoardController boardController) {
        this.boardController = boardController;
    }


    public void openRulesMenu(Event event) {
        rulesStage.show();
    }

    public void chooseFolderForDataLogs(Event event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setInitialDirectory(new File(Paths.get("").toAbsolutePath().toString()));
        File fileChosen = fileChooser.showDialog(BottomBar.getScene().getWindow());
        if (fileChosen != null) {
            DirectoryOfDataLogs.setText(fileChosen.getName());
            DirectoryOfDataLogs.setAccessibleText(fileChosen.getPath());
        }
    }

    public void openPlayerMenu(Event event) {
        playersStage.show();
    }
}

class ProgressForm {
    private final Stage dialogStage;
    private final ProgressBar pb = new ProgressBar();
    private final ProgressIndicator pin = new ProgressIndicator();

    public ProgressForm() {
        dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        dialogStage.setTitle("Running Simulations");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        // PROGRESS BAR
        final Label label = new Label();
        label.setText("alerto");

        pb.setProgress(-1F);
        pin.setProgress(-1F);

        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(pb, pin);

        Scene scene = new Scene(hb);
        dialogStage.setScene(scene);
    }

    public void activateProgressBar(final Task<?> task) {
        pb.progressProperty().bind(task.progressProperty());
        pin.progressProperty().bind(task.progressProperty());
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
}
