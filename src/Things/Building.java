package Things;

import Things.Floor;
import Things.LIFT;
import people.Client;
import people.Developer;
import people.Employee;
import people.Maintenance;
import people.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import people.Goggles;
import people.Mugtome;

/**
 * Represents the building class which contains an elevator and 7 floors. this class also handles calculating average wait times, and processing complaints. Key functions include: 
 *  <pre>
 *  {@link #CreateFloors(int)} 
 *  {@link #CreatePeople(int, int)} 
 *  {@link #Generateclients(Random)} 
 *  {@link #SpawnClients()}
 *  {@link #Generatecrew()} 
 *  {@link #Complaintchecker(HashMap)} 
 *  {@link #calculateAverageWait()} 
 *  {@link #tick()} 
 *   </pre>
 * @author Riyad K Rahman.
 * @see LIFT
 * @see Floor
 */

public class Building {
	
	protected ArrayList<Integer> totalAverageWait = new ArrayList<Integer>();									// An integer array (totalAverageWait), is used to contain the waiting times for everyone who used the elevator. 
	protected ArrayList<Person> waiting = new ArrayList<Person>();										// Contains the people waiting for the elevator on each floor 
	protected HashMap<Integer,ArrayList<Person>> Queue = new HashMap<Integer,ArrayList<Person>>();		//Contains a queue of waiting lists for each floor.
	
	protected int tick;						// current tick of the simulation
	private int ElevatorFloor;				// current floor which the elevator is on 
	private LIFT elevator = new LIFT();		// LIFT object variable named: elevator
	protected Floor floors[] = new Floor[7];	// Floor object array variable named: floors[] 
	protected final float P; 						// P, determines probability of changing floors during the simulation 
	protected final float Q;							// Q, determines probability of clients spawning during the simulation
	protected double arrivalprobability;		// Holds the probability of which a Maintenance crew spawns 
	protected final Random rand;						// Random object variable named: rand. (Seed is the same from Simulation)
	
	protected String complaints = "\n \n Complaints made:  \n";	//String holds all the information on the complaints throughout the simulation.
	protected int complaintcounter;						// counter which holds the number of complaints made throughout the simulation.
	protected int numClients;								// The number the clients spawned throughout the simulation. 
	protected int AverageWaitingTime;						// the average number of waiting time across all people
	protected int ClientComplaint;							// the number of client complaints made trhoughout the entire working day
	protected final long bseed;									// seed used to create a single instance of Random. enusres that results are repeatable.
	protected ArrayList <Integer> ClientSpawnTick = new ArrayList<Integer>();
	protected int counter = 0;
	/**
	 *  This constructs a building with the tick (t), Random object(r) and p & q values.
	 *  The constructor also creates 7 floors and user inputed number of people using 'CreateFloors(7)' and 'CreatePeople(numE,numD)'
	 * @param t - current tick
	 * @param r - random object passed from Simulation class
	 * @param p - float value which determines the probability of changing floors 
	 * @param q - float value which determines the probability of clients spawning 
	 * @param numE - number of Employees to be spawned.
	 * @param numD - number of Developers to be spawned.
	 */
	
	public Building(int t , int numE, int numD, float p, float q, long seed) {
		this.tick = t;
		this.rand = singleinstanceofRandom();
		this.P=p;
		this.Q=q;
		this.bseed = seed;
		CreateFloors(7);					//Creates 7 floors.
		CreatePeople(numE,numD);			//Creates 20 people. (10 Employees, 10 Developers)
		Generateclients(singleinstanceofRandom());
		Queue.put(7, waiting); 						// create the empty placeholder for unloading passengers to a floor
	}
	

	
	protected Random singleinstanceofRandom() {
		return new Random(this.bseed);
	}	
	
	
//----------------------------------------------------------Create floors------------------------------------------------------------------------//		
	/**
	 * Initializes/creates the floors
	 * @param f - number of floors to be made.
	 * 
	 */
	protected void CreateFloors(int f) {
			for(int i = 0; i < f; i++) {
				floors[i] = new Floor(i);
		}
			System.out.println("Created "+ f +" floors");
}
	

//----------------------------------------------------------Create people------------------------------------------------------------------------//	
	/**
	 * Creates people and generates a random starting floor for each person
	 * Created people are added to ground floor
	 * @param total - number of people to be made.
	 */
	protected void CreatePeople(int numberE, int numberD) {
		int total = numberE + numberD;
		int option = 0;
			for(int i=0; i < total; i++) {
				if(i < numberE) { option = 1; }
				else if(i >= numberE && i < (numberE + (numberD/2)) ) { option = 2; }
				else { option = 3; } 
				floors[0].AddPersonToFloor(Persontype(0,option, this.P , this.Q));
				
				floors[0].Persons.get(i).generateDestFloor();			//generates random floor for each person
				System.out.println("Destination: " + floors[0].Persons.get(i).getdestFloor() + floors[0].Persons.get(i).getname());	//prints generated floor for each person
		}
			System.out.println("Created People: "+ floors[0].getlistofppl() + " on floor 0" );
			
	}
		

	/**
	 * Creates employee or developer based on option parameter
	 * @param f	- destination floor is set to 0 by default.
	 * @param option - used as a counter to ensure exactly 10 employees are created 
	 * @return new Employee - name,counter,weight,destination,P,Q,complaint counter,at_correct_floor,business_status 
	 * @return new Developer - name,counter,weight,destination,P,Q,complaint counter,at_correct_floor,business_status 
	 */
	protected Person Persontype(int f, int option, float Pval , float Qval) {
			
		if (option == 1) { return new Employee("Employee",0,1,f,Pval,Qval,0,rand,false,false); }						//makes employees
		else if (option == 2 ) {return new Goggles("Goggle Developer",0,1,f,P,Q,0,rand,false,false); }					// makes Goggle Developers 
		else { return new Mugtome("Mugtome Developer",0,1,f,P,Q,0,rand,false,false);}									// makes  Mugtome Developers 				
							//name,counter,weight,destination,P,Q,complaint counter,at_correct_floor,business_status
		}					
						
		

	
	
	
	
//----------------------------------------------------------Tick method ------------------------------------------------------------------------//	
/**
 * Controls floor and lift ticks, and is used to call the functions: Generate_crew() , Generate_clients() , Complaint_checker(Queue);
* <pre>
* if nobody is getting off the lift, puts an empty placeholder for Queue [Queue.get(7)]
* Queue.get(7) is used in LIFT class and floors class for unloading passengers to a floor.
* Gets current floor of lift every tick
* Updates queue every tick
* Outputs number of complaints and clients spawned at end of day to user
*/
	
public void tick() {

	
	//if tick reaches 2880 (end of day), print out all the complaints that were made and exit method.
	if(this.tick == 2880) {
		calculateAverageWait();
		return;
	}
	
	if (Queue.get(7) == null) {
		Queue.put(7, waiting); 						// create the empty placeholder for unloading passengers to a floor
	}

	ElevatorFloor = elevator.getCurrentFloor();		//gets elevator floor every tick

	//tick floors & updates the queue 
	for(int i = 0; i < 7; i++) {		
		Queue = floors[i].tick(ElevatorFloor, Queue);	
		
	}
	
	//tick lift & updates the queue 
	Queue = elevator.ticklift(Queue);			 

	try {
		//if there are people in the unloading list , they are unloaded to their floor.
		if(Queue.get(7).size() > 0) {
				floors[ElevatorFloor].QueueToFloor(Queue.get(7));
				Queue.get(7).clear();
			}
	}
	catch(java.lang.NullPointerException exception) {
		
	}
	
	Generatecrew();
	SpawnClients();
	Complaintchecker(Queue);

	this.tick++;

	}





/**
 *  This method displays and calculates the average wait time throughout the simulation and prints data about the simulation.
 */
protected void calculateAverageWait() {

	System.out.print(complaints);
	System.out.print("\n total number of complaints: "  + complaintcounter);
	System.out.print("\n number of clients complaints: "  + ClientComplaint + "\n" );
	System.out.print(" number of clients spawned: "  + numClients + "\n" );
	
	
	totalAverageWait.addAll(floors[0].getTimes());
	for(int i = 0; i < 7; i++) {
		for(Person p: floors[i].Persons) {
			totalAverageWait.add(p.totalWaitTime);
		}
		try {
			ArrayList<Person> waitingPeople = new ArrayList<Person>();	
			waitingPeople.addAll(Queue.get(i));
			for(Person p: waitingPeople) {
				totalAverageWait.add(p.totalWaitTime);
			}
		}
		
		catch(java.lang.NullPointerException exception) {
		}
}

	double averages;
	
	totalAverageWait.forEach((time) -> {
		this.AverageWaitingTime += time;
	});
	
	averages = this.AverageWaitingTime / totalAverageWait.size();
	System.out.print(" Total number of people in simulation:  "  + totalAverageWait.size() + "\n" );  
	System.out.print("\n Total List of waiting times:  "  + totalAverageWait.toString() + "\n" );
	System.out.print(" Mean average waiting time: "  + averages + "\n \n" );
		
}


/**
 * Generates maintenance crew at ground floor every 200 ticks or at a rate of (0.005) per tick.
 * <pre> 
 * When the arrival probability reaches 1, a maintenance crew is generated.
 * after generating a crew, the probability is reset to 0.
 */

protected void Generatecrew() {		
	
	if(arrivalprobability >= 1) {
		arrivalprobability = 0;	//resets arrival probability
		
	floors[0].AddPersonToFloor(new Maintenance("Maintenance",0,4,6,P,Q,0,rand,false,false));
	System.out.println(" \n --------------------------------------------------------------------------------------------------- " );
	System.out.println(" \n 1 Maintenance crew has arrived on floor 0! \n " );
	System.out.println(" --------------------------------------------------------------------------------------------------- \n " );
	}	
	
	
	else {
		arrivalprobability += 0.005;
		
	}
	
}

/**
 * * Generates Client at ground floor, based on probability q.
 * <pre>  
 * When the arrival probability (arrival prob) reaches 1, a client is generated.
 * after generating a client, number of clients (numClients) is incremented.
 */

protected void Generateclients(Random rando) {
	double arrivalprob;
	
	while (counter < 2880) {
	arrivalprob = rando.nextDouble();
	counter++;
		if (arrivalprob < this.Q) {
			ClientSpawnTick.add(counter);
		}
	}

}


protected void SpawnClients() {
	for (int i = 0; i < ClientSpawnTick.size(); i++) {
		if(this.tick == ClientSpawnTick.get(i)) {
			Person localClient;													//Creates a variable localClient to store the new client in.
			localClient = new Client("Client",0,1,0,P,Q,0,rand,false,false);
			localClient.generateDestFloor();
			floors[0].AddPersonToFloor(localClient);							//moves local client to ground floor
			System.out.println(" \n --------------------------------------------------------------------------------------------------- " );
			System.out.println(" \n 1 Client has arrived on floor 0! \n " );
			System.out.println(" --------------------------------------------------------------------------------------------------- \n " );
			numClients++;
		}
	}
	
	
	
}

/**
 * Checks complaints (Such as who made the complaint and what tick).
 * then outputs complaints to user on the same tick if a complaint was made.
 * <pre>
 * if a complaint is made, then the complaint counter is reset for the person who made the complaint.
 * and a local total number of complaints is incremented
 * </pre>
 * @param queues - holds all the waiting lists for each floor, 
 * every person in a waiting list has their complaint count incremented, until it reaches 60.
 */

protected void Complaintchecker(HashMap<Integer,ArrayList<Person>> queues ) {
	queues.forEach((key,list) -> {
		
		 if(list.size() != 0 && key != 7) {
			 for(int i = 0; i < list.size(); i++) {
				 list.get(i).incComplaintCount();					//increments complaint count 
				 list.get(i).totalWaitTime++;						
				 if(list.get(i).getComplaintCount() > 60) {			//when complaint count is above 60 a complaint is made and the counter is reset.
					System.out.print("Complaint made by " + list.get(i).name + " on floor: " + key + "\n" );
					 complaints += (" Complaint made by " + list.get(i).name + " on floor: " + key + " at tick:" + (tick+1) + "\n" );
					 complaintcounter += 1;							//increments local number of complaints in the building
					 list.get(i).resetComplaintCount();				//complaint count reset. 
					 if(list.get(i) instanceof Client) {
						 this.ClientComplaint++;
							Person AngryClient = list.get(i);
							 list.get(i).setdestFloor(0);
							 list.get(i).setBstatus(true);
							 list.remove(AngryClient);
							 floors[key].Persons.add(AngryClient);		//adding client to floor. if they are on floor 0 they will be removed. if not then they will wait for the lift so that they can leave the building.
						 }
				 }
			 }
		 } 
	});
}

}	
		
