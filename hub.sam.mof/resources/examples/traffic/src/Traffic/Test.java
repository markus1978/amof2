package Traffic;

import junit.framework.TestCase;

public class Test extends TestCase {

    public Test() {
        super("Traffic Light Example");
    }

    public void testWithOutAs() {
        try {
            Simulator.main(null);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assertTrue(false);
        }
    }

    public void testWithAs() {
        try {
            ASSimulator.main(null);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assertTrue(false);
        }
    }

    /* TODO
    public void testWithAsWithDomainModel() {
        try {
            ASSimulatorWithDomainModel.main(null);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assertTrue(false);
        }
    }
    */
}
