package Dice;


/**
 * Created by marc on 20/11/2015.
 */
public class DiceRoll {
    private int sumOfDiceRolls;
    private boolean reRoll;

    public DiceRoll(int sumOfDiceRolls, boolean reRoll){
    	this.sumOfDiceRolls = sumOfDiceRolls;
    	this.reRoll = reRoll;
    }
    public int getSumOfDiceRolls(){
    	return sumOfDiceRolls;
    }
    public boolean isReRoll(){
    	return reRoll;
    }





    
}


