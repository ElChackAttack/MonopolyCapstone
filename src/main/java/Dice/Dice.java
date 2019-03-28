package Dice;


/**
 * Created by marc on 20/11/2015.
 */
public class Dice {
    private final int MIN = 1;
    private int MAX = 6;

	public Dice(){

    }
    public Dice(int sidedDice){
    	MAX = sidedDice;
    }
    public int rollTheDice(){

        return (int)(Math.random()*((MAX-MIN)+1) +MIN);

    }

    public int getMaxRoll() {
        return MAX;
    }
}
