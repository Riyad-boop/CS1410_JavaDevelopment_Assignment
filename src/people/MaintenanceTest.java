package people;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.Test;

import people.Maintenance;


/**
 * There are 2 tests, for the Maintenance Test class.
 *<pre>
 * Test 1: checks if the Maintenance crew set destination to 0 when then want to leave
 * Test 2: checks if add_probability works, which handles generating probabilities to make the crew leave the building 
 * </pre>
 * 
 * @author Amandeep Dhaliwal
 * @see People
 * @see Maintanence 
 */

public class MaintenanceTest {

	Random rand = new Random();
	float P = (float) 0.001;
	float Q = (float) 0.002;
	int expected = 0;
	int workcounter = 120;
	Maintenance testmain = new Maintenance("Maintenance",0,4,6,P,Q,0,rand,true,false);
	
	
	/**
	 * Test 1: checks if the Maintenance crew set destination to 0 when then want to leave
	 */
	@Test
	public void leaveBuildingTest() {
		int output = testmain.generateDestFloor();
		assertEquals(expected, output);
	}
	
	/**
	 * Test 2: checks if add_probability works, which handles generating probabilities to make the crew leave the building 
	 */
	@Test
	public void addProbabilityTest() {
		boolean ans = false;
		while (testmain.workcounter <= 240 && ans == false) {
			 ans = testmain.addprobability(true);
		}
		
		assertEquals(true, ans);
		
	}

}