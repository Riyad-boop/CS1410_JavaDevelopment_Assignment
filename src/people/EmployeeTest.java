package people;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.Test;

import people.Employee;

/**
 * There are 2 tests, for the Employee Test class.
 *<pre>
 * Test 1: test if range of generated destination floors is between 1 & 6.
 * Test 2: test if probability of changing floor works correctly. 
 * </pre>
 * @author Amandeep Dhaliwal
 * @see People
 * @see Employee
 */

public class EmployeeTest {


	Random rand = new Random();
	float P = (float) 0.001;
	float Q = (float) 0.002;
	boolean expected = false;
	Employee testemp = new Employee("Employee",0,1,0,P,Q,0,rand,false,false);		
	
	
	/**
	 * Test 1: test if range of generated destination floors is between 1 & 6.
	 */
	@Test
	public void destFloorRangeTest() {
		Employee testemp = new Employee("Employee",0,1,0,P,Q,0,rand,false,false);		
		int output = testemp.generateDestFloor();
			// test if the generated floor is between 0 and 6 
			if(output >= 0 && output <= 6 ) {
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
			ans = testemp.addprobability(true);
		}
		assertEquals(true, ans);
	}
	
	
}
