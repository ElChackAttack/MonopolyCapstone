package Board;

import Players.Player;
import Rules.AllRules;
import Rules.Bank;

/**
 * Created by Lucy on 2018/04/05.
 */
public abstract class Space {
    private Group group;
    private int location;
    private String name;


    public Group getGroup(){
        return group;
    }
    
    protected void setGroup(Group newGroup){
        group = newGroup;
    }

    public int getLocation(){
        return location;
    }

    protected void setLocation(int loc){
        location=loc;
    }

    public abstract void onVisit(Player player);

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
