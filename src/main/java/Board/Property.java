package Board;

import Logger.PropertyLogger;
import Players.Player;
import Rules.AllRules;

/**
 * Created by marc on 20/11/2015.
 */
public class Property extends Ownable {

    private int baseRent;
    private int rent;
    private int oneHouseRent;
    private int twoHouseRent;
    private int threeHouseRent;
    private int fourHouseRent;
    private int hotelRent;
    private int houses;
    private int hotels;
    private int houseCost;



    public Property(String name, Group group, int location, int baseRent, int cost, int mortgagePrice,int houseCost, int oneHouseRent, int twoHouseRent, int threeHouseRent, int fourHouseRent, int hotelRent){
        super.setGroup(group);
        super.setName(name);
        super.setLocation(location);
        super.setCost(cost);
        super.setMortgagePrice(mortgagePrice);
        this.baseRent = baseRent ;
        this.houseCost = houseCost;
        this.oneHouseRent = oneHouseRent;
        this.twoHouseRent = twoHouseRent;
        this.threeHouseRent = threeHouseRent;
        this.fourHouseRent = fourHouseRent;
        this.hotelRent = hotelRent;
        super.setMortgaged(false);

    }


    public void setHouses(int house) {
        houses = house;
    }

    public void setHotels(int hotels) {
        this.hotels = hotels;
    }
    public void addHouse() {
        this.houses = this.getHouses() + 1;
    }


    public void addHotel() {
        this.hotels = this.getHotels() + 1;
    }




    @Override
    public void onVisit(Player player) {
        if(super.getOwner() == null){
            if(player.wantsToBuyPropertyForPrice(this,super.getCost())){
                player.spendMoney(super.getCost());
                player.addProperty(this);
                super.setOwner(player);
            }
            else{
                AllRules.getBankRules().auctionProperty(this);
            }

        }
        else if(super.isMortgaged() || getOwner().equals(player)){
            //No Rent is paid
        }
        else{
            calculateRent();
            AllRules.getBankRules().payPlayer(player, super.getOwner(), rent);
            moneyEarned+=rent;

        }

    }

    private void calculateRent(){
       if(getHotels() >0){
           rent = hotelRent;
       }
       //Should be else if to incorperate the rule that the base rent doubles if owner has all the properties in group.

        else{
           switch(getHouses()){
               case 4:
                   rent = fourHouseRent;
                   break;
               case 3:
                   rent = threeHouseRent;
                   break;
               case 2:
                   rent = twoHouseRent;
                   break;
               case 1:
                   rent = oneHouseRent;
                   break;
               default:
                   if(super.getOwner().ownsSpacesOfGroup(super.getGroup()) == 3){
                       rent = baseRent*2;
                   }
                   else{
                       rent = baseRent;
                   }

                   break;
           }
       }
    }

    public int getHouseCost() {
        return houseCost;
    }


    public void removeHouse() {
        houses--;
    }

    public void removeHotel() {
        hotels--;
    }

    public int getHouses() {
        return houses;
    }

    public int getHotels() {
        return hotels;
    }


    public int getBaseRent() {
        return baseRent;
    }

    public int getOneHouseRent() {
        return oneHouseRent;
    }

    public int getTwoHouseRent() {
        return twoHouseRent;
    }

    public int getThreeHouseRent() {
        return threeHouseRent;
    }

    public int getFourHouseRent() {
        return fourHouseRent;
    }

    public int getHotelRent() {
        return hotelRent;
    }
}
