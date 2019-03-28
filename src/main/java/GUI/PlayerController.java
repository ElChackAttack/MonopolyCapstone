package GUI;

import Board.BoardHelper;
import Board.Space;
import Dice.*;
import Players.StartingPlayers;
import Players.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by userhp on 05/04/2016.
 */
public class PlayerController {

    public ListView Players;
    public TextField PlayerNameTextField;
    public TextField StartingMoneyTextField;
    public ComboBox StartingLocation;
    public TextField NumberOfDiceTextField;
    public TextField SidesOnDiceTextField;
    public TextField PlayerLuaFile;

    public void initialize() {

        reloadPlayerList();
        ArrayList<Space> arrayListOfAllLocations = new ArrayList(Arrays.asList(BoardHelper.getInstance().getAllSpaces().toArray()));
        StartingLocation.setItems(FXCollections.observableArrayList(arrayListOfAllLocations));
        StartingLocation.setCellFactory(lv -> new ListCell<Space>() {
            @Override
            public void updateItem(Space item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getName(); // get text from item
                    setText(text);
                }
            }
        });
        StartingLocation.setConverter(new StringConverter<Space>() {
            @Override
            public Space fromString(String string) {
                return null;
            }

            @Override
            public String toString(Space space) {
                if (space == null) {
                    return null;
                } else {
                    return space.getName();
                }
            }
        });

        Players.setCellFactory(lv -> new ListCell<Player>() {
            @Override
            public void updateItem(Player item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getName(); // get text from item
                    setText(text);
                }
            }
        });
        Players.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {

            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                if (newValue != null) {
                    PlayerNameTextField.setText(newValue.getName());
                    StartingMoneyTextField.setText(String.valueOf(newValue.getMoney()));
                    StartingLocation.getSelectionModel().select(newValue.getCurrentLocation());
                    NumberOfDiceTextField.setText(String.valueOf(newValue.getAllDice().length));
                    SidesOnDiceTextField.setText(String.valueOf(newValue.getAllDice()[0].getMaxRoll()));
                    PlayerLuaFile.setText(newValue.getLuaFileLocation());
                }
            }
        });

    }

    public void openPlayerLuaFile(Event event) {
    }

    public void addPlayer(Event event) {
        Player player = generatePlayer();
        StartingPlayers.getInstance().addPlayer(player);
        reloadPlayerList();
    }

    private Player generatePlayer() {
        String playerName = PlayerNameTextField.getText();
        int amountOfDice = Integer.parseInt(NumberOfDiceTextField.getText());
        int sideOfEachDice = Integer.parseInt(SidesOnDiceTextField.getText());
        Dice[] diceForPlayer = new Dice[amountOfDice];
        for (int i = 0; i < amountOfDice; i++) {
            diceForPlayer[i] = new Dice(sideOfEachDice);
        }
        int money = Integer.parseInt(StartingMoneyTextField.getText());
        Player player = new Player(playerName, money, diceForPlayer);
        player.setCurrentLocation((Space) StartingLocation.getSelectionModel().getSelectedItem());
        return player;
    }

    public void editPlayer(Event event) {
        Player newPlayer = generatePlayer();
        Player oldPlayer = (Player) Players.getSelectionModel().getSelectedItem();
        StartingPlayers.getInstance().replacePlayer(oldPlayer, newPlayer);
        reloadPlayerList();
    }

    public void deletePlayer(Event event) {

        StartingPlayers.getInstance().removePlayer((Player) Players.getSelectionModel().getSelectedItem());
        reloadPlayerList();
    }

    private void reloadPlayerList() {
        ArrayList<Player> players = new ArrayList(Arrays.asList(StartingPlayers.getInstance().getAllPlayers().toArray()));
        Players.setItems(FXCollections.observableList(players));

    }
}
