package Things;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import people.Client;
import people.Developer;
import people.Employee;
import people.Maintenance;
import people.Person;

/**
 * Represents the floors which handles; ticking people on the floor, moving people into a waiting list (calling the elevator), and finally unloading people from the lift. Key functions include: 
 * <pre>
 *  {@link #tick()} 
 *  {@link #call_Lift(HashMap)} 
 *  {@link #QueueToFloor(ArrayList)} 
 *  {@link #TickAllPeople()} 
 *  </pre>
 * @author Riyad K Rahman - 180129650 
 */
public class Floor {
	
	private final int floorNum;											// the floor number of THIS floor 
	
	protected ArrayList<Person> Persons = new ArrayList<Person>();		// list of people in floor
	protected ArrayList<Person> waiting = new ArrayList<Person>();		// list of people waiting for lift
	protected ArrayList<Integer> totalWaitTime = new ArrayList<Integer>();  	// waiting times of people who leave the building.
//------------------------------------------------- Constructor -----------------------------------------------------------------------//
	/**
	 * This is the constructor for the Floor class.  
	 * When a floor is created it must be assigned a floor_num to be identified by . 
	 * @param f - is the floors number.
	 */
	protected Floor (int f) {
		this.floorNum = f;

	}
	
//------------------------------------------------ people methods ---------------------------------------------------------------------//	
	
	/**
	 * adds a person, p, to the current floor
	 * @param p - person to be added 
	 */
	protected void AddPersonToFloor(Person p) {
		Persons.add(p);		
		
		}
	
	/**
	 * removes a person, p, from the current floor
	 * @param p - person to be removed 
	 */
	protected void RemovePersonFromFloor(Person p) {
			Persons.remove(p);
		}
		
	
	/**
	 * returns number of people on the current floor
	 * @return Persons.size() - number of people in the floor
	 */
	protected int getlistofppl() {			//for getting size of people on the current floor 
		return Persons.size();
	}
	
	
//-----------------------------------------------Main tick method----------------------------------------------------------------------//	
	/**
	 * Controls people moving in and out of the current floor
	 * Updates the queue based on people waiting for the lift
	 * @param liftFloor - the current floor which the lift is on
	 * @param queue	- used to store the waiting list of any Person that wants to change floors, and [Queue.get(7)] is used to unload people from the lift to the floor.
	 * @return queue - an updated queue is returned on every tick.
	 */
	protected HashMap<Integer, ArrayList<Person>> tick(int liftFloor, HashMap<Integer,ArrayList<Person>> queue) {	 
		
		
//if there are people on the floor, then tick those people.		
		if(Persons.size() > 0) {	
			TickAllPeople();
			
// for anyone on the floor, check if their destination has changed to a different floor, if they have then add to a waiting list and remove from floor list
				for (int i= 0; i < Persons.size(); i++) {
					if(Persons.get(i).getdestFloor() != this.floorNum) {
						waiting.add(Persons.get(i));
						RemovePersonFromFloor(Persons.get(i));  //removes person from floor into a temporary waiting list
						i--; 				//when a person is removed the array pushes the next element to 0, so we need to reset the counter
					}
				}
				queue = call_Lift(queue);		//updates queue if anyone is in the waiting list
			}
		return queue;				//returns queue back to the building
		}
	

//-------------------------------------------------Call Lift--------------------------------------------------------------------------//
	/**
	 * Updates the queue based on who is waiting for the lift.
	 * <pre>
	 * prints which floor lift is being called to
	 * </pre>
	 * @param queue - contains the waiting list for the current floor.
	 * @return queue - updated queue, which the lift will use to locate which floors people are waiting at. 
	 */
	protected HashMap<Integer, ArrayList<Person>> call_Lift(HashMap<Integer,ArrayList<Person>> queue) {
	
// if there are people in the waiting list, then the program will fill the Queue HashMap with the waiting list of people 		
		if(waiting.size() > 0) {							
			queue.remove(floorNum);						//removes current waiting list
			queue.put(floorNum, waiting);				//adds the new waiting list
			System.out.print("Lift being called to: " + this.floorNum + "\n");
			return queue;								//returns the new waiting list
		}
		else { return queue; }							//returns the unmodified (old) queue
	}
	
	
//----------------------------------------------Adding people to floor-----------------------------------------------------------------//						
	/**
	 * Adds people from the lift to the current floor.
	 * <pre> after a person is added their "at correct floor" variable is set to true. </pre>
	 * @param p - person to be added to current floor
	 */
//function to unload people from lift
	protected void QueueToFloor(ArrayList<Person> p) {		
		
		for (int i= 0; i < p.size(); i++) {
			Persons.add(p.get(i));
			Persons.get(i).setatcorrectFloor(true); 			//when a person is added to the correct floor they are set to true so that the q value can start incrementing
	
		}
	}
	
	

//------------------------------------------------Ticking all people-------------------------------------------------------------------//
	/**
	 * Controls the ticking of people:
	 * <pre> 
	 * Resets complaint count if a person is on the correct floor
	 * Removes a person (Client or Maintenance crew) from the ground floor once their business is completed
	 * </pre>
	 */
	protected void TickAllPeople() {
	for (int i= 0; i < Persons.size(); i++) {
		Persons.get(i).tickppl();				//ticks everyone on the current floor
	
			//when a person is on the correct floor they are set to true so that the work-counter can start incrementing.
			if(Persons.get(i).getdestFloor() == this.floorNum) {
				Persons.get(i).setatcorrectFloor(true); 		
			}
	
			//if the person has completed their business and they are on ground floor then remove person from building
			if(Persons.get(i).getbuisnessstatus() == true && this.floorNum == 0 ) {			
				System.out.print("\n \n " + Persons.get(i) + " has been removed  \n \n ");
				totalWaitTime.add(Persons.get(i).totalWaitTime);
				Persons.remove(i);		//removes person from building
			}
	
		}

	}

	protected ArrayList<Integer> getTimes() {
		return totalWaitTime;
	}

}
