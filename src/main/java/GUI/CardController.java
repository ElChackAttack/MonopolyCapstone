package GUI;

import Cards.Card;
import Cards.Deck;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by userhp on 28/03/2016.
 */
public class CardController {

    public GridPane CardList;
    public Text TypeOfCards;
    public ListView Cards;
    public Button addCard;
    public Button editCard;
    public Button deleteCard;

    public void initialize() {

        Cards.setCellFactory(lv -> new ListCell<Card>() {
            @Override
            public void updateItem(Card item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getName(); // get text from item
                    setText(text);
                }
            }
        });
        CardList.setVisible(false);
    }

    public void addCardToDeck(Event event) {
        Parent root;
        String title = "";
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (TypeOfCards.getText().contains("Chance")) {
            title = "New Chance Card";
        } else {
            title = "New Community Chest Card";
        }
        try {
            root = fxmlLoader.load(getClass().getClassLoader().getResource("guiCardInformationDesign.fxml"));
            Stage stage;
            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    updateList();
                }
            });

            //hide this current window (if this is whant you want
            //((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editCardInDeck(Event event) {
        Parent root;
        String title = ((Card) Cards.getSelectionModel().getSelectedItem()).getName();
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("guiCardInformationDesign.fxml"));
            root = fxmlLoader.load();
            Stage stage;
            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {

                    updateList();
                }
            });

            //hide this current window (if this is whant you want
            //((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Card card = (Card) Cards.getSelectionModel().getSelectedItem();

        ((CardInformationController) fxmlLoader.getController()).setPreviousCard(card);


    }

    public void deleteCardFromDeck(Event event) {
        Deck.getInstance().removeCard((Card) Cards.getSelectionModel().getSelectedItem());
        updateList();
    }

    private void updateList() {
        if (TypeOfCards.getText().contains("Chance")) {
            ArrayList<Card> CardNames = new ArrayList<>(Deck.getInstance().getChanceDeck());
            Cards.setItems(FXCollections.observableArrayList(CardNames));
        } else {
            ArrayList<Card> CardNames = new ArrayList<>(Deck.getInstance().getCommunityChestDeck());
            Cards.setItems(FXCollections.observableArrayList(CardNames));
        }
    }
}
