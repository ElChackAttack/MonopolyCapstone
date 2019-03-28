package Board;

import java.util.Comparator;

/**
 * Created by userhp on 25/02/2016.
 */
public class OwnableComparator implements Comparator<Ownable> {

    @Override
    public int compare(Ownable o1, Ownable o2) {
        int result = 0;
        if (o1.getCost() > o2.getCost()) {
            result = 1;
        } else if (o1.getCost() == o2.getCost()) {
            result = 0;
        } else {
            result = -1;
        }
        if (o1 instanceof Property && o2 instanceof Property) {
            if (((Property) o1).getHotels() > ((Property) o2).getHotels()) {
                result = 1;
            } else if (((Property) o2).getHotels() > ((Property) o1).getHotels()) {
                result = -1;
            } else if (((Property) o1).getHouses() > ((Property) o2).getHouses()) {
                result = 1;
            } else if (((Property) o1).getHouses() == ((Property) o2).getHouses()) {
                if (o1.getCost() > o2.getCost()) {
                    result = 1;
                } else if (o1.getCost() == o2.getCost()) {
                    result = 0;
                } else {
                    result = -1;
                }
            } else {
                result = -1;
            }

        }
        return result;
    }
}
