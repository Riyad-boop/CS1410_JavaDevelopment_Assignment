package Main;
import java.util.Random;
//RIYAD RAHMAN1
import java.util.Scanner;
import Things.Building;

/**
 * Represents the simulation of the working day (8 hours). One tick represents in real life 10 seconds, Key functions include:
 * <pre>
 * {@link #main(String[])} 
 * {@link #readinputs()} 
 * {@link #mkBuilding(int, int, int, float, float, long)} 
 * {@link #tick()} 
 * </pre>
 * the building is also initialized here. the seed is passed via the building's constructor, to create a single instance of Random.
 * @author Riyad K Rahman
 * @version 5.0.0 
 */

public class Simulation {

	private static int currentTick = 0;															// one tick represents 10 in real life seconds 				
	private static final int maxTicks = 2880;													// 	(8 hours * 60 mins * 60 sec) / 10 ticks == 2880.    						
	private static long seed;																	// the seed for the random object. is kept consistently throughout the simulation.
	private static Building building;															// Building object variable
	private static float pArray[]= { (float) 0.001, (float) 0.002, (float) 0.003, (float) 0.004, (float) 0.005}; 	// P float array, holds every P value that is allowed to be used in the simulation
	private static float qArray[]= { (float) 0.002, (float) 0.004, (float) 0.006, (float) 0.008, (float) 0.01 };		// Q float array, holds every Q value that is allowed to be used in the simulation
	private static float p;																		// The chosen p value by the user, which determines probability of changing floors during the simulation 
	private static float q;																		// The chosen q value by the user, which determines probability of clients spawning during the simulation
	private static int numOfEmployees; 															// the number of employees spawned
	private static int numOfDevelopers; 															// the number of developers spawned
//----------------------------------------------------------main method------------------------------------------------------------------------//	
	/**
	 * Main method for Simulation class
	 * Creates random number from seed
	 * Creates the building using fields given
	 * Sets up ticks using maxTicks
	 * @param args - holds user inputs
	 */
	
	public static void main(String[] args) {
	
		readinputs();
		mkBuilding(currentTick, numOfEmployees,numOfDevelopers, p,q,seed);
		ticks(maxTicks);
		
	}

//----------------------------------------------------------Input methods------------------------------------------------------------------------//		
	
	/**
	 * Reads user input for seed number, p and q values
	 * @throws e  NumberFormatException - if a number format error, the program asks user again for the correct inputs. 
	 * @throws e2  IndexOutOfRange - if the user picks a value which is out of range of the P or Q array, the program asks user again for the correct inputs.
	 */ 
	
	private static void readinputs() {
		int nump=0;		// Used to choose a P value, form the float array pArray;
		int numq=0;		// Used to choose a Q value, form the float array qArray;
		boolean Error = true;		// used to loop king user inputs until there are no errors (error == false)
		Scanner scanner = new Scanner(System.in);
	
		  do {
		        try {
		        	try {
		        		
		        		System.out.println("Enter an integer number for the number of employees to be spawned: ");
		        		numOfEmployees	= Integer.parseInt(scanner.next());
			            
			            System.out.println("Enter an integer number for the number of developers to be spawned: ");
			            numOfDevelopers = Integer.parseInt(scanner.next());

		        		
			    		System.out.println("Input a seed: ");
			    		seed = (long) Integer.parseInt(scanner.next());	
			    		
			        	System.out.println("Enter integer 0-4 to determine probability of changing floors: ");
			            nump = Integer.parseInt(scanner.next());
	
			            System.out.println("Enter integer 0-4 to determine probability of Client spawn: ");
			            numq = Integer.parseInt(scanner.next());
	
			            p= pArray[nump];
		      			q= qArray[numq];	
			      			
		      			
			            Error = false;     
			            
	
			        	} 
			        	catch (NumberFormatException e) {		// catches NumberFormat exception and prints out error message 
			        		System.out.println("Error! number format");
			        		scanner.reset();
			        	}
		        }
		        catch (Exception e2) {		// catches IndexOutOfBounds exception and prints out error message 
		        	System.out.println("Error, both p and q value must be between 0 and 4");
		            scanner.reset();
		        }
		        
		    } while (Error);
	}
	
	

	
//----------------------------------------------------------Tick methods------------------------------------------------------------------------//	
	/**
	 * Reads user input
	 * Sets value of tick increment to user input
	 * @return increment - the number of ticks the simulation will tick by 
	 */
	
	private static int tickinput() {
		int increment = 0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Input time in ticks to increment the simulation by: ");
		increment = scan.nextInt();						//sets value increment to user input
	
		return increment;
	}
	
	/**
	 * Controls what the simulation does with each tick.
	 * Increments ticks after every iteration of a tick
	 * Outputs current tick to user
	 * @param maxTick - 2880 ticks. When the tick counter reaches this max tick the simulation ends
	 */
	
	private static void ticks(int maxTick) {
		while (currentTick <= maxTick) {
		int increment = tickinput();
			for (int i = 0; i < increment; i++) {
			
				if(currentTick < maxTick) {
					building.tick();			//calls building.tick() method per every tick
					currentTick++;	
					System.out.println("Tick: "+ currentTick);
				}
			
				else { 
					building.tick();			//calls building.tick() method at the end to display data
					System.out.println("The working day is over, the system will now terminate!");
					System.exit(0);	 //exits the simulation
					
				}	
			}										
		}
		
	}
	
	
//---------------------------------------------------Initialize Building-------------------------------------------------------------------------//			
	/**
	 * Initializes building
	 * @param t - current tick number
	 * @param rand - random object with consistent seed (user inputed seed)
	 * @param p - float value, affects probability of changing floors 
	 * @param q - float value, affects probability of client spawn 
	 */ 
	
	private static void mkBuilding(int t, int numE, int numD, float p,float q , long seed) {
		building = new Building(t,numE,numD,p,q ,seed);
	}
	

//-------------------------------------------------------------------------------------------------------------------------------------------------//	
}



