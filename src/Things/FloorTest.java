package Things;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import Things.Floor;
import people.Client;
import people.Employee;
import people.Goggles;
import people.Maintenance;
import people.Mugtome;
import people.Person;


/**
 * 
/**
 * There are 14 tests, for the Floor Test class.
 *<pre>
 * Test 1: if Employee decides to change floor, tests to see if they have called the lift.
 * Test 2: if Client decides to change floor, tests to see if they have called the lift.
 * Test 3: if Maintenance crew decides to change floor, tests to see if they have called the lift.
 * Test 4: if Goggle decides to change floor, tests to see if they have called the lift.
 * Test 5: if Mugtome decides to change floor, tests to see if they have called the lift.
 * Test 6: if everyone decides to change floor, tests to see if they have called the lift.
 * Test 7: if everyone decides to change floor, tests to see if they have called the lift.
 * Test 8: if nobody people decides to change floor, tests to see if they have called the lift.
 * Test 9: if any person exists in the unloading list [queue.get(7) ,is full of people] , then tests if people are unloaded to floor correctly.
 * Test 10: if any person exists in the unloading list [queue.get(7)] ,but the lift is on the wrong floor, then tests if people stay in lift.
 * Test 11: if nobody is in waiting to get off the lift and the lift is on (this) floor, then test if people stay in lift. 
 * Test 12: if someone is on the correct floor, check if their "at correct floor" has been set to true.
 * Test 13: if a Client is on floor 0 and wants to leave, then check if they are deleted.
 * Test 14:	if a Maintenance crew is on floor 0 and wants to leave, then check if they are deleted
 * </pre>
 * 
 * @author Lily Martin
 * @see Floor
 * @see Building
 */
public class FloorTest {

	ArrayList<Person> waiting = new ArrayList<Person>();
	HashMap<Integer,ArrayList<Person>> Queue = new HashMap<Integer,ArrayList<Person>>();
	
	int floornum = 2;
	Floor floors = new Floor(floornum);
	int liftFloor = 0;
	float P = (float) 0.001;
	float Q = (float) 0.002;
	int expectederrors = 0;
	Random rand = new Random();
	Person pEmployee = new Employee("Employee",0,1,floornum,P,Q,0,rand,false,false);
	Person pClient = new Client("Client",0,0,floornum,P,Q,0,rand,true,true);
	Person pMaintenance = new Maintenance("Maintenance",0,4,floornum,P,Q,0,rand,true,true);
	Person pGoggle = new Goggles("Goggles",0,1,floornum,P,Q,0,rand,false,false);
	Person pMugtome = new Mugtome("mugtome",0,1,floornum,P,Q,0,rand,false,false);
	
	/**
	 * Test 1: if Employee decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if Employee decides to change floor, tests to see if they have called the lift.
	public void floorChangeEmployeeTest() {
		int newDestination = 0; 
		pEmployee.setdestFloor(newDestination);
		floors.Persons.add(pEmployee);
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() > 0)){
				fail("The lift call was not processed");
			}
	}
	/**
	 * Test 2: if Client decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if Client decides to change floor, tests to see if they have called the lift.
	public void floorChangeClientTest() {
		int newDestination = 0; 
		pClient.setdestFloor(newDestination);
		floors.Persons.add(pClient);
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() > 0)){
				fail("The lift call was not processed");
			}
	}
	/**
	 * Test 3: if Maintenance crew decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if Maintenance crew decides to change floor, tests to see if they have called the lift.
	public void floorChangeCrewTest() {
		int newDestination = 0; 
		pMaintenance.setdestFloor(newDestination);
		floors.Persons.add(pMaintenance);
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() > 0)){
				fail("The lift call was not processed");
			}
	}
	/**
	 * Test 4: if Goggle decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if Goggle decides to change floor, tests to see if they have called the lift.
	public void floorChangeGoggleTest() {
		int newDestination = 0; 
		pGoggle.setdestFloor(newDestination);
		floors.Persons.add(pGoggle);
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() > 0)){
				fail("The lift call was not processed");
			}
	}
	/**
	 * Test 5: if Mugtome decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if Mugtome decides to change floor, tests to see if they have called the lift.
	public void floorChangeMugtomeTest() {
		int newDestination = 0; 
		pMugtome.setdestFloor(newDestination);
		floors.Persons.add(pMugtome);
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() > 0)){
				fail("The lift call was not processed");
			}
	}
	/**
	 * Test 6: if everyone decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if everyone decides to change floor, tests to see if they have called the lift.
	public void floorChangeMuliplePeopleTest() {
		int newDestination = 0; 
		pMugtome.setdestFloor(newDestination);
		pGoggle.setdestFloor(newDestination);
		pMaintenance.setdestFloor(newDestination);
		pClient.setdestFloor(newDestination);
		pEmployee.setdestFloor(newDestination);
		
		floors.Persons.add(pMugtome);
		floors.Persons.add(pGoggle);
		floors.Persons.add(pMaintenance);
		floors.Persons.add(pClient);
		floors.Persons.add(pEmployee);
		
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() == 5)){
				fail("The lift call was not processed correctly, 5 people should have called the lift");
			}
	}
	/**
	 * Test 7: if everyone decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if some people decides to change floor, tests to see if they have called the lift.
	public void DoNotfloorChangeSomePeopleTest() {
		int newDestination = 0; 
		pMugtome.setdestFloor(newDestination);
		pMaintenance.setdestFloor(newDestination);
		pClient.setdestFloor(newDestination);
		
		
		floors.Persons.add(pMugtome);
		floors.Persons.add(pGoggle);
		floors.Persons.add(pMaintenance);
		floors.Persons.add(pClient);
		floors.Persons.add(pEmployee);
		
		floors.tick(liftFloor, Queue);
		
			if (! (Queue.get(floornum).size() == 3)){
				fail("The lift call was not processed, 3 people should have called the lift ");
			}
	}
	
	/**
	 * Test 8: if nobody people decides to change floor, tests to see if they have called the lift.
	 */
	@Test // if nobody people decides to change floor, tests to see if they have called the lift.
	public void DoNotfloorChangeAnyPersonTest() {
	
		
		floors.Persons.add(pMugtome);
		floors.Persons.add(pGoggle);
		floors.Persons.add(pMaintenance);
		floors.Persons.add(pClient);
		floors.Persons.add(pEmployee);
		
		floors.tick(liftFloor, Queue);
		
		try {
			if (! (Queue.get(floornum).size() == 0)){
				fail("The lift call was not processed, 0 people should have called the lift");
			}
		}
			catch (java.lang.NullPointerException exception){
				assertTrue(true);
			}
	}
	

	/**
	 * Test 9: if any person exists in the unloading list [queue.get(7) ,is full of people] , then tests if people are unloaded to floor correctly.
	 */
	@Test // if any person exists in the unloading list [queue.get(7) ,is full of people] , then tests if people are unloaded to floor correctly 
	public void unloadingLiftTest() {
		Queue.put(7, waiting);														//puts the waiting list inside Queue HashMap
		Queue.get(7).add(pEmployee);
		Queue.get(7).add(pMugtome);
		Queue.get(7).add(pGoggle);
		Queue.get(7).add(pClient);
		Queue.get(7).add(pMaintenance);
		floors.QueueToFloor(Queue.get(7));
		
			assertTrue(floors.Persons.size() == 5);
		
	}
	/**
	 * Test 10: if any person exists in the unloading list [queue.get(7)] ,but the lift is on the wrong floor, then tests if people stay in lift. .
	 */
	@Test //  if any person exists in the unloading list [queue.get(7)] ,but the lift is on the wrong floor, then tests if people stay in lift. 
	public void unloadToWrongFloorTest() {
		Queue.put(7, waiting);														//puts the waiting list inside Queue HashMap
		Queue.get(7).add(pEmployee);
		Queue.get(7).add(pMugtome);
		Queue.get(7).add(pGoggle);
		Queue.get(7).add(pClient);
		Queue.get(7).add(pMaintenance);
		liftFloor = 0;
			assertTrue(floors.Persons.size() == 0);
	}
	/**
	 * Test 11: if nobody is in waiting to get off the lift and the lift is on (this) floor, then test if people stay in lift. 
	 */
	@Test // if nobody is in waiting to get off the lift and the lift is on (this) floor, then test if people stay in lift. 
	public void unloadIsEmptyTest() {
		Queue.put(7, waiting);	
		liftFloor = 2;
			assertTrue(floors.Persons.size() == 0);
	}
	/**
	 * Test 12: if someone is on the correct floor, check if their "at correct floor" has been set to true.
	 */
	@Test // if someone is on the correct floor, check if their "at correct floor" has been set to true
	public void AtCorrectFloorStatusTest() {
		floors.Persons.add(pEmployee);
		floors.TickAllPeople();
			floors.Persons.forEach((person) -> { 
				if(person.atfloor == false) {
					expectederrors ++;
				}
			});
		assertEquals(0,expectederrors);
	}
	/**
	 * Test 13: if a Client is on floor 0 and wants to leave, then check if they are deleted.
	 */
	@Test // if a Client is on floor 0 and wants to leave, then check if they are deleted
	public void RemoveClientTest() {
		Floor floor0 = new Floor(0);
		floor0.Persons.add(pClient);
		floor0.TickAllPeople();
		
			if(floor0.Persons.size() != 0){
				fail("Client not removed");
			}
		}
	/**
	 * Test 14: if a Maintenance crew is on floor 0 and wants to leave, then check if they are deleted.
	 */
	@Test // if a Maintenance crew is on floor 0 and wants to leave, then check if they are deleted
	public void RemoveCrewTest() {
		Floor floor0 = new Floor(0);
		floor0.Persons.add(pMaintenance);
		floor0.TickAllPeople();
		
			if(floor0.Persons.size() != 0){
				fail("Maintenance crew not removed");
			}
		}

}
