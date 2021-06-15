package Things;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Things.Building;
import people.Client;
import people.Developer;
import people.Employee;
import people.Goggles;
import people.Maintenance;
import people.Mugtome;
import people.Person;


/**
 * There are 19 tests, for the Building Test class.
 *<pre>
 * Test 1: Calculating averages, tests function to calculating average wait time, using people on different floors and in queue.
 * Test 2: Generating Clients test, tests to if clients are spawned on ground floor when the spawn condition is satisfied.
 * Test 3: Generate maintenance test, tests if maintenance crew spawns on ground floor. 
 * Test 4: test if the complaint increment of a client who is getting off the lift is not incremented.
 * Test 5: tests if the complaint increment of the maintenance crew who get of the lift is not incremented.
 * Test 6: tests if the complaint increment of the employee who are getting of the lift is not incremented.
 * Test 7: tests if the complaint increment of each Goggle who are getting of the lift is not incremented.
 * Test 8: tests if the complaint increment of each Mugtome who are getting of the lift is not incremented.
 * Test 9: if only the normal complaint counter is incremented when a crew exists on the waiting list.
 * Test 10: test if only the normal complaint counter is incremented when a employee exists on the waiting list.
 * Test 11: test if only the normal complaint counter is incremented when a goggle exists on the waiting list.
 * Test 12: test if only the normal complaint counter is incremented when a mugtome exists on the waiting list.
 * Test 13: test if both client complaint counter and the normal complaint counter is incremented works when a different people exist on the waiting list on a different floor.
 * Test 14: test checks if the correct number of people have been generated.
 * Test 15: test checks if the correct number of employees have been generated.
 * Test	16:	test checks if the correct number of Developers have been generated.
 * Test 17: test checks if the correct number of Mugtome developers have been generated.
 * Test 18: test checks if the correct number of Goggle developers have been generated.
 * </pre>
 * 
 * @author Amandeep Dhaliwal
 * @see Building 
 */


public class BuildingTest {
	
	Random rand = new Random();
	float P = (float) 0.001;
	float Q = (float) 1.000;		//to ensure the create Client spawn condition is always true
	Building btest = new Building(0, 0, 0, P, Q, 0);
	Person prsnEmployee = new Employee("Employee",0,1,0,P,Q,0,rand,false,false); 
	Person prsnClient = new Client("Client",0,1,0,P,Q,0,rand,false,false);
	Person prsnMaintanance = new Maintenance("Maintenance",0,4,0,P,Q,0,rand,false,false);
	Person prsnMugtome = new Mugtome("mugtome",0,1,0,P,Q,0,rand,false,false);
	Person prsnGoggle = new Goggles("Goggles",0,1,0,P,Q,0,rand,false,false);
	public ArrayList<Person> waitinglist = new ArrayList<Person>();		
	
	
	/**
	 * Test 1: Calculating averages, tests function to calculating average wait time, using people on different floors and in queue.
	 */
	@Test // test calculating average wait time.
	 public void CalculateAverageTest() {
		int expected = 6;
			
			prsnEmployee.totalWaitTime = 2;
			prsnClient.totalWaitTime = 4;
			prsnMaintanance.totalWaitTime = 6;
			prsnMugtome.totalWaitTime = 8;
			prsnGoggle.totalWaitTime = 10;
				
						btest.floors[0].AddPersonToFloor(prsnEmployee);
						btest.floors[1].AddPersonToFloor(prsnClient);
						btest.floors[4].AddPersonToFloor(prsnMugtome);
						btest.floors[3].AddPersonToFloor(prsnGoggle);
						btest.floors[6].AddPersonToFloor(prsnMaintanance);	
						
							waitinglist.add(prsnEmployee);
							waitinglist.add(prsnClient);
							waitinglist.add(prsnMaintanance);
							waitinglist.add(prsnMugtome);
							waitinglist.add(prsnGoggle);
								btest.Queue.put(7, waitinglist);
							
			btest.calculateAverageWait();
			int output = btest.AverageWaitingTime / (btest.totalAverageWait.size());  
		 assertEquals(expected,output);
	}
	
	/**
	 * Test 2: Generating Clients test, tests if clients are spawned on ground floor when the spawn condition is satisfied.
	 */
	@Test 	//Generate client test
	public void generateClientTest() {
			btest.Generateclients(rand);
			btest.tick = 1;
			btest.SpawnClients();
			assertTrue(btest.floors[0].Persons.get(0) instanceof Client);	
	}

	/**
	 * Test 3: Generate maintenance test, tests if maintenance crew spawns on ground floor.
	 */
	@Test	//Generate crew test
	public void generateMaintenanceTest() {
			btest.arrivalprobability = 1;		//to ensure the create crew condition is always true
			btest.Generatecrew();
		assertTrue(btest.floors[0].Persons.get(0) instanceof Maintenance);	
	}
	
	/**
	 *  Test 4: test if the complaint increment of a client who is getting off the lift is not incremented.
	 */
	@Test	// test if the complaint increment of a client who is getting off the lift is not incremented
	public void doNotIncrementComplaintClientTest() {
			btest.Generateclients(rand);
			btest.tick = 1;
			btest.SpawnClients();
			Person p = btest.floors[0].Persons.get(0);
			btest.Queue.put(7, btest.waiting);
			btest.Queue.get(7).add(p);								// waiting-list 7 is a placeholder to get off the lift. it is NOT a floor
			btest.Queue.get(7).get(0).complaintcounter = 61;		// set complaint counter above 60 so that a complaint is made
			
			btest.Complaintchecker(btest.Queue);			//calls the function and passes on test queue
			
		if(btest.complaintcounter > 0) {
			fail("complaint counter was unnecessarily incremented, person was getting off the lift. Not waiting on a floor!");
		}
		
	}
	/**
	 *  Test 5: tests if the complaint increment of the maintenance crew who get of the lift is not incremented.
	 */
	@Test	// test if the complaint increment of maintenance crew  who are getting off the lift is not incremented
	public void doNotIncrementComplaintMcrewTest() {
			btest.arrivalprobability = 1;		//to ensure the create crew condition is always true
			btest.Generatecrew();
			Person p = btest.floors[0].Persons.get(0);
			btest.Queue.put(7, btest.waiting);
			btest.Queue.get(7).add(p);								// waiting-list 7 is a placeholder to get off the lift. it is NOT a floor
			btest.Queue.get(7).get(0).complaintcounter = 61;		// set complaint counter above 60 so that a complaint is made
			
			btest.Complaintchecker(btest.Queue);			//calls the function and passes on test queue
			
		if(btest.complaintcounter > 0) {
			fail("complaint counter was unnecessarily incremented, person was getting off the lift. Not waiting on a floor!");
		}
		
	}
	/**
	 *  Test 6: tests if the complaint increment of the employee who are getting of the lift is not incremented.
	 */
	@Test	// test if the complaint increment of a employee who are getting off the lift is not incremented
	public void doNotIncrementComplaintEmployeeTest() {
			btest.CreatePeople(1, 0);								// creates employee
			Person p = btest.floors[0].Persons.get(0);
			btest.Queue.put(7, btest.waiting);
			btest.Queue.get(7).add(p);								// waiting-list 7 is a placeholder to get off the lift. it is NOT a floor
			btest.Queue.get(7).get(0).complaintcounter = 61;		// set complaint counter above 60 so that a complaint is made
			
			btest.Complaintchecker(btest.Queue);			//calls the function and passes on test queue
			
		if(btest.complaintcounter > 0) {
			fail("complaint counter was unnecessarily incremented, person was getting off the lift. Not waiting on a floor!");
		}
		
	}
	/**
	 *  Test 7: tests if the complaint increment of each Goggle who are getting of the lift is not incremented.
	 */
	@Test	// test if the complaint increment of the Goggle who are getting off the lift is not incremented
	public void doNotIncrementComplaintGoggleTest() {
			btest.CreatePeople(0, 1);								// creates 1 goggle
			Person p = btest.floors[0].Persons.get(0);
			btest.Queue.put(7, btest.waiting);
			btest.Queue.get(7).add(p);								// waiting-list 7 is a placeholder to get off the lift. it is NOT a floor
			btest.Queue.get(7).get(0).complaintcounter = 61;		// set complaint counter above 60 so that a complaint is made
			
			btest.Complaintchecker(btest.Queue);			//calls the function and passes on test queue
			
		if(btest.complaintcounter > 0) {
			fail("complaint counter was unnecessarily incremented, person was getting off the lift. Not waiting on a floor!");
		}
		
	}
	/**
	 *  Test 8: tests if the complaint increment of each Mugtome who are getting of the lift is not incremented.
	 */
	@Test	// test if the complaint increment of the Mugtome who are getting off the lift is not incremented
	public void doNotIncrementComplaintMugtomeTest() {
			btest.CreatePeople(0, 2);								// creates 1 goggle & 1 mugtome
			Person p = btest.floors[0].Persons.get(1);
			btest.Queue.put(7, btest.waiting);
			btest.Queue.get(7).add(p);								// waiting-list 7 is a placeholder to get off the lift. it is NOT a floor
			btest.Queue.get(7).get(0).complaintcounter = 61;		// set complaint counter above 60 so that a complaint is made
			
			btest.Complaintchecker(btest.Queue);			//calls the function and passes on test queue
		
		if(btest.complaintcounter > 0) {
			fail("complaint counter was unnecessarily incremented, person was getting off the lift. Not waiting on a floor!");
		}
		
	}
	/**
	 *  Test 8: tests if the complaint increment of each Mugtome who are getting of the lift is not incremented.
	 */
	@Test // test if client complaint and normal complaint counter works when a client exists on the waiting list
	public void clientComplaintTest() {
			btest.Generateclients(rand);
			btest.tick = 1;
			btest.SpawnClients();
			Person p = btest.floors[0].Persons.get(0);
			
			btest.Queue.put(1, btest.waiting);
			btest.Queue.get(1).add(p);
			btest.Queue.get(1).get(0).complaintcounter = 61;
			
			btest.Complaintchecker(btest.Queue);	//calls the function and passes on test queue
		assertTrue(btest.ClientComplaint == 1 && btest.complaintcounter == 1);
	}
	/**
	 *  Test 9: Test if only the normal complaint counter is incremented when a crew exists on the waiting list.
	 */
	@Test // test if only the normal complaint counter is incremented when a crew exists on the waiting list
	public void crewComplaintTest() {
			btest.arrivalprobability = 1;		//to ensure the create crew condition is always true
			btest.Generatecrew();
			Person p = btest.floors[0].Persons.get(0);
			
			btest.Queue.put(1, btest.waiting);
			btest.Queue.get(1).add(p);
			btest.Queue.get(1).get(0).complaintcounter = 61;
			
			btest.Complaintchecker(btest.Queue);	//calls the function and passes on test queue
		assertTrue(btest.ClientComplaint == 0 && btest.complaintcounter == 1);
	}
	/**
	 *  Test 10: test if only the normal complaint counter is incremented when a employee exists on the waiting list.
	 */
	@Test // test if only the normal complaint counter is incremented when a employee exists on the waiting list
	public void employeeComplaintTest() {
			btest.CreatePeople(1, 0);					// creates 1 employee
			Person p = btest.floors[0].Persons.get(0);
			
			btest.Queue.put(1, btest.waiting);
			btest.Queue.get(1).add(p);
			btest.Queue.get(1).get(0).complaintcounter = 61;
			
			btest.Complaintchecker(btest.Queue);	//calls the function and passes on test queue
		assertTrue(btest.ClientComplaint == 0 && btest.complaintcounter == 1);
	}
	/**
	 *  Test 11: test if only the normal complaint counter is incremented when a goggle exists on the waiting list.
	 */
	@Test // test if only the normal complaint counter is incremented when a goggle exists on the waiting list
	public void goggleComplaintTest() {
			btest.CreatePeople(0, 1); 					// creates 1 goggle
			Person p = btest.floors[0].Persons.get(0);
			
			btest.Queue.put(1, btest.waiting);
			btest.Queue.get(1).add(p);
			btest.Queue.get(1).get(0).complaintcounter = 61;
			
			btest.Complaintchecker(btest.Queue);	//calls the function and passes on test queue
		assertTrue(btest.ClientComplaint == 0 && btest.complaintcounter == 1);
	}
	/**
	 *  Test 12: test if only the normal complaint counter is incremented when a mugtome exists on the waiting list.
	 */
	@Test // test if only the normal complaint counter is incremented works when a mugtome exists on the waiting list
	public void mugtomeComplaintTest() {
			btest.CreatePeople(0, 2);					// creates 1 goggle & 1 mugtome
			Person p = btest.floors[0].Persons.get(1);
			
			btest.Queue.put(1, btest.waiting);
			btest.Queue.get(1).add(p);
			btest.Queue.get(1).get(0).complaintcounter = 61;
			
			btest.Complaintchecker(btest.Queue);	//calls the function and passes on test queue
			
			
		assertTrue(btest.ClientComplaint == 0 && btest.complaintcounter == 1);
	}
	/**
	 *  Test 13: test if both client complaint counter and the normal complaint counter is incremented works when a different people exist on the waiting list on a different floor.
	 */
	@Test // test if both client complaint counter and the normal complaint counter is incremented works when a different people exist on the waiting list on a different floor
	public void multipleComplaintTest() {
			btest.CreatePeople(1, 2);
			btest.arrivalprobability = 1;		//to ensure the create crew condition is always true
			btest.Generatecrew();
			btest.Generateclients(rand);
			btest.tick = 1;
			btest.SpawnClients();
			
			btest.waiting.addAll( btest.floors[0].Persons);
			btest.waiting.forEach((person) -> {
			person.complaintcounter = 61;	
			});
			btest.Queue.put(3, btest.waiting);		//puts waiting list in floor 3.
			btest.Complaintchecker(btest.Queue);	//calls the function and passes on test queue
			
		assertTrue(btest.ClientComplaint == 1 && btest.complaintcounter == 5);	
	}
	/**
	 *  Test 14: test checks if the correct number of people have been generated.
	 */
	@Test //test checks if the correct number of people have been generated
	public void numOfPeopleTest() {
			int expected = 20;
			btest.CreatePeople(10, 10);
			
			int output = btest.floors[0].getlistofppl();	// we subtract 20 because when the building is initialized this function is called automatically and 20 people are made;
		assertEquals(expected, output);
	}
	/**
	 *  Test 15: test checks if the correct number of employees have been generated.
	 */
	@Test //test checks if the correct number of employees have been generated.
	public void numOfEmployeesTest() {
			int expected = 10;
			int output = 0;
			btest.CreatePeople(10, 0);
			
				for (Person p : btest.floors[0].Persons) {
					if(p instanceof Employee) {
						output++;
					}
				}
		assertEquals(expected, output);
	}
	/**
	 *  Test 16: test checks if the correct number of Developers have been generated.
	 */
	@Test //test checks if the correct number of Developers have been generated
	public void numOfDevleopersTest() {
			int expected = 10;
			int output = 0;
			btest.CreatePeople(0, 10);
			
				for (Person p : btest.floors[0].Persons) {
					if(p instanceof Developer) {
						output++;
					}
				}
		assertEquals(expected, output);
	}
	/**
	 *  Test 17: test checks if the correct number of Mugtome developers have been generated.
	 */
	@Test //test checks if the correct number of Mugtome developers have been generated
	public void numOfMugtomeTest() {
			int expected = 5;
			int output = 0;
			btest.CreatePeople(0, 10);
			
				for (Person p : btest.floors[0].Persons) {
					if(p instanceof Mugtome) {
						output++;
					}
				}
		assertEquals(expected, output);
	}
	/**
	 *  Test 18: test checks if the correct number of Goggle developers have been generated.
	 */
	@Test //test checks if the correct number of Goggle developers have been generated
	public void numOfGogglesTest() {
			int expected = 5;
			int output = 0;
			btest.CreatePeople(0, 10);
			
				for (Person p : btest.floors[0].Persons) {
					if(p instanceof Goggles) {
						output++;
					}
				}
		assertEquals(expected, output);
	}
	
	
}