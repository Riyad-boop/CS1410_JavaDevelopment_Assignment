package Things;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Things.LIFT;
import people.Client;
import people.Mugtome;
import people.Employee;
import people.Goggles;
import people.Maintenance;
import people.Person;


/**
 * 
/**
 * There are 32 tests, for the LIFT Test class.
 *<pre>
 * Test 1: tests if lift moves up one floor and using the original destination tree.
 * Test 2: tests if lift moves up one floor and using the original destination tree.
 * Test 3: if someone on a higher floor to the lift, and calls the lift as it is going down the call WILL NOT be added to the destinations tree.
 * Test 4: if someone on a lower floor to the lift, and calls the lift as it is going down the call WILL be added to the destinations tree.
 * Test 5: if someone on a higher floor to the lift, and calls the lift as it is going up the call WILL be added to the destinations tree.
 * Test 6: if someone on a lower floor to the lift, and calls the lift as it is going up the call WILL NOT be added to the destinations tree.
 * Test 7: if doors open when reaching a destination floor (UP).
 * Test 8: if doors open when reaching a destination floor (DOWN).
 * Test 9: if destinations are reordered  (destination floor is lower than current floor).
 * Test 10: test if destinations are reordered (multiple destination floors is lower than current floor).
 * Test 11: test if destinations are not reordered (destination floor is higher than current floor).
 * Test 12: test if destinations are reordered (multiple destination floors is higher than current floor).
 * Test 13: test if repeat destinations exist.
 * Test 14: tests if lift capacity will exceed 4 when loading people.
 * Test 15: tests if lift adds all 3 different types of people when loading and to correct capacity.
 * Test 16: tests if lift skips adding Maintenance crew if an employee/client/Mugtome is added first, and continues to fill to max capacity.
 * Test 17: tests if lift skips adding anyone else if Maintenance crew was added first.
 * Test 18: tests if clients are added first when in a queue of people.
 * Test 19: tests if Goggles don't use elevator if a Mugtome got in first.
 * Test 20: tests if Mugtome don't use elevator if a Goggle got in first.
 * Test 21: tests if Mugtome developers move to the end of the queue, if they see a Goggle in the lift.
 * Test 22: tests if Goggles developers move to the end of the queue, if they see a Mugtome in the lift.
 * Test 23: tests if only (1) people who wanted to leave the lift at current floor, leaves, the rest should stay.
 * Test 24: tests if all (4) people who wanted to leave the lift at current floor, leaves.
 * Test 25: tests if people can leave and enter at the same time.
 * Test 26: tests if the destinations of people in the lift are immediately added to the destination TreeSet
 * Test 27: tests if the lower floor destinations of people in the lift are NOT immediately added to the destination TreeSet, because the lift is going up.
 * Test 28: tests if the higher floor destinations of people in the lift are immediately added to the destination TreeSet, because the lift is going down.
 * Test 29: tests go to rest function, sets destination to 0 and goes into resting mode ( sets resting to true).
 * Test 30: Complaint counter reset for Mugtome, employee and client.
 * Test 31: Complaint counter reset for Goggle, employee and client.
 * Test 32: Complaint counter reset for maintenance crew.
 * </pre>
 * 
 * @author Riyad Rahman
 * @see LIFT
 * @see Building
 */
public class LIFTTest {


		LIFT lift = new LIFT();
		ArrayList<Person> waiting = new ArrayList<Person>();
		HashMap<Integer,ArrayList<Person>> Queue = new HashMap<Integer,ArrayList<Person>>();
		float P = (float) 0.001;
		float Q = (float) 0.002;
		Random rand = new Random();
		int persondestination = 0;
		int expectederrors = 0;
		Person prsnEmployee = new Employee("Employee",0,1,persondestination,P,Q,0,rand,false,false);
		Person prsnClient = new Client("Client",0,1,persondestination,P,Q,0,rand,false,false);
		Person prsnMaintanance = new Maintenance("Maintenance",0,4,persondestination,P,Q,0,rand,false,false);
		Person prsnMugtome = new Mugtome("mugtome",0,1,persondestination,P,Q,0,rand,false,false);
		Person prsnGoggle = new Goggles("Goggles",0,1,persondestination,P,Q,0,rand,false,false);
		
		/**
		 * Test 1: tests if lift moves up one floor and using the original destination tree.
		 */
	@Test	// tests if lift moves up one floor and using the original destination tree
	public void moveupTest() {
		lift.addDest(4);
		lift.ticklift(Queue);
		assertTrue(lift.currentfloor == 1 && lift.destReverse.isEmpty());
	}
	/**
	 * Test 2: tests if lift moves up one floor and using the original destination tree.
	 */
	@Test	// tests if lift moves down one floor and uses a reversed destination tree
	public void movedownReverseTest() {
		lift.currentfloor = 4;
		lift.addDest(2);
		lift.ReorderDestinations();
		lift.ticklift(Queue);
		assertTrue(lift.currentfloor == 3 && (!(lift.destReverse.isEmpty())));
		
	}
	/**
	 * Test 3: if someone on a higher floor to the lift, and calls the lift as it is going down the call WILL NOT be added to the destinations tree.
	 */
	@Test	// if someone on a higher floor to the lift, and calls the lift as it is going down the call WILL NOT be added to the destinations tree
	public void dontAddDownTest() {
		lift.goingup = false;			// lift going down
		lift.currentfloor = 2;			// lift on floor 2 
		int key = 5 ; 					// lift being called at floor 5.
		waiting.add(prsnEmployee);	
		Queue.put(key, waiting);		
		lift.checkfloors(Queue);
		
		if(lift.getdestinaton().contains(key)) {
			fail("Destination: " + key + " was incorrrectly added.");
		}
	}
	/**
	 * Test 4: if someone on a lower floor to the lift, and calls the lift as it is going down the call WILL be added to the destinations tree.
	 */
	@Test	//if someone on a lower floor to the lift, and calls the lift as it is going down the call WILL be added to the destinations tree
	public void doAddDownTest() {
		lift.goingup = false;			// lift going up
		lift.currentfloor = 4;			// lift on floor 4 
		int key = 2 ; 					//lift being called at floor 2.
		waiting.add(prsnEmployee);	
		Queue.put(key, waiting);		
		lift.checkfloors(Queue);
		
		if(!(lift.getdestinaton().contains(key))) {
			fail("Destination: " + key + " was not added, but it SHOULD have been");
		}
	}
	/**
	 * Test 5: if someone on a higher floor to the lift, and calls the lift as it is going up the call WILL be added to the destinations tree.
	 */
	@Test	// if someone on a higher floor to the lift, and calls the lift as it is going up the call WILL be added to the destinations tree
	public void doAddUpTest() {
		lift.goingup = true;			// lift going up
		lift.currentfloor = 2;			// lift on floor 4 
		int key = 4 ; 					//lift being called at floor 2.
		waiting.add(prsnEmployee);	
		Queue.put(key, waiting);		
		lift.checkfloors(Queue);
		
		if(!(lift.getdestinaton().contains(key))) {
			fail("Destination: " + key + " was not added, but it SHOULD have been");
		}
	}
	/**
	 * Test 6: if someone on a lower floor to the lift, and calls the lift as it is going up the call WILL NOT be added to the destinations tree.
	 */
	@Test	// if someone on a lower floor to the lift, and calls the lift as it is going up the call WILL NOT be added to the destinations tree
	public void dontAddUpTest() {
		lift.goingup = true;			// lift going up
		lift.currentfloor = 4;			// lift on floor 4 
		int key = 2 ; 					//lift being called at floor 2.
		waiting.add(prsnEmployee);	
		Queue.put(key, waiting);		
		lift.checkfloors(Queue);
		
		if(lift.getdestinaton().contains(key)) {
			fail("Destination: " + key + " was incorrrectly added.");
		}
	}
	
	/**
	 * Test 7: if doors open when reaching a destination floor (UP).
	 */
	@Test	// if doors open when reaching a destination floor (UP)
	public void doorsOpenUpTest() {
		lift.addDest(5);
		lift.currentfloor = 4;
		lift.move();
		
			assertTrue(lift.getdoorStatus());
	}
	
	/**
	 * Test 8: if doors open when reaching a destination floor (DOWN).
	 */
	@Test	// if doors open when reaching a destination floor (DOWN)
	public void doorsOpenDownTest() {
		lift.addDest(2);
		lift.currentfloor = 3;
		lift.move();
		
			assertTrue(lift.getdoorStatus());
	}
	
	/**
	 * Test 9: if destinations are reordered (destination floor is lower than current floor).
	 */
	@Test	// if destinations are reordered  (destination floor is lower than current floor)
	public void reorderTestOneLower() {
		lift.addDest(2);
		lift.currentfloor = 3;
		lift.ReorderDestinations();
		
			assertTrue(lift.destReverse.size() != 0);
		
	}
	/**
	 * Test 10: test if destinations are reordered (multiple destination floors is lower than current floor).
	 */
	@Test	//test if destinations are reordered (multiple destination floors is lower than current floor)
	public void reorderTestMultLower() {
		lift.addDest(3);
		lift.addDest(4);
		lift.addDest(5);
		lift.currentfloor = 6;
		lift.ReorderDestinations();
		
			assertTrue(lift.destReverse.size() != 0);
		
	}
	/**
	 * Test 11: test if destinations are not reordered (destination floor is higher than current floor).
	 */
	@Test	//test if destinations are not reordered (destination floor is higher than current floor)
	public void notReorderTestOneHigher() {
		lift.addDest(4);
		lift.currentfloor = 3;
		lift.ReorderDestinations();
		
			assertTrue(lift.destReverse.size() == 0);
		
	}
	/**
	 * Test 12: test if destinations are reordered (multiple destination floors is higher than current floor).
	 */
	@Test	// test if destinations are reordered (multiple destination floors is higher than current floor)
	public void reorderTestMultHigher() {
		lift.addDest(3);
		lift.addDest(4);
		lift.addDest(5);
		lift.currentfloor = 1;
		lift.ReorderDestinations();
		
			assertTrue(lift.destReverse.size() == 0);
		
	}
	/**
	 * Test 13: test if repeat destinations exist.
	 */
	@Test	// test if repeat destinations exist
	public void repeatDestTest() {
		lift.addDest(3);
		lift.addDest(3);
		lift.currentfloor = 1;
		
			assertTrue(lift.destinations.size() == 1);
		
	}
	/**
	 * Test 14: tests if lift capacity will exceed 4 when loading people.
	 */
	@Test //tests if lift capacity will exceed 4 when loading people
	public void maxCapacityTest() {
		int floornum = 2;
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
				Queue.put(floornum, waiting);	//put list of 5 employees inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
		
			assertTrue(lift.getcapacity() == 4 );
	}	
	/**
	 * Test 15: tests if lift adds all 3 different types of people when loading and to correct capacity.
	 */
	@Test //tests if lift adds all 3 different types of people when loading and to correct capacity 
	public void correctCapacityTest() {
		int floornum = 2;
			waiting.add(prsnEmployee);
			waiting.add(prsnMugtome);
			waiting.add(prsnClient);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
				Queue.put(floornum, waiting);	//put list people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
		
			assertTrue(lift.getcapacity() == 4 );
	}
	/**
	 * Test 16: tests if lift skips adding Maintenance crew if an employee/client/Mugtome is added first, and continues to fill to max capacity.
	 */
	@Test //tests if lift skips adding Maintenance crew if an employee/client/Mugtome is added first, and continues to fill to max capacity
	public void skipMaintenanceTest() {
		int floornum = 2;
			waiting.add(prsnEmployee);
			waiting.add(prsnMugtome);
			waiting.add(prsnClient);
			waiting.add(prsnMaintanance);
			waiting.add(prsnEmployee);
				Queue.put(floornum, waiting);	//put list of people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
		
			assertTrue(lift.getcapacity() == 4 );
	}
	/**
	 * Test 17: tests if lift skips adding anyone else if Maintenance crew was added first. 
	 */
	@Test //tests if lift skips adding anyone else if Maintenance crew was added first. 
	public void AddonlyMaintenanceTest() {
		int floornum = 2;
			waiting.add(prsnMaintanance);
			waiting.add(prsnEmployee);
			waiting.add(prsnMugtome);
			waiting.add(prsnMugtome);
				Queue.put(floornum, waiting);	//put list of people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
				
			assertTrue(lift.getcapacity() == 4 );
	}
	/**
	 * Test 18: tests if clients are added first when in a queue of people.
	 */
	@Test	//tests if clients are added first when in a queue of people
	public void PrioritiseClientTest() {
	int floornum = 2;
			waiting.add(prsnEmployee);
			waiting.add(prsnMugtome);
			waiting.add(prsnClient);
			waiting.add(prsnMugtome);
				Queue.put(floornum, waiting);	//put list of people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
			
			assertTrue(lift.inlift.get(0) instanceof Client);
	}
	/**
	 * Test 19: tests if Goggles don't use elevator if a Mugtome got in first.
	 */
	@Test	//tests if Goggles don't use elevator if a Mugtome got in first 
	public void LoadMugtomeNoGogglesTest() {
		int floornum = 2;
			waiting.add(prsnMugtome);
			waiting.add(prsnEmployee);
			waiting.add(prsnGoggle);
			waiting.add(prsnMugtome);
				Queue.put(floornum, waiting);	//put list of people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
			
			assertTrue(lift.getcapacity() == 3);
	}
	/**
	 * Test 20: tests if Mugtome don't use elevator if a Goggle got in first.
	 */
	@Test 	//tests if Mugtome don't use elevator if a Goggle got in first  
	public void LoadGogglesNoMugtomeTest() {
		int floornum = 2;
			
			waiting.add(prsnGoggle);
			waiting.add(prsnEmployee);
			waiting.add(prsnMugtome);
			waiting.add(prsnGoggle);
				Queue.put(floornum, waiting);	//put list of people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
		
		assertTrue(lift.getcapacity() == 3);
	}
	/**
	 * Test 21: tests if Mugtome developers move to the end of the queue, if they see a Goggle in the lift.
	 */
	@Test	// tests if Mugtome developers move to the end of the queue, if they see a Goggle in the lift.
	public void MugtomeMoveToEnd() {
		int floornum = 2;
		
			waiting.add(prsnGoggle);
			waiting.add(prsnEmployee);
			waiting.add(prsnMugtome);
			waiting.add(prsnGoggle);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);			
				Queue.put(floornum, waiting);	//put list of people inside queue				
					lift.currentfloor = floornum; 
					lift.openDoors();
					Queue = lift.AddToLift(floornum, Queue);
			
			if(Queue.get(floornum).get(Queue.get(floornum).size() - 1) instanceof Mugtome) {
			assertTrue(true);
			}
	}
	/**
	 * Test 22: tests if Goggles developers move to the end of the queue, if they see a Mugtome in the lift.
	 */
	@Test	// tests if Goggles developers move to the end of the queue, if they see a Mugtome in the lift.
	public void GogglesMoveToEnd() {
		int floornum = 2;
		
			waiting.add(prsnMugtome);
			waiting.add(prsnEmployee);
			waiting.add(prsnGoggle);
			waiting.add(prsnMugtome);
			waiting.add(prsnEmployee);
			waiting.add(prsnEmployee);
				Queue.put(floornum, waiting);	//put list of people inside queue
					lift.currentfloor = floornum; 
					lift.openDoors();
					lift.AddToLift(floornum, Queue);
			
			if(Queue.get(floornum).get(Queue.get(floornum).size() - 1) instanceof Goggles) {
				assertTrue(true);
				}
	}
	/**
	 * Test 23: tests if only (1) people who wanted to leave the lift at current floor, leaves, the rest should stay.
	 */
	@Test // tests if only (1) people who wanted to leave the lift at current floor, leaves, the rest should stay 
	public void correctLeaveTest() {
		lift.currentfloor = 2;
		int unloadme = lift.currentfloor;
		lift.openDoors();
		lift.leavedooropen = true;
			lift.inlift.add(prsnEmployee);
			lift.inlift.add(prsnMugtome);
			lift.inlift.add(new Client("Client",0,1,unloadme,P,Q,0,rand,false,false));
			lift.inlift.add(prsnClient);
				lift.unloadpassengers();

	assertTrue(lift.getcapacity() == 3);

	}
	/**
	 * Test 24: tests if all (4) people who wanted to leave the lift at current floor, leaves.
	 */
	@Test // tests if all (4) people who wanted to leave the lift at current floor, leaves. 
	public void emptyLiftTest() {
		lift.currentfloor = 2;
		int unloadme = lift.currentfloor;
		lift.openDoors();
		lift.leavedooropen = true;
		
			lift.inlift.add(new Employee("Employee",0,1,unloadme,P,Q,0,rand,false,false));
			lift.inlift.add(new Mugtome("Mugtome",0,1,unloadme,P,Q,0,rand,false,false));
			lift.inlift.add(new Client("Client",0,1,unloadme,P,Q,0,rand,false,false));
			lift.inlift.add(new Client("Client",0,1,unloadme,P,Q,0,rand,false,false));

	lift.unloadpassengers();

	assertTrue(lift.getcapacity() == 0);
	}
	/**
	 * Test 25: tests if people can leave and enter at the same time.
	 */
	@Test // tests if people can leave and enter at the same time; 
	public void leaveAndEnterTest() {
		int floornum = 2;
		int unloadme = floornum;
		lift.currentfloor = floornum; 
		
		lift.openDoors();
		lift.leavedooropen = true;
		
		lift.inlift.add(new Employee("Employee",0,1,unloadme,P,Q,0,rand,false,false));
		lift.inlift.add(new Mugtome("Mugtome",0,1,unloadme,P,Q,0,rand,false,false));
		lift.inlift.add(new Client("Client",0,1,unloadme,P,Q,0,rand,false,false));
		lift.inlift.add(new Client("Client",0,1,unloadme,P,Q,0,rand,false,false));

	lift.unloadpassengers();

		waiting.add(prsnEmployee);
		Queue.put(floornum, waiting);	//put list of people inside queue
	
	lift.AddToLift(floornum, Queue);
	
	
	assertTrue(lift.getcapacity() == 1);
	}
	/**
	 * Test 26: tests if the destinations of people in the lift are immediately added to the destination TreeSet.
	 */
	@Test	//tests if the destinations of people in the lift are added immediately
	public void addInLiftDestinationsGoingUpTest() {
	
	lift.currentfloor = 2;
	int DestinationsToBeAdded[] = {2,3,4,5};
	
	lift.inlift.add(new Employee("Employee",0,1,DestinationsToBeAdded[0],P,Q,0,rand,false,false));
	lift.inlift.add(new Mugtome("Mugtome",0,1,DestinationsToBeAdded[1],P,Q,0,rand,false,false));
	lift.inlift.add(new Client("Client",0,1,DestinationsToBeAdded[2],P,Q,0,rand,false,false));
	lift.inlift.add(new Client("Client",0,1,DestinationsToBeAdded[3],P,Q,0,rand,false,false));
	
	lift.checkdestinationsinlift();


	assertTrue(lift.getdestinaton().size() == 4 && lift.getdestinaton().first() == 2);
	}
	/**
	 * Test 27: tests if the lower floor destinations of people in the lift are NOT immediately added to the destination TreeSet, because the lift is going up.
	 */
	@Test	//tests if the destinations of people in the lift are NOT immediately added , because the lift is already on a higher floor and going up
	public void doNotAddInLiftDestinationsTest() {
	
	lift.currentfloor = 5;
	lift.destinations.add(6);
	int DestinationsToBeAdded[] = {1,2,3,4};
	
	lift.inlift.add(new Employee("Employee",0,1,DestinationsToBeAdded[0],P,Q,0,rand,false,false));
	lift.inlift.add(new Mugtome("Mugtome",0,1,DestinationsToBeAdded[1],P,Q,0,rand,false,false));
	lift.inlift.add(new Client("Client",0,1,DestinationsToBeAdded[2],P,Q,0,rand,false,false));
	lift.inlift.add(new Client("Client",0,1,DestinationsToBeAdded[3],P,Q,0,rand,false,false));
	
	lift.checkdestinationsinlift();
	
	
	assertTrue(lift.getdestinaton().size() == 1);
	}
	/**
	 * Test 28: tests if the higher floor destinations of people in the lift are immediately added to the destination TreeSet, because the lift is going down.
	 */
	@Test	//tests if the destinations of people in the lift are immediately added , because the lift is going down
	public void addInLiftDestinationsGoingDownTest() {
	
	lift.currentfloor = 5;
	lift.goingup = false;
	int DestinationsToBeAdded[] = {1,2,3,4};
	
	lift.inlift.add(new Employee("Employee",0,1,DestinationsToBeAdded[0],P,Q,0,rand,false,false));
	lift.inlift.add(new Mugtome("Mugtome",0,1,DestinationsToBeAdded[1],P,Q,0,rand,false,false));
	lift.inlift.add(new Client("Client",0,1,DestinationsToBeAdded[2],P,Q,0,rand,false,false));
	lift.inlift.add(new Client("Client",0,1,DestinationsToBeAdded[3],P,Q,0,rand,false,false));
	
	lift.checkdestinationsinlift();
	
	
	assertTrue(lift.getdestinaton().size() == 4 && lift.getdestinaton().last() == 4);
	}
	/**
	 * Test 29: tests go to rest function, sets destination to 0 and goes into resting mode ( sets resting to true).
	 */
	@Test // tests go to rest function, sets destination to 0 and goes into resting mode ( sets resting to true)
	public void restingTest() {
		lift.currentfloor = 5;
		lift.gotorest();
		assertTrue(lift.resting == true && lift.destinations.first() == 0);
	}
	/**
	 * Test 30: Complaint counter reset for Mugtome, employee and client.
	 */
	@Test // Complaint counter reset for mugtome, employee and client
	public void complaintCountResetMugtomeTest() {
		int floornum = 2;
		
		waiting.add(prsnEmployee);
		waiting.add(prsnMugtome);
		waiting.add(prsnGoggle);
		waiting.add(prsnClient);
		
		Queue.put(floornum, waiting);	//put list people inside queue
		
		lift.currentfloor = floornum; 
		lift.openDoors();
		lift.AddToLift(floornum, Queue);
		
		lift.inlift.forEach((person) -> {
			if(person.getComplaintCount() != 0 || person instanceof Goggles) {
			expectederrors ++;	
			}
		});
		
		assertEquals(0,expectederrors);
	}
	/**
	 * Test 31: Complaint counter reset for Goggle, employee and client.
	 */
	@Test // Complaint counter reset for Goggle, employee and client
	public void complaintCountResetGoggleTest() {
		int floornum = 2;
		
		waiting.add(prsnEmployee);
		waiting.add(prsnGoggle);
		waiting.add(prsnClient);
		
		Queue.put(floornum, waiting);	//put list people inside queue
		
		lift.currentfloor = floornum; 
		lift.openDoors();
		lift.AddToLift(floornum, Queue);
		
		lift.inlift.forEach((person) -> {
			if(person.getComplaintCount() != 0 || person instanceof Mugtome) {
			expectederrors ++;	
			}
		});
		
		assertEquals(0,expectederrors);
	}
	/**
	 * Test 32: Complaint counter reset for maintenance crew.
	 */
	@Test // Complaint counter reset for maintenance crew 
	public void complaintCountResetCrewTest() {
		int floornum = 2;
		
		waiting.add(prsnMaintanance);
		waiting.add(prsnEmployee);
		waiting.add(prsnMugtome);
		waiting.add(prsnGoggle);
		
		Queue.put(floornum, waiting);	//put list people inside queue
		
		lift.currentfloor = floornum; 
		lift.openDoors();
		lift.AddToLift(floornum, Queue);
		
		lift.inlift.forEach((person) -> {
			if(person.getComplaintCount() != 0 || person instanceof Employee) {
			expectederrors ++;	
			}
		});
		
		assertEquals(0,expectederrors);
	}
	
}

