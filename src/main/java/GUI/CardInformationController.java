package GUI;

import Board.*;
import Cards.Card;
import Cards.CardAction;
import Cards.ChanceCard;
import Cards.CommunityChestCard;
import Cards.Deck;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by userhp on 28/03/2016.
 */
public class CardInformationController {


    public GridPane CardInfo;
    public ComboBox CardActionComboBox;
    public Text Argument1;
    public Text Argument2;
    public TextField CardNameTextField;
    public TextField Argument1TextField;
    public TextField Argument2TextField;
    public ComboBox Argument1ComboBox;
    public Button saveCardButton;
    public Button closeButton;
    private String title;
    private Card previousCard;

    public void initialize() {
        ArrayList<CardAction> cardActions = new ArrayList<CardAction>(Arrays.asList(CardAction.values()));
        ArrayList<Space> arrayListOfAllLocations = new ArrayList(Arrays.asList(BoardHelper.getInstance().getAllSpaces().toArray()));
        Argument1ComboBox.setItems(FXCollections.observableArrayList(arrayListOfAllLocations));
        Argument1ComboBox.setCellFactory(lv -> new ListCell<Space>() {
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
        Argument1ComboBox.setConverter(new StringConverter<Space>() {
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
        CardActionComboBox.setItems(FXCollections.observableArrayList(cardActions));

        CardActionComboBox.valueProperty().addListener(new ChangeListener<CardAction>() {
            @Override
            public void changed(ObservableValue<? extends CardAction> observable, CardAction oldValue, CardAction newValue) {
                String name = "";
                CardAction action = null;
                Space space = null;
                String feeArgument = "";
                String feePerHouse = "";
                String feePerHotel = "";
                String spacesToMoveBack = "";


                Argument1.setVisible(false);
                Argument1TextField.setVisible(false);
                Argument2TextField.setVisible(false);
                Argument2.setVisible(false);
                Argument1ComboBox.setVisible(false);


                switch (newValue) {

                    case AdvanceToLocation:
                        if (action == newValue) {
                            Argument1ComboBox.getSelectionModel().select(space);
                        }
                        Argument1.setText("Move to Location");
                        Argument1.setVisible(true);
                        Argument1ComboBox.setVisible(true);
                        break;
                    case CollectMoneyFromBank:
                        if (action == newValue) {
                            Argument1TextField.setText(feeArgument);
                        }
                        Argument1.setText("Receive Money from Bank");
                        Argument1.setVisible(true);
                        Argument1TextField.setVisible(true);

                        break;
                    case PayBank:
                        if (action == newValue) {
                            Argument1TextField.setText(feeArgument);
                        }
                        Argument1.setText("Pay Bank");
                        Argument1.setVisible(true);
                        Argument1TextField.setVisible(true);
                        break;
                    case CollectFromPlayers:
                        if (action == newValue) {
                            Argument1TextField.setText(feeArgument);
                        }
                        Argument1.setText("Amount from Each Player");
                        Argument1.setVisible(true);
                        Argument1TextField.setVisible(true);
                        break;
                    case PayBankDependingOnHousesAndHotelsOwned:
                        if (action == newValue) {
                            Argument1TextField.setText(feePerHouse);
                            Argument1TextField.setText(feePerHotel);
                        }
                        Argument1.setText("Cost Per House");
                        Argument1.setVisible(true);
                        Argument1TextField.setVisible(true);
                        Argument2.setText("Cost Per Hotel");
                        Argument2.setVisible(true);
                        Argument2TextField.setVisible(true);

                        break;
                    case GoBackSpaces:
                        if (action == newValue) {
                            Argument1TextField.setText(spacesToMoveBack);
                        }
                        Argument1.setText("Spaces To Move Back");
                        Argument1.setVisible(true);
                        Argument1TextField.setVisible(true);
                        break;
                    case PayPlayers:
                        if (action == newValue) {
                            Argument1TextField.setText(feeArgument);
                        }
                        Argument1.setText("Amount to Each Player");
                        Argument1.setVisible(true);
                        Argument1TextField.setVisible(true);
                        break;

                }

            }
        });
        CardActionComboBox.getSelectionModel().select(CardAction.GoToJail);
    }

    public void saveCard(Event event) {
        title = ((Stage) CardInfo.getScene().getWindow()).getTitle();
        Card card;
        String CardName = CardNameTextField.getText();
        CardAction action = (CardAction) CardActionComboBox.getSelectionModel().getSelectedItem();
        if (title.contains("Chance") || previousCard instanceof ChanceCard) {
            switch (action) {
                case AdvanceToLocation:
                    String nameOfSpace = (String) Argument1ComboBox.getSelectionModel().getSelectedItem();
                    Space space = BoardHelper.getInstance().getSpaceOnBoard(nameOfSpace);
                    card = new ChanceCard(CardName, action, space);
                    break;
                case CollectMoneyFromBank:
                    int moneyToBeGiven = Integer.valueOf(Argument1TextField.getText());
                    card = new ChanceCard(CardName, action, moneyToBeGiven);
                    break;
                case PayBank:
                    int moneyToPay = Integer.valueOf(Argument1TextField.getText());
                    card = new ChanceCard(CardName, action, moneyToPay);
                    break;
                case CollectFromPlayers:
                    int moneyToReceiveFromPlayers = Integer.valueOf(Argument1TextField.getText());
                    card = new ChanceCard(CardName, action, moneyToReceiveFromPlayers);
                    break;
                case PayBankDependingOnHousesAndHotelsOwned:
                    int moneyToPayPerHouse = Integer.valueOf(Argument1TextField.getText());
                    int moneyToPayPerHotel = Integer.valueOf(Argument1TextField.getText());
                    card = new ChanceCard(CardName, action, moneyToPayPerHouse, moneyToPayPerHotel);
                    break;
                case GoBackSpaces:
                    int spacesToMoveBack = Integer.valueOf(Argument1TextField.getText());
                    card = new ChanceCard(CardName, action, spacesToMoveBack);
                    break;
                case PayPlayers:
                    int moneyToPayOtherPlayers = Integer.valueOf(Argument1TextField.getText());
                    card = new ChanceCard(CardName, action, moneyToPayOtherPlayers);
                    break;

                default:
                    card = new ChanceCard(CardName, action);
                    break;

            }

        } else {
            switch (action) {
                case AdvanceToLocation:
                    Space space = (Space) Argument1ComboBox.getSelectionModel().getSelectedItem();
                    card = new CommunityChestCard(CardName, action, space);
                    break;
                case CollectMoneyFromBank:
                    int moneyToBeGiven = Integer.valueOf(Argument1TextField.getText());
                    card = new CommunityChestCard(CardName, action, moneyToBeGiven);
                    break;
                case PayBank:
                    int moneyToPay = Integer.valueOf(Argument1TextField.getText());
                    card = new CommunityChestCard(CardName, action, moneyToPay);
                    break;
                case CollectFromPlayers:
                    int moneyToReceiveFromPlayers = Integer.valueOf(Argument1TextField.getText());
                    card = new CommunityChestCard(CardName, action, moneyToReceiveFromPlayers);
                    break;
                case PayBankDependingOnHousesAndHotelsOwned:
                    int moneyToPayPerHouse = Integer.valueOf(Argument1TextField.getText());
                    int moneyToPayPerHotel = Integer.valueOf(Argument1TextField.getText());
                    card = new CommunityChestCard(CardName, action, moneyToPayPerHouse, moneyToPayPerHotel);
                    break;
                case GoBackSpaces:
                    int spacesToMoveBack = Integer.valueOf(Argument1TextField.getText());
                    card = new CommunityChestCard(CardName, action, spacesToMoveBack);
                    break;
                case PayPlayers:
                    int moneyToPayOtherPlayers = Integer.valueOf(Argument1TextField.getText());
                    card = new CommunityChestCard(CardName, action, moneyToPayOtherPlayers);
                    break;

                default:
                    card = new CommunityChestCard(CardName, action);
                    break;

            }
        }
        if (previousCard == null) {
            Deck.getInstance().addCard(card);
        } else {
            Deck.getInstance().replaceCard(previousCard, card);
        }
        previousCard = null;

        CardInfo.getScene().getWindow().hide();
    }

    public void setPreviousCard(Card card) {
        this.previousCard = card;

        CardNameTextField.setText(card.getName());
        CardAction action = card.getAction();
        CardActionComboBox.getSelectionModel().select(action);
        switch (action) {

            case AdvanceToLocation:
                Argument1ComboBox.getSelectionModel().select(card.getLocation());
            case CollectMoneyFromBank:
                Argument1TextField.setText(Integer.toString(card.getFeeToPlayer()));
                break;
            case PayBank:
                Argument1TextField.setText(Integer.toString(card.getFeeToPlayer()));
                break;
            case CollectFromPlayers:
                Argument1TextField.setText(Integer.toString(card.getFeeToPlayer()));
                break;
            case PayBankDependingOnHousesAndHotelsOwned:
                Argument1TextField.setText(Integer.toString(card.getFeePerHouse()));
                Argument2TextField.setText(Integer.toString(card.getFeePerHotel()));
                break;
            case GoBackSpaces:
                Argument1TextField.setText(Integer.toString(card.getSpacesToMove()));
                break;
            case PayPlayers:
                Argument1TextField.setText(Integer.toString(card.getFeeToPlayer()));
                break;
        }
    }
}
