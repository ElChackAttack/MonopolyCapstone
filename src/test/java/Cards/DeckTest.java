package Cards;

import Cards.CardAction;
import Cards.ChanceCard;
import Cards.CommunityChestCard;
import Cards.Deck;
import junit.framework.TestCase;

/**
 * Created by userhp on 26/01/2016.
 */
public class DeckTest extends TestCase {

    private Deck deck = Deck.getInstance();
    public void setUp(){
        
        deck.initializeBlankDeck();
    }
    public void testInitialiseEmptyDeck(){
       
        assertTrue(deck.drawChanceCard() == null);
        assertTrue(deck.drawCommunityChestCard() == null);
    }
    public void testAddingChanceCardToDeck(){
       
        ChanceCard card = new ChanceCard("Test 1", CardAction.GoBackSpaces);
        deck.addCard(card);
        assertEquals(card,deck.drawChanceCard());
    }
    public void testAddingSeveralChanceCardToDeck(){
       
        ChanceCard card = new ChanceCard("Test 1", CardAction.GoBackSpaces);
        ChanceCard card1 = new ChanceCard("Test 2", CardAction.GoBackSpaces);
        ChanceCard card2 = new ChanceCard("Test 3", CardAction.GoBackSpaces);
        ChanceCard card3 = new ChanceCard("Test 4", CardAction.GoBackSpaces);
        ChanceCard card4 = new ChanceCard("Test 5", CardAction.GoBackSpaces);
        deck.addCard(card);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        assertEquals(card,deck.drawChanceCard());
        assertEquals(card1,deck.drawChanceCard());
        assertEquals(card2,deck.drawChanceCard());
        assertEquals(card3,deck.drawChanceCard());
        assertEquals(card4,deck.drawChanceCard());

    }
    public void testAddingCommunityChestCardsToDeck(){
       
        CommunityChestCard card = new CommunityChestCard("Test 1", CardAction.GoBackSpaces);
        deck.addCard(card);
        assertEquals(card,deck.drawCommunityChestCard());
    }
    public void testAddingSeveralCommunityChestCardsToDeck(){
       
        CommunityChestCard card = new CommunityChestCard("Test 1", CardAction.GoBackSpaces);
        CommunityChestCard card1 = new CommunityChestCard("Test 2", CardAction.GoBackSpaces);
        CommunityChestCard card2 = new CommunityChestCard("Test 3", CardAction.GoBackSpaces);
        CommunityChestCard card3 = new CommunityChestCard("Test 4", CardAction.GoBackSpaces);
        CommunityChestCard card4 = new CommunityChestCard("Test 5", CardAction.GoBackSpaces);
        deck.addCard(card);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        assertEquals(card,deck.drawCommunityChestCard());
        assertEquals(card1,deck.drawCommunityChestCard());
        assertEquals(card2,deck.drawCommunityChestCard());
        assertEquals(card3,deck.drawCommunityChestCard());
        assertEquals(card4,deck.drawCommunityChestCard());

    }
    public void testShuffleFunction(){
       
        ChanceCard card = new ChanceCard("Test 1", CardAction.GoBackSpaces);
        ChanceCard card1 = new ChanceCard("Test 2", CardAction.GoBackSpaces);
        ChanceCard card2 = new ChanceCard("Test 3", CardAction.GoBackSpaces);
        ChanceCard card3 = new ChanceCard("Test 4", CardAction.GoBackSpaces);
        ChanceCard card4 = new ChanceCard("Test 5", CardAction.GoBackSpaces);
        CommunityChestCard card5 = new CommunityChestCard("Test 1", CardAction.GoBackSpaces);
        CommunityChestCard card6 = new CommunityChestCard("Test 2", CardAction.GoBackSpaces);
        CommunityChestCard card7 = new CommunityChestCard("Test 3", CardAction.GoBackSpaces);
        CommunityChestCard card8 = new CommunityChestCard("Test 4", CardAction.GoBackSpaces);
        CommunityChestCard card9 = new CommunityChestCard("Test 5", CardAction.GoBackSpaces);
        deck.addCard(card);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);
        deck.addCard(card7);
        deck.addCard(card8);
        deck.addCard(card9);
        deck.shuffleDecks();
        


    }

}