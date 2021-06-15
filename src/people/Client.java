package people;

import java.util.Random;

/**
 *  Goggle is a subclass of developer. it inherits all the methods from developer, but has it's own constructor method as well as overwritten methods, which include:
 * <pre>
 * {@link #addprobability(boolean)} 
 * {@link #generateDestFloor()}
 * </pre>
 * @author Riyad Rahman
 * @see Person
 * @see Developer
 */
public class Client extends Person{
	/**
	 * This constructs a Client, whose parameters are defined in the building class. 
	 * 
	 * @param n : (Integer) name, sets the name of the object. In this class "Client" is used.
	 * @param wc : (Integer) work counter, is used to allow a client to generate random probabilities to leave the building.
	 * @param w : (Integer) weight, is the weight of a person. In this class it is set to "1".
	 * @param f : (Integer) destination floor, the floor which a person wants to go to.
	 * @param P : (Float) probability of changing floors, 
	 * @param Q : (Float) probability of client spawn,
	 * @param Cc : (Integer) Complaint counter, when a client has waited for 60 ticks, a complaint is made.
	 * @param r : (Random) Random object, whose seed is kept consistent throughout the simulation.
	 * @param crtflr : (Boolean) correct floor, when a client is on the correct floor it is used to begin determining the probability of a client leaving the building. 
	 * @param busicomplete : (Boolean) business status, used to leave the building once a client has finished their work.
	 * 
	 */
	
	public Client(String n,int wc, int w, int f, float P, float Q, int Cc,Random r, boolean crtflr, boolean busicomplete)  {
		super(n,wc,w,f,P,Q,Cc,r,crtflr,busicomplete);
	}
	
	
	
	
	

//----------------------------------------------------------client moving methods------------------------------------------------------------------------//
	
/**
 *  Manages probability of Client leaving the building
 * @param correctfloor - boolean which is set to true when a client reaches their destination floor
 */
	@Override
	public boolean addprobability(boolean correctfloor) {
		boolean genafloor = false;
		

		if(correctfloor == true) {		
			this.workcounter += 1;
			
			if(workcounter >= 60 && workcounter < 180) {		//	Business last for count: 60 to 180 ticks
				double leaveprob;
				leaveprob = randomobject.nextDouble();
				
				if (leaveprob <  0.009 ) {
					genafloor = true;
				}
	
			}
			
			else if (workcounter >=180) {  genafloor = true; } 
			
		}
	
		return genafloor;
	}
	
/**
 * Generates destination floor for client based on whether they are arriving or leaving.
 * @return destFloor - returns the destination floor which the client wants to go to.
 */
	
	@Override 
	public int generateDestFloor() {
	
		
	if(workcounter > 60) {
		
//if the client is already at his destination floor, then the q increment will start		
			if(destfloor == 0) {					
				 setatcorrectFloor(true);
			 }
			
			setBstatus(true);
			return destfloor = 0; 

	}
	
	else { 		//if the client has just spawned in, then the client will choose a floor 0 to 3 to go to, and stay for at least 60 ticks to 180 ticks
		destfloor = randomobject.nextInt((3 - 0) + 1) + 0;	
		return destfloor;
	}
	
	}
	
}
