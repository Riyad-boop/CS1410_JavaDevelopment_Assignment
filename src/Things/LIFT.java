package Things;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

import people.Client;
import people.Goggles;
import people.Mugtome;
import people.Maintenance;
import people.Person;

/**
 * Represents the elevator which handles moving around passengers to different floors Key functions include: 
 * <pre>
 *  {@link #ticklift(HashMap)} 
 *  {@link #move()} 
 *  {@link #beginmoving(TreeSet)} 
 *  {@link #gotorest()} 
 *  {@link #checkfloors(HashMap)} 
 *  {@link #checkdestinationsinlift()} 
 *  {@link #ReorderDestinations()} 
 *  {@link #AddToLift(int, HashMap)} 
 *  {@link #unloadpassengers()} 

 *  </pre>
 * @author Riyad K Rahman 
 */

public class LIFT {
	
	
	protected HashMap<Integer,ArrayList<Person>> updateQueue = new HashMap<Integer,ArrayList<Person>>();	// a local queue HashMap that will store the values from the 'queue' passed into lift. This is because you can not pass a parameter back as a return value.
	protected ArrayList<Person> inlift = new ArrayList<Person>();											// ArrayList of people inside the lift 
	protected ArrayList<Person> unloading = new ArrayList<Person>();										// ArrayList of people to be unloaded to the floor class. The floor class will empty these people onto the floor.
	private int currentCapacity = 0;; 																	// keeps track of current capacity
	private final int maxCapacity = 4;												 					// keeps track of max allowed capacity
	private boolean doors; 																				// elevator doors status
	protected int currentfloor; 																			// current floor elevator is on
	protected TreeSet<Integer> destinations; 																// destination floors, a tree set that will automatically order its elements 
	protected TreeSet<Integer> destReverse = new TreeSet<Integer>(Collections.reverseOrder());				// a REVERSED destination floors, a tree set that will automatically REVERSE order its elements 
	
	private int queueonfloor; 																			// the floor in which the elevator is being called
	private int LocalTick; 																				// used to control adding destinations and elevator doors
	protected boolean leavedooropen = false;																// used to keep the door open for one more tick
	protected boolean resting = false;																	// used to call elevator to ground if destinations are empty
	private boolean loading = false;
	private int localtick2;																				// used to time/control elevator doors
	protected boolean goingup = true;																				// used to order destinations in the TreeSet destinations
	
	

	/**
	 *  This constructs a LIFT, and initializes the following variables:
	 *<pre>
	 * current_floor - current floor, is the floor which the lift is currently on 
	 * doors - the status of the doors. TRUE is open. FALSE is closed
	 * destinations - Integer TreeSet which contains all the destinations for the elevator.
	 */
	
	public LIFT() {
		
		this.currentfloor = 0;
		this.doors = false;
		this.destinations = new TreeSet<Integer>();
		
}

	
//----------------------------------------------------- move method -------------------------------------------------------------------//
	
	protected void move() {
	
	// if the elevator is going down the order which it visits floors must be reversed. Since it must go from highest to lowest when moving down	 
	if (destReverse.size() == 0) {
		beginmoving(destinations);
		goingup = true;
	}
	
	else { 
		beginmoving(destReverse); 
		goingup = false;
		}
		 

}

//-------------------------------------------------- Main move method -----------------------------------------------------------------//	
	
	/**
	 * This method handles moving the elevator and calling unloading or open door functions when a destination floor is met.
	 * @param OrderedDestinations - A destination TreeSet the elevator uses to control its movements.  
	 */
	private void beginmoving(TreeSet<Integer> OrderedDestinations) {
		 
		
	if (OrderedDestinations.size() != 0) {
		
		if (inlift.size() == 0 && OrderedDestinations.first() == 0 && OrderedDestinations.last() == 0 ) { resting = true; System.out.println("\n Elevator going down to floor: 0");}  //Sets resting to true, if no requests have been called for the elevator
	 	else {resting = false;}								// if someone is using the lift, then resting should be set to false
		
		if(goingup == true && resting == false) {
		System.out.println("\n Elevator going up to floor: " + OrderedDestinations.first());
		}
		
		else if (resting == false) {
			System.out.println("\n Elevator going down to floor: " + OrderedDestinations.first());
		}
		
			if(doors == true && leavedooropen == false) {
				closeDoors();
			}
			
	
			else if(OrderedDestinations.first() > currentfloor && doors == false ) {
				currentfloor++;																				// move elevator to up to next floor
				reachedfloor(OrderedDestinations.first());
			}
		
			else if(OrderedDestinations.first() < currentfloor && doors == false ) {
				currentfloor--;																				// move elevator down to next floor
				reachedfloor(OrderedDestinations.first());
		
		
			}
		
			else  {
				removeDest(this.currentfloor); 
				unloadpassengers();
	
			}
		
		}
	
				else { 
					if(destinations.size() == 0 && inlift.size() == 0) {gotorest(); }
		 
				}
		
	}


/**
 * This method handles opening the doors when a destination floor is reached.
 * @param dest - the current destination, which the elevator has reached. 
 */
	// opens elevators doors when elevator reaches the correct floor
			protected void reachedfloor(int dest) {
				if(doors == false && resting == false && dest == currentfloor) {
				 	openDoors();
				 	LocalTick++;
					leavedooropen = true;
				 }
			}

			
/**
 * This method makes the elevator go to resting mode if there are no requests.
 */
	protected void gotorest() {
		 if (currentfloor != 0 ) { destinations.add(0);  resting = true;}
		
		 else {
			 destinations.clear();
		 }
		 
	}
	
	
/**
 *   This method opens elevators doors
 */
	protected void openDoors() {
			this.doors = true;
			System.out.print("doors open \n");	
	}
	
	
/**
 *   This method closes elevators doors
 */	
	protected void closeDoors() {
		this.doors = false;
		System.out.print("doors closed \n");
	}
	
	
/**
 *   This method adds destination to the elevator
 *   @param dest - the destination to be added to the TreeSet which contains all the elevator destinations 
 */		
	protected void addDest(int dest) {
		if(dest != 7) {
		this.destinations.add(dest);
		resting = false;
		}
	}
	
	
/**
 *   This method removes a destination to the elevator
 *    @param dest - the destination to be removed to the TreeSet which contains all the elevator destinations 
 */	

	protected void removeDest(int dest) {
		this.destinations.remove(dest);
		if (destReverse.size() != 0) {		//delete the destination from destination reverse TreeSet if it exists
			destReverse.remove(dest);
		}
	}
	
//----------------------------------------------------- Tick method -------------------------------------------------------------------//	

	/**
	 * This function is used to call every other function in this class, such as:
	 * <pre> move() - which controls elevator movement
	 * check_floors() - adds the appropriate destinations of lift calls  for each floor, depending on the elevators direction.
	 * check_destinations_in_lift() - re-orders the destinations depending on the direction of travel of the elevator 
	 * </pre>
	 * @param queue - contains all the floors which people are waiting at
	 * @return updateQueue - returns an updated HashMap containing all the floors which people are waiting at.  
	 */
	
	protected HashMap<Integer, ArrayList<Person>> ticklift(HashMap<Integer,ArrayList<Person>> queue) {
		
		move();	
		
		if (LocalTick == 0) {
			
			if(destinations.size() == 0 && currentfloor != 0 ) {goingup = false;}	//if the elevator is not on floor 0, and it has no destinations, then it should be going down to 0. fixes goingup=true on move() without causing more errors
				
		checkfloors(queue);
		checkdestinationsinlift();
		}
		
		
		if (LocalTick > 0) {
			LocalTick --;
		}
		
		ReorderDestinations();
		
		
		System.out.println("Elevator currently at floor: "+ currentfloor + " capacity: " + currentCapacity);
		
		 if (doors == true && leavedooropen == false && loading == true && inlift.size() == 0  && LocalTick == 0 && localtick2 == 0) {			// CLOSES door after unloading ppl to floor 0. (bug patch)
		closeDoors();					//calls move again to close the doors ONLY AFTER PPL HAVE UNLOADED / LOADED
		loading = false;
		 }
		 else { localtick2 = 0;}
		 
		
		return updateQueue;
	}
	
//------------------------------------------------ Reordering Destination list --------------------------------------------------------//	

/**
 *  This method re-orders the destinations of the lift depending on which direction it is heading towards,
 *  for example:
 *  <pre> if the lift is going down then the destinations should be ordered highest floor to the lowest floor.
 * if the lift is going up then the destinations should be ordered lowest floor to the highest floor. </pre>
 */
protected void ReorderDestinations() {
	
	if(! destinations.isEmpty()) {				//if there are are some destinations and the elevator is gonna go down the order of destinations is reversed 
	
	if (currentfloor > destinations.first()) {
		destReverse.addAll(destinations);
	}
	
	else { destReverse.clear(); }
	
	
	}
	
	else { destReverse.clear();}
}

//-------------------------------------------- Adding lift_calls Destination list -----------------------------------------------------//	
	/**
	 * This function handles which floors, which have people have called the lift to,  are added to the destination of the lift.
	 * Floors are added depending on which direction the lift is going. 
	 * <pre></pre>
	 * if the lift is on a floor which has people waiting for it then Add_to_lift() function is called, and updateQueue is updated. 
	 * @param queue - contains all the floors which people are waiting at
	 * @return updateQueue - returns an updated HashMap containing all the floors which people are waiting at.  
	 */
	protected HashMap<Integer, ArrayList<Person>> checkfloors(HashMap<Integer, ArrayList<Person>> queue) {
		

			queue.forEach((key,list) -> {
			
			 if(list.size() != 0) {
				 queueonfloor = key;
			
				 if(goingup == true) {
					 if(queueonfloor != currentfloor && queueonfloor > currentfloor && currentCapacity < maxCapacity) {
						 addDest(queueonfloor);	
					 }
				
				 }
				 
				 else if (goingup == false) {
					 if(queueonfloor != currentfloor && queueonfloor < currentfloor && currentCapacity < maxCapacity) {
					 addDest(queueonfloor);	
					 }
					
				 } 
					 
		
			updateQueue = AddToLift(queueonfloor,queue);
		
			 } 
		});
			
		return updateQueue;
		}
		
	
	
//---------------------------------------------- Adding people to lift ----------------------------------------------------------------//	
	/**
	 *  This function handles many operations such as:
	 *  <pre> 	1) Opens doors if lift capacity is not full.
	 *  	2) if the doors are already open then it pushes clients to the front of the queue.
	 *  	3) adds a person into the lift.
	 *      3.1) if a person is a mugtome or goggle it will check if there exists a rival developer in the lift. 
	 *  	   if this is true, the developer on the floor moves to the back of the queue. and the lift will check the next person, or exit the method if nobody remains waiting on the floor.
	 *  	4)if a maintenance crew is added first, the lift will leave the rest of the people waiting on the floor.
	 *      4.1) if someone enters the lift before a maintenance crew, the maintenance crew is instead left to wait on the floor.
	 *  	5) people that are added are removed from the floor waiting list.
	 *  </pre>
	 * @param floorkey - the current floor of the lift and the floor which people are being moved from
	 * @param queue -  contains all the floors which people are waiting at
	 * @return queue - returns an updated queue.
	 */
protected HashMap<Integer, ArrayList<Person>> AddToLift(int floorkey, HashMap<Integer, ArrayList<Person>> queue) {
	
	 Person Tobeadded;	// creates a local person to be added to the lift.
	 
	 if (currentfloor == floorkey && currentCapacity < 4) {	
	 loading = true;		
		 
	 if(doors == false) {
		 openDoors();
		leavedooropen = true; 		//leaves door open while people get on lift;
		resting = false;
		System.out.print("Loading people into lift");
	 }

	 else  {	
		 for (int ClC =0; ClC < queue.get(floorkey).size(); ClC++ ) {
			 if (queue.get(floorkey).get(ClC) instanceof Client) {
				 Person PriotiseClient = queue.get(floorkey).get(ClC);
				  queue.get(floorkey).remove(ClC);
				  queue.get(floorkey).add(0, PriotiseClient);
			 }
		 }
		 
		 
		 boolean cont1nue = true; 
		 ArrayList<Person> movetoback = new ArrayList<Person>();
		 
		 
		 for (int i=0; i < queue.get(floorkey).size(); i++ ) {
			 if(currentCapacity < 4) {
				 
				 for(int li=0; li < inlift.size(); li++ ) {
					 if((inlift.get(li) instanceof Goggles && queue.get(floorkey).get(i) instanceof Mugtome) || (inlift.get(li) instanceof Mugtome && queue.get(floorkey).get(i) instanceof Goggles) ) {
						  Person moveDeveloper = queue.get(floorkey).get(i);
						 movetoback.add(moveDeveloper);
						 queue.get(floorkey).remove(moveDeveloper);
						 i--;
						 cont1nue = false;
						 this.LocalTick = 2; 	
						 break;	// exits loop if condition is met.
						
					 }
				 }
				 
				 // tests to see if anymore people exist on the floor. if they don't then the loop ends and the elevator closes its doors.
				 try {		
					 Tobeadded = queue.get(floorkey).get(i);
				 }
				 
				 catch (IndexOutOfBoundsException error) {
					 this.LocalTick +=2;
					 break;
				 }
				 
				 
				 
				 
				 
			 if(currentCapacity == 0 && Tobeadded instanceof Maintenance && cont1nue == true) {
				 inlift.add(Tobeadded);
				 
				 currentCapacity += inlift.get(i).weight;

			 		}
			 
			//adds ppl from the floor waiting list to the lift list
			 else if (! (Tobeadded instanceof Maintenance)  && cont1nue == true) {														
					 inlift.add(Tobeadded);		
					 currentCapacity += 1;
					
			 		}
		 
			//if there is not enough room for anyone else, i.e. someone enters lift before maintenance crew the for loop will break early 
			 else if (cont1nue == true) {									
				 if(i == queue.get(floorkey).size()) {break;} 			//if all people on the floor have been checked, then break the loop.
				 else {}												//else it will check the next person waiting.
				 removeDest(this.currentfloor);							// the floor is not called again for 2 ticks
				 this.LocalTick +=2; 									//increments LocalTick by 2 so the elevator is able to leave the floor
			 }		
	 		
			 }
		
			 cont1nue = true;	 
		 }
	
	leavedooropen = false;												//after people have been added the door can close on next tick
	 resting = false;													// if a person is added in then resting becomes false
	
	 queue.get(floorkey).addAll(movetoback);							// adds rival developers to back of the queue.
	 	}
	 
	 
	 for(Person p: inlift) { 											//removes people from the floor waiting list and adds them to the lift
		 System.out.print(p + " is added to lift \n");
		p.resetComplaintCount();
		 queue.get(floorkey).remove(p);	
	 }
	 
	
	
	 }
	 

	
	 return queue; 
	 
 }

//---------------------------------------------- Check in lift destinations ------------------------------------------------------------//	

 /**
 *  This method adds a destination for each person inside the lift,
 *  depending on the elevators direction.
 */
 
 protected void checkdestinationsinlift() {
	
	 if (inlift.size() > 0) {	
		for(int i=0; i < inlift.size(); i++ ) {
			if(goingup == true) {
				 if(inlift.get(i).getdestFloor() > currentfloor || destinations.size() == 0) {
					 destinations.add(inlift.get(i).getdestFloor());		
				 }
			 }	 
			
			else if (goingup == false) {
				if(inlift.get(i).getdestFloor() < currentfloor || destinations.size() == 0 ) {
					 destinations.add(inlift.get(i).getdestFloor());	
				}
			 }
			 System.out.println("People going to:  "+ inlift.get(i).getdestFloor());			
		}
	
	}
	
 }
 
 
//---------------------------------------------- Removing people from lift ------------------------------------------------------------//	
/**
 *  Method handles unloading passengers to a floor. Anyone who wants to leave is passed into a unloading ArrayList 
 *  and deleted from the lift.
 *  <pre></pre>
 *  This method also controls the doors staying open or signaling it to close on the next tick.
 */
 
 protected void unloadpassengers() {	
	 loading = true;		
	 
	  if(leavedooropen == true && inlift.size() > 0) {
	
	 				for(int i=0; i < inlift.size(); i++ ) {
	 						if (inlift.get(i).getdestFloor() == currentfloor) {
	 							System.out.println("Elevator unloading 1 passenger: " + inlift.get(i));
	 							unloading.add(inlift.get(i));
	 							updateQueue.put(7, unloading);
	 							inlift.remove(i);
	 							i--; 				//when a person is removed the array pushes the next element to 0, so we need to reset the counter
	 						}
	 				}
	 leavedooropen = false;
	 currentCapacity = inlift.size();

	  
	  
	  }		
	 
	 
	 if (inlift.size() == 0) {						// When the last person in the lift has left
	localtick2++;									//calls changes localtick2 to 1 so that the  close the doors ONLY AFTER PPL HAVE UNLOADED 
	 } 


	// CLOSES door in +1 tick after unloading ppl to floor 0. (bug patch)
	  else if (currentfloor == 0 && doors == true && resting == true  ) {		
		 leavedooropen = false;
	  }
	  
	  
 }


//------------------------------------------------ Getters / setters ------------------------------------------------------------------//	

 	protected TreeSet<Integer> getdestinaton() {			
		return this.destinations;
}
	
	
	
 	protected int getCurrentFloor() {
		return currentfloor;
	}
	
	
	
 	protected boolean getdoorStatus() {
		return doors;
	}
	
	
 	protected int getLocalTick() {
		return LocalTick;
	}
	
 	protected int getcapacity() {
		return currentCapacity;
	}

}
