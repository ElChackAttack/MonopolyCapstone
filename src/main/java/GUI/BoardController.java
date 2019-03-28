package GUI;

import Board.*;
import Cards.Card;
import Cards.Deck;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

/**
 * Created by userhp on 28/03/2016.
 */
public class BoardController {
    public GridPane BoardGui;
    private CardController Cc;
    private SpaceInformationController Sic;

    public void initialize() {
        reloadButtons();


    }

    public void openSpaceInformation(Event event) {

        Button b = (Button) event.getSource();
        Sic.fillFields(b.getId());
        Sic.SpaceInfo.setVisible(true);
        Cc.CardList.setVisible(false);
        Sic.SaveChangesButton.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Sic.saveInformation(event);
                reloadButtons();
            }
        });

    }

    public void openChanceCards(Event event) {
        Sic.SpaceInfo.setVisible(false);
        ArrayList<Card> CardNames = new ArrayList<>(Deck.getInstance().getChanceDeck());
        Cc.TypeOfCards.setText("Chance Cards");
        Cc.Cards.setItems(FXCollections.observableArrayList(CardNames));
        Cc.CardList.setVisible(true);
    }

    public void openCommunityChestCards(Event event) {
        Sic.SpaceInfo.setVisible(false);
        ArrayList<Card> CardNames = new ArrayList<>(Deck.getInstance().getCommunityChestDeck());
        Cc.TypeOfCards.setText("Community Chest Cards");
        Cc.Cards.setItems(FXCollections.observableArrayList(CardNames));
        Cc.CardList.setVisible(true);
    }

    public void accessToControllers(CardController cc, SpaceInformationController sic) {
        Cc = cc;
        Sic = sic;

    }

    public void reloadButtons() {
        Vector<Space> allSpaces = BoardHelper.getInstance().getAllSpaces();
        int index = 0;
        for (Node n : BoardGui.getChildren()) {
            if (n instanceof Button) {
                String spaceName = allSpaces.get(index).getName();
                ((Button) n).setText(spaceName);
                String id = Integer.toString(allSpaces.get(index).getLocation());
                ((Button) n).setId(id);
                index++;

            }
        }
    }
}
