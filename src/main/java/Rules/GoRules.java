package Rules;

/**
 * Created by Lucy on 2018/04/02.
 */
public class GoRules {
    private static int salary;

    public GoRules() {
        salary = 200;
    }

    public int getSalary() {
        return calculateSalary();
    }
    
    // salary calculated here
    private int calculateSalary() {
        return salary;
    }
}
