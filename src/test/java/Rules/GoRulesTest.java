package Rules;

import junit.framework.TestCase;

import java.nio.file.Paths;

/**
 * Created by userhp on 09/02/2016.
 */
public class GoRulesTest extends TestCase {


    public void testGetSalary() throws Exception {
        GoRules rules = new GoRules(Paths.get("").toAbsolutePath().toString() + "/src/main/LuaFiles/GoRules.lua");
        assertEquals(200, rules.getSalary());
    }
}