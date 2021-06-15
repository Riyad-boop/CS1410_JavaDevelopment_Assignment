package Things;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Runs all tests for the "Things" package, which includes components such as: Building,Floors and Lift.
 * @author Riyad K Rahman
 * @see Building
 * @see LIFT
 * @see Floor
 */

@RunWith(Suite.class)
@SuiteClasses({ BuildingTest.class, FloorTest.class, LIFTTest.class })
public class AllTestsComponents {

}
