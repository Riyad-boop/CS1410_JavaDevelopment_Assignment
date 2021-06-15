package people;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Runs all tests for the "People" package, which includes all people such as: Client, Developer (Mugtome/Goggle) , Employee, Maintenance crew 
 * @author Amandeep Dhaliwal
 * @see ClientTest
 * @see DeveloperTest
 * @see EmployeeTest
 * @see MaintanenceTest
 */

@RunWith(Suite.class)
@SuiteClasses({ ClientTest.class, DeveloperTest.class, EmployeeTest.class, MaintenanceTest.class })
public class AllTestPeople {

}
