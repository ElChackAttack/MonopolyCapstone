package Board;

import Players.Player;

/**
 * Created by userhp on 31/01/2016.
 */
public abstract class Ownable extends Space {
    private int cost;
    private int mortgagePrice;
    private boolean mortgaged;
    private Player owner;
    protected int moneyEarned;

    public int getCost() {
        return cost;
    }
    public void setMoneyEarned(int moneyEarned){
        this.moneyEarned = moneyEarned;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(int mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public int getMoneyEarned(){return moneyEarned;}
}
