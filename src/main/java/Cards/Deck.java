package Cards;

import Board.BoardHelper;

import java.io.*;
import java.util.*;

/**
 * Created by userhp on 26/01/2016.
 */
public  class Deck {
    private  LinkedList<CommunityChestCard> CommunityChestDeck;
    private  LinkedList<ChanceCard> ChanceDeck;

    private static Deck instance = new Deck();
    private Deck(){}

    public static Deck getInstance() {
        return instance ;
    }


    public  void shuffleDecks() {
        Collections.shuffle(CommunityChestDeck);
        Collections.shuffle(ChanceDeck);
    }

    public void initializeDeck(String csvFile) {
        initializeBlankDeck();
        File in = new File(csvFile);
        try {
            FileReader fr;
            BufferedReader br;
            fr = new FileReader(in);
            br = new BufferedReader(fr);
            int size = 0;

            while (!(br.readLine() == null)) size++;
            fr = new FileReader(in);
            br = new BufferedReader(fr);


            br.readLine();
            String line = br.readLine();
            while (!(line == null)) {
                String[] splittedString = line.split(",");
                if (splittedString[0].equalsIgnoreCase("Chance")) {
                    ChanceDeck.add(generateChanceCard(splittedString));
                } else {
                    CommunityChestDeck.add(generateCommunityChestCard(splittedString));
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	shuffleDecks();
    }

    private ChanceCard generateChanceCard(String[] splittedString) {
        CardAction action = parseCardAction(splittedString[1]);
        ChanceCard card;

        String name = splittedString[2];
        switch (action) {

            case AdvanceToLocation:
                card = new ChanceCard(name, action, BoardHelper.getInstance().getSpaceOnBoard(splittedString[3]));
                break;
            case CollectMoneyFromBank:
                card = new ChanceCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            case PayBank:
                card = new ChanceCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            case CollectFromPlayers:
                card = new ChanceCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            case PayBankDependingOnHousesAndHotelsOwned:
                card = new ChanceCard(name, action, Integer.parseInt(splittedString[3]), Integer.parseInt(splittedString[4]));
                break;
            case GoBackSpaces:
                card = new ChanceCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            default:
                card = new ChanceCard(name, action);
        }
        return card;
    }

    private CommunityChestCard generateCommunityChestCard(String[] splittedString) {
        CardAction action = parseCardAction(splittedString[1]);
        CommunityChestCard card;

        String name = splittedString[2];
        switch (action) {

            case AdvanceToLocation:
                card = new CommunityChestCard(name, action, BoardHelper.getInstance().getSpaceOnBoard(splittedString[3]));
                break;
            case CollectMoneyFromBank:
                card = new CommunityChestCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            case PayBank:
                card = new CommunityChestCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            case CollectFromPlayers:
                card = new CommunityChestCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            case PayBankDependingOnHousesAndHotelsOwned:
                card = new CommunityChestCard(name, action, Integer.parseInt(splittedString[3]), Integer.parseInt(splittedString[4]));
                break;
            case GoBackSpaces:
                card = new CommunityChestCard(name, action, Integer.parseInt(splittedString[3]));
                break;
            default:
                card = new CommunityChestCard(name, action);
        }
        return card;
    }

    private CardAction parseCardAction(String cardActionString) {
        CardAction cardAction = null;
        for (CardAction action : CardAction.values()) {
            if (action.toString().equals(cardActionString)) {
                cardAction = action;
            }

        }
        return cardAction;
    }

    public  void initializeBlankDeck(){
        CommunityChestDeck = new LinkedList<CommunityChestCard>();
        ChanceDeck = new LinkedList<ChanceCard>();
    }
    public  ChanceCard drawChanceCard(){
        ChanceCard card = null;
        try{
            card = ChanceDeck.pop();
        }
        catch(NoSuchElementException e){
            System.out.println("No Chance Cards in Deck");
        }
        return card;
    }

    public void addCard(Card card) {
        if (card instanceof ChanceCard){
            ChanceDeck.addLast((ChanceCard) card);
        }
        else{
            CommunityChestDeck.addLast((CommunityChestCard) card);
        }
    }
    public  CommunityChestCard drawCommunityChestCard(){
        CommunityChestCard card = null;
        try{
            card = CommunityChestDeck.pop();
        }
        catch(NoSuchElementException e){
            System.out.println("No Community Chest cards in Deck");
        }
        return card;
    }


    public LinkedList<CommunityChestCard> getCommunityChestDeck() {
        return CommunityChestDeck;
    }

    public LinkedList<ChanceCard> getChanceDeck() {
        return ChanceDeck;
    }

    public void replaceCard(Card previousCard, Card card) {
        if (previousCard instanceof ChanceCard) {
            int indexOfPreviousCard = ChanceDeck.indexOf(previousCard);
            ChanceDeck.set(indexOfPreviousCard, (ChanceCard) card);
        } else {
            int indexOfPreviousCard = CommunityChestDeck.indexOf(previousCard);
            CommunityChestDeck.set(indexOfPreviousCard, (CommunityChestCard) card);
        }
    }

    public void removeCard(Card card) {
        if (card instanceof ChanceCard) {
            ChanceDeck.remove(card);
        } else {
            CommunityChestDeck.remove(card);
        }
    }
}
