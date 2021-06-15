package people;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.Test;

import people.Developer;

/**
 * There are 2 tests, for the Developer Test class.
 *<pre>
 * Test 1: test if range of generated destination floors is between 3 & 6.
 * Test 2: test if probability of changing floor works correctly. 
 * </pre>
 * @author Amandeep Dhaliwal
 * @see People
 * @see Developer 
 * @see Mugtome 
 * @see Goggles
 */


public class DeveloperTest {

	Random rand = new Random();
	float P = (float) 0.001;
	float Q = (float) 0.002;
	boolean expected = false;
	Developer testdev = new Developer("Developer",0,1,0,P,Q,0,rand,false,false);
	
	
	/**
	 * Test 1: test if range of generated destination floors is between 3 & 6.
	 */
	@Test
	public void destFloorRangeTest() {
			int output = testdev.generateDestFloor();
				// test if the generated floor is between 3 and 6 
				if(output >= 3 && output <= 6 ) {
					expected = true;
				}
		assertTrue(expected);
	}	
		

	/**
	 * Test 2: test if probability of changing floor works correctly. 
	 */
	@Test
	public void addProbabilityTest() {
		boolean ans = false;
		while (ans == false) {
			ans = testdev.addprobability(true);
		}
		assertEquals(true, ans);
	}
}
