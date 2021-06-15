package people;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.Test;

import people.Client;



/**
 * There are 3 tests, for the Client Test class.
 *<pre>
 * Test 1: checks if the client set destination to 0 when then want to leave
 * Test 2: if the client wants to pick a floor to go to, it checks if the floor is less than 3. 
 * Test 3: checks if add_probability works, which handles generating probabilities to make the client leave the building 
 * </pre>
 * 
 * @author Amandeep Dhaliwal
 * @see People
 * @see Client 
 */

public class ClientTest {

	Random rand = new Random();
	float P = (float) 0.001;
	float Q = (float) 0.002;
	int workcounter = 61;
	int expected = 0;
	boolean lessthan4 = false;

	
	/**
	 * Test 1: checks if the client set destination to 0 when then want to leave
	 */
	@Test // if work counter greater than 60 tests destination floor generated is 0
	public void leaveWhenFinishTest() {
		Client testcli = new Client("Client",workcounter,1,0,P,Q,0, rand,false,false);		
		int output = testcli.generateDestFloor();
		assertEquals(expected, output);
	}
	
	
	/**
	 * Test 2: if the client wants to pick a floor to go to, it checks if the floor is less than 3. 
	 */	
	@Test // if work counter less than 60 tests if destination floor generated is between 0 and 3.
	public void destFloorRangeTest() {
		workcounter = 59;
		Client testcli2 = new Client("Client",workcounter,1,0,P,Q,0, rand,false,false);		
		int output = testcli2.generateDestFloor();
		if(output >= 0 && output <= 3) {
			lessthan4 = true;
		}
		assertTrue(lessthan4);	
	}
	
	
	/**
	 * Test 3: checks if add_probability works, which handles generating probabilities to make the client leave the building 
	 */
	@Test // while work counter less than 180 tests if addProbability works.
	public void addProbabilityTest() {
		boolean ans = false;
		Client testcli3 = new Client("Client",workcounter,1,0,P,Q,0, rand,false,false);
		while (testcli3.workcounter <= 180 && ans == false) {
			 ans = testcli3.addprobability(true);
		}
		assertEquals(true, ans);	
	}
}
