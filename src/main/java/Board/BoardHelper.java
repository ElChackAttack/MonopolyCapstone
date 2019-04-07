package Board;

import Players.Player;
import Rules.AllRules;

import java.io.*;
import java.util.Vector;

/**
 * Contains the layout of all the spaces within the board.
 *
 * Created by marc on 27/12/2015.
 */
public class BoardHelper {

    private Vector<Space> spaces;
    private static BoardHelper instance = new BoardHelper();

    private BoardHelper() {
    }

    public static BoardHelper getInstance() {
        return instance;
    }
    
    public void populateBoard(String fileName) {
        File in = new File(fileName);
        try {
            FileReader fr;
            BufferedReader br;
            fr = new FileReader(in);
            br = new BufferedReader(fr);
            int size = 0;
            
            while(!(br.readLine()== null))size++;
            fr = new FileReader(in);
            br = new BufferedReader(fr);
            spaces = new Vector<Space>(size-1);

            br.readLine();
            String line =br.readLine();
            while(!(line== null)){

                String [] splittedstring = line.split(",");
                int loc = Integer.parseInt(splittedstring[0])-1;
                Space space = generateSpace(splittedstring, loc);

                spaces.add(loc,space);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Space generateSpace(String[] splittedstring, int loc) {
        String name = splittedstring[1];
        Group group = getGroup(splittedstring[2]);

        int cost = Integer.parseInt(splittedstring[3]);
        int mtg = Integer.parseInt(splittedstring[4]);
        int houseCost = Integer.parseInt(splittedstring[5]);
        int baseRent = Integer.parseInt(splittedstring[6]);
        int oneHouse = Integer.parseInt(splittedstring[7]);
        int twoHouse = Integer.parseInt(splittedstring[8]);
        int threeHouse = Integer.parseInt(splittedstring[9]);
        int fourHouse = Integer.parseInt(splittedstring[10]);
        int hotelRent = Integer.parseInt(splittedstring[11]);

        Space space = null;
        switch(group) {

            case GO:
                space = new GO(name,loc,group);
                break;

            case Jail:
                space = new Jail(name,loc,group);
                break;

            case Tax:
                space = new Tax(name, loc,cost);
                break;

            case Chance:
               space = new Chance(name,loc,group);
                break;


            case CommunityChest:
               space = new CommunityChest(name,loc,group);
                break;


            case Station:
                space = new Station(name,loc,group,cost,mtg);
                break;

            case Utility:
                space = new Utilities(name,loc,group,cost,mtg);
                break;

            case GoToJail:
                space = new GoToJail(name,loc,group);
                break;


            case FreeParking:
                space = new FreeParking(name,loc,group);
                break;

            default:
                space = new Property(name,group,loc,baseRent,cost,mtg,houseCost,oneHouse,twoHouse,threeHouse,fourHouse,hotelRent);
                break;

        }
        return space;
    }

    private Group getGroup(String anObject) {
        Group group = null;
        for(Group g : Group.values()){
            if(g.toString().equals(anObject)){
                group = g;
            }

        }
        return group;
    }
    public Vector<Space> getAllSpaces(){
        return spaces;
    }
    public Space getSpaceOnBoard(int loc){
        return spaces.elementAt(loc);
    }
    public Space getSpaceOnBoard(String name){
        for (Space s : spaces ) {
            if (s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        System.out.println("Space with that name not found "+ name);
        return null;

    }
    public int getLocationOfSpace(Space space){
        return spaces.indexOf(space);
    }

    public Space moveToSpace(Player playerToMove, int spacesToMove) {
        //Needs to check if movesPast go
        int newLocation = this.getLocationOfSpace(playerToMove.getCurrentLocation()) + spacesToMove;
        if (newLocation < 0) {
            newLocation = spaces.size() + newLocation;
        } else if (newLocation >= spaces.size()) {
            if (ParameterFetch.getSalaryPerNumOfTurn() == 0) {
                playerToMove.receiveMoney(AllRules.getGoRules().getSalary(playerToMove));
            }
            newLocation = newLocation % (spaces.size());
        }
        try {
            spaces.elementAt(newLocation);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
        return spaces.elementAt(newLocation);
    }
    public Space moveToNearestStation(Space currentLocation){
        int locationInVector = this.getLocationOfSpace(currentLocation);
        for (Space space : spaces.subList(locationInVector,spaces.size())) {
            if(space.getGroup() == Group.Station){
                return space;
            }

        }
        //In case the nearest station is past go.
        for (Space space : spaces.subList(0,locationInVector)) {
            if(space.getGroup() == Group.Station){
                return space;
            }

        }
        return null;
    }
    public Space moveToNearestUtility(Space currentLocation){
        int locationInVector = this.getLocationOfSpace(currentLocation);
        for (Space space : spaces.subList(locationInVector,spaces.size())) {
            if(space.getGroup() == Group.Utility){
                return space;
            }

        }
        //In case the nearest station is past go.
        for (Space space : spaces.subList(0,locationInVector)) {
            if(space.getGroup() == Group.Utility){
                return space;
            }

        }
        return null;
    }

    public int amountOfSpacesInGroup(Group group) {
        int spaceCount = 0;
        for (Space space : spaces) {
            if (space.getGroup().equals(group)) spaceCount++;
        }
        return spaceCount;
    }


    public Vector<Space> getAllSpacesOfGroup(Group g) {
        Vector<Space> spacesInGroup = new Vector<Space>();
        for (Space space : spaces) {
            if (space.getGroup().equals(g)) spacesInGroup.add(space);
        }
        return spacesInGroup;
    }

    public void replaceSpaceOnBoard(Space lastSpaceLoaded, Space newSpace) {
        int index = spaces.indexOf(lastSpaceLoaded);
        spaces.set(index, newSpace);


    }
}
